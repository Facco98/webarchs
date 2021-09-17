package it.unitn.disi.webarch;

public class HttpProtocolException extends Throwable {
    public HttpProtocolException(String protocol_error) {
        super(protocol_error);

    }
}
