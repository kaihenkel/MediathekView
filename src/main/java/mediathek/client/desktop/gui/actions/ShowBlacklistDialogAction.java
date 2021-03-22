package mediathek.client.desktop.gui.actions;

import mediathek.util.daten.Daten;
import mediathek.client.desktop.gui.dialog.DialogLeer;
import mediathek.client.desktop.gui.dialogEinstellungen.PanelBlacklist;
import mediathek.client.desktop.gui.mainwindow.MediathekGui;
import mediathek.client.desktop.tools.res.GetIcon;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ShowBlacklistDialogAction extends AbstractAction {
    private static final String PANEL_BLACKLIST_NAME_POSTFIX = "_2";
    private final JFrame parent;
    private final Daten daten;

    public ShowBlacklistDialogAction(JFrame parent, Daten daten) {
        this.daten = daten;
        this.parent = parent;

        putValue(NAME, "Blacklist öffnen...");
        putValue(SMALL_ICON, GetIcon.getProgramIcon("menue-blacklist.png", 16, 16));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DialogLeer dialog = new DialogLeer(parent, true);
        dialog.init("Blacklist", new PanelBlacklist(daten, MediathekGui.ui(), PanelBlacklist.class.getName() + PANEL_BLACKLIST_NAME_POSTFIX));
        dialog.setVisible(true);
    }
}
