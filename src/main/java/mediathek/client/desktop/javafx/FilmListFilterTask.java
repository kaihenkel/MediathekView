package mediathek.client.desktop.javafx;

import javafx.concurrent.Task;
import mediathek.util.daten.Daten;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FilmListFilterTask extends Task<Void> {
    private final Daten daten = Daten.getInstance();
    private static final Logger logger = LogManager.getLogger(FilmListFilterTask.class);

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

        logger.trace("FilmListFilterTask finished");

        return null;
    }
}
