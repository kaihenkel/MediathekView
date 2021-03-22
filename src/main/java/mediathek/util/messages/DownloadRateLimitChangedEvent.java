package mediathek.util.messages;

public class DownloadRateLimitChangedEvent implements BaseEvent {
    /**
     * new limit in KBytes
     */
    public int newLimit;
}
