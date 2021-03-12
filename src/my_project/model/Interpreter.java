package my_project.model;

import KAGO_framework.model.abitur.datenstrukturen.List;

public class Interpreter {
     List tokenList;
     Scanner.Token token;
    public Interpreter(){
        tokenList = new List<Scanner.Token>();


    }
    public void interpretieren(List ttokenlist){
        ttokenlist.toFirst();
        for(int i = 0; !ttokenlist.isEmpty();i++){
            if(ttokenlist.getContent().equals(token)){

            }
        }

    }

}
