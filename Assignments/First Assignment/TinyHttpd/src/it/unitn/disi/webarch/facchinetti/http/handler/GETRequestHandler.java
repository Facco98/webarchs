package it.unitn.disi.webarch.facchinetti.http.handler;

import it.unitn.disi.webarch.facchinetti.http.HttpStatus;
import it.unitn.disi.webarch.facchinetti.http.TinyHTTPRequest;
import it.unitn.disi.webarch.facchinetti.http.TinyHTTPResponse;
import it.unitn.disi.webarch.facchinetti.jhtml.exception.UndefinedNameException;
import it.unitn.disi.webarch.facchinetti.jhtml.resolver.MapResolver;
import it.unitn.disi.webarch.facchinetti.jhtml.resolver.NameResolver;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GETRequestHandler extends RequestHandler {
    @Override
    public TinyHTTPResponse handleRequest(TinyHTTPRequest request) throws IOException, UndefinedNameException, ClassNotFoundException {
        if( request.getPath().startsWith("/process") ){
            return this.handleProcessRequest(request);
        } else{
            return this.handleFileRequest(request);
        }
    }

    private TinyHTTPResponse handleProcessRequest(TinyHTTPRequest request) throws IOException {

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

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;

        while( (line = reader.readLine()) != null ){
            sb.append(line).append("\n");
        }

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
