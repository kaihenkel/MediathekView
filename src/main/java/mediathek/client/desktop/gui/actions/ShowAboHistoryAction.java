package mediathek.client.desktop.gui.actions;

import mediathek.util.mv.Daten;
import mediathek.client.desktop.gui.history.AboHistoryDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ShowAboHistoryAction extends AbstractAction {
    private final Frame owner;
    private final Daten daten;

    public ShowAboHistoryAction(Frame owner, Daten daten) {
        this.owner = owner;
        this.daten = daten;

        putValue(Action.NAME, "Abo-Historie anzeigen...");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AboHistoryDialog dialog = new AboHistoryDialog(owner);
        dialog.pack();
        dialog.setVisible(true);
    }

}
