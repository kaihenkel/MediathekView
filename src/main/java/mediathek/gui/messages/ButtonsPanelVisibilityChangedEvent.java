package mediathek.gui.messages;

public class ButtonsPanelVisibilityChangedEvent implements BaseEvent {
    public boolean visible;

    public ButtonsPanelVisibilityChangedEvent(boolean visible) {
        this.visible = visible;
    }
}
