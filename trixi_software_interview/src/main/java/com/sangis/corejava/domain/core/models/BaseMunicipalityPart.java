package com.sangis.corejava.domain.core.models;

import com.sun.istack.internal.NotNull;

public class BaseMunicipalityPart {
    protected final int code;
    protected final int municipalityCode;
    protected final String name;


    public  BaseMunicipalityPart(int code, int municipalityCode, String name) throws IllegalArgumentException {
        this.code = code;
        this.municipalityCode = municipalityCode;
        this.name = name;
        validate();
    }

    public int getMunicipalityCode() {
        return municipalityCode;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private void validate() throws IllegalArgumentException {
        if (code < 1 || name == null || municipalityCode < 1) throw new IllegalArgumentException();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseMunicipalityPart)) return false;

        BaseMunicipalityPart that = (BaseMunicipalityPart) o;

        return getCode() == that.getCode();
    }

    @Override
    public int hashCode() {
        return getCode();
    }
}
