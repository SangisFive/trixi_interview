package com.sangis.corejava;

import com.sangis.corejava.domain.core.MunicipalityApplication;
import com.sangis.corejava.infrastructure.IMunicipalityProvider;
import com.sangis.corejava.infrastructure.MunicipalityProvider;
import com.sangis.corejava.infrastructure.fileProvider.FileProviderUnzipDecorator;
import com.sangis.corejava.infrastructure.fileProvider.URLFileProvider;
import com.sangis.corejava.infrastructure.fileProvider.IFileProvider;
import com.sangis.corejava.infrastructure.parser.MunicipalityParser;
import com.sangis.corejava.infrastructure.parser.XmlMunicipalityParser;
import com.sangis.corejava.infrastructure.persistence.IMunicipalityRepository;
import com.sangis.corejava.infrastructure.persistence.PostgreMunicipalityRepository;

public class ApplicationFactory {
    private static final String FILE_URL = "http://localhost:8080/files/20200930_OB_573060_UZSZ.xml.zip";
//    public static final String FILE_NAME = "20200930_OB_573060_UZSZ.xml.zip";

    //SQL
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "1234567";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/municipality_db";
    private static final boolean DB_SSL = true;

    public static MunicipalityApplication make(){
       return new MunicipalityApplicationImpl(ApplicationFactory.makeMunicipalityProvider(), makeMunicipalityRepository());
    }


    private static  IFileProvider makeFileProvider(){
        IFileProvider fileProvider = new URLFileProvider(FILE_URL);
//       IFileProvider fileProvider = new ResourcesFileProvider(FILE_NAME);
        fileProvider = new FileProviderUnzipDecorator(fileProvider);
        return fileProvider;
    }

     private static MunicipalityParser makeMunicipalityParser(){
        return new XmlMunicipalityParser();
    }

     private static IMunicipalityProvider makeMunicipalityProvider(){
        return new MunicipalityProvider(makeFileProvider(), makeMunicipalityParser());
    }

     private  static IMunicipalityRepository makeMunicipalityRepository(){
        return new PostgreMunicipalityRepository(DB_URL,DB_USERNAME, DB_PASSWORD, DB_SSL);
    }



}
