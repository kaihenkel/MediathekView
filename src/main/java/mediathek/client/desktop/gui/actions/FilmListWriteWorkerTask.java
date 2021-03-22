package mediathek.client.desktop.gui.actions;

import javafx.concurrent.Task;
import mediathek.util.mv.Daten;
import mediathek.server.filmlisten.writer.FilmListWriter;

public class FilmListWriteWorkerTask extends Task<Void> {

    private final Daten daten;

    public FilmListWriteWorkerTask(Daten daten) {
        super();
        this.daten = daten;
    }

    @Override
    protected Void call() {
        FilmListWriter writer = new FilmListWriter(false);
        updateMessage("Schreibe Filmliste");
        updateProgress(0d, 1d);
        writer.writeFilmList(Daten.getDateiFilmliste(),
                daten.getListeFilme(),
                prog -> updateProgress(prog, 1d));

        return null;
    }
}
