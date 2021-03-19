package my_project.model;

import KAGO_framework.model.abitur.datenstrukturen.List;
import com.sun.jdi.Value;

public abstract class KrasseListe<TokenValue,TokenType> {
    /**
     * Innere Klasse Token. Kapselt die Informationen beim Scannen in
     * Token-Objekten. Trivial, daher nicht weiter kommentiert.
     * @param <Value> Der Datentyp des WERTES, den das Token hat. Zumeist String oder char.
     * @param <Type> Der Datentyp, der für den TYP des Tokens verwendet wird. Z.B. String, int oder ein Enum.
     */
    protected class Token<Value,Type> {

        private Value value;
        private Type type;

        public Token(Value value, Type type){
            this.value = value;
            this.type = type;
        }

        public Value getValue(){
            return value;
        }

        public Type getType(){
            return type;
        }

        public void setValue(Value value) {
            this.value = value;
        }

        public void setType(Type type) {
            this.type = type;
        }
    }

    // Hinweis: doppelte Generik, da List generisch von Token abhängt und Token von den Datentypen von Wert und Typ.
    protected List<Token<TokenValue,TokenType>> tokenList;

    /**
     * Greift auf das aktuelle Token in der Token-Liste zu.
     * @return gibt den Wert des Tokens zurück, null, falls kein Token verfügbar
     */
    public TokenValue getValue(){
        if(tokenList.hasAccess()) {
            return tokenList.getContent().getValue();
        }
        return null;
    }

    /**
     * Greift auf das aktuelle Token in der Token-Liste zu.
     * @return gibt den Typ des Tokens zurück, null, falls kein Token verfügbar
     */
    public TokenType getType(){
        if(tokenList.hasAccess()) {
            return tokenList.getContent().getType();
        }
        return null;
    }

    /**
     * Greift auf das aktuelle Token in der Token-Liste zu.
     * Wechselt zum nächsten Element in der Liste (vgl. Dok. List).
     */
    public void nextToken(){
        tokenList.next();
    }

    public void setValue(TokenValue value){
        if (tokenList.hasAccess()){
            tokenList.getContent().setValue(value);
        }
    }

}

