package it.unitn.disi.webarch.facchinetti.jtml.resolver;

import java.lang.reflect.InvocationTargetException;

public interface NameResolver {

    <T> T resolveName(String name, Class<T> resultClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException;

    Object resolveName(String name) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException;

    <T> void addName(String name, Object value, Class<T> type) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

}
