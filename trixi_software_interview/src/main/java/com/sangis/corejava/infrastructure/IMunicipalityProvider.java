package com.sangis.corejava.infrastructure;

import com.sangis.corejava.domain.core.models.BaseMunicipality;
import com.sangis.corejava.infrastructure.parser.MunicipalityParserException;

import java.io.IOException;

public interface IMunicipalityProvider {
    Iterable<BaseMunicipality> getMunicipalities() throws IOException, MunicipalityParserException;
}
