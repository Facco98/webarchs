package it.unitn.disi.webarch;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class TinyHTTPConnection implements Runnable {

    private Socket socket;

    private static final Logger logger = Logger.getLogger("TinyHttpConnection");

    public TinyHTTPConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        TinyHTTPRequest request = null;
        TinyHTTPResponse response = null;
        try {
            request = new TinyHTTPRequest(this.socket);
            logger.info("Received request: " + request);
            if( request.getMethod().equalsIgnoreCase("get") ){
                response = this.handleGetRequest(request);
                response.printTo(this.socket.getOutputStream());
            } else {
                String version = request.getVersion();
                Map<String, String> headers = this.getPlainTextHeaders("Unsupported method: " + request.getMethod());
                response = new TinyHTTPResponse(version, HttpStatus.BAD_REQUEST, headers, "Unsupported method: " + request.getMethod());
            }
        } catch (IOException e) {
            e.printStackTrace();
            String version = (request == null) ? "HTTP/1.1" : request.getVersion();
            Map<String, String> headers = this.getPlainTextHeaders("Internal server error: " + e.getMessage());
            response = new TinyHTTPResponse(version, HttpStatus.BAD_REQUEST, headers, "Internal server error: " + e.getMessage());

        } catch (HttpProtocolException e){
            e.printStackTrace();
            String version = "HTTP/1.1";
            Map<String, String> headers = this.getPlainTextHeaders("Bad request: " + e.getMessage());
            response = new TinyHTTPResponse(version, HttpStatus.BAD_REQUEST, headers, "Bad request: " + e.getMessage());
        }

        try {
            response.printTo(this.socket.getOutputStream());
            this.socket.getOutputStream().close();
            this.socket.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }


    }


    private TinyHTTPResponse handleGetRequest(TinyHTTPRequest request) throws IOException {

        try {
            File file = new File("htdocs/" + request.getPath());
            BufferedReader reader = new BufferedReader(new FileReader(file));

            Map<String, String> headers = new HashMap<>();
            String contentLength = "" + file.length();
            String contentType = "text/html";

            headers.put("Content-Type", contentType);
            headers.put("Content-Length", contentLength);

            StringBuilder sb = new StringBuilder();
            String tmp = null;

            while((tmp = reader.readLine()) != null)
                sb.append(tmp);

            return new TinyHTTPResponse(request.getVersion(), HttpStatus.OK, headers, sb.toString());

        } catch(FileNotFoundException ex){

            Map<String, String> headers = this.getPlainTextHeaders("Not found");
            return new TinyHTTPResponse(request.getVersion(), HttpStatus.NOT_FOUND, headers, "Not found");
        }

    }


    private Map<String, String> getPlainTextHeaders(String message){

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/plain");
        headers.put("Content-Length", "" + (message.getBytes(StandardCharsets.UTF_8).length));
        return headers;

    }
}
