package mediathek.util.messages.info.filmlist;

import mediathek.util.messages.info.InfomationEvent;

public abstract class FilmListReadEvent implements InfomationEvent {
    private final String url;

    protected FilmListReadEvent(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " url: " + url;
    }
}
