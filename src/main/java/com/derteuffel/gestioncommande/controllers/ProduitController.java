package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.CategoryService;
import com.derteuffel.gestioncommande.Services.CompteService;
import com.derteuffel.gestioncommande.Services.ProductService;
import com.derteuffel.gestioncommande.entities.AddedProduct;
import com.derteuffel.gestioncommande.entities.Category;
import com.derteuffel.gestioncommande.entities.Compte;
import com.derteuffel.gestioncommande.entities.Product;
import com.derteuffel.gestioncommande.helpers.PageModel;
import com.derteuffel.gestioncommande.repositories.AddedProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/produit")
public class ProduitController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CompteService compteService;
    @Autowired
    private PageModel pageModel;

    @Autowired
    private AddedProductRepository addedProductRepository;


    //---------- Get All Products ------------//

    @GetMapping("/produits")
    public String all(Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());
        pageModel.setSIZE(5);
        pageModel.initPageAndSize();
        model.addAttribute("compte",compte);
        model.addAttribute("added_product",new AddedProduct());
        model.addAttribute("lists",productService.findAll());
        model.addAttribute("produit", new Product());
        model.addAttribute("categories",categoryService.findAll());

        return "produit/all";
    }


    @PostMapping("/save")
    public String save(Product product){

        productService.save(product);
        return "redirect:/produit/produits";
    }

    @PostMapping("/update")
    public String update(Long productId, String name, int quantity){
        Product product = productService.getOne(productId);
        if (!name.isEmpty())
        product.setName(name);
        if (quantity != 0)
        product.setQuantity(quantity);


        return "redirect:/produit/produits";
    }


    @GetMapping("/delete/{productId}")
    public String delete(@PathVariable Long productId){
        productService.deleteById(productId);
        return "redirect:/produit/produits";
    }
}
