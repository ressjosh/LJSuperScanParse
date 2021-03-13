package my_project.model;

public class CodeParser implements Parser {

    private CodeScanner scanner;

    public CodeParser(CodeScanner scan){
        scanner = scan;

    }

    @Override
    /**
     * Diese Methode parst eine Eingabe und stellt fest, ob sie zur Sprache L_Knebi = k(ne)*bi gehört
     */
    public boolean parse() {
        scanner.tokenList.toFirst();
        if(scanner.getType().equals("start")){
            scanner.nextToken();
            while(scanner.getType().equals("methodenkopf")){
                scanner.nextToken();
            }
            if(scanner.getType().equals("befehle")) {
                scanner.nextToken();
                while (scanner.getType().equals("bewegung") || scanner.getType().equals("baum") ||scanner.getType().equals("methodenaufruf")
                        || scanner.getType().equals("verzweigung")) {
                    scanner.nextToken();
                }
                if(scanner.getType().equals("ende")){
                    scanner.nextToken();
                    if(scanner.getType().equals("NODATA")) {
                        return true;
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

}
