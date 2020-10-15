package com.sangis.corejava.domain.core.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public  class BaseMunicipality {
    protected final long code;
    protected final String name;
    protected Collection<BaseMunicipalityPart> parts = new ArrayList<BaseMunicipalityPart>();


    public BaseMunicipality(long code, String name){
        this.code = code;
        this.name = name;
    }

    public  long getCode() {
        return this.code;
    }
    public  String getName(){
        return this.name;
    }

    public Iterator<BaseMunicipalityPart> getParts(){
        return this.parts.iterator();
    }
    public  void addParts(Iterator<BaseMunicipalityPart> parts){
        while(parts.hasNext()){
            this.parts.add(parts.next());
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseMunicipality)) return false;

        BaseMunicipality that = (BaseMunicipality) o;

        return getCode() == that.getCode();
    }

    @Override
    public int hashCode() {
        return (int) (getCode() ^ (getCode() >>> 32));
    }
}
