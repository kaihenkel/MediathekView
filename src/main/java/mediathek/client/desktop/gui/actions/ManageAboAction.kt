package mediathek.client.desktop.gui.actions

import jiconfont.icons.font_awesome.FontAwesome
import jiconfont.swing.IconFontSwing
import mediathek.client.desktop.gui.abo.ManageAboDialog
import mediathek.client.desktop.gui.mainwindow.MediathekGui
import java.awt.event.ActionEvent
import javax.swing.AbstractAction

class ManageAboAction : AbstractAction() {
    private var dialog: ManageAboDialog? = null

    fun closeDialog() {
        dialog?.dispose()
    }

    override fun actionPerformed(e: ActionEvent?) {
        dialog = ManageAboDialog(MediathekGui.ui())
        dialog!!.isVisible = true
        dialog = null
    }

    init {
        putValue(NAME, "Abos verwalten...")
        putValue(SMALL_ICON, IconFontSwing.buildIcon(FontAwesome.DATABASE, 16f))
    }
}