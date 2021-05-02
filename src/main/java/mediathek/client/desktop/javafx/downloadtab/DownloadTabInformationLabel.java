package mediathek.client.desktop.javafx.downloadtab;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import mediathek.util.mv.Daten;
import mediathek.client.desktop.gui.messages.TimerEvent;
import mediathek.client.desktop.gui.messages.UpdateStatusBarLeftDisplayEvent;
import mediathek.client.desktop.javafx.CenteredBorderPane;
import mediathek.client.desktop.javafx.InfoLabel.*;
import mediathek.client.desktop.javafx.VerticalSeparator;
import mediathek.tool.MessageBus;
import net.engio.mbassy.listener.Handler;


public class DownloadTabInformationLabel extends HBox {
    private final Daten daten;
    private final GesamtdownloadsLabel overallDownloadLabel = new GesamtdownloadsLabel();
    private final AboLabel aboLabel = new AboLabel();
    private final NumDownloadsLabel numDownloadsLabel = new NumDownloadsLabel();
    private final ActiveDownloadsLabel activeDownloadLabel = new ActiveDownloadsLabel();
    private final WaitingLabel waitingLabel = new WaitingLabel();
    private final FinishedLabel finishedLabel = new FinishedLabel();
    private final ErrorLabel errorLabel = new ErrorLabel();
    private final HBox finishedBox = new HBox();
    private final HBox waitingBox = new HBox();
    private final HBox activeBox = new HBox();
    public DownloadTabInformationLabel(Daten daten) {
        super();
        this.daten = daten;

        setupListeners();
        initLayout();
    }

    private void setupListeners() {
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

    private void initLayout() {
        finishedBox.getChildren().addAll(new CenteredBorderPane(finishedLabel), new VerticalSeparator());
        waitingBox.getChildren().addAll(new CenteredBorderPane(waitingLabel), new VerticalSeparator());
        activeBox.getChildren().addAll(new CenteredBorderPane(activeDownloadLabel), new VerticalSeparator());

        getChildren().addAll(new CenteredBorderPane(overallDownloadLabel),
                new VerticalSeparator(),
                new CenteredBorderPane(aboLabel),
                new VerticalSeparator(),
                new CenteredBorderPane(numDownloadsLabel),
                new VerticalSeparator(),
                activeBox,
                waitingBox,
                finishedBox,
                new CenteredBorderPane(errorLabel));
    }

    @Handler
    private void handleLeftDisplayUpdate(UpdateStatusBarLeftDisplayEvent e) {
        Platform.runLater(this::getInfoTextDownloads);
    }

    @Handler
    private void handleTimerEvent(TimerEvent e) {
        Platform.runLater(this::getInfoTextDownloads);
    }

    private void getInfoTextDownloads() {
        final var listeDownloads = daten.getListeDownloads();
        final var info = listeDownloads.getStarts();
        final var children = getChildren();

        overallDownloadLabel.updateLabel(listeDownloads, info);
        aboLabel.updateLabel(info);
        numDownloadsLabel.updateLabel(info);

        if (info.running > 0) {
            if (!children.contains(activeBox))
                children.add(activeBox);
            activeDownloadLabel.updateLabel(daten, info);
        } else
            children.remove(activeBox);

        if (info.initialized > 0) {
            if (!children.contains(waitingBox))
                children.add(waitingBox);
            waitingLabel.updateLabel(info);
        } else
            children.remove(waitingBox);

        if (info.finished > 0) {
            if (!children.contains(finishedBox))
                children.add(finishedBox);
            finishedLabel.updateLabel(info);
        } else
            children.remove(finishedBox);

        if (info.error > 0) {

            if (!children.contains(errorLabel))
                children.add(new CenteredBorderPane(errorLabel));
            errorLabel.updateLabel(info);
        } else
            children.remove(errorLabel);
    }
}
