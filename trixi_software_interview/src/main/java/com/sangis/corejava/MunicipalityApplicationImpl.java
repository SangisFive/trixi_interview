package com.sangis.corejava;

import com.sangis.corejava.domain.core.MunicipalityApplication;
import com.sangis.corejava.domain.core.models.BaseMunicipality;
import com.sangis.corejava.infrastructure.IMunicipalityProvider;
import com.sangis.corejava.infrastructure.parser.MunicipalityParserException;
import com.sangis.corejava.infrastructure.persistence.IMunicipalityRepository;
import com.sangis.corejava.infrastructure.persistence.PersistenceException;
import com.sangis.corejava.utils.EmptyIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;

public class MunicipalityApplicationImpl extends MunicipalityApplication {
    private final IMunicipalityProvider provider;
    private final IMunicipalityRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(MunicipalityApplicationImpl.class);

    public MunicipalityApplicationImpl(IMunicipalityProvider provider, IMunicipalityRepository repository) {
        this.provider = provider;
        this.repository = repository;
    }


    private void onError(Exception e) {
        logger.error("Fatal:", e);
    }

    protected Iterator<BaseMunicipality> getMunicipalities() {
        try {
            Iterator<BaseMunicipality> municipalities = provider.getMunicipalities().iterator();
            logger.info("Municipalities have been successfully loaded");
            return municipalities;
        } catch (IOException | MunicipalityParserException e) {
            onError(e);
        }
        return new EmptyIterator<BaseMunicipality>();

    }

    protected void persistMunicipality(BaseMunicipality municipality) {
        try {
            BaseMunicipality saved = repository.saveMunicipality(municipality);
            logger.info("Municipality: " + saved.toString() + "\n has been successfully saved");
        } catch (PersistenceException e) {
            onError(e);
        }
    }
}
