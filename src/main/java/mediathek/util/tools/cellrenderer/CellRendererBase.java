package mediathek.util.tools.cellrenderer;

import mediathek.util.tools.sender_icon_cache.MVSenderIconCache;
import mediathek.util.tools.sender_icon_cache.MVSenderIconCache;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.Optional;

/**
 * Base class for all cell renderer.
 */
public class CellRendererBase extends DefaultTableCellRenderer {
    /**
     * Draws the sender icon in the sender model column.
     *
     * @param sender Name of the sender.
     */
    protected void setSenderIcon(String sender, boolean small) {
        setHorizontalAlignment(SwingConstants.CENTER);
        final Optional<ImageIcon> optIcon = MVSenderIconCache.get(sender, small);
        optIcon.ifPresent(icon -> {
            setText("");
            setIcon(icon);
        });
    }
}
