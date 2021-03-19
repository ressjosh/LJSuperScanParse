package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.model.abitur.datenstrukturen.Stack;
import my_project.control.CentralControll;
import my_project.control.ViewControll;

import javax.swing.*;

public class Interpreter extends GraphicalObject {
    private CodeScanner scanner;
    private CentralControll cC;
    private ViewControll vC;
    private double timer;
    private boolean interpretiere;
    private Parameter parameter;
    private List<KrasseListe<String,String>.Token<String, String>> arbeitsliste;
    private int befehlsnummer;

    public Interpreter(CentralControll cC, ViewControll vC){
        this.cC = cC;
        this.vC = vC;
        arbeitsliste = new List<>();
        interpretiere = false;
        timer = 2;
        parameter = new Parameter();
        befehlsnummer = 0;
    }

    private void interpret(){
        scanner.tokenList.toFirst();
        while(!scanner.getType().equals("NODATA")) {
                if (scanner.getType().equals("bewegung")) {
                    fuehreBewegungAus();
                } else if (scanner.getType().equals("baum")) {
                    arbeiteAnBaum();
                }
                scanner.nextToken();
        }
    }

    private void interpret02(){
        try {
            if (!arbeitsliste.getContent().getType().equals("NODATA")) {
                if (arbeitsliste.getContent().getType().equals("bewegung")) {
                    fuehreBewegungAus();
                } else if (arbeitsliste.getContent().getType().equals("baum")) {
                    arbeiteAnBaum();
                } else if (arbeitsliste.getContent().getType().equals("methodenaufruf")) {
                    bearbeiteMethode(scanner.getThis(arbeitsliste.getContent().getValue()));
                }else if (arbeitsliste.getContent().getType().equals("verzweigung")) {
                    bearbeiteVerzweigung();
                }else if (arbeitsliste.getContent().getType().equals("addieren")) {
                    parameter.addiere(arbeitsliste.getContent().getValue());
                }else if (arbeitsliste.getContent().getType().equals("subtrahieren")) {
                    parameter.subtrahiere(arbeitsliste.getContent().getValue());
                }
                arbeitsliste.next();
                befehlsnummer++;
                timer = 2;
            } else{
                interpretiere = false;
                befehlsnummer = 0;
            }
        }catch(Exception e){
            System.out.println("Error 2");
            befehlsnummer = 0;
            interpretiere = false;
        }
    }

    @Override
    public void update(double dt) {
        timer = timer - 4*dt;
        if(interpretiere && timer < 0){
            interpret02();
        }
    }

    public void start(CodeScanner scanner){
        this.scanner = scanner;
        scanner.tokenList.toFirst();
        arbeitsliste = scanner.getTokenlist();
        interpretiere = true;
        befehlsnummer = 0;
    }

    private void fuehreBewegungAus(){
        if(arbeitsliste.getContent().getValue().equals("vor")){
            geheVor();
        }else if(arbeitsliste.getContent().getValue().equals("rechtsUm")){
            vC.getBiber().setRichtung(1);

        }else if(arbeitsliste.getContent().getValue().equals("linksUm")){
            vC.getBiber().setRichtung(-1);
        }
    }

    public void arbeiteAnBaum(){
        if(arbeitsliste.getContent().getValue().equals("pflanzen")){
            vC.getAktuellesFeld().erhoeheBaumAnzahl(1);
        }else if(arbeitsliste.getContent().getValue().equals("ernten")){
            if(vC.getAktuellesFeld().getBaumAnzahl() > 0 ){
                vC.getAktuellesFeld().erhoeheBaumAnzahl(-1);
            }else JOptionPane.showMessageDialog(null, "Hier ist nichts zum ernten");
        }

    }

    private void bearbeiteMethode(Methode methode){
        methode.tokenList.toFirst();
        while(methode.tokenList.hasAccess()) {
            arbeitsliste.insert(methode.tokenList.getContent());
            methode.nextToken();
        }
        arbeitsliste.remove();
        geheInListeAnStelle(befehlsnummer - 1);

    }

    private void geheVor(){
        if(vC.getBiber().getRichtung() == 0){
            if(vC.getBiber().getWeite() < 9){
                vC.getBiber().setWeite(vC.getBiber().getWeite()+1);
            }else JOptionPane.showMessageDialog(null, "Aua! Eine Wand");
        }else if(vC.getBiber().getRichtung() == 1){
            if(vC.getBiber().getHoehe() < 5){
                vC.getBiber().setHoehe(vC.getBiber().getHoehe()+1);
            }else JOptionPane.showMessageDialog(null, "Aua! Eine Wand");
        }else if(vC.getBiber().getRichtung() == 2){
            if(vC.getBiber().getWeite() > 0){
                vC.getBiber().setWeite(vC.getBiber().getWeite()-1);
            }else JOptionPane.showMessageDialog(null, "Aua! Eine Wand");
        }else if(vC.getBiber().getRichtung() == 3){
            if(vC.getBiber().getHoehe() >0){
                vC.getBiber().setHoehe(vC.getBiber().getHoehe()-1);
            }else JOptionPane.showMessageDialog(null, "Aua! Eine Wand");
        }
    }

    private void bearbeiteVerzweigung(){
        BedingungsCode diese = scanner.getVerzweigungsInfo(Integer.parseInt(scanner.getValue()));
        if(!diese.bedingungpruefen()){
            for(int i = 0; i < diese.anzahlbefehle(); i++){
                arbeitsliste.next();
                befehlsnummer++;
            }
        }
    }

    public Parameter getParameter(){
        return parameter;
    }

    public void setInterpretiere(boolean b){
        interpretiere = b;
    }

    public void parameterListeAufNull(){
        parameter = new Parameter();
    }

    private void geheInListeAnStelle(int i){
        arbeitsliste.toFirst();
        befehlsnummer = i;
        while(i>0){
            arbeitsliste.next();
            i--;
        }
    }
}
