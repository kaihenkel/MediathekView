package mediathek.gui.actions.import_actions;

import javafx.scene.control.Alert;
import mediathek.util.constants.Konstanten;

class ImportSettingsAlert extends Alert {
    public ImportSettingsAlert(AlertType alertType) {
        super(alertType);
        setTitle(Konstanten.PROGRAMMNAME);
        setHeaderText("Einstellungen importieren");
    }
}
