package mediathek.javafx.filmtab;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import mediathek.util.mv.Daten;
import mediathek.client.desktop.gui.messages.DownloadInfoUpdateAvailableEvent;
import mediathek.client.desktop.gui.messages.TimerEvent;
import mediathek.client.desktop.gui.messages.UpdateStatusBarLeftDisplayEvent;
import mediathek.client.desktop.gui.tabs.tab_film.GuiFilme;
import mediathek.javafx.CenteredBorderPane;
import mediathek.javafx.VerticalSeparator;
import mediathek.tool.MessageBus;
import net.engio.mbassy.listener.Handler;

public class FilmTabInfoPane extends HBox {
    private final FilmTabDownloadInformationLabel downloadInformationLabel;
    private final FilmInfoLabel filmInfoLabel;

    public FilmTabInfoPane(Daten daten, GuiFilme tabFilme) {
        super();
        downloadInformationLabel = new FilmTabDownloadInformationLabel(daten);
        filmInfoLabel = new FilmInfoLabel(daten,tabFilme);

        getChildren().addAll(new CenteredBorderPane(filmInfoLabel),
                new VerticalSeparator(),
                new CenteredBorderPane(downloadInformationLabel),
                new VerticalSeparator());

        if (isVisible())
            MessageBus.getMessageBus().subscribe(this);

        visibleProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    MessageBus.getMessageBus().subscribe(this);
                } else {
                    MessageBus.getMessageBus().unsubscribe(this);
                }
            }
        });
    }

    private void updateLayout() {
        filmInfoLabel.updateValues();
    }

    @Handler
    private void handleDownloadInfoUpdate(DownloadInfoUpdateAvailableEvent e) {
        Platform.runLater(downloadInformationLabel::setInfoFilme);
    }
    @Handler
    private void handleLeftDisplayUpdate(UpdateStatusBarLeftDisplayEvent e) {
        Platform.runLater(this::updateLayout);
    }


    @Handler
    private void handleTimerEvent(TimerEvent e) {
        Platform.runLater(this::updateLayout);
    }
}
