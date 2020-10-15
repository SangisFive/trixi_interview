package com.sangis.corejava;

import com.sangis.corejava.domain.core.MunicipalityApplication;
import com.sangis.corejava.domain.infrastructure.IMunicipalityProvider;
import com.sangis.corejava.domain.infrastructure.MunicipalityProvider;
import com.sangis.corejava.domain.infrastructure.fileProvider.FileProviderUnzipDecorator;
import com.sangis.corejava.domain.infrastructure.fileProvider.URLFileProvider;
import com.sangis.corejava.domain.infrastructure.fileProvider.IFileProvider;
import com.sangis.corejava.domain.infrastructure.parser.MunicipalityParser;
import com.sangis.corejava.domain.infrastructure.parser.XmlMunicipalityParser;
import com.sangis.corejava.domain.infrastructure.persistence.IMunicipalityRepository;
import com.sangis.corejava.domain.infrastructure.persistence.JDBCMunicipalityRepository;

public class ApplicationFactory {
    public static final String FILE_URL = "http://localhost:8080/files/20200930_OB_573060_UZSZ.xml.zip";
    public static final String FILE_NAME = "20200930_OB_573060_UZSZ.xml.zip";

    public static MunicipalityApplication make(){
       return new MunicipalityApplicationImpl(ApplicationFactory.makeMunicipalityProvider(), makeMunicipalityRepository());
    }


    static private IFileProvider makeFileProvider(){
        IFileProvider fileProvider = new URLFileProvider(FILE_URL);
//       IFileProvider fileProvider = new ResourcesFileProvider(FILE_NAME);
        fileProvider = new FileProviderUnzipDecorator(fileProvider);
        return fileProvider;
    }

    static private MunicipalityParser makeMunicipalityParser(){
        return new XmlMunicipalityParser();
    }

    static private IMunicipalityProvider makeMunicipalityProvider(){
        return new MunicipalityProvider(makeFileProvider(), makeMunicipalityParser());
    }

    static private IMunicipalityRepository makeMunicipalityRepository(){
        return new JDBCMunicipalityRepository();
    }



}
