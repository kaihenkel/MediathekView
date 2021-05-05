package mediathek.client.desktop.javafx;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import mediathek.client.desktop.config.CommandLineConfig;
import mediathek.util.daten.Daten;
import mediathek.client.desktop.javafx.filmlist.FilmListAgeLabel;
import mediathek.client.desktop.javafx.filmlist.FilmListInfoPane;
import mediathek.util.messages.info.filmlist.FilmListReadCompleteEvent;
import mediathek.util.messages.info.filmlist.FilmListReadProgressEvent;
import mediathek.util.messages.info.filmlist.FilmListReadStartEvent;
import mediathek.util.tools.MessageBus;
import net.engio.mbassy.listener.Handler;
import org.controlsfx.control.StatusBar;

public class StatusBarController {
    private final Label progressLabel = new Label("");
    private final ProgressBar progressBar = new ProgressBar();
    /**
     * The new javafx based status bar
     */
    private final StatusBar statusBar = new StatusBar();
    private final FilmListInfoPane filmListInfoPane;
    private final GarbageCollectionButton btnGc = new GarbageCollectionButton();
    private Pane progressPane;

    public StatusBarController(Daten daten) {
        filmListInfoPane = new FilmListInfoPane(daten);

        MessageBus.getMessageBus().subscribe(this);

        createProgressPane();
    }

    @Handler
    private void handleFilmListReadStart(FilmListReadStartEvent event) {
        addProgressItems();
        if (CommandLineConfig.isDebugModeEnabled()) {
            Platform.runLater(() -> statusBar.setText(event.getUrl()));
        }
    }

    @Handler
    private void handleFilmListReadProgress(FilmListReadProgressEvent event) {
        Platform.runLater(() -> {
            if (!progressBar.isVisible()) {
                progressBar.setVisible(true);
            }

            if (event.getMax() == 0 || event.getProgress() == event.getMax()) {
                progressBar.setProgress(-1d);
            } else {
                final double max = event.getMax();
                final double progress = event.getProgress();

                progressBar.setProgress(progress / max);
            }
            progressLabel.setText(event.getText());
        });
    }

    @Handler
    private void handleFilListReadComplete(FilmListReadCompleteEvent event) {
        Platform.runLater(() -> progressBar.setProgress(0d));
        removeProgressItems();
        if (CommandLineConfig.isDebugModeEnabled()) {
            Platform.runLater(() -> statusBar.setText(""));
        }

    }

    public FilmListAgeLabel getFilmlistAgeLabel() {
        return filmListInfoPane.getFilmListAgeLabel();
    }

    public StatusBar getStatusBar() {
        return statusBar;
    }

    private void createProgressPane() {
        HBox hb = new HBox();
        hb.setSpacing(5d);
        hb.setMinWidth(Region.USE_PREF_SIZE);
        hb.getChildren().addAll(new VerticalSeparator(),
                new CenteredBorderPane(progressLabel),
                new CenteredBorderPane(progressBar)
        );

        progressPane = hb;
    }


    private void addProgressItems() {
        Platform.runLater(() -> {
            ObservableList<Node> rightItems = statusBar.getRightItems();
            //fix strange exception that duplicate was added...
            if (!rightItems.contains(progressPane))
                rightItems.add(progressPane);

        });
    }

    private void removeProgressItems() {
        Platform.runLater(() -> {
            ObservableList<Node> rightItems = statusBar.getRightItems();
            rightItems.remove(progressPane);
        });
    }

    private void setupLeftPane() {
        ObservableList<Node> leftItems = statusBar.getLeftItems();

        if (CommandLineConfig.isDebugModeEnabled()) {
            leftItems.add(btnGc);
            leftItems.add(new VerticalSeparator());
        }
    }

    private void setupRightPane() {
        statusBar.getRightItems().add(filmListInfoPane);
    }

    public StatusBar createStatusBar() {
        //reset text
        statusBar.setText("");

        setupLeftPane();
        setupRightPane();

        return statusBar;
    }
}
