package my_project.control;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.model.Biber;
import my_project.model.Feld;
import my_project.model.KrasseListe;
import my_project.view.Ausgabefeld;
import my_project.view.Eingabefeld;

import javax.swing.*;

public class ViewControll {
    private Biber biber;
    private Eingabefeld eingabe;
    private CentralControll cC;
    private Ausgabefeld ausgabe;
    private Feld[][] felder;

    public ViewControll(CentralControll central, ViewController vC) {
        cC = central;
        felder = new Feld[10][5];
        generateFields();
        biber = new Biber();
        eingabe = new Eingabefeld(cC, this);
        ausgabe  = new Ausgabefeld(this);
        vC.draw(ausgabe);
    }

    public Biber getBiber() {
        return biber;
    }

    public void biberToStart(){
        biber.setHoehe(2);
        biber.removeRichtung();
        biber.setWeite(0);
        cC.getInterpreter().setInterpretiere(false);
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 5; j++){
                felder[i][j].setBaumAnzahl(0);
            }
        }
    }

    public void showError(){
        JOptionPane.showMessageDialog(null, "Leider liegt ein Fehler in deiner Syntax vor, bitte prüfe diese noch einmal");
    }

    public Feld getAktuellesFeld(){
        return felder[biber.getWeite()][biber.getHoehe()];
    }

    private void generateFields(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 5; j++){
                felder[i][j] = new Feld();
            }
        }
    }

    public Feld[][] getFelder(){
        return felder;
    }

    public List<KrasseListe<Integer, String>.Token<Integer, String>> gibParameter(){
        return cC.getInterpreter().getParameter().gibParameter();
    }
}
