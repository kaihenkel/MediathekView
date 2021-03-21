package mediathek.client.desktop.gui.dialog.reset;

import mediathek.util.mv.Daten;
import mediathek.client.desktop.gui.dialog.StandardCloseDialog;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class ResetSettingsDialog extends StandardCloseDialog {
    public ResetSettingsDialog(Frame owner, Daten daten) {
        super(owner, "Programm zurücksetzen", true);
        setResizable(false);
    }

    @Override
    public JComponent createContentPanel() {
        return new ResetSettingsPanel(null);
    }
}
