package mediathek.util.messages.info.filmlist;

public class FilmListReadCompleteEvent extends FilmListReadEvent {
    public FilmListReadCompleteEvent(String url) {
        super(url);
    }
}
