package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.model.abitur.datenstrukturen.List;
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

     List tokenList;
     Scanner.Token token;
    public Interpreter(CentralControll cC, ViewControll vC){
        this.cC = cC;
        this.vC = vC;
        interpretiere = false;
        timer = 2;
        tokenList = new List<Scanner.Token>();
        parameter = new Parameter(null);
    }


    public void interpretieren(List ttokenlist){
        ttokenlist.toFirst();
        for(int i = 0; !ttokenlist.isEmpty();i++){
            if(ttokenlist.getContent().equals(token)){

            }
        }

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
            if (!scanner.getType().equals("NODATA")) {
                if (scanner.getType().equals("bewegung")) {
                    fuehreBewegungAus();
                } else if (scanner.getType().equals("baum")) {
                    arbeiteAnBaum();
                } else if (scanner.getType().equals("methodenaufruf")) {
                    bearbeiteMethode(scanner.getThis(scanner.getValue()));
                }else if (scanner.getType().equals("verzweigung")) {
                    bearbeiteVerzweigung();
                }else if (scanner.getType().equals("addieren")) {
                    parameter.addiere(scanner.getValue());
                }else if (scanner.getType().equals("subtrahieren")) {
                    parameter.subtrahiere(scanner.getValue());
                }else if (scanner.getType().equals("parameter")) {
                    String[] tmp = scanner.getValue().split(" ");
                    System.out.println("Hier wurde ein Parameter angelegt: " + tmp[0] + "; " + tmp[1]);
                    parameter.legeParameterAn(tmp[0], tmp[1]);
                }
                scanner.nextToken();
                timer = 2;
            } else interpretiere = false;
        }catch(Exception e){
            System.out.println("Error 2");
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
        interpretiere = true;
        //interpret();
    }

    private void fuehreBewegungAus(){
        if(scanner.getValue().equals("vor")){
            geheVor();
        }else if(scanner.getValue().equals("rechtsUm")){
            vC.getBiber().setRichtung(1);

        }else if(scanner.getValue().equals("linksUm")){
            vC.getBiber().setRichtung(-1);
        }
    }

    public void arbeiteAnBaum(){
        if(scanner.getValue().equals("pflanzen")){
            vC.getAktuellesFeld().erhoeheBaumAnzahl(1);
        }else if(scanner.getValue().equals("ernten")){
            if(vC.getAktuellesFeld().getBaumAnzahl() > 0 ){
                vC.getAktuellesFeld().erhoeheBaumAnzahl(-1);
            }else JOptionPane.showMessageDialog(null, "Hier ist nichts zum ernten");
        }
    }

    private void bearbeiteMethode(Methode methode){
        methode.tokenList.toFirst();
        while(methode.tokenList.hasAccess()) {
            if (methode.getValue().equals("vor")) {
                geheVor();
            } else if (methode.getValue().equals("rechtsUm")) {
                vC.getBiber().setRichtung(1);
            } else if (methode.getValue().equals("linksUm")) {
                vC.getBiber().setRichtung(-1);
            } else if (methode.getValue().equals("pflanzen")) {
                vC.getAktuellesFeld().erhoeheBaumAnzahl(1);
            } else if (methode.getValue().equals("ernten")) {
                if(vC.getAktuellesFeld().getBaumAnzahl() > 0 ){
                    vC.getAktuellesFeld().setBaumAnzahl(-1);
                }else JOptionPane.showMessageDialog(null, "Hier ist nichts zum ernten");
            }else if(methode.getType().equals("methodenaufruf")){
                bearbeiteMethode(scanner.getThis(methode.getValue()));
            }else if (methode.getType().equals("verzweigung")) {
                bearbeiteVerzweigungInMethode(methode);
            }
            methode.nextToken();
        }
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
        Verzweigung diese = scanner.getVerzweigungsInfo(Integer.parseInt(scanner.getValue()));
        if(!diese.bedingungpruefen()){
            for(int i = 0; i < diese.anzahlbefehle(); i++){
                scanner.nextToken();
            }
        }
    }
    private void bearbeiteVerzweigungInMethode(Methode methode){
        Verzweigung diese = scanner.getVerzweigungsInfo(Integer.parseInt(methode.getValue()));
        if(!diese.bedingungpruefen()){
            for(int i = 0; i < diese.anzahlbefehle(); i++){
                methode.nextToken();
            }
        }
    }
}
