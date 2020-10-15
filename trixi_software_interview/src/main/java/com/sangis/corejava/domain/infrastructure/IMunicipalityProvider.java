package com.sangis.corejava.domain.infrastructure;

import com.sangis.corejava.domain.core.models.BaseMunicipality;
import com.sangis.corejava.domain.infrastructure.parser.MunicipalityParserException;

import java.io.IOException;

public interface IMunicipalityProvider {
    Iterable<BaseMunicipality> getMunicipalities() throws IOException, MunicipalityParserException;
}
