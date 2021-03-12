package my_project.view;

import my_project.control.CentralControll;
import my_project.control.ProgramController;
import my_project.control.ViewControll;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An dieser Klasse sind keine Modifikationen erforderlich
 */
public class Eingabefeld {

    private JPanel mainPanel;
    private JButton parseButton;
    private JButton executeButton;
    private JTextPane outputPane;
    private JButton newCodeButton;
    private JTextArea input;
    private JSpinner languageIndex;
    private JLabel languageLabel;
    private JCheckBox soundCheckBox;
    private JFrame fenster;
    private CentralControll cC;
    private ViewControll vC;

    public Eingabefeld(CentralControll cC, ViewControll vC) {
        this.vC = vC;
        this.cC = cC;
        fenster = new JFrame("Lasst uns spielen");
        fenster.setContentPane(mainPanel);
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.pack();
        fenster.setVisible(true);

        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] tmp  = input.getText().split("/n");
                cC.executeCode(input.getText());
                executeButton.setEnabled(false);
                input.setEnabled(false);
            }
        });

        newCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vC.biberToStart();
                executeButton.setEnabled(true);
                input.setEnabled(true);
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
