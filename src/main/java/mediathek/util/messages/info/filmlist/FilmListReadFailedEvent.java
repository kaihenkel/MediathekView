package mediathek.util.messages.info.filmlist;

public class FilmListReadFailedEvent extends FilmListReadEvent {
    public FilmListReadFailedEvent(String url) {
        super(url);
    }
}
