package com.sangis.corejava.domain.infrastructure.fileProvider;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class URLFileProvider implements IFileProvider {
    private final String fileUrl;

    public URLFileProvider(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public File getFile() throws IOException {
        URL url = new URL(fileUrl);
        BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
        File tempFile = File.createTempFile("File", getExtension(url.getFile()));
        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        byte[] dataBuffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
            fileOutputStream.write(dataBuffer, 0, bytesRead);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        return tempFile;
    }


    private String getExtension(String fileName) {
        String ext = "";

        int extensionStart = fileName.lastIndexOf(".");
        if (extensionStart > -1) {
            ext = fileName.substring(extensionStart);
        }
        return ext;
    }
}
