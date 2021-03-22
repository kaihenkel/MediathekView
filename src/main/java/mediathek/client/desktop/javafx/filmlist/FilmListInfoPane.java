package mediathek.client.desktop.javafx.filmlist;

import javafx.application.Platform;
import javafx.scene.layout.HBox;
import mediathek.util.daten.Daten;
import mediathek.server.filmeSuchen.ListenerFilmeLaden;
import mediathek.server.filmeSuchen.ListenerFilmeLadenEvent;
import mediathek.client.desktop.javafx.CenteredBorderPane;
import mediathek.client.desktop.javafx.VerticalSeparator;
import mediathek.util.tools.MessageBus;

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

        SwingUtilities.invokeLater(() -> daten.getFilmeLaden().addAdListener(new ListenerFilmeLaden() {
            @Override
            public void start(ListenerFilmeLadenEvent event) {
                MessageBus.getMessageBus().unsubscribe(this);
                Platform.runLater(() -> setVisible(false));
            }

            @Override
            public void fertig(ListenerFilmeLadenEvent event) {
                MessageBus.getMessageBus().subscribe(this);
                Platform.runLater(() -> {
                    filmListCreationDateLabel.computeCreationDate();
                    setVisible(true);
                });
            }
        }));

        filmListCreationDateLabel = new FilmListCreationDateLabel(daten);

        filmListAgeLabel = new FilmListAgeLabel();
        getChildren().addAll(new CenteredBorderPane(filmListCreationDateLabel),
                new VerticalSeparator(),
                new CenteredBorderPane(filmListAgeLabel));
    }

    public FilmListAgeLabel getFilmListAgeLabel() {
        return filmListAgeLabel;
    }
}
