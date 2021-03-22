package mediathek.client.desktop.javafx.InfoLabel;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import mediathek.util.daten.DownloadStartInfo;
import mediathek.client.desktop.daten.ListeDownloads;

public class GesamtdownloadsLabel extends Label {
    public GesamtdownloadsLabel() {
        setTooltip(new Tooltip("Gesamtzahl aller Downloads"));
    }

    public void updateLabel(ListeDownloads listeDownloads, DownloadStartInfo info) {
        final int anz = listeDownloads.size();
        final int diff = anz - info.total_starts;
        String download = "Gesamtdownloads: " + anz;
        if (diff >= 1) {
            download += " (" + diff + " zurückgestellt)";
        }
        setText(download);
    }
}
