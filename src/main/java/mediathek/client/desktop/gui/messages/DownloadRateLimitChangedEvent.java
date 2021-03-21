package mediathek.client.desktop.gui.messages;

public class DownloadRateLimitChangedEvent implements BaseEvent {
    /**
     * new limit in KBytes
     */
    public int newLimit;
}
