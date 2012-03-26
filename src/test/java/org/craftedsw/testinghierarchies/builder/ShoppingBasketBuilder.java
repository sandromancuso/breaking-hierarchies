package org.craftedsw.testinghierarchies.builder;

import org.craftedsw.testinghierarchies.domain.Product;
import org.craftedsw.testinghierarchies.pricing.ShoppingBasket;

import java.util.HashMap;
import java.util.Map;

public class ShoppingBasketBuilder {

    private Map<Product, Integer> products = new HashMap<Product, Integer>();

    public static ShoppingBasketBuilder aShoppingBasket() {
        return new ShoppingBasketBuilder();
    }
    
    public ShoppingBasketBuilder with(Product product) {
        this.products.put(product, 1);
        return this;
    }
    
    public ShoppingBasketBuilder with(ProductBuilder productBuilder) {
        return with(productBuilder.build());
    }
    
    public ShoppingBasketBuilder with(Integer quantity, Product product) {
        this.products.put(product, quantity);
        return this;
    }
    
    public ShoppingBasketBuilder with(Integer quantity, ProductBuilder productBuider) {
        return with(quantity, productBuider.build());
    }
    
    public ShoppingBasket build() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            shoppingBasket.add(entry.getKey(), entry.getValue());
        }
        return shoppingBasket;
    }

}
