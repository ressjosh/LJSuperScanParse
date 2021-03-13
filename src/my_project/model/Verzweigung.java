package my_project.model;

public class Verzweigung{

    private String bedingung;
    private int index;
    private int bedingungsart;
    private int laenge;
    private CodeScanner scanner;

    public Verzweigung(CodeScanner scanner, String bedingung,int anzahlbefehle, int verzweigungsindex) {
        this.scanner = scanner;
        this.bedingung = bedingung;
        this.index = verzweigungsindex;
        this.laenge = anzahlbefehle;
        bedingungsArtErmitteln();
    }

    public boolean bedingungpruefen(){
        return false;
    }

    public int anzahlbefehle(){
        return laenge;
    }

    private void bedingungsArtErmitteln(){
        char[] tmp = bedingung.toCharArray();
        boolean operator = false;
        for(int i = 0; i < tmp.length; i++){
            if(tmp[i] == '=' || tmp[i] == '<' || tmp[i] == '>'){
                operator = true;
            }
        }

        if(operator == true){
            bedingungsart = 0;
        }else bedingungsart = 1;
    }
}
