package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.ProductService;
import com.derteuffel.gestioncommande.entities.AddedProduct;
import com.derteuffel.gestioncommande.entities.Product;
import com.derteuffel.gestioncommande.repositories.AddedProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/added")
public class AddedProductController {

    @Autowired
    private AddedProductRepository addedProductRepository;

    @Autowired
    private ProductService productService;


    @PostMapping("/update/{productId}")
    public String save(AddedProduct addedProduct, @PathVariable Long productId){

        Product product = productService.getOne(productId);
        product.setQuantity(product.getQuantity() + addedProduct.getQuantity());
        productService.save(product);
        addedProduct.setProduct(product);
        addedProductRepository.save(addedProduct);

        return "redirect:/produit/produits";
    }



}
