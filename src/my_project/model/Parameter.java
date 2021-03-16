package my_project.model;

import KAGO_framework.model.abitur.datenstrukturen.List;

import javax.swing.*;

public class Parameter extends KrasseListe<Integer, String>{

    private CodeScanner scanner;

    public Parameter(CodeScanner scanner) {
        this.tokenList = new List();
        this.scanner = scanner;
    }

    public boolean addiere(String name){
        tokenList.toFirst();
        while (!getType().equals(name) && tokenList.hasAccess()){
            nextToken();
        }
        if (tokenList.hasAccess()){
            setValue(getValue()+1);
            return true;
        }
        return false;
    }

    public boolean subtrahiere(String name){
        tokenList.toFirst();
        while (!getType().equals(name) && tokenList.hasAccess()){
            nextToken();
        }
        if (tokenList.hasAccess()){
            setValue(getValue()-1);
            return true;
        }
        return false;
    }

    public boolean legeParameterAn(String name, String wert){
        String input = wert.trim();
        try {
            int temp = 0;
            char[] tmp = input.toCharArray();
            for(int i = 0; i < tmp.length; i++){
                if(!Character.isDigit(tmp[i])) return false;
            }
            temp = Integer.parseInt(input);
            tokenList.append(new Token<Integer,String>(temp,name));
            return true;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Der Wert des Parameters ist nicht akzeptabel.");
            return false;
        }
    }
}
