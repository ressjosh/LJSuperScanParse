package my_project.view;

import my_project.control.ProgramController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An dieser Klasse sind keine Modifikationen erforderlich
 */
public class MainGUI {

    private JPanel mainPanel;
    private JButton parseButton;
    private JButton buttonCloseProgram;
    private JTextPane outputPane;
    private JTextField inputTextField;
    private JSpinner languageIndex;
    private JLabel languageLabel;
    private JCheckBox soundCheckBox;

    private ProgramController programController;

    public MainGUI(ProgramController programController) {
        this.programController = programController;
        languageIndex.setModel(new SpinnerNumberModel());
        ((SpinnerNumberModel)languageIndex.getModel()).setMinimum(0);
        ((SpinnerNumberModel)languageIndex.getModel()).setStepSize(1);
        languageIndex.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                switch((int)(languageIndex.getValue())){
                    case 0: languageLabel.setText("Momentan ist die Sprache L_Knebi gewählt!"); break;
                    // Hier könnten theoretisch weitere Beschreibungen stehen, ist aber nicht dringend.

                    default: languageLabel.setText("Es ist die Sprache mit dem Index "+languageIndex.getValue()+" gewählt."); break;
                }
            }
        });
        buttonCloseProgram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        // Action Listener für das scannen und parsen
        parseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(programController.scanString(inputTextField.getText(),(int)(languageIndex.getValue()))){
                    outputPane.setText("Scan erfolgreich!");
                } else {
                    outputPane.setText("Scan nicht erfolgreich!");
                    if(soundCheckBox.isSelected()){
                        programController.playNo();
                    }
                }
                if(programController.parseString(inputTextField.getText(),(int)(languageIndex.getValue()))){
                    outputPane.setText(outputPane.getText()+"\nParse erfolgreich!");
                    if(soundCheckBox.isSelected()){
                        programController.playYes();
                    }
                } else {
                    outputPane.setText(outputPane.getText()+"\nParse nicht erfolgreich!");
                    if(soundCheckBox.isSelected()){
                        programController.playNo();
                    }
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
