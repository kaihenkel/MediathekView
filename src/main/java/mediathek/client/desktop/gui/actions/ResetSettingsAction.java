package mediathek.client.desktop.gui.actions;

import mediathek.util.mv.Daten;
import mediathek.client.desktop.gui.dialog.reset.ResetSettingsDialog;
import mediathek.util.tools.GuiFunktionen;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ResetSettingsAction extends AbstractAction {
    private final JFrame owner;
    private final Daten daten;

    public ResetSettingsAction(JFrame parent, Daten daten) {
        super();
        owner = parent;
        this.daten = daten;

        putValue(NAME, "Einstellungen zurücksetzen...");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ResetSettingsDialog dialog = new ResetSettingsDialog(owner, daten);
        GuiFunktionen.centerOnScreen(dialog, false);
        dialog.setVisible(true);
    }
}
