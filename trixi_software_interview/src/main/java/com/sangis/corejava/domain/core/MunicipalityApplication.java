package com.sangis.corejava.domain.core;

import com.sangis.corejava.domain.core.models.BaseMunicipality;

import java.util.Iterator;

public abstract class MunicipalityApplication {
    protected  void initialize(){}
    protected abstract Iterator<BaseMunicipality> getMunicipalities();
    protected abstract void persistMunicipality(BaseMunicipality municipality);
    public final void run(){
        initialize();
        Iterator<BaseMunicipality> municipalities = getMunicipalities();
       while(municipalities.hasNext()){
           persistMunicipality(municipalities.next());
       }
    }
}
