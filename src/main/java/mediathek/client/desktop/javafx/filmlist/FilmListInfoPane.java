package mediathek.client.desktop.javafx.filmlist;

import javafx.application.Platform;
import javafx.scene.layout.HBox;
import mediathek.util.daten.Daten;
import mediathek.client.desktop.javafx.CenteredBorderPane;
import mediathek.client.desktop.javafx.VerticalSeparator;
import mediathek.util.messages.info.filmlist.FilmListReadCompleteEvent;
import mediathek.util.messages.info.filmlist.FilmListReadStartEvent;
import mediathek.util.tools.MessageBus;
import net.engio.mbassy.listener.Handler;

import javax.swing.*;

/**
 * Pane which will display common information about the current filmlist.
 * Also handles updating the subcomponents based on a TimerEvent.
 */
public class FilmListInfoPane extends HBox {
    private final FilmListCreationDateLabel filmListCreationDateLabel;
    private final FilmListAgeLabel filmListAgeLabel;

    public FilmListInfoPane(Daten daten) {
        super();
        setSpacing(4d);
        MessageBus.getMessageBus().subscribe(this);

        filmListCreationDateLabel = new FilmListCreationDateLabel(daten);

        filmListAgeLabel = new FilmListAgeLabel();
        getChildren().addAll(new CenteredBorderPane(filmListCreationDateLabel),
                new VerticalSeparator(),
                new CenteredBorderPane(filmListAgeLabel));
    }

    public FilmListAgeLabel getFilmListAgeLabel() {
        return filmListAgeLabel;
    }

    @Handler
    private void handleFilmListReadStart(FilmListReadStartEvent event) {
        Platform.runLater(() -> setVisible(false));
    }

    @Handler
    private void handleFilmListReadComplete(FilmListReadCompleteEvent event) {
        Platform.runLater(() -> {
            filmListCreationDateLabel.computeCreationDate();
            setVisible(true);
        });
    }
}
