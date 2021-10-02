package it.unitn.disi.webarch.facchinetti.jtml.resolver;


import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class MapResolver implements NameResolver {

    private final Map<String, EnvironmentVariable<?>> environment = new HashMap<>();
    private final Map<Class<?>, Class<?>> primitives;

    public MapResolver(){

        this.primitives = new HashMap<>();
        this.primitives.put(java.lang.Integer.class, int.class);
        this.primitives.put(java.lang.Double.class, double.class);

    }

    @Override
    public <T> T resolveName(String name, Class<T> resultClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        Object result = null;
        if( name.startsWith("\"") ){
            result = this.instantiate(name.replaceAll("\"", ""), resultClass).getValue();
        } else {
            result = this.resolveName(name);
        }
        return resultClass.cast(result);
    }

    @Override
    public Object resolveName(String name) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Object result = null;
         if( name.startsWith("\"") ){
            result = this.instantiate(name.replaceAll("\"", ""), String.class).getValue();
        } else if (Character.isDigit(name.charAt(0)) || name.startsWith("-")) {
            if(!name.contains(".")){
                result = Integer.parseInt(name);
            } else {
                result = Double.parseDouble(name);
            }
        } else if( name.contains(".") ){
             result= Class.forName(name);
         } else {
            result = this.environment.getOrDefault(name, new EnvironmentVariable<>(null, Void.class)).getValue();
        }
        return result;
    }

    public <T> EnvironmentVariable<T> instantiate(Object value, Class<T> resultClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Class<?> constructorParam = this.primitives.getOrDefault(value.getClass(), value.getClass());
        Object val = resultClass.getConstructor(constructorParam).newInstance(value);
        return new EnvironmentVariable<>(val, resultClass);

    }

    @Override
    public <T> void addName(String name, Object value, Class<T> type) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.environment.put(name, this.instantiate(value, type));
    }

}
