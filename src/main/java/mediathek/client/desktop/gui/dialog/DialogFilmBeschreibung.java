package mediathek.client.desktop.gui.dialog;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import mediathek.client.desktop.tools.FileDialogs;
import mediathek.client.desktop.tools.GuiFunktionen;
import mediathek.util.daten.Daten;
import mediathek.util.constants.Konstanten;
import mediathek.util.config.MVConfig;
import mediathek.util.config.StandardLocations;
import mediathek.util.daten.DatenFilm;
import mediathek.client.desktop.daten.DatenPset;
import mediathek.client.desktop.daten.ListePset;
import mediathek.client.desktop.javafx.tool.JFXHiddenApplication;
import mediathek.client.desktop.javafx.tool.JavaFxUtils;
import mediathek.client.desktop.gui.mainwindow.MediathekGui;
import mediathek.util.tools.*;
import mediathek.client.desktop.tools.EscapeKeyHandler;
import mediathek.util.tools.FilenameUtils;
import mediathek.client.desktop.javafx.FXErrorDialog;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;

public class DialogFilmBeschreibung extends JDialog {
    private static final String TITLE = "Beschreibung ändern";

    public DialogFilmBeschreibung(JFrame parent, DatenFilm datenFilm) {
        super(parent, true);

        initComponents();

        setTitle(TITLE);
        if (parent != null)
            setLocationRelativeTo(parent);

        EscapeKeyHandler.installHandler(this, this::dispose);

        jTextArea1.setText(datenFilm.getDescription());
        jTextFieldTitel.setText(datenFilm.getTitle());

        jButtonOk.addActionListener(e -> {
            datenFilm.setDescription(jTextArea1.getText());
            dispose();
        });

        jButtonHilfe.setIcon(IconFontSwing.buildIcon(FontAwesome.QUESTION_CIRCLE_O, 16));
        jButtonHilfe.addActionListener(e -> Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Hilfe zu " + TITLE);
            alert.setContentText("""
                    Diese Funktion richtet sich z.B. an Benutzer,welche eine angepasste Beschreibung der Sendung in Form der Infodatei ("Filmname.txt") anlegen und durch Drittprogramme einlesen lassen wollen.

                    Achtung: Diese Änderungen gehen nach dem Neuladen einer Filmliste verloren.""");
            JFXHiddenApplication.showAlert(alert, MediathekGui.ui());
        }));

        jButtonSpeichern.addActionListener(e -> {
            datenFilm.setDescription(jTextArea1.getText());

            String titel = FilenameUtils.replaceLeerDateiname(datenFilm.getTitle(), false,
                    Boolean.parseBoolean(MVConfig.get(MVConfig.Configs.SYSTEM_USE_REPLACETABLE)),
                    Boolean.parseBoolean(MVConfig.get(MVConfig.Configs.SYSTEM_ONLY_ASCII)));
            String pfad = "";
            ListePset lp = Daten.listePset.getListeSpeichern();
            if (!lp.isEmpty()) {
                DatenPset p = lp.get(0);
                pfad = p.getZielPfad();
            }
            if (pfad.isEmpty()) {
                pfad = StandardLocations.getStandardDownloadPath();
            }

            if (titel.isEmpty()) {
                titel = StringUtils.replace(datenFilm.getSender(), " ", "-") + ".txt";
            } else {
                titel += ".txt";
            }

            pfad = GuiFunktionen.addsPfad(pfad, titel);
            var destFile = FileDialogs.chooseSaveFileLocation(MediathekGui.ui(),"Infos speichern", pfad);
            if (destFile != null) {
                final Path path = destFile.toPath();
                try {
                    MVInfoFile file = new MVInfoFile();
                    var url = HttpUrl.parse(datenFilm.getUrl());
                    file.writeInfoFile(datenFilm, path, url);

                    JavaFxUtils.invokeInFxThreadAndWait(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Infodatei schreiben");
                        alert.setContentText("Infodatei wurde erfolgreich geschrieben.");
                        JFXHiddenApplication.showAlert(alert, MediathekGui.ui());
                    });
                }
                catch (IOException ex) {
                    JavaFxUtils.invokeInFxThreadAndWait(() -> FXErrorDialog.showErrorDialog(Konstanten.PROGRAMMNAME, "Infodatei schreiben", "Ein unbekannter Fehler ist aufgetreten!", ex));
                    logger.error("Ziel: {}", path.toAbsolutePath().toString(), ex);
                }
            }
        });
    }

    private static final Logger logger = LogManager.getLogger();

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    // Generated using JFormDesigner non-commercial license
    private void initComponents() {
        var panel1 = new JPanel();
        jButtonOk = new JButton();
        jButtonHilfe = new JButton();
        jButtonSpeichern = new JButton();
        var jScrollPane1 = new JScrollPane();
        jTextArea1 = new JTextArea();
        var panel2 = new JPanel();
        var jLabel2 = new JLabel();
        jTextFieldTitel = new JTextField();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            new LC().fill().insets("5").hideMode(3).gridGap("5", "5"), //NON-NLS
            // columns
            new AC()
                .grow().fill(),
            // rows
            new AC()
                .fill().gap()
                .grow().fill().gap()
                .fill()));

        //======== panel1 ========
        {
            panel1.setLayout(new MigLayout(
                new LC().insets("0").hideMode(3).gridGap("5", "5"), //NON-NLS
                // columns
                new AC()
                    .fill().gap()
                    .grow().fill().gap()
                    .fill().gap()
                    .fill(),
                // rows
                new AC()
                    .fill()));

            //---- jButtonOk ----
            jButtonOk.setText("Ok"); //NON-NLS
            panel1.add(jButtonOk, new CC().cell(3, 0));

            //---- jButtonHilfe ----
            jButtonHilfe.setIcon(new ImageIcon(getClass().getResource("/mediathek/res/muster/button-help.png"))); //NON-NLS
            jButtonHilfe.setToolTipText("Hilfe anzeigen"); //NON-NLS
            panel1.add(jButtonHilfe, new CC().cell(2, 0));

            //---- jButtonSpeichern ----
            jButtonSpeichern.setText("Speichern"); //NON-NLS
            panel1.add(jButtonSpeichern, new CC().cell(0, 0));
        }
        contentPane.add(panel1, new CC().cell(0, 2));

        //======== jScrollPane1 ========
        {

            //---- jTextArea1 ----
            jTextArea1.setColumns(20);
            jTextArea1.setLineWrap(true);
            jTextArea1.setRows(5);
            jTextArea1.setWrapStyleWord(true);
            jScrollPane1.setViewportView(jTextArea1);
        }
        contentPane.add(jScrollPane1, new CC().cell(0, 1));

        //======== panel2 ========
        {
            panel2.setLayout(new MigLayout(
                new LC().insets("0").hideMode(3).gridGap("5", "5"), //NON-NLS
                // columns
                new AC()
                    .fill().gap()
                    .grow().fill(),
                // rows
                new AC()
                    .fill()));

            //---- jLabel2 ----
            jLabel2.setText("Filmtitel:"); //NON-NLS
            panel2.add(jLabel2, new CC().cell(0, 0));

            //---- jTextFieldTitel ----
            jTextFieldTitel.setEditable(false);
            panel2.add(jTextFieldTitel, new CC().cell(1, 0));
        }
        contentPane.add(panel2, new CC().cell(0, 0));
        setSize(490, 185);
        setLocationRelativeTo(getOwner());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JButton jButtonOk;
    private JButton jButtonHilfe;
    private JButton jButtonSpeichern;
    private JTextArea jTextArea1;
    private JTextField jTextFieldTitel;
    // End of variables declaration//GEN-END:variables

}
