package my_project.model;

import KAGO_framework.model.abitur.datenstrukturen.List;

import javax.swing.*;
import javax.xml.stream.events.Characters;

/**
 * Diese Klasse scannt Strings für die Sprache L_Knebi = k(ne)*bi
 */
public class CodeScanner extends Scanner<String,String> {

    private String debugOutput;
    private String[] aktuelleBefehle;
    private String aktuelleBefehleString;
    private CodeParser parser;
    private List<Methode> methodenliste;

    public CodeScanner() {
        parser = new CodeParser(this);
        methodenliste = new List<>();
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
                while(aktuelleBefehleString.charAt(i) != '#') i++;
            }else if (i+6 < input.length() && (input.substring(i, i+7)).equals("process")) {
                int laenge = ermitteleMethodenkopf(i);
                this.tokenList.append(new Token(input.substring(i+8, i+laenge),"methodenaufruf"));
                i = i+laenge;
            }else if (input.charAt(i) == ' ') {

            }else return false;

        }
        this.tokenList.append(new Token("#","NODATA"));
        tokenList.toFirst(); // WICHTIG!
        return true;
    }

    private boolean scanneCode(){
        methodenliste = new List<>();
        this.tokenList = new List();
        if(scan(aktuelleBefehleString)){
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
        System.out.println(aktuelleBefehle.length);
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
        String input = aktuelleBefehleString;
        Methode newMethod = new Methode(methodenname);
        methodenliste.append(newMethod);
        int j = i;
        while (input.charAt(j) != '#' && j < input.length()) {
           if (j+4 < input.length() && (input.substring(j, j+5)).equals("vor()")) {
               j = j+4;
                newMethod.weitererBefehl("vor");
            }else if (j+8 < input.length() && (input.substring(j, j+9)).equals("linksUm()")) {
               j = j+8;
               newMethod.weitererBefehl("linksUm");
            }else if (j+9 < input.length() && (input.substring(j, j+10)).equals("rechtsUm()")) {
               j = j+9;
               newMethod.weitererBefehl("rechtsUm");
            }else if (j+9 < input.length() && (input.substring(j, j+10)).equals("pflanzen()")) {
               j = j+9;
               newMethod.weitererBefehl("pflanzen");
            }else if (j+7 < input.length() && (input.substring(j, j+8)).equals("ernten()")) {
               j = j+7;
               newMethod.weitererBefehl("ernten");
            }else if (j+6 < input.length() && (input.substring(j, j+7)).equals("process")) {
               int laenge = ermitteleMethodenkopf(j);
               newMethod.weitererBefehl(input.substring(j+8, j+laenge));
               j = j+laenge;
           }else if (input.charAt(j) == ' ') {

           }else return false;
            j++;
        }
        if(input.length() <= j) return false;
        return true;
    }

    public List<Methode> getMethodenliste(){
        return methodenliste;
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
        return new Verzweigung(this, null, 0, 0);
    }

    private boolean scanneUndParseBedingung(int i, int bedingugnsIndex){
        String input = aktuelleBefehleString;
        int tmp = 1;
        while (aktuelleBefehleString.charAt(i + tmp) == ' ') {
            tmp = tmp + 1;
        }
        if(tmp+9 < input.length() && (input.substring(tmp, tmp+10)).equals("nussHier()")){
            return scanneUndParseVerzweigung("nussHier", bedingugnsIndex);
        }else if(tmp+11 < input.length() && (input.substring(tmp, tmp+12)).equals("wandVoraus()")){
            return scanneUndParseVerzweigung("wandVoraus", bedingugnsIndex);
        }else{
            int beginn = tmp;
            int ende = 0;
            while (aktuelleBefehleString.charAt(i + tmp) != ' ') {
                tmp = tmp + 1;
            }
            ende = tmp - 1;
            char[] bedingungC = input.substring(beginn, ende+1).toCharArray();
            String bedingung = input.substring(beginn, ende+1);
            String operator = ermitteleOperator(bedingungC);
            if(operator == null) return false;
            String[] werte = bedingung.split(operator);
            if(ausZahlen(werte[0]) && ausZahlen(werte[1])){
                return scanneUndParseVerzweigung(bedingung, bedingugnsIndex);
            }else return false;
        }
    }

    private boolean scanneUndParseVerzweigung(String bedingung, int bedingugnsIndex){
        int tmp = bedingugnsIndex;
        while(aktuelleBefehleString.charAt(tmp) != '#') tmp++;
        //scan(aktuelleBefehleString)
        return false;
    }

    public boolean ausZahlen(String potentielleZahl){
        if(potentielleZahl.equals("nussZahl") || potentielleZahl.equals("nusseGesammelt")) return true;

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
}
