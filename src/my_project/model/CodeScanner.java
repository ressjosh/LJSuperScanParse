package my_project.model;

import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.control.ViewControll;

import javax.swing.*;
import javax.xml.stream.events.Characters;


public class CodeScanner extends Scanner<String,String> {

    private String[] aktuelleBefehle;
    private String aktuelleBefehleString;
    private CodeParser parser;
    private List<Methode> methodenliste;
    private List<Verzweigung> verzweigungen;
    private ViewControll vC;

    public CodeScanner(ViewControll vC) {
        this.vC = vC;
        parser = new CodeParser(this);
        methodenliste = new List<>();
        verzweigungen = new List<>();
    }

    @Override
    public boolean scan(String input) {
        if (input == null || input.length() == 0) {
            return false;
        }
        for (int i = 0; i < input.length(); i++) {
            if (i+4 < input.length() && (input.substring(i, i+5)).equals("start")) {
                i = i+4;
                this.tokenList.append(new Token("start","start"));
            }else if (i+3 < input.length() && (input.substring(i, i+4)).equals("ende")) {
                i = i+3;
                this.tokenList.append(new Token("ende","ende"));
            }else if (i+6 < input.length() && (input.substring(i, i+7)).equals("befehle")) {
                this.tokenList.append(new Token("befehl","befehle"));
                i = i+6;
            }else if (i+4 < input.length() && (input.substring(i, i+5)).equals("vor()")) {
                i = i+4;
                this.tokenList.append(new Token("vor","bewegung"));
            }else if (i+8 < input.length() && (input.substring(i, i+9)).equals("linksUm()")) {
                this.tokenList.append(new Token("linksUm","bewegung"));
                i = i+8;
            }else if (i+9 < input.length() && (input.substring(i, i+10)).equals("rechtsUm()")) {
                this.tokenList.append(new Token("rechtsUm","bewegung"));
                i = i+9;
            }else if (i+9 < input.length() && (input.substring(i, i+10)).equals("pflanzen()")) {
                this.tokenList.append(new Token("pflanzen","baum"));
                i = i+9;
            }else if (i+7 < input.length() && (input.substring(i, i+8)).equals("ernten()")) {
                this.tokenList.append(new Token("ernten","baum"));
                i = i+7;
            }else if (i+6 < input.length() && (input.substring(i, i+7)).equals("methode")) {
                int laenge = ermitteleMethodenkopf(i);
                this.tokenList.append(new Token(input.substring(i+8, i+laenge),"methodenkopf"));
                if(!scanneUndParseMethodenRumpf(i+laenge+1, input.substring(i+8, i+laenge))) return false;
                i = i+7;
                int anzahlRauten = 1;
                while(anzahlRauten!= 0){
                    if (i+3 < input.length() && (input.substring(i, i+4)).equals("wenn")){
                        anzahlRauten++;
                    }else if(input.charAt(i) == '#'){
                        anzahlRauten--;
                    }
                    i++;
                }
            }else if (i+6 < input.length() && (input.substring(i, i+7)).equals("process")) {
                int laenge = ermitteleMethodenkopf(i);
                this.tokenList.append(new Token(input.substring(i+8, i+laenge),"methodenaufruf"));
                i = i+laenge;
            }else if (i+3 < input.length() && (input.substring(i, i+4)).equals("wenn")) {
                this.tokenList.append(new Token(""+i,"verzweigung"));
                if(!scanneUndParseBedingung(i+3, i, null)) return false;
                int anzahlRauten = 1;
                i = i+4;
                while(anzahlRauten!= 0){
                    if (i+3 < input.length() && (input.substring(i, i+4)).equals("wenn")){
                        anzahlRauten++;
                    }else if(input.charAt(i) == '#'){
                        anzahlRauten--;
                    }
                    i++;
                }
            }else if (i+2 < input.length() && (input.substring(i, i+3)).equals("sub")) {
                int laenge = laengeAddSub(i);
                this.tokenList.append(new Token(input.substring(i+4,i+laenge),"subtrahieren"));
                i = i+laenge;
            }else if (i+2 < input.length() && (input.substring(i, i+3)).equals("add")) {
                int laenge = laengeAddSub(i);
                this.tokenList.append(new Token(input.substring(i+4,i+laenge),"addieren"));
                i = i+laenge;
            }else if (i+8 < input.length() && (input.substring(i, i+9)).equals("functions")) {
                this.tokenList.append(new Token("methoden","methoden"));
                i = i+8;
            }else if (i+2 < input.length() && (input.substring(i, i+3)).equals("int")) {
                System.out.println("Int erkannt");
                int parameterTeile = ermitteleParameterElemente(i+3);
                System.out.println("so lang ist der Teil: " + parameterTeile +input.substring(i+3, parameterTeile));
                this.tokenList.append(new Token(input.substring(i+3, parameterTeile).trim(),"parameter"));
                i = parameterTeile-1;
            }else if (input.charAt(i) == ' ') {

            }else return false;

        }
        return true;
    }

