package com.sangis.corejava.infrastructure.fileProvider;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileProviderUnzipDecorator implements IFileProvider {
    final protected IFileProvider fileProvider;

    public FileProviderUnzipDecorator(IFileProvider fileProvider) {
        this.fileProvider = fileProvider;
    }

    public File getFile() throws IOException {
        return unzip(fileProvider.getFile());
    }

    private File unzip(File file) throws IOException{

        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        if(zipEntry == null){
            throw new FileNotFoundException("No files in zipfile!");
        }   else {
            File unzippedTemporaryFile =  File.createTempFile("temp", ".xml");

            FileOutputStream fileOutputStream = new FileOutputStream(unzippedTemporaryFile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = zipInputStream.read(buffer))>0){
                fileOutputStream.write(buffer,0 , len);
            }
            zipInputStream.closeEntry();
            zipInputStream.close();

            fileOutputStream.flush();
            fileOutputStream.close();
            return unzippedTemporaryFile;
        }


    }
}
