package mediathek.client.desktop.javafx;

import javafx.concurrent.Task;
import mediathek.util.mv.Daten;
import mediathek.util.tools.FilmListUpdateType;
import mediathek.util.tools.GuiFunktionen;

public class FilmListNetworkReaderTask extends Task<Void> {

    @Override
    protected Void call() {
        final Daten daten = Daten.getInstance();

        updateProgress(-1, 4);
        updateMessage("Prüfe Alter der Filmliste");

        if (GuiFunktionen.getFilmListUpdateType() == FilmListUpdateType.AUTOMATIC && daten.getListeFilme().needsUpdate()) {
            updateMessage("Lade Filmliste Netzwerk");
            daten.getFilmeLaden().loadFilmlist("", true);
        }

        return null;
    }
}
