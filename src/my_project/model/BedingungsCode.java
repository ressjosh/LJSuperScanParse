package my_project.model;

import my_project.control.ViewControll;

import javax.swing.*;

public class BedingungsCode {

    private String bedingung;
    private int index;
    private int bedingungsart;
    private int laenge;
    private CodeScanner scanner;
    private ViewControll vC;
    private Interpreter interpreter;

    public BedingungsCode(CodeScanner scanner, Interpreter i, String bedingung, int anzahlbefehle, int verzweigungsindex, ViewControll vC) {
        this.vC = vC;
        interpreter = i;
        this.scanner = scanner;
        this.bedingung = bedingung;
        this.index = verzweigungsindex;
        this.laenge = anzahlbefehle;
        bedingungsArtErmitteln();
    }

    public boolean bedingungpruefen(){
        try {
            if (bedingungsart == 0) {
                int operator = operatorFiltern();
                String[] werte;
                if (operator == 0) {
                    werte = bedingung.split("=");
                    if (gibZahlenwertWieder(werte[0]) == gibZahlenwertWieder(werte[1])) {
                        return true;
                    } else return false;
                } else if (operator == 1) {
                    werte = bedingung.split("=>");
                    if (gibZahlenwertWieder(werte[0]) >= gibZahlenwertWieder(werte[1])) {
                        return true;
                    } else return false;
                } else if (operator == 2) {
                    werte = bedingung.split("=<");
                    if (gibZahlenwertWieder(werte[0]) <= gibZahlenwertWieder(werte[1])) {
                        return true;
                    } else return false;
                } else if (operator == 3) {
                    werte = bedingung.split(">");
                    if (gibZahlenwertWieder(werte[0]) > gibZahlenwertWieder(werte[1])) {
                        return true;
                    } else return false;
                } else if (operator == 4) {
                    werte = bedingung.split("<");
                    if (gibZahlenwertWieder(werte[0]) < gibZahlenwertWieder(werte[1])) {
                        return true;
                    } else return false;
                }
            } else if (bedingung.equals("nussHier")) {
                System.out.println("Bedingungsprüfung: " + vC.getAktuellesFeld().isBaumdrauf());
                return vC.getAktuellesFeld().isBaumdrauf();
            } else if (bedingung.equals("wandVoraus")) {

            }
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error 3");
            interpreter.setInterpretiere(false);
            return false;
        }
    }

    public int anzahlbefehle(){
        return laenge;
    }

    private void bedingungsArtErmitteln(){
        char[] tmp = bedingung.toCharArray();
        boolean operator = false;
        for(int i = 0; i < tmp.length; i++){
            if(tmp[i] == '=' || tmp[i] == '<' || tmp[i] == '>'){
                operator = true;
            }
        }

        if(operator == true){
            bedingungsart = 0;
        }else bedingungsart = 1;
    }

    public int getMyIndex(){
        return index;
    }
    /* = ist 0
       => ist 1
       =< ist 2
       > ist 3
       < ist 4
     */
    private int operatorFiltern(){
        char[] bedingung0 = bedingung.toCharArray();
        for(int i = 0; i <bedingung0.length; i++){
            if(bedingung0[i] == '='){
                if(bedingung0[i+1] == '>'){
                    return 1;
                }else if(bedingung0[i+1] == '<'){
                   return 2;
                }else{
                    return 0;
                }
            }else if(bedingung0[i] == '>'){
                return 3;
            }else if(bedingung0[i] == '<'){
                return 4;
            }
        }
        return 0;
    }

    private int gibZahlenwertWieder(String zahl){
        if(zahl.equals("nussAnzahl")){
            return vC.getAktuellesFeld().getBaumAnzahl();
        }else if(zahl.equals("nusseGesammelt")){

        }else if(interpreter.getParameter().istParameter(zahl)){
            return interpreter.getParameter().gibWert(zahl);
        }
        return Integer.parseInt(zahl);
    }

    public void erhoeheAnzahlBefehle(){
        laenge++;
    }

    public void reduziereAnzahlBefehle(){
        laenge--;
    }
}
