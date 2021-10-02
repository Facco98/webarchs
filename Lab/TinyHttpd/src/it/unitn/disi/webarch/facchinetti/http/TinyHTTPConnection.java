package it.unitn.disi.webarch.facchinetti.http;

import it.unitn.disi.webarch.facchinetti.http.excpetion.HttpProtocolException;
import it.unitn.disi.webarch.facchinetti.http.handler.GETRequestHandler;
import it.unitn.disi.webarch.facchinetti.http.handler.RequestHandler;
import it.unitn.disi.webarch.facchinetti.jtml.exception.UndefinedNameException;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
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
        TinyHTTPResponse response;
        try {
            request = new TinyHTTPRequest(this.socket);
            logger.info("Received request: " + request);
            RequestHandler handler = null;
            if( request.getMethod().equalsIgnoreCase("get") ){
                handler = new GETRequestHandler();
            }
            if ( handler != null ){
                response = handler.handleRequest(request);
            } else {
                String version = request.getVersion();
                Map<String, String> headers = RequestHandler.getPlainTextHeaders("Unsupported method: " + request.getMethod());
                response = new TinyHTTPResponse(version, HttpStatus.BAD_REQUEST, headers, "Unsupported method: " + request.getMethod());
            }
        } catch (IOException | UndefinedNameException | ClassNotFoundException | InterruptedException e) {
            logger.info(Arrays.toString(e.getStackTrace()));
            String version = (request == null) ? "HTTP/1.1" : request.getVersion();
            String message = "Internal server error: " + e.getMessage();
            Map<String, String> headers = RequestHandler.getPlainTextHeaders(message);
            response = new TinyHTTPResponse(version, HttpStatus.INTERNAL_SERVER_ERROR, headers, message);
        } catch (HttpProtocolException e){
            String version = "HTTP/1.1";
            String msg = "Bad request: " + e.getMessage();
            Map<String, String> headers = RequestHandler.getPlainTextHeaders(msg);
            response = new TinyHTTPResponse(version, HttpStatus.BAD_REQUEST, headers, msg);
        }

        try {
            response.printTo(this.socket.getOutputStream());
            this.socket.getOutputStream().close();
            this.socket.close();
        } catch (Exception ex){
            logger.warning("Error: " + ex.getMessage());
        }


    }

}
