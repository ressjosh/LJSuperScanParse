package my_project.model;

import KAGO_framework.model.abitur.datenstrukturen.List;

public abstract class Scanner<TokenValue,TokenType> extends KrasseListe<TokenValue,TokenType>{

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


}
