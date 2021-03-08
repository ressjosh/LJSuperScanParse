package my_project.model;

import KAGO_framework.model.abitur.datenstrukturen.List;

public class LJScanner extends Scanner<String,String> {

    public LJScanner() {
    }

    //G = {
    //T ={h, k, l, p, w, s}
    //N = {A,B,C,D}
    //P={
    //A→ hB
    //B→ kC | lC
    //C→ p | pD | wD | D
    //D→ s
    //  h(k|l)(p|ws|s)

    @Override
    public boolean scan(String input) {
        if (input == null || input.length() == 0) {
            return false;
        }
        this.tokenList = new List();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == 'h') {
                this.tokenList.append(new Token(input.charAt(i), "hemd"));
            } else if (input.charAt(i) == 'k' || input.charAt(i) == 'l') {
                this.tokenList.append(new Token(input.charAt(i), "hose"));
            } else if (input.charAt(i) == 'p' || input.charAt(i) == 's') {
                    this.tokenList.append(new Token(input.charAt(i), "oberteil"));
            } else if (input.charAt(i) == 'w') {
                if(i<input.length()-1){
                    if(input.charAt(i+1) == 's'){
                        this.tokenList.append(new Token(input.substring(i,i+2),"kombi"));
                        i++;
                    } else return false;
                } else return false;
            }else return false;
        }
        this.tokenList.append(new Token("#", "NODATA"));
        tokenList.toFirst(); // WICHTIG!
        return true;
    }
}


