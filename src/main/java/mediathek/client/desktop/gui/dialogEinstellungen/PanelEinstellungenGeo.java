package mediathek.client.desktop.gui.dialogEinstellungen;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import mediathek.util.daten.Daten;
import mediathek.server.daten.GeoblockingField;
import mediathek.util.res.GetFile;
import mediathek.client.desktop.gui.dialog.DialogHilfe;
import mediathek.client.desktop.gui.messages.BlacklistChangedEvent;
import mediathek.client.desktop.gui.messages.GeoStateChangedEvent;
import mediathek.util.config.ApplicationConfiguration;
import mediathek.util.tools.MessageBus;
import org.apache.commons.configuration2.Configuration;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class PanelEinstellungenGeo extends JPanel {
    private final JFrame parentComponent;

    public PanelEinstellungenGeo(JFrame pparentComponent) {
        parentComponent = pparentComponent;

        initComponents();
        init();
    }

    private void init() {
        final Configuration config = ApplicationConfiguration.getConfiguration();

        switch (config.getString(ApplicationConfiguration.GEO_LOCATION)) {
            case GeoblockingField.GEO_CH -> jRadioButtonCH.setSelected(true);
            case GeoblockingField.GEO_AT -> jRadioButtonAt.setSelected(true);
            case GeoblockingField.GEO_EU -> jRadioButtonEu.setSelected(true);
            case GeoblockingField.GEO_WELT -> jRadioButtonSonst.setSelected(true);
            default -> jRadioButtonDe.setSelected(true);
        }
        jRadioButtonDe.addActionListener(e -> {
            config.setProperty(ApplicationConfiguration.GEO_LOCATION, GeoblockingField.GEO_DE);
            filterBlacklistAndNotifyChanges();
        });
        jRadioButtonCH.addActionListener(e -> {
            config.setProperty(ApplicationConfiguration.GEO_LOCATION, GeoblockingField.GEO_CH);
            filterBlacklistAndNotifyChanges();
        });
        jRadioButtonAt.addActionListener(e -> {
            config.setProperty(ApplicationConfiguration.GEO_LOCATION, GeoblockingField.GEO_AT);
            filterBlacklistAndNotifyChanges();
        });
        jRadioButtonEu.addActionListener(e -> {
            config.setProperty(ApplicationConfiguration.GEO_LOCATION, GeoblockingField.GEO_EU);
            filterBlacklistAndNotifyChanges();
        });
        jRadioButtonSonst.addActionListener(e -> {
            config.setProperty(ApplicationConfiguration.GEO_LOCATION, GeoblockingField.GEO_WELT);
            filterBlacklistAndNotifyChanges();
        });

        jCheckBoxMarkieren.setSelected(config.getBoolean(ApplicationConfiguration.GEO_REPORT));
        jCheckBoxMarkieren.addActionListener(e -> {
            config.setProperty(ApplicationConfiguration.GEO_REPORT, jCheckBoxMarkieren.isSelected());
            filterBlacklistAndNotifyChanges();
        });
        jButtonHilfe.setIcon(IconFontSwing.buildIcon(FontAwesome.QUESTION_CIRCLE_O, 16));
        jButtonHilfe.addActionListener(e -> new DialogHilfe(parentComponent, true, new GetFile().getHilfeSuchen(GetFile.PFAD_HILFETEXT_GEO)).setVisible(true));
    }

    /**
     * Filter blacklist and notify other components that changes were made.
     */
    private void filterBlacklistAndNotifyChanges() {
        final var daten = Daten.getInstance();

        daten.getListeBlacklist().filterListe();
        MessageBus.getMessageBus().publishAsync(new GeoStateChangedEvent());
        MessageBus.getMessageBus().publishAsync(new BlacklistChangedEvent());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    // Generated using JFormDesigner non-commercial license
    private void initComponents() {
        var jPanel6 = new JPanel();
        jCheckBoxMarkieren = new JCheckBox();
        var panel1 = new JPanel();
        jRadioButtonDe = new JRadioButton();
        jRadioButtonCH = new JRadioButton();
        jRadioButtonAt = new JRadioButton();
        jRadioButtonEu = new JRadioButton();
        jRadioButtonSonst = new JRadioButton();
        jButtonHilfe = new JButton();

        //======== this ========

        //======== jPanel6 ========
        {
            jPanel6.setBorder(new TitledBorder("Geogeblockte Filme")); //NON-NLS

            //---- jCheckBoxMarkieren ----
            jCheckBoxMarkieren.setText("geblockte Sendungen gelb markieren"); //NON-NLS

            //======== panel1 ========
            {
                panel1.setBorder(new TitledBorder("Mein Standort")); //NON-NLS
                panel1.setLayout(new VerticalLayout());

                //---- jRadioButtonDe ----
                jRadioButtonDe.setSelected(true);
                jRadioButtonDe.setText("DE - Deutschland"); //NON-NLS
                panel1.add(jRadioButtonDe);

                //---- jRadioButtonCH ----
                jRadioButtonCH.setText("CH - Schweiz"); //NON-NLS
                panel1.add(jRadioButtonCH);

                //---- jRadioButtonAt ----
                jRadioButtonAt.setText("AT - \u00d6sterreich"); //NON-NLS
                panel1.add(jRadioButtonAt);

                //---- jRadioButtonEu ----
                jRadioButtonEu.setText("EU (EBU - European Broadcasting Union)"); //NON-NLS
                panel1.add(jRadioButtonEu);

                //---- jRadioButtonSonst ----
                jRadioButtonSonst.setText("Sonstiger"); //NON-NLS
                panel1.add(jRadioButtonSonst);
            }

            //---- jButtonHilfe ----
            jButtonHilfe.setIcon(new ImageIcon(getClass().getResource("/mediathek/res/muster/button-help.png"))); //NON-NLS
            jButtonHilfe.setToolTipText("Hilfe anzeigen"); //NON-NLS

            GroupLayout jPanel6Layout = new GroupLayout(jPanel6);
            jPanel6.setLayout(jPanel6Layout);
            jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup()
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup()
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jCheckBoxMarkieren)
                                .addGap(0, 150, Short.MAX_VALUE))
                            .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGap(0, 331, Short.MAX_VALUE)
                                .addComponent(jButtonHilfe)))
                        .addContainerGap())
            );
            jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup()
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jCheckBoxMarkieren)
                        .addGap(18, 18, 18)
                        .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonHilfe)
                        .addContainerGap())
            );
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        //---- buttonGroup1 ----
        var buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(jRadioButtonDe);
        buttonGroup1.add(jRadioButtonCH);
        buttonGroup1.add(jRadioButtonAt);
        buttonGroup1.add(jRadioButtonEu);
        buttonGroup1.add(jRadioButtonSonst);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JCheckBox jCheckBoxMarkieren;
    private JRadioButton jRadioButtonDe;
    private JRadioButton jRadioButtonCH;
    private JRadioButton jRadioButtonAt;
    private JRadioButton jRadioButtonEu;
    private JRadioButton jRadioButtonSonst;
    private JButton jButtonHilfe;
    // End of variables declaration//GEN-END:variables

}
