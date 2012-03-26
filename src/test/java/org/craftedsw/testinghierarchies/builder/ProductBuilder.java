package org.craftedsw.testinghierarchies.builder;

import org.craftedsw.testinghierarchies.domain.Product;

public class ProductBuilder {
    
    private double price;
    private String name;
    
    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }
    
    public ProductBuilder costing (double price) {
        this.price = price;
        return this;
    }
    
    public ProductBuilder named(String name) {
        this.name = name;
        return this;
    }
    
    public Product build() {
        Product product = new Product();
        product.setPrice(price);
        product.setName(name);
        return product;
    }
}
