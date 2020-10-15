package com.sangis.corejava.domain.infrastructure.persistence;

import com.sangis.corejava.domain.core.models.BaseMunicipality;
import com.sangis.corejava.domain.core.models.BaseMunicipalityPart;

public class JDBCMunicipalityRepository implements IMunicipalityRepository {
    //TODO : implement persistence
    //TODO : test persistence
    public BaseMunicipality saveMunicipality(BaseMunicipality municipality) {
        return null;
    }

    public BaseMunicipalityPart saveMunicipalityPart(BaseMunicipalityPart municipalityPart) {
        return null;
    }

    public Iterable<BaseMunicipality> fetchMunicipalities() {
        return null;
    }

    public Iterable<BaseMunicipalityPart> fetchMunicipalityPartsByMunicipalityId(long municipalityId) {
        return null;
    }
}
