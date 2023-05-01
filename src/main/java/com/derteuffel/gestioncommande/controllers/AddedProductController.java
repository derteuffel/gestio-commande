package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.ArticleService;
import com.derteuffel.gestioncommande.Services.CaisseService;
import com.derteuffel.gestioncommande.Services.CommandeService;
import com.derteuffel.gestioncommande.Services.ProductService;
import com.derteuffel.gestioncommande.entities.*;
import com.derteuffel.gestioncommande.repositories.AddedProductRepository;
import com.derteuffel.gestioncommande.repositories.CaisseRepository;
import com.derteuffel.gestioncommande.repositories.CommandeRepository;
import com.derteuffel.gestioncommande.repositories.MouvementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/added")
public class AddedProductController {

    @Autowired
    private AddedProductRepository addedProductRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommandeRepository commandeRepository;


    @PostMapping("/update/save/{productId}")
    public String saveAjout(AddedProduct addedProduct, @PathVariable Long productId){

        Product product = productService.getOne(productId);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        addedProduct.setAddedDate(sdf.format(new Date()));
        addedProduct.setActionType("AJOUT");
        addedProduct.setProduct(product);
        addedProduct.setValide(false);
        addedProductRepository.save(addedProduct);

        return "redirect:/produit/detail/"+product.getProductId();
    }
    @PostMapping("/update/save/retrait/{productId}")
    public String saveRetrait(AddedProduct addedProduct, @PathVariable Long productId){

        Product product = productService.getOne(productId);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        addedProduct.setAddedDate(sdf.format(new Date()));
        addedProduct.setActionType("RETRAIT");
        addedProduct.setValide(false);

        addedProduct.setProduct(product);
        addedProductRepository.save(addedProduct);

        return "redirect:/produit/detail/"+product.getProductId();
    }
    @GetMapping("/update/activate/{addedProductId}")
    public String updateAction(@PathVariable Long addedProductId){

        AddedProduct addedProduct = addedProductRepository.getOne(addedProductId);
        Product product = addedProduct.getProduct();
        addedProduct.setValide(true);

        if (addedProduct.getActionType().equals("AJOUT")){
            product.setQuantity(product.getQuantity() + addedProduct.getQuantity());
        }else {
            product.setQuantity(product.getQuantity() - addedProduct.getQuantity());
            Article article = new Article();
            Commande commande = new Commande();

            article.setProductCode(product.getProductCode());
            article.setDescription(addedProduct.getDescription());
            article.setMonnaie(addedProduct.getDevise());
            article.setName(product.getName());
            article.setQuantity(addedProduct.getQuantity());
            if (addedProduct.getDevise().equals("CDF")) {
                article.setTotalCDF(addedProduct.getTotalCost());
                article.setTotalUSD(addedProduct.getTotalCost()/addedProduct.getTauxDuJour());
            }else {
                article.setTotalCDF(addedProduct.getTotalCost() * addedProduct.getTauxDuJour());
                article.setTotalUSD(addedProduct.getTotalCost());
            }
            article.setPrice(addedProduct.getUnitCost());
            article.setMonnaie(addedProduct.getDevise());

            commande.setAmountCDF(article.getTotalCDF());
            commande.setAmountUSD(article.getTotalUSD());
            commande.setNbreArticle(commande.getNbreArticle() + 1);
            commande.setQuantity(article.getQuantity());

            Commande commandeSaved = commandeRepository.save(commande);
            articleService.save(article, commandeSaved.getCommandeId());


        }
        productService.save(product);
        addedProductRepository.save(addedProduct);

        return "redirect:/produit/detail/"+product.getProductId();
    }



}
