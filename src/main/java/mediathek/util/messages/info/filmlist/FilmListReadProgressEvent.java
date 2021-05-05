package mediathek.util.messages.info.filmlist;

public class FilmListReadProgressEvent extends FilmListReadEvent {

    private final String text;
    private final int progress;
    private final int max;

    public FilmListReadProgressEvent(String url, String text, int progress, int max) {
        super(url);
        this.text = text;
        this.progress = progress;
        this.max = max;
    }

    public String getText() {
        return text;
    }

    public int getProgress() {
        return progress;
    }

    public int getMax() {
        return max;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", title='" + text + '\'' +
                ", progress=" + progress +
                ", max=" + max;
    }
}
