package com.manju.ecomp.model.ves.v5.common;

import java.util.List;

public class NamedArrayOfFields {
    String name;
    List<Field> fields;

    public NamedArrayOfFields() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "NamedArrayOfFields{" +
                "name='" + name + '\'' +
                ", fields=" + fields +
                '}';
    }
}
