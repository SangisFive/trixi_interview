package com.sangis.corejava;

import com.sangis.corejava.domain.core.MunicipalityApplication;
import com.sangis.corejava.domain.core.models.BaseMunicipality;
import com.sangis.corejava.domain.infrastructure.IMunicipalityProvider;
import com.sangis.corejava.domain.infrastructure.parser.MunicipalityParserException;
import com.sangis.corejava.domain.infrastructure.persistence.IMunicipalityRepository;
import com.sangis.corejava.domain.infrastructure.persistence.PersistenceException;
import com.sangis.corejava.utils.EmptyIterator;

import java.io.IOException;
import java.util.Iterator;

public class MunicipalityApplicationImpl extends MunicipalityApplication {
    private final IMunicipalityProvider provider;
    private  final IMunicipalityRepository repository;
    public MunicipalityApplicationImpl(IMunicipalityProvider provider, IMunicipalityRepository repository) {
        this.provider = provider;
        this.repository = repository;
    }


    private void onError(Exception e) {
        e.printStackTrace();
    }

    protected Iterator<BaseMunicipality> getMunicipalities() {
        try {
            return provider.getMunicipalities().iterator();
        } catch (IOException e) {
            onError(e);
        } catch (MunicipalityParserException e) {
            onError(e);
        }
        return new EmptyIterator<BaseMunicipality>();

    }

    protected void persistMunicipality(BaseMunicipality municipality) {
        try {
            repository.saveMunicipality(municipality);
        } catch (PersistenceException e) {
            onError(e);
        }
    }
}
