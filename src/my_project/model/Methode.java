package my_project.model;

import KAGO_framework.model.abitur.datenstrukturen.List;

public class Methode extends KrasseListe<String, String>{
    private String methodenname;

    public Methode(String methodenname) {
        this.tokenList = new List();
        this.methodenname = methodenname;
    }

    public String getName(){
        return methodenname;
    }
}
