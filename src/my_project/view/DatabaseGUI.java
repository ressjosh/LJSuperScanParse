package my_project.view;

import my_project.control.ProgramController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatabaseGUI {

    private JPanel mainPanel;
    private JButton verbindungHerstellenButton;
    private JButton SQLAusführenButton;
    private JButton programmSchließenButton;
    private JTextPane outputPane;
    private JEditorPane inputPane;

    private ProgramController programController;

    public DatabaseGUI(ProgramController programController) {
        this.programController = programController;
        programmSchließenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                programController.closeProgram();
            }
        });
        SQLAusführenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // todo Der eingegeben SQL-Code wird auf der Datenbank ausgeführt (Database-Controller verwenden). Dann wird entweder im Feld unter dem Button das Ergebnis angezeigt oder die Fehlermeldung bei SQL-Fehlern

            }
        });
        verbindungHerstellenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // todo Es wird eine SQL-Verbindung hergestellt (Database-Controller verwenden)

            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
