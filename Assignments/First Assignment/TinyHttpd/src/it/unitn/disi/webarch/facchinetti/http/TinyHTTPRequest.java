package it.unitn.disi.webarch.facchinetti.http;

import it.unitn.disi.webarch.facchinetti.http.excpetion.HttpProtocolException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TinyHTTPRequest {

    private String method;
    private String path;
    private Map<String, String> queryStringParams;
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
            String completePath = splitted[1].replaceAll("\\.\\.", "").trim();

            this.computeQueryParams(completePath);

            this.path = completePath.split("\\?")[0];
            if(this.path.endsWith("/"))
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

    private void computeQueryParams(String completePath){

        this.queryStringParams = new HashMap<>();
        String[] splitted = completePath.split("\\?");
        if( splitted.length > 1) {
            String queryString = splitted[1];
            String[] params = queryString.split("&");
            for( int i = 0; i < params.length; i++ ){

                String[] param = params[i].split("=");
                if( param.length > 1 ){
                    this.queryStringParams.put(param[0], param[1]);
                }
            }
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

    public Map<String, String> getQueryStringParams() {
        return queryStringParams;
    }

    public void setQueryStringParams(Map<String, String> queryStringParams) {
        this.queryStringParams = queryStringParams;
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
