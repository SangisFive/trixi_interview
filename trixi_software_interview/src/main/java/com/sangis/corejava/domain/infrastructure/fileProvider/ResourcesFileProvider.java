package com.sangis.corejava.domain.infrastructure.fileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class ResourcesFileProvider implements IFileProvider {
    private final String filePath;
    public ResourcesFileProvider(String filePath){
        this.filePath = filePath;
    }
    public File getFile() throws IOException {
        try {
            URL resourceUrl = getClass().getResource(filePath);
            if (resourceUrl == null) {
                throw new FileNotFoundException();
            }
            URI uri = resourceUrl.toURI();

            return new File(uri);

        } catch (URISyntaxException e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }
}
