package mediathek.util.tools.models;

import mediathek.util.constants.MVColor;
import mediathek.util.tools.MVC;

public class TModelColor extends NonEditableTableModel {
    public TModelColor(Object[][] data) {
        super(data, new String[]{"Beschreibung", "Farbe"});
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class<?> result;
        if (columnIndex == MVColor.MVC_COLOR) {
            result = MVC.class;
        } else {
            result = String.class;
        }
        return result;
    }
}
