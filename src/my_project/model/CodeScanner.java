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

    public CodeScanner() {
        parser = new CodeParser(this);
    }

    @Override
    public boolean scan(String input) {
        if (input == null || input.length() == 0) {
            return false;
        }

        this.tokenList = new List();
        for (int i = 0; i < input.length(); i++) {
            if (i+4 < input.length() && (input.substring(i, i+5)).equals("start")) {
                System.out.println("Aktueller Index: " + i + "; " + i+4 + "Länge: " + input.length() + input);
                i = i+4;
                this.tokenList.append(new Token("start","Start"));
            }else if (i+3 < input.length() && (input.substring(i, i+4)).equals("ende")) {
                System.out.println("Hey");
                i = i+3;
                this.tokenList.append(new Token("ende","Ende"));
            }else if (i+6 < input.length() && (input.substring(i, i+7)).equals("befehle")) {
                this.tokenList.append(new Token("befehl","befehle"));
                i = i+6;
                System.out.println("Hey");
            }else if (i+4 < input.length() && (input.substring(i, i+5)).equals("vor()")) {
                System.out.println("Hey");
                i = i+4;
                this.tokenList.append(new Token("vor","bewegung"));
            }else if (i+8 < input.length() && (input.substring(i, i+9)).equals("linksUm()")) {
                this.tokenList.append(new Token("linksUm","bewegung"));
                i = i+8;
                System.out.println("Hey");
            }else if (i+9 < input.length() && (input.substring(i, i+10)).equals("rechtsUm()")) {
                this.tokenList.append(new Token("rechtsUm","bewegung"));
                i = i+9;
                System.out.println("Hey");
            }else if (input.charAt(i) == ' ') {
                System.out.println("leerzeichen");
            }else if (i+1 < input.length() && (input.substring(i, i+2)).equals("\\n")) {
                System.out.println("Zeilenumbruch");
                i++;
            }else return false;

        }
        this.tokenList.append(new Token("#","NODATA"));
        tokenList.toFirst(); // WICHTIG!
        return true;
    }

    private void scanneCode(){
        boolean fehlergefunden = false;
        if(scan(aktuelleBefehleString)){
            parser.parse();
        }else fehlergefunden = true;
        System.out.println("Scan ist" + scan(aktuelleBefehleString));
    }

    public void ankommendesStringAbarbeiten(String code){
        aktuelleBefehleString = leifereEinzelneZeilen(code);
        aktuelleBefehleString = aktuelleBefehleString.replaceAll("\n", " ");
        scanneCode();
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
}
