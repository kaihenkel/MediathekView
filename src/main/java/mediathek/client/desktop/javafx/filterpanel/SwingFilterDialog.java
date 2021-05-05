package mediathek.client.desktop.javafx.filterpanel;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import mediathek.util.daten.Daten;
import mediathek.util.messages.TableModelChangeEvent;
import mediathek.util.config.ApplicationConfiguration;
import mediathek.util.messages.info.filmlist.FilmListReadCompleteEvent;
import mediathek.util.messages.info.filmlist.FilmListReadStartEvent;
import mediathek.util.tools.MessageBus;
import net.engio.mbassy.listener.Handler;
import org.apache.commons.configuration2.sync.LockMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.NoSuchElementException;

public class SwingFilterDialog extends JDialog {
    private final JFXPanel fxPanel = new JFXPanel();

    public SwingFilterDialog(Frame owner, CommonViewSettingsPane content) {
        super(owner);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setTitle("Filter");
        setType(Type.UTILITY);
        setContentPane(fxPanel);
        Platform.runLater(() -> {
            fxPanel.setScene(new Scene(content));
            SwingUtilities.invokeLater(() -> {
                pack();
                restoreWindowSizeFromConfig();
                restoreDialogVisibility();
                registerWindowSizeListener();
            });
        });

        MessageBus.getMessageBus().subscribe(this);

    }

    @Handler
    private void handleTableModelChangeEvent(TableModelChangeEvent e) {
        SwingUtilities.invokeLater(() -> setEnabled(!e.active));
    }

    @Handler
    private void handleFilmListReadStart(FilmListReadStartEvent event) {
        setEnabled(false);
        SwingUtilities.invokeLater(() -> fxPanel.setEnabled(false));
    }

    @Handler
    private void handleFilmListReadComplete(FilmListReadCompleteEvent event) {
        setEnabled(true);
        SwingUtilities.invokeLater(() -> fxPanel.setEnabled(true));
    }

    private void restoreDialogVisibility() {
        var config = ApplicationConfiguration.getConfiguration();
        final boolean visible = config.getBoolean(ApplicationConfiguration.FilterDialog.VISIBLE, false);
        setVisible(visible);
    }

    private void storeDialogVisibility() {
        var config = ApplicationConfiguration.getConfiguration();
        config.setProperty(ApplicationConfiguration.FilterDialog.VISIBLE, isVisible());
    }

    private void restoreWindowSizeFromConfig() {
        var config = ApplicationConfiguration.getConfiguration();
        try {
            config.lock(LockMode.READ);
            final int width = config.getInt(ApplicationConfiguration.FilterDialog.WIDTH);
            final int height = config.getInt(ApplicationConfiguration.FilterDialog.HEIGHT);
            final int x = config.getInt(ApplicationConfiguration.FilterDialog.X);
            final int y = config.getInt(ApplicationConfiguration.FilterDialog.Y);

            setBounds(x, y, width, height);
        } catch (NoSuchElementException ignored) {
            //do not restore anything
        } finally {
            config.unlock(LockMode.READ);
        }

    }

    private void registerWindowSizeListener() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                storeWindowPosition(e);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                storeWindowPosition(e);
            }

            @Override
            public void componentShown(ComponentEvent e) {
                storeDialogVisibility();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                storeWindowPosition(e);
                storeDialogVisibility();
            }

            private void storeWindowPosition(ComponentEvent e) {
                var config = ApplicationConfiguration.getConfiguration();
                var component = e.getComponent();

                var dims = component.getSize();
                var loc = component.getLocation();
                try {
                    config.lock(LockMode.WRITE);
                    config.setProperty(ApplicationConfiguration.FilterDialog.WIDTH, dims.width);
                    config.setProperty(ApplicationConfiguration.FilterDialog.HEIGHT, dims.height);
                    config.setProperty(ApplicationConfiguration.FilterDialog.X, loc.x);
                    config.setProperty(ApplicationConfiguration.FilterDialog.Y, loc.y);
                } finally {
                    config.unlock(LockMode.WRITE);
                }
            }
        });

    }
}
