package com.derteuffel.controllers;


import com.derteuffel.entities.Product;
import com.derteuffel.services.CategoryService;
import com.derteuffel.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listProduct")
    public String listProduct(Model model){
        model.addAttribute("products", productService.findAllProduct());
        return "product/products";
    }

    @GetMapping("/form")
    public String formProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAllCategory());
        return "product/form";
    }

    @PostMapping("/save")
    public String saveProduct(Product product){
        productService.saveOrUpdateProduct(product);
        return "redirect:/product/listProduct";
    }

    @GetMapping("/{idProduct}")
    public String deleteProduct(@PathVariable Long idProduct, Model model){
        Product product = productService.findProductByIdProduct(idProduct);
        productService.deleteProduct(idProduct);
        model.addAttribute("products", productService.findAllProduct());
        return "product/products";
    }

    @GetMapping("/detail/{idProduct}")
    public String detailProduct (@PathVariable Long idProduct, Model model)
    {
        Product product = productService.findProductByIdProduct(idProduct);
        model.addAttribute("product", product);
        return"product/detail";

    }

    @GetMapping("/edit/{idProduct}")
    public String showUpdateForm(@PathVariable("idProduct") long idProduct, Model model) {
        Product product = productService.findProductByIdProduct(idProduct);
        //.orElseThrow(() -> new IllegalArgumentException("Invalid  product Id:" + idProduct));

        model.addAttribute("products", product);
        return "product/edit";
    }


    @PostMapping("/update/{idProduct}")
    public String updateProduct(@PathVariable("idProduct") long idProduct, @Valid Product product,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            product.setIdProduct(idProduct);
            return "product/form";
        }

        productService.saveOrUpdateProduct(product);
        model.addAttribute("produit", productService.findAllProduct());
        return "product/products";
    }
}
