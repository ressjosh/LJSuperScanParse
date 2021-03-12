package Rubbish;

public class LJParser {

    private LJScanner scanner;

    public LJParser() {
        scanner = new LJScanner();
    }


    public boolean parse(String input) {
        if(scanner.scan(input)) {
            if (scanner.getType().equals("hemd")) {
                scanner.nextToken();
                if (scanner.getType().equals("hose")) {
                    scanner.nextToken();
                    if (scanner.getType().equals("kombi") || scanner.getType().equals("oberteil") ) {
                        scanner.nextToken();
                        if (scanner.getType().equals("NODATA")) return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean getScannerResult(String input) {
        return scanner.scan(input);
    }

}
