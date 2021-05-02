package mediathek.client.desktop.os.mac;

import mediathek.util.mv.Daten;
import mediathek.util.constants.Konstanten;
import mediathek.client.desktop.gui.actions.ShowAboutAction;
import mediathek.client.desktop.gui.messages.DownloadFinishedEvent;
import mediathek.client.desktop.gui.messages.DownloadStartEvent;
import mediathek.client.desktop.gui.messages.InstallTabSwitchListenerEvent;
import mediathek.client.desktop.os.mac.tabs.TabDownloadsMac;
import mediathek.client.desktop.os.mac.tabs.TabFilmeMac;
import mediathek.client.desktop.os.mac.touchbar.TouchBarUtils;
import mediathek.client.desktop.gui.mainwindow.MediathekGui;
import mediathek.tool.notification.INotificationCenter;
import mediathek.tool.notification.MacNotificationCenter;
import mediathek.tool.threads.IndicatorThread;
import net.engio.mbassy.listener.Handler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class MediathekGuiMac extends MediathekGui {
    protected static final Logger logger = LogManager.getLogger(MediathekGuiMac.class);
    private final OsxPowerManager powerManager = new OsxPowerManager();

    public MediathekGuiMac() {
        super();

        setupDockIcon();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        if (TouchBarUtils.isTouchBarSupported()) {
            var comp = tabbedPane.getSelectedComponent();
            if (comp.equals(tabFilme)) {
                // bugfix for macOS 11.1 Big Sur which otherwise wouldn´t show the touchbar on startup...
                // window must be visible to activate touchbar
                tabFilme.showTouchBar();
            }
        }
    }

    @Override
    protected boolean officialLauncherInUse() {
        boolean macOSBinaryInuse = true;
        final var osxOfficialApp = System.getProperty(Konstanten.MACOS_OFFICIAL_APP);
        if (osxOfficialApp == null || osxOfficialApp.isEmpty() || osxOfficialApp.equalsIgnoreCase("false")) {
            macOSBinaryInuse = false;
        }
        return macOSBinaryInuse;
    }

    @Override
    protected void installAdditionalHelpEntries() {
        //unused on macOS
    }

    @Override
    public void initializeSystemTray() {
        //we don´t use it on macOS
    }

    @Override
    protected void installTouchBarSupport() {
        logger.trace("install touch bar support");
        if (TouchBarUtils.isTouchBarSupported()) {
            tabbedPane.addChangeListener(e -> {
                var comp = tabbedPane.getSelectedComponent();
                if (comp.equals(tabFilme)) {
                    tabDownloads.hideTouchBar();
                    tabFilme.showTouchBar();
                } else if (comp.equals(tabDownloads)) {
                    tabFilme.hideTouchBar();
                    tabDownloads.showTouchBar();
                }
            });
        }

    }

    @Override
    protected INotificationCenter getNotificationCenter() {
        return new MacNotificationCenter();
    }

    @Override
    protected JPanel createTabFilme(@NotNull Daten daten) {
        return new TabFilmeMac(daten, this);
    }

    @Override
    protected JPanel createTabDownloads(@NotNull Daten daten) {
        return new TabDownloadsMac(daten, this);
    }

    @Override
    protected void shutdownComputer() {
        try {
            Runtime.getRuntime().exec("nohup bin/mv_shutdown_helper");
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Override
    protected void installMenuTabSwitchListener() {
        //do not use on OS X as it violates HIG...
    }

    @Override
    @Handler
    protected void handleInstallTabSwitchListenerEvent(InstallTabSwitchListenerEvent msg) {
        //do not use on OS X as it violates HIG...
    }

    @Override
    protected void initMenus() {
        super.initMenus();

        setupUserInterfaceForOsx();
    }

    @Override
    protected IndicatorThread createProgressIndicatorThread() {
        return new OsxIndicatorThread();
    }

    @Override
    protected void handleDownloadStart(DownloadStartEvent msg) {
        super.handleDownloadStart(msg);
        powerManager.disablePowerManagement();

        setDownloadsBadge(numDownloadsStarted.get());
    }

    @Override
    protected void handleDownloadFinishedEvent(DownloadFinishedEvent msg) {
        super.handleDownloadFinishedEvent(msg);

        final int numDownloads = numDownloadsStarted.get();
        if (numDownloads == 0)
            powerManager.enablePowerManagement();

        setDownloadsBadge(numDownloads);
    }

    /**
     * Set the OS X dock icon badge to the number of running downloads.
     *
     * @param numDownloads The number of active downloads.
     */
    private void setDownloadsBadge(int numDownloads) {
        if (Taskbar.isTaskbarSupported()) {
            var taskbar = Taskbar.getTaskbar();
            if (taskbar.isSupported(Taskbar.Feature.ICON_BADGE_NUMBER)) {
                if (numDownloads > 0)
                    taskbar.setIconBadge(Integer.toString(numDownloads));
                else {
                    taskbar.setIconBadge("");
                }
            }
        }
    }

    /**
     * Setup the UI for OS X
     */
    private void setupUserInterfaceForOsx() {
        Desktop desktop = Desktop.getDesktop();
        desktop.disableSuddenTermination();
        desktop.setQuitHandler((e, response) -> {
            if (!beenden(false, false)) {
                response.cancelQuit();
            } else {
                response.performQuit();
            }
        });
        desktop.setAboutHandler(e -> new ShowAboutAction().actionPerformed(null));
        desktop.setPreferencesHandler(e -> getSettingsDialog().setVisible(true));
    }

    /**
     * Install MediathekView app icon in dock
     */
    private void setupDockIcon() {
        try {
            if (Taskbar.isTaskbarSupported()) {
                var taskbar = Taskbar.getTaskbar();
                if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                    final URL url = this.getClass().getResource("/mediathek/res/MediathekView.png");
                    final BufferedImage appImage = ImageIO.read(url);
                    Taskbar.getTaskbar().setIconImage(appImage);
                }
            }
        } catch (IOException ex) {
            logger.error("OS X Application image could not be loaded", ex);
        }
    }
}
