package mediathek.client.desktop.javafx.filterpanel;

import javafx.scene.control.ComboBox;

public class ThemaComboBox extends ComboBox<String> {
    public ThemaComboBox() {
        super();
        getItems().add("");
        getSelectionModel().select(0);
        setEditable(true);
    }
}
