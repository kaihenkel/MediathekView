package mediathek.client.desktop.gui.dialog;

import mediathek.util.daten.Daten;
import mediathek.util.constants.Konstanten;
import mediathek.server.daten.ListePsetVorlagen;
import mediathek.util.tools.EscapeKeyHandler;
import mediathek.util.tools.GuiFunktionenProgramme;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class DialogAboNoSet extends JDialog {
    public DialogAboNoSet(JFrame parent) {
        super(parent, true);
        initComponents();

        setTitle(Konstanten.PROGRAMMNAME);
        if (parent != null) {
            setLocationRelativeTo(parent);
        }

        EscapeKeyHandler.installHandler(this, this::dispose);

        jTextArea1.setText("""
                Ein Set von Programmen zum Aufzeichnen wurde nicht angelegt.

                Im Menü unter:
                "Datei->Einstellungen->Aufzeichnen und Abspielen"
                ein Programm zum Aufzeichnen für Abos festlegen. (Oder die Standardsets importieren)""");
        jButtonImport.addActionListener(l
                -> GuiFunktionenProgramme.addSetVorlagen(parent, Daten.getInstance(),
                        ListePsetVorlagen.getStandarset(parent, true), true));
        jButtonOk.addActionListener((ActionEvent e) -> dispose());

        setSize(470,200);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    // Generated using JFormDesigner non-commercial license
    private void initComponents() {
        var jScrollPane1 = new JScrollPane();
        jTextArea1 = new JTextArea();
        jLabelIcon = new JLabel();
        panel1 = new JPanel();
        jButtonImport = new JButton();
        jButtonOk = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            new LC().insets("5").hideMode(3).gridGap("5", "5"), //NON-NLS
            // columns
            new AC()
                .fill().gap()
                .grow().fill(),
            // rows
            new AC()
                .grow().fill().gap()
                .fill()));

        //======== jScrollPane1 ========
        {

            //---- jTextArea1 ----
            jTextArea1.setEditable(false);
            jTextArea1.setColumns(20);
            jTextArea1.setLineWrap(true);
            jTextArea1.setRows(4);
            jTextArea1.setWrapStyleWord(true);
            jScrollPane1.setViewportView(jTextArea1);
        }
        contentPane.add(jScrollPane1, new CC().cell(1, 0).grow());

        //---- jLabelIcon ----
        jLabelIcon.setIcon(new ImageIcon(getClass().getResource("/mediathek/res/programm/achtung.png"))); //NON-NLS
        contentPane.add(jLabelIcon, new CC().cell(0, 0).alignY("top").growY(0)); //NON-NLS

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout(FlowLayout.RIGHT));

            //---- jButtonImport ----
            jButtonImport.setText("Standardsets importieren"); //NON-NLS
            panel1.add(jButtonImport);

            //---- jButtonOk ----
            jButtonOk.setText("Schlie\u00dfen"); //NON-NLS
            panel1.add(jButtonOk);
        }
        contentPane.add(panel1, new CC().cell(1, 1));
        pack();
        setLocationRelativeTo(getOwner());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JTextArea jTextArea1;
    private JLabel jLabelIcon;
    private JPanel panel1;
    private JButton jButtonImport;
    private JButton jButtonOk;
    // End of variables declaration//GEN-END:variables

}
