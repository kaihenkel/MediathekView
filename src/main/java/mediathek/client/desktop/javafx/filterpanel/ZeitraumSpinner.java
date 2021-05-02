package mediathek.client.desktop.javafx.filterpanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class ZeitraumSpinner extends Spinner<String> {
    public static final String UNLIMITED_VALUE = "∞";

    public ZeitraumSpinner() {
        super();
        ObservableList<String> days = FXCollections.observableArrayList(UNLIMITED_VALUE);
        for (int i = 1; i <= 30; i++)
            days.add(String.valueOf(i));

        SpinnerValueFactory<String> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(days);
        valueFactory.setWrapAround(true);
        setValueFactory(valueFactory);
        valueFactory.setValue(UNLIMITED_VALUE);
        setEditable(true);
        getEditor().textProperty().addListener(((observable, oldValue, newValue) -> {
            boolean found = false;
            for (var item : days) {
                if (item.startsWith(newValue)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                getEditor().setText(oldValue);
        }));
    }
}
