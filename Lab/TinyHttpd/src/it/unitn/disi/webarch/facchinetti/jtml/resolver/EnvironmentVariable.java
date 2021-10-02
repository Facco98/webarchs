package it.unitn.disi.webarch.facchinetti.jtml.resolver;

public class EnvironmentVariable<T> {

    private final Object value;
    private final Class<?> type;

    public EnvironmentVariable(Object value, Class<?> type) {
        this.value = value;
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public Class<?> getType() {
        return type;
    }
}
