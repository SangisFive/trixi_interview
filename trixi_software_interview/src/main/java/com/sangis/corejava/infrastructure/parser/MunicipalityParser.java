package com.sangis.corejava.infrastructure.parser;

import com.sangis.corejava.domain.core.models.BaseMunicipality;

import java.io.InputStream;

public interface MunicipalityParser {
    Iterable<BaseMunicipality> parse(InputStream inputStream) throws MunicipalityParserException;
}
