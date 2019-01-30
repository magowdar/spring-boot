package com.manju.ecomp.model.ves.v5.measurement;

public class CodecsInUse {
    private String codecIdentifer;
    private int numberInUse;

    public CodecsInUse() {
    }

    public String getCodecIdentifer() {
        return codecIdentifer;
    }

    public void setCodecIdentifer(String codecIdentifer) {
        this.codecIdentifer = codecIdentifer;
    }

    public int getNumberInUse() {
        return numberInUse;
    }

    public void setNumberInUse(int numberInUse) {
        this.numberInUse = numberInUse;
    }

    @Override
    public String toString() {
        return "CodecsInUse{" +
                "codecIdentifer='" + codecIdentifer + '\'' +
                ", numberInUse=" + numberInUse +
                '}';
    }
}
