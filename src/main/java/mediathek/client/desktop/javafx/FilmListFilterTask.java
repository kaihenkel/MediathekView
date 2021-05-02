package mediathek.client.desktop.javafx;

import javafx.concurrent.Task;
import mediathek.util.daten.Daten;
import mediathek.server.filmeSuchen.ListenerFilmeLadenEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class FilmListFilterTask extends Task<Void> {
    private final Daten daten = Daten.getInstance();
    private final boolean submitEvent;
    private static final Logger logger = LogManager.getLogger(FilmListFilterTask.class);

    public FilmListFilterTask(boolean submitEvent) {
        this.submitEvent = submitEvent;
    }

    @Override
    protected Void call() {
        logger.trace("FilmListFilterTask started");

        updateMessage("Themen suchen");
        updateProgress(-1, 4);
        daten.getListeFilme().fillSenderList();

        updateMessage("Abos eintragen");
        updateProgress(-1, 4);
        daten.getListeAbo().setAboFuerFilm(daten.getListeFilme(), false);

        updateMessage("Alle Filter anwenden");
        updateProgress(-1, 4);
        daten.getListeBlacklist().filterListe();

        SwingUtilities.invokeLater(() -> daten.getFilmeLaden().notifyFertig(new ListenerFilmeLadenEvent("", "", 100, 100, false)));

        logger.trace("FilmListFilterTask finished");

        return null;
    }
}
