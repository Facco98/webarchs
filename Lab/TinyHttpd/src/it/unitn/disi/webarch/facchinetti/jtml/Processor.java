package it.unitn.disi.webarch.facchinetti.jtml;


import it.unitn.disi.webarch.facchinetti.jtml.exception.UndefinedNameException;
import it.unitn.disi.webarch.facchinetti.jtml.resolver.NameResolver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Processor {

    private NameResolver nameResolver;

    public Processor(NameResolver nameResolver){
        this.nameResolver = nameResolver;
    }

    public Object evaluate(String code) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, UndefinedNameException {

        String[] lines = code.split("\n");

        Object output = "";
        for( String line : lines ){

            if( line.trim().length() > 0 ){
                String[] equalSplitted = line.split("=");
                if( equalSplitted.length >= 2 ){
                    this.performAssignment(equalSplitted[0], equalSplitted[1]);
                } else {
                    output = this.evaluateMethodCall(line);
                }

            }

        }
        return output;

    }

    private void performAssignment(String leftHand, String rightHand) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, UndefinedNameException {

        Variable lValue = this.parseVariable(leftHand);
        Object rValue = this.evaluateMethodCall(rightHand.trim());

        this.nameResolver.addName(lValue.getName(), rValue, lValue.getType());

    }

    private Object evaluateMethodCall(String line) throws ClassNotFoundException, UndefinedNameException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        String[] names = line.split("->");
        Variable variable = this.parseVariable(names[0]);
        Optional<?> resolvedVariable;
        if( variable.getType() != null ) {
            resolvedVariable = Optional.ofNullable(this.nameResolver.resolveName(variable.getName(), variable.getType()));
        } else {
            resolvedVariable = Optional.ofNullable(this.nameResolver.resolveName(variable.getName()));
        }
        if (resolvedVariable.isEmpty()) {
            throw new UndefinedNameException(variable.getName());
        }
        Object o = resolvedVariable.get();
        for (int i = 1; i < names.length; i++) {

            String methodName = names[i].split("\\(")[0].trim();
            Pattern pattern = Pattern.compile("\\((.*?)\\)", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(names[i]);
            matcher.find();
            List<Object> tmp = Arrays.asList(matcher.group(1).split(","));
            ArrayList<Object> params = new ArrayList<>(tmp);
            params.removeIf(el -> ((String) el).trim().equals(""));
            for (int j = 0; j < params.size(); j++) {
                Variable v = this.parseVariable((String) params.get(j));
                if( v.getType() != null ) {
                    params.set(j, this.nameResolver.resolveName(v.getName(), v.getType()));
                } else {
                    params.set(j, this.nameResolver.resolveName(v.getName()));
                }
            }
            boolean invoked = false;
            boolean entered = false;
            Class<?> c = o.getClass();
            if( o instanceof Class )
                c = (Class<?>) o;
            for (Method m : c.getMethods()) {
                entered = true;
                if (m.getName().equals(methodName) && !invoked) {
                    try {
                        if (params.size() > 0)
                            o = m.invoke(o, params.toArray());
                        else
                            o = m.invoke(o);
                        invoked = true;
                    } catch (Exception ignored) {
                    }
                }
            }
            if (!invoked && entered)
                throw new UndefinedNameException("Method name not defined: [" + methodName + "]");
        }
        return o;

    }

    private Variable parseVariable(String str) throws ClassNotFoundException {

        String[] variable = str.split(":");
        String variableName = variable[0].trim();
        Class<?> c = null;
        if( variable.length > 1 )
            c = Class.forName(variable[1].trim());

        return new Variable(variableName, c);

    }

}
