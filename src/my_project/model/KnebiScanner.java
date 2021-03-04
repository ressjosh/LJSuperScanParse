package my_project.model;

import KAGO_framework.model.abitur.datenstrukturen.List;

/**
 * Diese Klasse scannt Strings für die Sprache L_Knebi = k(ne)*bi
 */
public class KnebiScanner extends Scanner<String,String> {

    private String debugOutput;

    @Override
    public boolean scan(String input) {
        debugOutput = "\nScanner recognized following tokens:\n";
        if(input == null || input.length() == 0){
            debugOutput+="Sorry, can't scan empty String.";
            return false;
        }
        this.tokenList = new List();
        for(int i = 0; i<input.length(); i++){
            if(input.charAt(i) == 'k'){
                this.tokenList.append(new Token(input.charAt(i),"START"));
                debugOutput+="START > ";
            } else if (input.charAt(i) == 'n'){
                if(i<input.length()-1){
                    if(input.charAt(i+1) == 'e'){
                        this.tokenList.append(new Token(input.substring(i,i+2),"MIDDLE"));
                        debugOutput+="MIDDLE > ";
                        i++;
                    } else return false;
                } else return false;
            } else if (input.charAt(i) == 'b'){
                if(i<input.length()-1){
                    if(input.charAt(i+1) == 'i'){
                        this.tokenList.append(new Token(input.substring(i,i+2),"END"));
                        debugOutput+="END > ";
                        i++;
                    } else return false;
                } else {
                    return false;
                }
            } else return false;
        }
        this.tokenList.append(new Token("#","NODATA"));
        tokenList.toFirst(); // WICHTIG!
        return true;
    }

    public String getDebugOutput(){
        return debugOutput;
    }
}
