package com.sangis.corejava.domain.infrastructure;

import com.sangis.corejava.domain.core.models.BaseMunicipality;
import com.sangis.corejava.domain.infrastructure.fileProvider.IFileProvider;
import com.sangis.corejava.domain.infrastructure.parser.MunicipalityParser;
import com.sangis.corejava.domain.infrastructure.parser.MunicipalityParserException;

import java.io.FileInputStream;
import java.io.IOException;


public class MunicipalityProvider implements IMunicipalityProvider {

    private final IFileProvider fileProvider;
    private final MunicipalityParser parser;
    public MunicipalityProvider(IFileProvider fileProvider, MunicipalityParser parser){
        this.fileProvider = fileProvider;
        this.parser = parser;
    }
    public Iterable<BaseMunicipality> getMunicipalities() throws IOException, MunicipalityParserException {
        return parser.parse(new FileInputStream(fileProvider.getFile()));
    }
}


