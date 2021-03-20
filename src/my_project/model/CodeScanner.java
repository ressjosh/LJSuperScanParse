package my_project.model;

import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.control.CentralControll;
import my_project.control.ViewControll;

import javax.swing.*;


public class CodeScanner extends Scanner<String,String> {

    private String[] aktuelleBefehle;
    private String aktuelleBefehleString;
    private CodeParser parser;
    private List<Methode> methodenliste;
    private List<BedingungsCode> bedingungsCodeliste;
    private ViewControll vC;
    private CentralControll cC;

    public CodeScanner(ViewControll vC, CentralControll cC) {
        this.cC = cC;
        this.vC = vC;
        parser = new CodeParser(this);
        methodenliste = new List<>();
        bedingungsCodeliste = new List<>();
    }

    @Override
    public boolean scan(String input) {
        if (input == null || input.length() == 0) {
            return false;
        }
        for (int i = 0; i < input.length(); i++) {
            if (i+4 < input.length() && (input.substring(i, i+5)).equals("start")) {
                i = i+4;
                this.tokenList.append(new Token<String, String>("start","start"));
            }else if (i+3 < input.length() && (input.substring(i, i+4)).equals("ende")) {
                i = i+3;
                this.tokenList.append(new Token<String, String>("ende","ende"));
            }else if (i+6 < input.length() && (input.substring(i, i+7)).equals("befehle")) {
                this.tokenList.append(new Token<String, String>("befehl","befehle"));
                i = i+6;
            }else if (i+4 < input.length() && (input.substring(i, i+5)).equals("vor()")) {
                i = i+4;
                this.tokenList.append(new Token<String, String>("vor","bewegung"));
            }else if (i+8 < input.length() && (input.substring(i, i+9)).equals("linksUm()")) {
                this.tokenList.append(new Token<String, String>("linksUm","bewegung"));
                i = i+8;
            }else if (i+9 < input.length() && (input.substring(i, i+10)).equals("rechtsUm()")) {
                this.tokenList.append(new Token<String, String>("rechtsUm","bewegung"));
                i = i+9;
            }else if (i+9 < input.length() && (input.substring(i, i+10)).equals("pflanzen()")) {
                this.tokenList.append(new Token<String, String>("pflanzen","baum"));
                i = i+9;
            }else if (i+7 < input.length() && (input.substring(i, i+8)).equals("ernten()")) {
                this.tokenList.append(new Token<String, String>("ernten","baum"));
                i = i+7;
            }else if (i+6 < input.length() && (input.substring(i, i+7)).equals("methode")) {
                int laenge = ermitteleMethodenkopf(i, input);
                this.tokenList.append(new Token<String, String>(input.substring(i+8, i+laenge),"methodenkopf"));
                if(!scanneUndParseMethodenRumpf(i+laenge+1, input.substring(i+8, i+laenge))) return false;
                i = i+7;
                int anzahlRauten = 1;
                while(anzahlRauten!= 0){
                    if ((i+3 < input.length() && (input.substring(i, i+4)).equals("wenn"))|| (i+6 < input.length() &&(input.substring(i, i+7)).equals("solange"))){
                        anzahlRauten++;
                    }else if(input.charAt(i) == '#'){
                        anzahlRauten--;
                    }
                    i++;
                }
            }else if (i+6 < input.length() && (input.substring(i, i+7)).equals("process")) {
                int laenge = ermitteleMethodenkopf(i,input);
                this.tokenList.append(new Token<String, String>(input.substring(i+8, i+laenge),"methodenaufruf"));
                i = i+laenge;
            }else if (i+3 < input.length() && (input.substring(i, i+4)).equals("wenn")) {
                this.tokenList.append(new Token<String, String>(""+i,"verzweigung"));
                if(!scanneUndParseBedingung(i+3, i, null, input, 5)) return false;
                int anzahlRauten = 1;
                i = i+4;
                while(anzahlRauten!= 0){
                    if ((i+3 < input.length() && (input.substring(i, i+4)).equals("wenn"))|| (i+6 < input.length() &&(input.substring(i, i+7)).equals("solange"))){
                        anzahlRauten++;
                    }else if(input.charAt(i) == '#'){
                        anzahlRauten--;
                    }
                    i++;
                }
            }else if (i+6 < input.length() && (input.substring(i, i+7)).equals("solange")) {
                this.tokenList.append(new Token<String, String>(""+i,"schleife"));
                if(!scanneUndParseBedingung(i+6, i, null, input, 8)) return false;
                int anzahlRauten = 1;
                i = i+7;
                while(anzahlRauten!= 0){
                    if ((i+3 < input.length() && (input.substring(i, i+4)).equals("wenn"))|| (i+6 < input.length() &&(input.substring(i, i+7)).equals("solange"))){
                        anzahlRauten++;
                    }else if(input.charAt(i) == '#'){
                        anzahlRauten--;
                    }
                    i++;
                }
            }else if (i+2 < input.length() && (input.substring(i, i+3)).equals("sub")) {
                int laenge = laengeAddSub(i, input);
                this.tokenList.append(new Token<String, String>(input.substring(i+4,i+laenge),"subtrahieren"));
                i = i+laenge;
            }else if (i+2 < input.length() && (input.substring(i, i+3)).equals("add")) {
                int laenge = laengeAddSub(i, input);
                this.tokenList.append(new Token<String, String>(input.substring(i+4,i+laenge),"addieren"));
                i = i+laenge;
            }else if (i+8 < input.length() && (input.substring(i, i+9)).equals("functions")) {
                this.tokenList.append(new Token<String, String>("methoden","methoden"));
                i = i+8;
            }else if (i+2 < input.length() && (input.substring(i, i+3)).equals("int")) {
                int parameterTeile = ermitteleParameterElemente(i+3);
                String[] tmp = input.substring(i+3, parameterTeile).trim().split(" ");
                cC.getInterpreter().getParameter().legeParameterAn(tmp[0], tmp[1]);
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
        cC.getInterpreter().parameterListeAufNull();
        methodenliste = new List<>();
        this.tokenList = new List<>();
        bedingungsCodeliste = new List<>();
        if(scan(aktuelleBefehleString)){
            this.tokenList.append(new Token<String, String>("#","NODATA"));
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

    private int ermitteleMethodenkopf(int i, String s){
        String input = s;
        int tmp = 7;
        while(input.charAt(i+tmp) == ' '){
            tmp = tmp +1;
        }
        while(input.charAt(i+tmp) != ' '){
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
                newMethod.tokenList.append(new Token<String, String>("vor","bewegung"));
            }else if (j+8 < input.length() && (input.substring(j, j+9)).equals("linksUm()")) {
                j = j+8;
                newMethod.tokenList.append(new Token<String, String>("linksUm","bewegung"));
            }else if (j+9 < input.length() && (input.substring(j, j+10)).equals("rechtsUm()")) {
                j = j+9;
                newMethod.tokenList.append(new Token<String, String>("rechtsUm","bewegung"));
            }else if (j+9 < input.length() && (input.substring(j, j+10)).equals("pflanzen()")) {
                j = j+9;
                newMethod.tokenList.append(new Token<String, String>("pflanzen","baum"));
            }else if (j+7 < input.length() && (input.substring(j, j+8)).equals("ernten()")) {
                j = j+7;
                newMethod.tokenList.append(new Token<String, String>("ernten","baum"));
            }else if (j+6 < input.length() && (input.substring(j, j+7)).equals("process")) {
                int laenge = ermitteleMethodenkopf(j, aktuelleBefehleString);
                newMethod.tokenList.append(new Token<String, String>(input.substring(j+8, j+laenge),"methodenaufruf"));
                j = j+laenge;
            }else if (j+3 < input.length() && (input.substring(j, j+4)).equals("wenn")) {
                newMethod.tokenList.append(new Token<String, String>(""+j,"verzweigung"));
                if(!scanneUndParseBedingung(j+3, j, newMethod, aktuelleBefehleString, 5)) return false;
                j = j+4;
                int anzahlRauten = 1;
                while(anzahlRauten!= 0){
                    if ((j+3 < input.length() && (input.substring(j, j+4)).equals("wenn"))|| (j+6 < input.length() &&(input.substring(j, j+7)).equals("solange"))){
                        anzahlRauten++;
                    }else if(input.charAt(j) == '#'){
                        anzahlRauten--;
                    }
                    j++;
                }
            }else if (j+6 < input.length() && (input.substring(j, j+7)).equals("solange")) {
                newMethod.tokenList.append(new Token<String, String>(""+j,"schleife"));
                if(!scanneUndParseBedingung(j+3, j, newMethod, input, 8)) return false;
                j = j+7;
                int anzahlRauten = 1;
                while(anzahlRauten!= 0){
                    if ((j+3 < input.length() && (input.substring(j, j+4)).equals("wenn"))|| (j+6 < input.length() &&(input.substring(j, j+7)).equals("solange"))){
                        anzahlRauten++;
                    }else if(input.charAt(j) == '#'){
                        anzahlRauten--;
                    }
                    j++;
                }
            }else if (j+2 < input.length() && (input.substring(j, j+3)).equals("sub")) {
                int laenge = laengeAddSub(j, input);
                newMethod.tokenList.append(new Token<String, String>(input.substring(j+4,j+laenge),"subtrahieren"));
                i = i+laenge;
            }else if (j+2 < input.length() && (input.substring(j, j+3)).equals("add")) {
                int laenge = laengeAddSub(j, input);
                newMethod.tokenList.append(new Token<String, String>(input.substring(j+4,j+laenge),"addieren"));
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

    public BedingungsCode getVerzweigungsInfo(int index){
        bedingungsCodeliste.toFirst();
        while(bedingungsCodeliste.hasAccess() && bedingungsCodeliste.getContent().getMyIndex() != index){
            bedingungsCodeliste.next();
        }
        return bedingungsCodeliste.getContent();
    }

    private boolean scanneUndParseBedingung(int i, int bedingugnsIndex, Methode innerhalbMethode, String s, int wortlange){
        String input = s;
        int tmp = i+1;
        while (input.charAt(tmp) == ' ') {
            tmp = tmp + 1;
        }
        if(tmp+7 < input.length() && (input.substring(tmp, tmp+8)).equals("nussHier")){
            return scanneUndParseVerzweigung("nussHier", bedingugnsIndex, innerhalbMethode, input, wortlange);
        }else if(tmp+11 < input.length() && (input.substring(tmp, tmp+12)).equals("wandVoraus")){
            return scanneUndParseVerzweigung("wandVoraus", bedingugnsIndex, innerhalbMethode, input, wortlange);
        }else{
            int beginn = tmp;
            int ende = 0;
            while (input.charAt(tmp) != ' ') {
                tmp = tmp + 1;
            }
            ende = tmp - 1;
            char[] bedingungC = input.substring(beginn, ende+1).toCharArray();
            String bedingung = input.substring(beginn, ende+1);
            String operator = ermitteleOperator(bedingungC);
            if(operator == null) return false;
            String[] werte = bedingung.split(operator);
            if(ausZahlen(werte[0]) && ausZahlen(werte[1])){
                return scanneUndParseVerzweigung(bedingung, bedingugnsIndex, innerhalbMethode, input, wortlange);
            }else return false;
        }
    }

    private boolean scanneUndParseVerzweigung(String bedingung, int bedingungsIndex, Methode innerhalbMethode, String s, int wortlange){
        int tmp = bedingungsIndex+wortlange + bedingung.length();
        String input = s;

        int anzahlRauten = 1;
        while(anzahlRauten!= 0){
            if ((tmp+3 < input.length() && (input.substring(tmp, tmp+4)).equals("wenn"))|| (tmp+6 < input.length() &&(input.substring(tmp, tmp+7)).equals("solange"))){
                anzahlRauten++;
            }else if(input.charAt(tmp) == '#'){
                anzahlRauten--;
            }
            tmp++;
        }
        int anzahlbefehle = ermitteleAnzahlBefehle(input.substring(bedingungsIndex+wortlange + bedingung.length(), tmp-1));
        bedingungsCodeliste.append(new BedingungsCode(this, cC.getInterpreter(), bedingung, anzahlbefehle, bedingungsIndex, vC));
        if(innerhalbMethode == null){
            return scan(input.substring(bedingungsIndex+wortlange + bedingung.length(), tmp-1));
        }else{
            return scanneMethode(bedingungsIndex+wortlange + bedingung.length(), innerhalbMethode);
        }
    }

    public boolean ausZahlen(String potentielleZahl){
        if(potentielleZahl.equals("nussAnzahl") || potentielleZahl.equals("nusseGesammelt")) return true;
        if(cC.getInterpreter().getParameter().istParameter(potentielleZahl)){
            return true;
        }
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
        String[] befehlfolge = s.trim().split(" ");
        int anzahlBefehle = 0;
        for(int i = 0; i < befehlfolge.length; i++){
            if(befehlfolge[i].equals("add")||befehlfolge[i].equals("sub")||befehlfolge[i].equals("wenn")
                    ||befehlfolge[i].equals("solange")||befehlfolge[i].equals("vor()")
                    ||befehlfolge[i].equals("pflanzen()")||
                    befehlfolge[i].equals("process") ||befehlfolge[i].equals("linksUm()")||
                    befehlfolge[i].equals("rechtsUm()")||befehlfolge[i].equals("ernten()")){
                anzahlBefehle++;
            }
        }
        return anzahlBefehle;
    }

    private int laengeAddSub(int i, String s){
        String input = s;
        int tmp = 3;
        while(input.charAt(i+tmp) == ' '){
            tmp = tmp +1;
        }
        while(input.charAt(i+tmp) != ' '){
            tmp = tmp +1;
        }
        return tmp;
    }

    public List<Token<String, String>> getTokenlist(){
        return tokenList;
    }
}
