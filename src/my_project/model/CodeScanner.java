package my_project.model;

import KAGO_framework.model.abitur.datenstrukturen.List;

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
        methodenliste = new List<>();
        this.tokenList = new List();
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
        return methodenliste.getContent();
    }
}
