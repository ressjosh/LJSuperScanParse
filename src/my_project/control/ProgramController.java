package my_project.control;

import KAGO_framework.control.ViewController;
import my_project.model.CodeParser;
import Rubbish.LJParser;
import my_project.view.Ausgabefeld;
import my_project.view.Eingabefeld;

import java.awt.event.MouseEvent;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    //Attribute


    // Referenzen
    private ViewController viewController;  // diese Referenz soll auf ein Objekt der Klasse viewController zeigen. Über dieses Objekt wird das Fenster gesteuert.
    private CentralControll cC;

    /**
     * Konstruktor
     * Dieser legt das Objekt der Klasse ProgramController an, das den Programmfluss steuert.
     * Damit der ProgramController auf das Fenster zugreifen kann, benötigt er eine Referenz auf das Objekt
     * der Klasse viewController. Diese wird als Parameter übergeben.
     * @param viewController das viewController-Objekt des Programms
     */
    public ProgramController(ViewController viewController){
        this.viewController = viewController;
    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen. Achtung: funktioniert nicht im Szenario-Modus
     */
    public void startProgram() {
        cC = new CentralControll(viewController);
        viewController.getDrawFrame().setSize(530,260);
        viewController.getSoundController().loadSound("assets/yesyesyes.mp3","yes",false);
        viewController.getSoundController().loadSound("assets/nonono.mp3","no",false);

    }

    /**
     *ring ein Wort der Sprache des Parsers ist.
     */
   /* public boolean parseString(String input, int parserIndex){
        switch(parserIndex){
            case 0:
                return knebiParser.parse(input);

            // todo Hier können weitere Parser aufgeführt werden
            case 1:
                return lJParser.parse(input);

            case 2:
                return lJParserProVersion.parse(input);

            default: System.out.println("\nDebug-Info: Für diesen Index ist kein Parser definiert!");
        }
        return false;
    }

    /**
     * Der übergebene String wird gescannt.
     * @param input der zu scannende String
     * @param parserIndex der zu verwendende Scanner (ist dem Parser zugeordnet, daher Parserindex)
     * @return true, falls der Scan erfolgreich war, andernfalls false
     */
    /*public boolean scanString(String input, int parserIndex){
        switch(parserIndex){
            case 0:
                boolean result = knebiParser.getScannerResult(input);
                System.out.println("\n- KnebiScanner-DEBUG - "+knebiParser.getScannerOutput());
                return result;
            // todo Hier können weitere Scanner aufgeführt werden.
            case 1:
                return lJParser.getScannerResult(input);

            case 2:
                return lJParserProVersion.getScannerResult(input);

            default: System.out.println("\nDebug-Info: Für diesen Index ist kein Scanner definiert!");
        }
        return false;
    }

    // ********* FRAMEWORK-METHODEN *************

    /**
     * Sorgt dafür, dass zunächst gewartet wird, damit der SoundController die
     * Initialisierung abschließen kann. Die Wartezeit ist fest und damit nicht ganz sauber
     * implementiert, aber dafür funktioniert das Programm auch bei falscher Java-Version
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt){

    }


    /**
     * Verarbeitet einen Mausklick.
     * @param e das Objekt enthält alle Informationen zum Klick
     */
    public void mouseClicked(MouseEvent e){

    }

    /*public void playYes(){
        viewController.getSoundController().playSound("yes");
    }

    public void playNo(){
        viewController.getSoundController().playSound("no");
    }*/
}
