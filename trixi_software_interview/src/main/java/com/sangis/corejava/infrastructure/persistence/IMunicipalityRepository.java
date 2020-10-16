package com.sangis.corejava.infrastructure.persistence;

import com.sangis.corejava.domain.core.models.BaseMunicipality;
import com.sangis.corejava.domain.core.models.BaseMunicipalityPart;

public interface IMunicipalityRepository {
        BaseMunicipality saveMunicipality(BaseMunicipality municipality) throws PersistenceException;
        BaseMunicipalityPart saveMunicipalityPart(BaseMunicipalityPart municipalityPart) throws PersistenceException;
        Iterable<BaseMunicipality> fetchMunicipalities() throws PersistenceException;
        Iterable<BaseMunicipalityPart> fetchMunicipalityPartsByMunicipalityCode(int municipalityCode)  throws PersistenceException;
}
