package it.unitn.disi.webarch.facchinetti.http;

public enum HttpStatus {

    OK(200, "OK"),
    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    NOT_FOUND(404, "Not found"),
    UNSUPPORTED_METHOD(405, "Unsupported method"),
    BAD_REQUEST(400, "Bad request");

    public final int code;
    public final String message;

    private HttpStatus(int code, String message){
        this.code = code;
        this.message = message;
    }


}
