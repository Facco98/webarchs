package it.unitn.disi.webarch.facchinetti.http.handler;

import it.unitn.disi.webarch.facchinetti.http.HttpStatus;
import it.unitn.disi.webarch.facchinetti.http.TinyHTTPRequest;
import it.unitn.disi.webarch.facchinetti.http.TinyHTTPResponse;
import it.unitn.disi.webarch.facchinetti.jtml.exception.UndefinedNameException;
import it.unitn.disi.webarch.facchinetti.jtml.resolver.MapResolver;
import it.unitn.disi.webarch.facchinetti.jtml.resolver.NameResolver;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;

public class GETRequestHandler extends RequestHandler {

    private final Logger logger = Logger.getLogger("GETRequestHandler");

    @Override
    public TinyHTTPResponse handleRequest(TinyHTTPRequest request) throws IOException, UndefinedNameException, ClassNotFoundException, InterruptedException {
        if( request.getPath().startsWith("/process") ){
            return this.handleProcessRequest(request);
        } else{
            return this.handleFileRequest(request);
        }
    }

    private TinyHTTPResponse handleProcessRequest(TinyHTTPRequest request) throws IOException, InterruptedException {

        logger.info("Preparing to launch the process");
        File executableFile = new File(request.getPath().substring(1) + ".java");
        List<String> cmds = new ArrayList<>();
        cmds.add("java");
        cmds.add(executableFile.getName());

        for (Map.Entry<String, String> param : request.getQueryStringParams().entrySet()) {
            cmds.add("-" + param.getKey() + "=" + param.getValue());
        }

        ProcessBuilder pb = new ProcessBuilder(cmds);
        pb.directory(executableFile.getParentFile().getAbsoluteFile());
        pb.redirectErrorStream(true);
        Process p = pb.start();

        logger.info("Process started: " + executableFile.getAbsolutePath());

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;

        while( (line = reader.readLine()) != null ){
            sb.append(line).append("\n");
        }

        logger.info("Output received: " + sb.toString());

        p.waitFor();

        String result = sb.toString();
        Map<String, String> headers = RequestHandler.getPlainTextHeaders(result);
        return new TinyHTTPResponse(request.getVersion(), HttpStatus.OK, headers, result.getBytes(StandardCharsets.UTF_8));
    }

    private TinyHTTPResponse handleFileRequest(TinyHTTPRequest request) throws UndefinedNameException, ClassNotFoundException, IOException {

        try {
            File file = new File("htdocs/" + request.getPath());
            BufferedReader reader = new BufferedReader(new FileReader(file));


            String tmp = null;
            StringBuilder sb = new StringBuilder();

            while((tmp = reader.readLine()) != null) {
                sb.append(tmp.trim()).append("\n");
            }

            String rawBody = sb.toString();
            NameResolver resolver = new MapResolver();
            resolver.addName("date", new Date().toString(), String.class );
            String finalResult = this.interpretJHTML(rawBody, resolver);

            Map<String, String> headers = new HashMap<>();
            String contentType = "text/html";

            headers.put("Content-Type", contentType);
            headers.put("Content-Length", "" + finalResult.getBytes(StandardCharsets.UTF_8).length);

            return new TinyHTTPResponse(request.getVersion(), HttpStatus.OK, headers, finalResult);

        } catch(FileNotFoundException ex){
            Map<String, String> headers = this.getPlainTextHeaders("Not found");
            return new TinyHTTPResponse(request.getVersion(), HttpStatus.NOT_FOUND, headers, "Not found");
        } catch (InvocationTargetException | InstantiationException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
            Map<String, String> headers = this.getPlainTextHeaders("Internal Server Error: " +  e.getMessage());
            return new TinyHTTPResponse(request.getVersion(), HttpStatus.INTERNAL_SERVER_ERROR, headers, "Internal Server Error: " +  e.getMessage());

        }


    }

}
