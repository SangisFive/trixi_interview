package com.sangis.javacore.springdemo.web.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.Files;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("files")
public class XmlFileController {
     private static final String FOLDER_PATH = "classpath:data";
     private static final String FILENAME = "20200930_OB_573060_UZSZ.xml.zip";
     protected final ResourceLoader resourceLoader;
     public  XmlFileController(ResourceLoader resourceLoader){
          this.resourceLoader = resourceLoader;
     }
     @GetMapping(produces = "application/zip", value = FILENAME)
     public byte[] get() throws IOException {
          Resource resource = resourceLoader.getResource(String.format("%s/%s",FOLDER_PATH, FILENAME));
          try {
               File file = resource.getFile();
               return Files.readAllBytes(file.toPath());
          } catch (IOException e) {
               e.printStackTrace();
               throw  e;
          }
     }

}
