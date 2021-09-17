package it.unitn.disi.webarch;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TinyHTTPRequest {

    private String method;
    private String path;
    private String version;
    private Map<String, String> headers;


    public TinyHTTPRequest(Socket socket) throws IOException, HttpProtocolException {

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String firstLine = br.readLine();
        if( firstLine == null ){
            throw new HttpProtocolException("Protocol error in first line");
        }
        try {
            String[] splitted = firstLine.split(" ");
            this.method = splitted[0].trim();
            this.path = splitted[1].trim();
            if(path.endsWith("/"))
                this.path = this.path + "index.html";
            this.version = splitted[2].trim();
        } catch(Exception ex){
            throw new HttpProtocolException("Protocol error in first line");
        }

        try{

            String header = null;
            this.headers = new HashMap<>();
            while((header = br.readLine()) != null && header.length() > 0){

                String[] splitted = header.split(":");
                this.headers.put(splitted[0].toLowerCase(), splitted[1]);

            }
        } catch(Exception ex){
            throw new HttpProtocolException("Protocol error in headers");
        }


    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getHeader(String name){

        return this.headers.get(name.toLowerCase());

    }

    @Override
    public String toString() {
        String res = "{\n\t" + this.getMethod() + " " + this.getPath() + " " + this.getVersion() + "\n";
        if( this.headers != null ) {
            for (Map.Entry<String, String> entry : this.headers.entrySet()) {
                res += "\t" + entry.getKey() + ": " + entry.getValue() + "\n";
            }
        }
        res += "}\n";
        return res;
    }
}
