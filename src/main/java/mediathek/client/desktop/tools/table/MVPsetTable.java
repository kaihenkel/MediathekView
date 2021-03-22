package mediathek.client.desktop.tools.table;

import mediathek.client.desktop.daten.DatenPset;
import mediathek.client.desktop.tools.models.TModel;

public class MVPsetTable extends MVTable {
    private static final long serialVersionUID = 7582553351667887172L;

    @Override
    protected void setupTableType() {
        maxSpalten = DatenPset.MAX_ELEM;
        spaltenAnzeigen = getSpaltenEinAus(DatenPset.spaltenAnzeigen, DatenPset.MAX_ELEM);

        setModel(new TModel(new Object[][]{}, DatenPset.COLUMN_NAMES));
        setRowSorter(null);
        setAutoCreateRowSorter(false); // Reihenfolge ist die Anzeige der Button!
    }

    @Override
    protected void spaltenAusschalten() {
        //do nothing
    }

}
