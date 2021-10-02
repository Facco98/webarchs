package it.unitn.disi.webarch.facchinetti;

import it.unitn.disi.webarch.facchinetti.http.TinyHTTPConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Logger;

public class TinyHTTPd {

    private final static Logger logger = Logger.getLogger("TinyHTTPd");

    public static void main(String[] args){
        int port = 8888;
        try {
            if (args.length > 0) port = Integer.parseInt(args[0]);
        } catch(NumberFormatException ex){
            logger.warning("Invalid port number, defaulting to " + port);
        }
        logger.info("Server is listening on port " +port);
        try{
            ServerSocket ss = new ServerSocket(port);
            while(true) {
                Socket clientSocker = ss.accept();
                TinyHTTPConnection conn = new TinyHTTPConnection(clientSocker);
                Thread t = new Thread(conn);
                t.start();
            }
        } catch (IOException e) {

            logger.info("Unexpected error occurred " + Arrays.toString(e.getStackTrace()));
        }

    }

}
