package mediathek.server.filmlisten;

import mediathek.util.daten.ListeFilme;

interface IDownloadAction {
    boolean performDownload(String dateiUrl, ListeFilme listeFilme, int days);
}
