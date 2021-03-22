package mediathek.client.desktop.gui.actions

import mediathek.client.desktop.gui.dialog.about.AboutDialog
import mediathek.client.desktop.gui.mainwindow.MediathekGui
import mediathek.client.desktop.tools.GuiFunktionen
import java.awt.event.ActionEvent
import javax.swing.AbstractAction

class ShowAboutAction : AbstractAction() {
    override fun actionPerformed(e: ActionEvent?) {
        val dialog = AboutDialog(MediathekGui.ui())
        GuiFunktionen.centerOnScreen(dialog, false)
        dialog.isVisible = true
        dialog.dispose()
    }

    init {
        putValue(NAME, "Über dieses Programm...")
    }
}