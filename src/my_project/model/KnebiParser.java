package my_project.model;

public class KnebiParser implements Parser {

    private KnebiScanner scanner;

    public KnebiParser(){
        scanner = new KnebiScanner();
    }

    @Override
    /**
     * Diese Methode parst eine Eingabe und stellt fest, ob sie zur Sprache L_Knebi = k(ne)*bi gehört
     */
    public boolean parse(String input) {
        if(scanner.scan(input)) {
            if (scanner.getType().equals("START")) {
                scanner.nextToken();
                if (scanner.getType().equals("MIDDLE")) {
                    scanner.nextToken();
                    while (scanner.getType().equals("MIDDLE")) scanner.nextToken();
                    if (scanner.getType().equals("END")) {
                        scanner.nextToken();
                        if (scanner.getType().equals("NODATA")) return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    /**
     * Diese Methode dient dazu die Funktionalität des verwendeten Scanners zu überprüfen.
     * @return true, falls der Scanner für die Sprache des Parsers das Wort akzeptiert, sonst false
     */
    public boolean getScannerResult(String input) {
        return scanner.scan(input);
    }

    // ****************** AB HIER FOLGT EINE ÄQUIVALENTE VORGEHENSWEISE (für die Klasse redundant) ********************

    /**
     * Diese ist eine alternative Methode für das Parsen von L_Knebi = k(ne)*bi
     * @param input der zu parsenden String
     * @return true, falls die Eingabe ein Wort der Sprache ist, anderfalls false
     */
    public boolean alternativeParse(String input){
        if(scanner.scan(input)) {
            return checkS();
        }
        return false;
    }

    private boolean checkS(){
        if(scanner.getType().equals("START")) {
            scanner.nextToken();
            return checkA();
        }
        return false;
    }

    private boolean checkA(){
        if (scanner.getType().equals("MIDDLE")) {
            scanner.nextToken();
            while (scanner.getType().equals("MIDDLE")) scanner.nextToken();
            return checkB();
        }
        return false;
    }

    private boolean checkB(){
        if (scanner.getType().equals("END")) {
            scanner.nextToken();
            if (scanner.getType().equals("NODATA")) return true;
        }
        return false;
    }

    //************** Ausgabe der Scanner-Analyse zur Nachvollziehbarkeit *********

    public String getScannerOutput(){
        return scanner.getDebugOutput();
    }

}
