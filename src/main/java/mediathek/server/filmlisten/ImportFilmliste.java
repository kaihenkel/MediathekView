/*
 * MediathekView
 * Copyright (C) 2008 W. Xaver
 * W.Xaver[at]googlemail.com
 * http://zdfmediathk.sourceforge.net/
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package mediathek.server.filmlisten;

import mediathek.server.filmlisten.reader.FilmListReader;
import mediathek.util.daten.ListeFilme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImportFilmliste {

    private static final Logger logger = LogManager.getLogger(ImportFilmliste.class);
    private final FilmListReader msFilmListReader;

    public ImportFilmliste() {
        msFilmListReader = new FilmListReader();
    }

    /**
     * Filmeliste importieren, URL automatisch wählen
     */
    public void importFromUrl(ListeFilme listeFilme, ListeFilme listeFilmeDiff, int days) {
        Thread importThread = new FilmeImportierenAutoThread(listeFilme, listeFilmeDiff, days,this::urlLaden);
        importThread.start();
    }

    /**
     * Filmeliste importieren, mit fester URL/Pfad
     */
    public void importFromFile(String pfad, ListeFilme listeFilme, int days) {
        Thread t = new Thread(() -> urlLaden(pfad, listeFilme, days));
        t.start();
    }

    private boolean urlLaden(String dateiUrl, ListeFilme listeFilme, int days) {
        try {
            if (!dateiUrl.isEmpty()) {
                logger.trace("Filmliste laden von: {}", dateiUrl);
                return msFilmListReader.readFilmListe(dateiUrl, listeFilme, days);
            }
        } catch (Exception ex) {
            logger.error("urlLaden", ex);
        }
        return false;
    }
}
