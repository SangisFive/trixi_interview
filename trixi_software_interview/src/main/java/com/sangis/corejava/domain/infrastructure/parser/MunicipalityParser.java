package com.sangis.corejava.domain.infrastructure.parser;

import com.sangis.corejava.domain.core.models.BaseMunicipality;

import java.io.File;

public interface MunicipalityParser {
    Iterable<BaseMunicipality> parse(File file) throws MunicipalityParserException;
}
