package com.sangis.corejava.domain.core.models;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class BaseMunicipality {
    protected final int code;
    protected final String name;
    protected final Collection<BaseMunicipalityPart> parts = new HashSet<BaseMunicipalityPart>();


    public BaseMunicipality(int code, String name) throws IllegalArgumentException {
        this.code = code;
        this.name = name;
        validate();
    }
    public int getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
    public Iterator<BaseMunicipalityPart> getParts() {
        return parts.iterator();
    }

    public void addParts(Iterator<BaseMunicipalityPart> parts) {
        while (parts.hasNext()) {
            this.parts.add(parts.next());
        }
    }

    private void validate() throws IllegalArgumentException {
        if (code < 1 || name == null) throw new IllegalArgumentException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseMunicipality)) return false;

        BaseMunicipality that = (BaseMunicipality) o;

        return getCode() == that.getCode();
    }

    @Override
    public int hashCode() {
        return getCode();
    }
}
