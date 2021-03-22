package mediathek.client.desktop.gui.dialog;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import mediathek.util.daten.Daten;
import mediathek.client.desktop.tools.res.GetFile;
import mediathek.client.desktop.tools.EscapeKeyHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

@SuppressWarnings("serial")
public class DialogNewSet extends JDialog {
    public boolean ok;
    public boolean morgen = true;
    private final JFrame parent;

    public DialogNewSet(JFrame pparent) {
        super(pparent, true);
        initComponents();
        parent = pparent;
        if (parent != null) {
            setLocationRelativeTo(parent);
        }
        setTitle("Das Standardset wurde aktualisiert");
        jTextArea3.setText("""

                   Es gibt ein neues Standardset der Videoplayer
                   für den Download und das Abspielen der Filme.
                """);
        jCheckBoxMorgen.setSelected(true);
        jCheckBoxMorgen.addActionListener(e -> morgen = jCheckBoxMorgen.isSelected());

        jTextArea1.setText("""

                   Die bestehenden Einstellungen werden nicht verändert.
                   Das neue Set wird nur angefügt und muss dann erst noch in den
                   "Datei->Einstellungen->Set bearbeiten"
                   aktiviert werden.
                """);

        jTextArea2.setText("""

                Es werden alle Programmsets (auch eigene)\s
                gelöscht und die neuen Standardsets wieder angelegt.

                (Wenn Sie die Einstellungen nicht verändert haben
                 ist das die Empfehlung)""".indent(3));

        jButtonAdd.addActionListener(e -> {
            ok = true;
            beenden();
        });
        jButtonAbbrechen.addActionListener(e -> {
            ok = false;
            beenden();
        });
        jButtonReplace.addActionListener(e -> {
            int ret = JOptionPane.showConfirmDialog(parent, "Alle Sets zurücksetzen?", "Alle Sets zurücksetzen!", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.OK_OPTION) {
                Daten.listePset.clear();
                ok = true;
                beenden();
            }
        });
        jButtonSetHelp.setIcon(IconFontSwing.buildIcon(FontAwesome.QUESTION_CIRCLE_O, 16));
        jButtonSetHelp.addActionListener(e -> new DialogHilfe(parent, true, new GetFile().getHilfeSuchen(GetFile.PFAD_HILFETEXT_RESET_SET)).setVisible(true));

        EscapeKeyHandler.installHandler(this, () -> {
            ok = false;
            dispose();
        });

        pack();
    }

    private void beenden() {
        this.dispose();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    // Generated using JFormDesigner non-commercial license
    private void initComponents() {
        jButtonAbbrechen = new JButton();
        jCheckBoxMorgen = new JCheckBox();
        var jPanel3 = new JPanel();
        var jScrollPane1 = new JScrollPane();
        jTextArea1 = new JTextArea();
        jButtonAdd = new JButton();
        var jPanel4 = new JPanel();
        jButtonReplace = new JButton();
        var jScrollPane2 = new JScrollPane();
        jTextArea2 = new JTextArea();
        var jScrollPane3 = new JScrollPane();
        jTextArea3 = new JTextArea();
        jButtonSetHelp = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        var contentPane = getContentPane();

        //---- jButtonAbbrechen ----
        jButtonAbbrechen.setText("Abbrechen"); //NON-NLS

        //---- jCheckBoxMorgen ----
        jCheckBoxMorgen.setSelected(true);
        jCheckBoxMorgen.setText("Morgen wieder fragen"); //NON-NLS

        //======== jPanel3 ========
        {
            jPanel3.setBorder(new TitledBorder(null, "Entweder", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, null, new Color(0, 102, 204))); //NON-NLS

            //======== jScrollPane1 ========
            {

                //---- jTextArea1 ----
                jTextArea1.setEditable(false);
                jTextArea1.setColumns(20);
                jTextArea1.setRows(5);
                jScrollPane1.setViewportView(jTextArea1);
            }

            //---- jButtonAdd ----
            jButtonAdd.setText("Neue Sets hinzuf\u00fcgen"); //NON-NLS

            GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
            jPanel3.setLayout(jPanel3Layout);
            jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup()
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup()
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButtonAdd)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
            );
            jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup()
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonAdd)
                        .addContainerGap())
            );
        }

        //======== jPanel4 ========
        {
            jPanel4.setBorder(new TitledBorder(null, "Oder", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, null, new Color(0, 102, 204))); //NON-NLS

            //---- jButtonReplace ----
            jButtonReplace.setText("Bestehende Sets durch die neuen ersetzen"); //NON-NLS

            //======== jScrollPane2 ========
            {

                //---- jTextArea2 ----
                jTextArea2.setEditable(false);
                jTextArea2.setColumns(20);
                jTextArea2.setRows(5);
                jScrollPane2.setViewportView(jTextArea2);
            }

            GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
            jPanel4.setLayout(jPanel4Layout);
            jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup()
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup()
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jButtonReplace)
                                .addGap(0, 233, Short.MAX_VALUE))
                            .addComponent(jScrollPane2))
                        .addContainerGap())
            );
            jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup()
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonReplace)
                        .addGap(13, 13, 13))
            );
        }

        //======== jScrollPane3 ========
        {

            //---- jTextArea3 ----
            jTextArea3.setEditable(false);
            jTextArea3.setBackground(new Color(244, 244, 244));
            jTextArea3.setColumns(20);
            jTextArea3.setRows(3);
            jScrollPane3.setViewportView(jTextArea3);
        }

        //---- jButtonSetHelp ----
        jButtonSetHelp.setIcon(new ImageIcon(getClass().getResource("/mediathek/res/muster/button-help.png"))); //NON-NLS
        jButtonSetHelp.setToolTipText("Hilfe anzeigen"); //NON-NLS

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(jCheckBoxMorgen)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonAbbrechen)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButtonSetHelp)))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(39, 39, 39)
                    .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jPanel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonAbbrechen)
                            .addComponent(jCheckBoxMorgen))
                        .addComponent(jButtonSetHelp))
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JButton jButtonAbbrechen;
    private JCheckBox jCheckBoxMorgen;
    private JTextArea jTextArea1;
    private JButton jButtonAdd;
    private JButton jButtonReplace;
    private JTextArea jTextArea2;
    private JTextArea jTextArea3;
    private JButton jButtonSetHelp;
    // End of variables declaration//GEN-END:variables

}
