package mediathek.util.messages.info.filmlist;

public class FilmListReadCompleteEvent extends FilmListReadEvent {

    private final boolean failed;

    private final int size;

    public FilmListReadCompleteEvent(String url, boolean failed, int size) {
        super(url);
        this.failed = failed;
        this.size = size;
    }

    public boolean isFailed() {
        return failed;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", failed=" + failed
                +", size=" + size;
    }
}
