package com.sangis.corejava.domain.infrastructure.persistence;

import com.sangis.corejava.domain.core.models.BaseMunicipality;
import com.sangis.corejava.domain.core.models.BaseMunicipalityPart;

public interface IMunicipalityRepository {
        BaseMunicipality saveMunicipality(BaseMunicipality municipality);
        BaseMunicipalityPart saveMunicipalityPart(BaseMunicipalityPart municipalityPart);
        Iterable<BaseMunicipality> fetchMunicipalities();
        Iterable<BaseMunicipalityPart> fetchMunicipalityPartsByMunicipalityId(long municipalityId);
}
