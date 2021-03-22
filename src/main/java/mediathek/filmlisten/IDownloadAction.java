package mediathek.filmlisten;

import mediathek.server.daten.ListeFilme;

interface IDownloadAction {
    boolean performDownload(String dateiUrl, ListeFilme listeFilme, int days);
}
