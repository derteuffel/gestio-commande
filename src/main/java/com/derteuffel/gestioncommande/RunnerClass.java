package com.derteuffel.gestioncommande;

import com.derteuffel.gestioncommande.Services.ArticleService;
import com.derteuffel.gestioncommande.entities.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class RunnerClass implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(RunnerClass.class);

    @Value("${file.upload-dir}")
    String uploadPath;
    @Value("${file.produce-dir}")
    String producePath;





    @Autowired
    private ArticleService articleService;

    @Override
    public void run(String... args) throws Exception {
        File uploadPathAsFile = new File(uploadPath);
        File producePathAsFile = new File(producePath);

        if (uploadPathAsFile.mkdirs()){
            System.out.println("------ Upload Path created -------");
        }else {
            System.out.println("------ Failed to create Path ------");
        }

        if (producePathAsFile.mkdirs()){
            System.out.println("------ Produce Path created ------- ");
        }else {
            System.out.println("------ Failed to create Path ------");
        }
        /*
        Article article1= new Article();
        article1.setPrice(25.9);
        article1.setName("A3");
        article1.setQuantity(13);
        Article article2= new Article();
        article2.setPrice(24.9);
        article2.setName("A2");
        article2.setQuantity(13);
        Article article3= new Article();
        article3.setPrice(25.9);
        article3.setName("A5");
        article3.setQuantity(13);
        Article article4= new Article();
        article4.setPrice(25.9);
        article4.setName("B7");
        article4.setQuantity(13);
        Article article5= new Article();
        article5.setPrice(25.9);
        article5.setName("A3");
        article5.setQuantity(13);
        Article article6= new Article();
        article6.setPrice(25.9);
        article6.setName("A3");
        article6.setQuantity(13);

        articleService.save(article1);

        List<Article> articles=new ArrayList<>();
        articles.add(article2);
        articles.add(article3);
        articles.add(article4);
        articles.add(article5);
        articles.add(article6);

        articleService.saveAll(articles);*/


    }
}
