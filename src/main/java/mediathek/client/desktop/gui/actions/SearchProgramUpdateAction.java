package mediathek.client.desktop.gui.actions;

import mediathek.client.desktop.gui.update.ProgrammUpdateSuchen;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SearchProgramUpdateAction extends AbstractAction {
    public SearchProgramUpdateAction() {
        putValue(Action.NAME, "Nach Update suchen...");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new ProgrammUpdateSuchen().checkVersion(true, false, false, false);
    }
}
