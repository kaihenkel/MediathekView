package mediathek.util.messages;

public class InstallTabSwitchListenerEvent implements BaseEvent {
    public enum INSTALL_TYPE {INSTALL, REMOVE}

    public INSTALL_TYPE event;
}
