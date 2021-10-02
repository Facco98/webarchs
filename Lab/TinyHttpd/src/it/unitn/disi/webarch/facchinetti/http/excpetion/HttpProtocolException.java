package it.unitn.disi.webarch.facchinetti.http.excpetion;

public class HttpProtocolException extends Throwable {
    public HttpProtocolException(String protocol_error) {
        super(protocol_error);

    }
}
