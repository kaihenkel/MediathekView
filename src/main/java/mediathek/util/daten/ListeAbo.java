/*
 *    MediathekView
 *    Copyright (C) 2008   W. Xaver
 *    W.Xaver[at]googlemail.com
 *    http://zdfmediathk.sourceforge.net/
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package mediathek.util.daten;

import com.google.common.base.Stopwatch;
import mediathek.util.daten.Daten;
import mediathek.util.config.MVConfig;
import mediathek.util.daten.abo.DatenAbo;
import mediathek.util.daten.abo.FilmLengthState;
import mediathek.client.desktop.gui.dialog.DialogEditAbo;
import mediathek.client.desktop.gui.messages.AboListChangedEvent;
import mediathek.client.desktop.gui.mainwindow.MediathekGui;
import mediathek.util.tools.*;
import mediathek.util.tools.FilenameUtils;
import mediathek.util.tools.GermanStringSorter;
import mediathek.util.tools.MVMessageDialog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class ListeAbo extends LinkedList<DatenAbo> {
    private static final String[] LEER = {""};
    private static final Logger logger = LogManager.getLogger(ListeAbo.class);
    private final Daten daten;
    private int nr;

    public ListeAbo(Daten ddaten) {
        daten = ddaten;
    }

    public void addAbo(String aboName) {
        addAbo(aboName, "", "", "");
    }

    public void addAbo(String aboname, String filmSender, String filmThema, String filmTitel) {
        int min;
        try {
            min = Integer.parseInt(MVConfig.get(MVConfig.Configs.SYSTEM_ABO_MIN_SIZE));
        } catch (Exception ex) {
            min = 0;
            MVConfig.add(MVConfig.Configs.SYSTEM_ABO_MIN_SIZE, "0");
        }

        addAbo(filmSender, filmThema, filmTitel, "", "", min, true, aboname);
    }

    public void addAbo(String filmSender, String filmThema, String filmTitel, String filmThemaTitel, String irgendwo, int mindestdauer, boolean min, String namePfad) {
        //abo anlegen, oder false wenns schon existiert
        namePfad = FilenameUtils.replaceLeerDateiname(namePfad, false /*nur ein Ordner*/,
                Boolean.parseBoolean(MVConfig.get(MVConfig.Configs.SYSTEM_USE_REPLACETABLE)),
                Boolean.parseBoolean(MVConfig.get(MVConfig.Configs.SYSTEM_ONLY_ASCII)));

        DatenAbo datenAbo = new DatenAbo();
        datenAbo.setName(namePfad);
        datenAbo.setSender(filmSender);
        datenAbo.setThema(filmThema);
        datenAbo.setTitle(filmTitel);
        datenAbo.setThemaTitel(filmThemaTitel);
        datenAbo.setIrgendwo(irgendwo);
        datenAbo.setMindestDauerMinuten(mindestdauer);
        FilmLengthState state;
        if (min)
            state = FilmLengthState.MINIMUM;
        else
            state = FilmLengthState.MAXIMUM;
        datenAbo.setFilmLengthState(state);
        datenAbo.setZielpfad(namePfad);
        datenAbo.setPsetName("");

        DialogEditAbo dialogEditAbo = new DialogEditAbo(MediathekGui.ui(), datenAbo, false /*onlyOne*/);
        dialogEditAbo.setTitle("Neues Abo anlegen");
        dialogEditAbo.setVisible(true);
        if (dialogEditAbo.successful()) {
            if (!aboExistiertBereits(datenAbo)) {
                MVConfig.add(MVConfig.Configs.SYSTEM_ABO_MIN_SIZE, Integer.toString(datenAbo.getMindestDauerMinuten())); // als Vorgabe merken
                addAbo(datenAbo);
                aenderungMelden();
                sort();
            } else {
                MVMessageDialog.showMessageDialog(null, "Abo existiert bereits", "Abo anlegen", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void addAbo(DatenAbo datenAbo) {
        // die Änderung an der Liste wird nicht gemeldet!!
        // für das Lesen der Konfig-Datei beim Programmstart
        ++nr;
        datenAbo.setNr(nr);
        if (datenAbo.getName().isEmpty()) {
            // Downloads ohne "Aboname" sind manuelle Downloads
            datenAbo.setName("Abo_" + nr);
        }

        add(datenAbo);
    }

    public void aboLoeschen(@NotNull DatenAbo abo) {
        remove(abo);
        aenderungMelden();
    }

    /**
     * Get the number of abos which are active and used.
     *
     * @return num of used abos
     */
    public long activeAbos() {
        return stream().filter(DatenAbo::isActive).count();
    }

    /**
     * Get the number of abos which are created but offline.
     *
     * @return number of abos which are offline
     */
    public long inactiveAbos() {
        return stream().filter(abo -> !abo.isActive()).count();
    }

    public void aenderungMelden() {
        // Filmliste anpassen
        setAboFuerFilm(daten.getListeFilme(), true);
        MessageBus.getMessageBus().publishAsync(new AboListChangedEvent());
    }

    public void sort() {
        Collections.sort(this);
    }

    public ArrayList<String> getPfade() {
        // liefert eine Array mit allen Pfaden
        ArrayList<String> pfade = new ArrayList<>();
        for (DatenAbo abo : this) {
            final String zielpfad = abo.getZielpfad();
            if (!pfade.contains(zielpfad)) {
                pfade.add(zielpfad);
            }
        }

        pfade.sort(GermanStringSorter.getInstance());
        return pfade;
    }

    private boolean aboExistiertBereits(DatenAbo abo) {
        // true wenn es das Abo schon gibt
        for (DatenAbo datenAbo : this) {
            if (Filter.aboExistiertBereits(datenAbo, abo)) {
                return true;
            }
        }
        return false;
    }

    public DatenAbo getAboFuerFilm_schnell(DatenFilm film, boolean laengePruefen) {
        // da wird nur in der Filmliste geschaut, ob in "DatenFilm" ein Abo eingetragen ist
        // geht schneller, "getAboFuerFilm" muss aber vorher schon gelaufen sein!!
        final var abo = film.getAbo();
        if (abo == null) {
            return null;
        } else {
            if (laengePruefen) {
                if (!Filter.laengePruefen(abo.getMindestDauerMinuten(), film.getFilmLength(),
                        abo.getFilmLengthState() == FilmLengthState.MINIMUM)) {
                    return null;
                }
            }
            return abo;
        }
    }

    private void deleteAboInFilm(DatenFilm film) {
        // für jeden Film Abo löschen
        film.setAbo(null);
    }

    private void createAbo(DatenAbo abo) {
        if (abo.getTitle().isEmpty()) {
            abo.setTitelFilterPattern(LEER);
        } else {
            abo.setTitelFilterPattern(Filter.isPattern(abo.getTitle())
                    ? new String[]{abo.getTitle()} : abo.getTitle().toLowerCase().split(","));
        }
        if (abo.getThemaTitel().isEmpty()) {
            abo.setThemaFilterPattern(LEER);
        } else {
            abo.setThemaFilterPattern(Filter.isPattern(abo.getThemaTitel())
                    ? new String[]{abo.getThemaTitel()} : abo.getThemaTitel().toLowerCase().split(","));
        }
        if (abo.getIrgendwo().isEmpty()) {
            abo.setIrgendwoFilterPattern(LEER);
        } else {
            abo.setIrgendwoFilterPattern(Filter.isPattern(abo.getIrgendwo())
                    ? new String[]{abo.getIrgendwo()} : abo.getIrgendwo().toLowerCase().split(","));
        }
    }

    /**
     * Assign found abo to the film objects.
     * Time-intensive procedure!
     *
     * @param film assignee
     */
    private void assignAboToFilm(@NotNull DatenFilm film) {
        stream().filter(abo
                -> Filter.filterAufFilmPruefen(abo.getSender(), abo.getThema(),
                abo.getTitelFilterPattern(),
                abo.getThemaFilterPattern(),
                abo.getIrgendwoFilterPattern(),
                film))
                .findAny().
                ifPresentOrElse(film::setAbo, () -> deleteAboInFilm(film));
    }

    public void setAboFuerFilm(ListeFilme listeFilme, boolean aboLoeschen) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        // hier wird tatsächlich für jeden Film die Liste der Abos durchsucht
        // braucht länger

        if (this.isEmpty() && aboLoeschen) {
            listeFilme.parallelStream().forEach(this::deleteAboInFilm);
            return;
        }

        // leere Abos löschen, die sind Fehler
        this.stream().filter(DatenAbo::isInvalid).forEach(this::remove);

        // und jetzt erstellen
        forEach(this::createAbo);

        // das kostet die Zeit!!
        listeFilme.parallelStream().forEach(this::assignAboToFilm);

        // und jetzt wieder löschen
        forEach(datenAbo -> {
            datenAbo.setTitelFilterPattern(LEER);
            datenAbo.setThemaFilterPattern(LEER);
            datenAbo.setIrgendwoFilterPattern(LEER);
        });

        stopwatch.stop();
        logger.debug("setAboFuerFilm: {}", stopwatch);
    }
}
