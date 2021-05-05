package mediathek.util.messages.info.filmlist;

public class FilmListReadCompleteEvent extends FilmListReadEvent {

    private final boolean failed;

    public FilmListReadCompleteEvent(String url, boolean failed) {
        super(url);
        this.failed = failed;
    }

    public boolean isFailed() {
        return failed;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", failed=" + failed;
    }
}
