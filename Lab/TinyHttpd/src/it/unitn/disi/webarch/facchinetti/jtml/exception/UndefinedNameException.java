package it.unitn.disi.webarch.facchinetti.jtml.exception;

public class UndefinedNameException extends Exception {
    public UndefinedNameException(String variableName) {
        super(variableName);
    }
}
