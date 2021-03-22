package mediathek.util.messages;

public class TableModelChangeEvent implements BaseEvent {
    public boolean active;

    public TableModelChangeEvent(boolean active) {
        this.active = active;
    }
}
