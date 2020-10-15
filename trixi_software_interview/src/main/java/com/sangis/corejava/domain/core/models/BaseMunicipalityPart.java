package com.sangis.corejava.domain.core.models;

public  class BaseMunicipalityPart {
    protected long code;
    protected long municipalityId;
    protected String name;

    public BaseMunicipalityPart(long code, long municipalityId, String name) {
        this.code = code;
        this.municipalityId = municipalityId;
        this.name = name;
    }

    public  long getCode() {
        return this.code;
    }
    public  String getName(){
        return this.name;
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
        return (int) (getCode() ^ (getCode() >>> 32));
    }
}
