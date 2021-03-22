package mediathek.client.desktop.javafx;

import javafx.concurrent.Task;
import mediathek.util.mv.Daten;
import mediathek.server.filmlisten.reader.FilmListReader;
import mediathek.client.desktop.gui.messages.FilmListReadStartEvent;
import mediathek.util.config.ApplicationConfiguration;
import mediathek.tool.MessageBus;

public class FilmListReaderTask extends Task<Void> {
    private final Daten daten;

    public FilmListReaderTask() {
        super();
        daten = Daten.getInstance();
    }

    @Override
    protected Void call() {
        MessageBus.getMessageBus().publishAsync(new FilmListReadStartEvent());

        updateProgress(-1, 4);
        updateMessage("Lese lokale Filmliste");
        try (FilmListReader reader = new FilmListReader()) {
            final int num_days = ApplicationConfiguration.getConfiguration().getInt(ApplicationConfiguration.FilmList.LOAD_NUM_DAYS,0);
            reader.readFilmListe(Daten.getDateiFilmliste(), daten.getListeFilme(), num_days);
        }

        return null;
    }
}
