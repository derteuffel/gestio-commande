package com.derteuffel.gestioncommande.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;

@Component
public class StartLoader implements CommandLineRunner{

    @Value("${file.upload-dir}")
    String uploadPath;
    @Value("${file.produce-dir}")
    String producePath;






    @Override
    public void run(String... args) throws Exception {

        /*if (uploadPathAsFile.mkdirs()){
            System.out.println("------ Upload Path created -------");
        }else {
            System.out.println("------ Failed to create Path ------");
        }

        if (producePathAsFile.mkdirs()){
            System.out.println("------ Produce Path created ------- ");
        }else {
            System.out.println("------ Failed to create Path ------");
        }*/
    }
}
