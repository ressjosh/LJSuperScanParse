package Rubbish;

import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.model.Scanner;

public class LJScannerProVersion extends Scanner<String,String> {

    public LJScannerProVersion() {



}

//h(k|l)(wnsn|p)

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
            } else if (input.charAt(i) == 'p') {
                this.tokenList.append(new Token(input.charAt(i), "oberteil"));
            } else if (input.charAt(i) == 'w') {
                this.tokenList.append(new Token(input.charAt(i), "weste"));
            } else if (input.charAt(i) == 's') {
                this.tokenList.append(new Token(input.charAt(i), "sakko"));
            }else return false;
        }
        this.tokenList.append(new Token("#", "NODATA"));
        tokenList.toFirst(); // WICHTIG!
        return true;
        }
}
