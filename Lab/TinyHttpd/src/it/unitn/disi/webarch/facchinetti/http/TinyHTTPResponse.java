package it.unitn.disi.webarch.facchinetti.http;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class TinyHTTPResponse {


    private String version;
    private HttpStatus status;
    private Map<String, String> headers;
    private byte[] body;

    public TinyHTTPResponse(String version, HttpStatus status, Map<String, String> headers, byte[] body) {
        this.version = version;
        this.status = status;
        this.headers = headers;
        this.body = body;
    }

    public TinyHTTPResponse(String version, HttpStatus status, Map<String, String> headers, String body) {
        this.version = version;
        this.status = status;
        this.headers = headers;
        this.body = body.getBytes(StandardCharsets.UTF_8);
    }


    public void printTo(OutputStream os) throws IOException {

        StringBuilder sb = new StringBuilder();
        sb.append(version).append(" ").append(status.code).append(" ").append(status.message).append("\r\n");
        if (this.headers != null) {
            for (Map.Entry<String, String> entry : this.headers.entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
            }
            sb.append("\r\n");
        }
        os.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        os.flush();
        os.write(this.body);
        os.flush();
        os.write("\r\n\r\n".getBytes(StandardCharsets.UTF_8));
        os.flush();
    }
}
