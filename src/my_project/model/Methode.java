package my_project.model;

import KAGO_framework.model.abitur.datenstrukturen.List;

public class Methode {
    private List<String> commands;
    private String methodenname;

    public Methode(String methodenname) {
        this.methodenname = methodenname;
        commands = new List<>();
    }

    public void weitererBefehl(String befehl){
        commands.append(befehl);
    }

    public void commandsToFirst(){
        commands.toFirst();
    }

    public String getCommand(){
        return commands.getContent();
    }

    public void nextCommand(){
        commands.next();
    }
}
