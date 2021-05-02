package mediathek.util.messages.info.filmlist;

public class FilmListReadStartEvent extends FilmListReadEvent {

    public FilmListReadStartEvent(String url) {
        super(url);
    }
}
