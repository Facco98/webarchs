package it.unitn.disi.webarch.facchinetti.http.handler;

import it.unitn.disi.webarch.facchinetti.http.TinyHTTPRequest;
import it.unitn.disi.webarch.facchinetti.http.TinyHTTPResponse;
import it.unitn.disi.webarch.facchinetti.jtml.Processor;
import it.unitn.disi.webarch.facchinetti.jtml.exception.UndefinedNameException;
import it.unitn.disi.webarch.facchinetti.jtml.resolver.NameResolver;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RequestHandler {

    public abstract TinyHTTPResponse handleRequest(TinyHTTPRequest request) throws IOException, UndefinedNameException,
            ClassNotFoundException, InterruptedException;

    public static Map<String, String> getPlainTextHeaders(String message){

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/plain");
        headers.put("Content-Length", "" + (message.getBytes(StandardCharsets.UTF_8).length));
        return headers;

    }

    protected String interpretJHTML(String rawBody, NameResolver resolver) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, UndefinedNameException {

        Processor processor = new Processor(resolver);

        Pattern pattern = Pattern.compile("<jtml>(.*?)</jtml>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(rawBody);

        while(matcher.find()){
            for( int i = 0; i < matcher.groupCount(); i++ ) {
                Object r = processor.evaluate(matcher.group(i+1));
                String res = String.valueOf(r);
                rawBody = matcher.replaceFirst(res);
            }
            matcher = pattern.matcher(rawBody);
        }

        rawBody = rawBody.replaceAll("\\R", "");
        return rawBody;

    }

}
