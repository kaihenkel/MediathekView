package mediathek.gui.messages;

public class TableModelChangeEvent implements BaseEvent {
    public boolean active;

    public TableModelChangeEvent(boolean active) {
        this.active = active;
    }
}