    private int ermitteleParameterElemente(int i){
        String input = aktuelleBefehleString;
        int temp = i;
        while(aktuelleBefehleString.charAt(temp) == ' '){
            temp++;
        }
        while(aktuelleBefehleString.charAt(temp) != ' '){
            temp++;
        }
        while(aktuelleBefehleString.charAt(temp) == ' '){
            temp++;
        }
        while(aktuelleBefehleString.charAt(temp) != ' '){
            temp++;
        }
        return temp;
    }


    private boolean scanneCode(){
        methodenliste = new List<>();
        this.tokenList = new List();
        verzweigungen = new List<>();
        if(scan(aktuelleBefehleString)){
            this.tokenList.append(new Token("#","NODATA"));
            tokenList.toFirst();
            return parser.parse();
        }else return false;
    }

    public boolean ankommendesStringAbarbeiten(String code){
        aktuelleBefehleString = leifereEinzelneZeilen(code);
        aktuelleBefehleString = aktuelleBefehleString.replaceAll("\n", " ");
        return scanneCode();
    }

    private String leifereEinzelneZeilen(String code){
        aktuelleBefehle = code.split("//n");
        String tmp = "";
        for(int i = 0; i < aktuelleBefehle.length; i++){
            tmp = tmp + aktuelleBefehle[i];
        }
        return tmp;
    }

    private int ermitteleMethodenkopf(int i){
        int tmp = 7;
        while(aktuelleBefehleString.charAt(i+tmp) == ' '){
            tmp = tmp +1;
        }
        while(aktuelleBefehleString.charAt(i+tmp) != ' '){
            tmp = tmp +1;
        }
        return tmp;
    }

    private boolean scanneUndParseMethodenRumpf(int i, String methodenname){
        Methode newMethod = new Methode(methodenname);
        methodenliste.append(newMethod);
        return scanneMethode(i, newMethod);
    }

    private boolean scanneMethode(int i, Methode newMethod){
        String input = aktuelleBefehleString;
        int j = i;
        while (input.charAt(j) != '#' && j < input.length()) {
            if (j+4 < input.length() && (input.substring(j, j+5)).equals("vor()")) {
                j = j+4;
                newMethod.tokenList.append(new Token("vor","bewegung"));
            }else if (j+8 < input.length() && (input.substring(j, j+9)).equals("linksUm()")) {
                j = j+8;
                newMethod.tokenList.append(new Token("linksUm","bewegung"));
            }else if (j+9 < input.length() && (input.substring(j, j+10)).equals("rechtsUm()")) {
                j = j+9;
                newMethod.tokenList.append(new Token("rechtsUm","bewegung"));
            }else if (j+9 < input.length() && (input.substring(j, j+10)).equals("pflanzen()")) {
                j = j+9;
                newMethod.tokenList.append(new Token("pflanzen","baum"));
            }else if (j+7 < input.length() && (input.substring(j, j+8)).equals("ernten()")) {
                j = j+7;
                newMethod.tokenList.append(new Token("ernten","baum"));
            }else if (j+6 < input.length() && (input.substring(j, j+7)).equals("process")) {
                int laenge = ermitteleMethodenkopf(j);
                newMethod.tokenList.append(new Token(input.substring(j+8, j+laenge),"methodenaufruf"));
                j = j+laenge;
            }else if (j+3 < input.length() && (input.substring(j, j+4)).equals("wenn")) {
                newMethod.tokenList.append(new Token(""+j,"verzweigung"));
                if(!scanneUndParseBedingung(j+3, j, newMethod)) return false;
                j = j+4;
                int anzahlRauten = 1;
                while(anzahlRauten!= 0){
                    if (j+3 < input.length() && (input.substring(j, j+4)).equals("wenn")){
                        anzahlRauten++;
                    }else if(input.charAt(j) == '#'){
                        anzahlRauten--;
                    }
                    j++;
                }
            }else if (j+2 < input.length() && (input.substring(j, j+3)).equals("sub")) {
                int laenge = laengeAddSub(j);
                newMethod.tokenList.append(new Token(input.substring(j+4,j+laenge),"subtrahieren"));
                i = i+laenge;
            }else if (j+2 < input.length() && (input.substring(j, j+3)).equals("add")) {
                int laenge = laengeAddSub(j);
                newMethod.tokenList.append(new Token(input.substring(j+4,j+laenge),"addieren"));
                j = j+laenge;

            }else if (input.charAt(j) == ' ') {

            }else return false;
            j++;
        }
        if(input.length() <= j) return false;
        return true;
    }

    public Methode getThis(String methode){
        methodenliste.toFirst();

        while(methodenliste.hasAccess() && !methodenliste.getContent().getName().equals(methode) ){
            methodenliste.next();
        }
        if(methodenliste.getContent() == null)
            JOptionPane.showMessageDialog(null, "Die Methode: " + methode + " existiert leider nicht, überprüfe die korrekte Schreibweise \n und versuche es erneut!");
        return methodenliste.getContent();
    }

