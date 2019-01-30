package com.manju.ecomp.model.ves.v5.common;

public class Field {
    private String name;
    private String value;

    public Field() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Field{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
