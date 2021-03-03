package my_project.model;

import KAGO_framework.model.abitur.datenstrukturen.List;

public abstract class Scanner<TokenValue,TokenType> {

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

    }

    // Hinweis: doppelte Generik, da List generisch von Token abhängt und Token von den Datentypen von Wert und Typ.
    protected List<Token<TokenValue,TokenType>> tokenList;

    /**
     * Initialisiert die tokenList neu!
     * Scannt einen String, zerlegt ihn dabei in Tokens mit einem Typ und einem Wert gemäß den
     * generischen Typen der Scanner-Klasse und fügt diese Tokens hinten die Tokenliste an.
     * Im Anschluss wird der Zeiger der Tokenliste auf das erste Element gesetzt, damit ein
     * Parser seine Arbeit beginnen kann.
     * @param input der zu scannende String
     * @return true, sofern der Scan komplett erfolgreich war, sonst false.
     */
    public abstract boolean scan(String input);

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

}
