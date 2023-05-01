package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.CompteService;
import com.derteuffel.gestioncommande.Services.ProductService;
import com.derteuffel.gestioncommande.entities.AddedProduct;
import com.derteuffel.gestioncommande.entities.Compte;
import com.derteuffel.gestioncommande.entities.Product;
import com.derteuffel.gestioncommande.helpers.PageModel;
import com.derteuffel.gestioncommande.repositories.AddedProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/produit")
public class ProduitController {

    @Autowired
    private ProductService productService;


    @Autowired
    private CompteService compteService;
    @Autowired
    private PageModel pageModel;

    @Autowired
    private AddedProductRepository addedProductRepository;

    final static List<String> categories = new ArrayList<>(Arrays.asList("category 1","category 2","category 3"));


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
        model.addAttribute("categories",categories);
        model.addAttribute("produit", new Product());

        return "produit/all";
    }

    //---------- Get All Products By category------------//

    @GetMapping("/produits/category")
    public String findAllByCategory(Model model, HttpServletRequest request, @RequestParam("category") String category){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());
        pageModel.setSIZE(5);
        pageModel.initPageAndSize();
        model.addAttribute("compte",compte);
        model.addAttribute("added_product",new AddedProduct());
        model.addAttribute("lists",productService.findAllByCategory("%"+category+"%"));
        model.addAttribute("categories", categories);
        model.addAttribute("produit", new Product());

        return "produit/all";
    }


    //---------- Get One Product ------------//

    @GetMapping("/detail/{id}")
    public String findOne(Model model, HttpServletRequest request, @PathVariable Long id){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());
        pageModel.setSIZE(5);
        pageModel.initPageAndSize();
        model.addAttribute("compte",compte);
        model.addAttribute("added_product",new AddedProduct());
        model.addAttribute("addedItems", addedProductRepository.findAllByProduct_ProductIdAndActionType(id,"AJOUT"));
        model.addAttribute("removedItems", addedProductRepository.findAllByProduct_ProductIdAndActionType(id,"RETRAIT"));
        model.addAttribute("produit",productService.getOne(id));
        model.addAttribute("categories", categories);
        return "produit/detail";
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
