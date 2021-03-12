package my_project.control;

import KAGO_framework.control.ViewController;
import my_project.model.Biber;
import my_project.view.Ausgabefeld;
import my_project.view.Eingabefeld;

import javax.swing.*;

public class ViewControll {
    private Biber biber;
    private Eingabefeld eingabe;
    private CentralControll cC;
    private Ausgabefeld ausgabe;
    private ViewController vC;

    public ViewControll(CentralControll central, ViewController vC) {
        cC = central;
        this.vC = vC;
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
        biber.setRichtung(0);
        biber.setWeite(0);
    }

    public void showError(){
        JOptionPane.showMessageDialog(null, "Leider liegt ein Fehler in deiner Syntax vor, bitte prüfe diese noch einmal");
    }
}
