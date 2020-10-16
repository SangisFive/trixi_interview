package com.sangis.corejava.domain.infrastructure.parser;

import com.sangis.corejava.domain.core.models.BaseMunicipality;

import java.io.File;
import java.io.InputStream;

public interface MunicipalityParser {
    Iterable<BaseMunicipality> parse(InputStream inputStream) throws MunicipalityParserException;
}
