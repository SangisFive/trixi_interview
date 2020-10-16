package com.sangis.corejava.infrastructure;

import com.sangis.corejava.domain.core.models.BaseMunicipality;
import com.sangis.corejava.infrastructure.fileProvider.IFileProvider;
import com.sangis.corejava.infrastructure.parser.MunicipalityParser;
import com.sangis.corejava.infrastructure.parser.MunicipalityParserException;

import java.io.File;
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
        File file = fileProvider.getFile();
        file.deleteOnExit();
        return parser.parse(new FileInputStream(file));
    }
}


