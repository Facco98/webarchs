package it.unitn.disi.webarch;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TinyHTTPd {

    public static void main(String[] args){
        int port = 8888;
        try {
            if (args.length > 0) port = Integer.parseInt(args[0]);
        } catch(NumberFormatException ex){
            System.out.println("Invalid port number, defaulting to " + port);
        }
        System.out.println("Server is listening on port " +port);
        try{
            ServerSocket ss = new ServerSocket(port);
            while(true) {
                Socket clientSocker = ss.accept();
                TinyHTTPConnection conn = new TinyHTTPConnection(clientSocker);
                Thread t = new Thread(conn);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Unexpected error occurred");
        }

    }

}
