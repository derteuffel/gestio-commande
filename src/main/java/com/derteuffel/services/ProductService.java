package com.derteuffel.services;

import com.derteuffel.entities.Product;

import java.util.List;

public interface ProductService {

    public List<Product> findAllProduct();
    public Product saveOrUpdateProduct(Product product);
    public  void  deleteProduct(Long idProduct);
    public Product findProductByIdProduct(Long idProduct);
}
