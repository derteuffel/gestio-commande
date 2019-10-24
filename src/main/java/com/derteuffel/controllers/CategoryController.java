package com.derteuffel.controllers;


import com.derteuffel.entities.Category;
import com.derteuffel.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/ListCategories")
    public String ListCategory(Model model){
        model.addAttribute("categories",categoryService.findAllCategory());

        return "category/categories";

    }

    @GetMapping("/{idCat}")
    public String getOne(@PathVariable Long idCat, Model model){
        model.addAttribute("category", categoryService.findCategoryById(idCat));
        return "category/detail";

    }

    @GetMapping("/form")
    public String  formCategorie(Model model){
        model.addAttribute("category", new Category());
        return "category/form";
    }



    @PostMapping("/save")
    public String saveCategorie(Category category){
        categoryService.saveOrUpdate(category);
        return "redirect:/category/ListCategories";
    }

    @GetMapping("/delete/{idCat}")
    public String deleteCategory(@PathVariable Long idCat, Model model)
    {
        Category category= categoryService.findCategoryById(idCat);
        categoryService.deleteCategory(idCat);
        model.addAttribute("category",categoryService.findAllCategory());
        return "redirect:/category/ListCategory";
    }

}
