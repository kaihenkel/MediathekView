package mediathek.client.desktop.gui.actions;

import mediathek.client.desktop.gui.update.ProgrammUpdateSuchen;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ShowProgramInfosAction extends AbstractAction {
    public ShowProgramInfosAction() {
        putValue(Action.NAME,"Programminfos anzeigen...");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new ProgrammUpdateSuchen().checkVersion(false, true, false, false);
    }
}
