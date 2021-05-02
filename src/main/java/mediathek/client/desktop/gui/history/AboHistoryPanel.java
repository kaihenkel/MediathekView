package mediathek.client.desktop.gui.history;

import mediathek.client.desktop.gui.messages.history.AboHistoryChangedEvent;
import mediathek.tool.MessageBus;
import net.engio.mbassy.listener.Handler;

import javax.swing.*;

public final class AboHistoryPanel extends PanelErledigteUrls {

    public AboHistoryPanel() {
        super();
        workList = daten.getAboHistoryController();

        MessageBus.getMessageBus().subscribe(this);
    }

    @Handler
    private void handleAboHistoryChangeEvent(AboHistoryChangedEvent e) {
        SwingUtilities.invokeLater(this::changeListHandler);
    }
}