    public Verzweigung getVerzweigungsInfo(int index){
        verzweigungen.toFirst();
        while(verzweigungen.hasAccess() && verzweigungen.getContent().getMyIndex() != index){
            verzweigungen.next();
        }
        return verzweigungen.getContent();
    }

    private boolean scanneUndParseBedingung(int i, int bedingugnsIndex, Methode innerhalbMethode){
        String input = aktuelleBefehleString;
        int tmp = i+1;
        while (aktuelleBefehleString.charAt(tmp) == ' ') {
            tmp = tmp + 1;
        }
        if(tmp+7 < input.length() && (input.substring(tmp, tmp+8)).equals("nussHier")){
            return scanneUndParseVerzweigung("nussHier", bedingugnsIndex, innerhalbMethode);
        }else if(tmp+11 < input.length() && (input.substring(tmp, tmp+12)).equals("wandVoraus")){
            return scanneUndParseVerzweigung("wandVoraus", bedingugnsIndex, innerhalbMethode);
        }else{
            int beginn = tmp;
            int ende = 0;
            while (aktuelleBefehleString.charAt(tmp) != ' ') {
                tmp = tmp + 1;
            }
            ende = tmp - 1;
            char[] bedingungC = input.substring(beginn, ende+1).toCharArray();
            String bedingung = input.substring(beginn, ende+1);
            String operator = ermitteleOperator(bedingungC);
            if(operator == null) return false;
            String[] werte = bedingung.split(operator);
            if(ausZahlen(werte[0]) && ausZahlen(werte[1])){
                return scanneUndParseVerzweigung(bedingung, bedingugnsIndex, innerhalbMethode);
            }else return false;
        }
    }

    private boolean scanneUndParseVerzweigung(String bedingung, int bedingungsIndex, Methode innerhalbMethode){
        int tmp = bedingungsIndex+5 + bedingung.length();
        String input = aktuelleBefehleString;

        //TODO If-Verzweigung in If-Verzweigung testen, Grundlage ist hier geschaffen.
        int anzahlRauten = 1;
        while(anzahlRauten!= 0){
            if (tmp+3 < input.length() && (input.substring(tmp, tmp+4)).equals("wenn")){
                anzahlRauten++;
            }else if(input.charAt(tmp) == '#'){
                anzahlRauten--;
            }
            tmp++;
        }
        int anzahlbefehle = ermitteleAnzahlBefehle(aktuelleBefehleString.substring(bedingungsIndex+5 + bedingung.length(), tmp-1));
        verzweigungen.append(new Verzweigung(this, bedingung, anzahlbefehle, bedingungsIndex, vC));
        if(innerhalbMethode == null){
            return scan(aktuelleBefehleString.substring(bedingungsIndex+5 + bedingung.length(), tmp-1));
        }else{
            return scanneMethode(bedingungsIndex+5 + bedingung.length(), innerhalbMethode);
        }
    }

    public boolean ausZahlen(String potentielleZahl){
        if(potentielleZahl.equals("nussAnzahl") || potentielleZahl.equals("nusseGesammelt")) return true;

        //Todo Bei Parametereinabreitung hier unbedingt neue Listenüberprüfung beifügen!

        char[] tmp = potentielleZahl.toCharArray();
        for(int i = 0; i < tmp.length; i++){
            if(!Character.isDigit(tmp[i])) return false;
        }
        return true;
    }

    private String ermitteleOperator(char[] bedingung){
        int gefundeneOperatoren = 0;
        String operator = "";
        for(int i = 0; i <bedingung.length; i++){
            if(bedingung[i] == '='){
                if(bedingung[i+1] == '>'){
                    operator = "" + bedingung[i] + bedingung[i+1];
                    i++;
                }else if(bedingung[i+1] == '<'){
                    operator = "" + bedingung[i] + bedingung[i+1];
                    i++;
                }else{
                    operator = "" + bedingung[i];
                }
                gefundeneOperatoren++;
            }else if(bedingung[i] == '>'){
                operator = "" + bedingung[i];
                gefundeneOperatoren++;
            }else if(bedingung[i] == '<'){
                operator = "" + bedingung[i];
                gefundeneOperatoren++;
            }
        }
        if(gefundeneOperatoren == 1){
            return operator;
        }else return null;
    }

    private int ermitteleAnzahlBefehle(String s){
        //TODO Methodenaufrufe integrieren
        String[] befehlfolge = s.trim().split(" ");
        int anzahlBefehle = befehlfolge.length;
        return anzahlBefehle;
    }

    private int laengeAddSub(int i){
        int tmp = 3;
        while(aktuelleBefehleString.charAt(i+tmp) == ' '){
            tmp = tmp +1;
        }
        while(aktuelleBefehleString.charAt(i+tmp) != ' '){
            tmp = tmp +1;
        }
        return tmp;
    }
}
