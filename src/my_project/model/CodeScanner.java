package my_project.model;

import KAGO_framework.model.abitur.datenstrukturen.List;

/**
 * Diese Klasse scannt Strings für die Sprache L_Knebi = k(ne)*bi
 */
public class CodeScanner extends Scanner<String,String> {

    private String debugOutput;
    private String[] aktuelleBefehle;
    private CodeParser parser;

    public CodeScanner() {
        parser = new CodeParser();
    }

    @Override
    public boolean scan(String input) {
        if (input == null || input.length() == 0) {
            return false;
        }

        this.tokenList = new List();
        for (int i = 0; i < input.length(); i++) {
            if (i+4 < input.length() && (input.substring(i, i+5)).equals("start")) {
                System.out.println("Hey");
            }else
            if (i+3 < input.length() && (input.substring(i, i+4)).equals("ende")) {
                System.out.println("Hey");
            }else
            if (i+6 < input.length() && (input.substring(i, i+7)).equals("befehle")) {
                System.out.println("Hey");
            }else
            if (i+4 < input.length() && (input.substring(i, i+5)).equals("vor()")) {
                System.out.println("Hey");
            }else
            if (i+8 < input.length() && (input.substring(i, i+9)).equals("linksUm()")) {
                System.out.println("Hey");
            }else
            if (i+9 < input.length() && (input.substring(i, i+10)).equals("rechtsUm()")) {
                System.out.println("Hey");
            }else
            if (input.charAt(i) == 0) {
                System.out.println("Hey");
            }else return false;

        }
        this.tokenList.append(new Token("#","NODATA"));
        tokenList.toFirst(); // WICHTIG!
        return true;
    }

    private void scanneEinzelneZeilen(){
        boolean fehlergefunden = false;
        for(int i = 0; i < aktuelleBefehle.length && !fehlergefunden;i++){
            if(scan(aktuelleBefehle[i])){
                parser.parse(aktuelleBefehle[i]);
            }else fehlergefunden = true;
        }
    }

    public void ankommendesStringAbarbeiten(String code){
        leifereEinzelneZeilen(code);
        scanneEinzelneZeilen();
        System.out.println("Hello");
    }

    private String[] leifereEinzelneZeilen(String code){
        aktuelleBefehle = code.split("/n");
        return aktuelleBefehle;
    }
}
