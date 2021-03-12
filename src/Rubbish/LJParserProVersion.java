package Rubbish;

public class LJParserProVersion {

    private LJScannerProVersion scanner;

    public LJParserProVersion() {
        scanner = new LJScannerProVersion();
    }


    public boolean parse(String input) {
        int anzahl = 0;
        if(scanner.scan(input)) {
            if (scanner.getType().equals("hemd")) {
                scanner.nextToken();
                if (scanner.getType().equals("hose")) {
                    scanner.nextToken();
                    if (scanner.getType().equals("oberteil")) {
                        scanner.nextToken();
                        if (scanner.getType().equals("NODATA")) return true;
                    }else if (scanner.getType().equals("weste")) {
                        while(scanner.getType().equals("weste")){
                            anzahl++;
                            scanner.nextToken();
                        }
                        
                        if(anzahl < 2 ) return false;

                        while(scanner.getType().equals("sakko")){
                            anzahl--;
                            scanner.nextToken();
                        }
                        if (scanner.getType().equals("NODATA") && anzahl == 0) return true;
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
