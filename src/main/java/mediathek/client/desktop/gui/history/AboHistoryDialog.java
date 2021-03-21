package mediathek.client.desktop.gui.history;

import mediathek.client.desktop.gui.dialog.StandardCloseDialog;

import javax.swing.*;
import java.awt.*;

public class AboHistoryDialog extends StandardCloseDialog {
    public AboHistoryDialog(Frame owner) {
        super(owner, "Abo-Historie", true);
    }

    @Override
    public JComponent createContentPanel() {
        return new AboHistoryPanel();
    }
}
