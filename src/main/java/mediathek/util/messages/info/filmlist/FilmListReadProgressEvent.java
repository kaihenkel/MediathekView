package mediathek.util.messages.info.filmlist;

public class FilmListReadProgressEvent extends FilmListReadEvent {

    private final String title;
    private final int progress;
    private final int max;

    public FilmListReadProgressEvent(String url, String title, int progress, int max) {
        super(url);
        this.title = title;
        this.progress = progress;
        this.max = max;
    }

    public String getTitle() {
        return title;
    }

    public int getProgress() {
        return progress;
    }

    public int getMax() {
        return max;
    }
}
