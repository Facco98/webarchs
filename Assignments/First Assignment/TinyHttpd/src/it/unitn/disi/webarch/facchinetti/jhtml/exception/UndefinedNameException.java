package it.unitn.disi.webarch.facchinetti.jhtml.exception;

public class UndefinedNameException extends Exception {
    public UndefinedNameException(String variableName) {
        super(variableName);
    }
}
