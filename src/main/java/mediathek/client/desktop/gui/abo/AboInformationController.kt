package mediathek.client.desktop.gui.abo

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import mediathek.util.daten.Daten
import mediathek.util.messages.AboListChangedEvent
import mediathek.client.desktop.javafx.tool.JavaFxUtils
import mediathek.util.tools.MessageBus
import net.engio.mbassy.listener.Handler
import java.net.URL
import java.util.*

class AboInformationController : Initializable {
    @FXML
    private lateinit var totalAbos: Label

    @FXML
    private lateinit var activeAbos: Label

    @FXML
    private lateinit var inactiveAbos: Label

    private fun updateDisplayText() {
        val listeAbo = Daten.getInstance().listeAbo
        val numAbos = listeAbo.size

        if (numAbos == 1) {
            totalAbos.text = "Gesamt: 1 Abo"
        } else {
            totalAbos.text = "Gesamt: $numAbos Abos"
        }
        activeAbos.text = "${listeAbo.activeAbos()} eingeschaltet"
        inactiveAbos.text = "${listeAbo.inactiveAbos()} ausgeschaltet"
    }

    @Handler
    private fun handleAboChangedEvent(e: AboListChangedEvent) {
        JavaFxUtils.invokeInFxThreadAndWait { updateDisplayText() }
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        MessageBus.messageBus.subscribe(this)
        updateDisplayText()
    }
}