package com.uic.ahegde5.instrumentation.utility;

public class Pair {

    private String qualifiedName;
    private String value;

    public Pair() {
    }

    public Pair(String qualifiedName, String value) {
        this.qualifiedName = qualifiedName;
        this.value = value;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getQualifiedName() + " = " + getValue();
    }
}
