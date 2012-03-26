package org.craftedsw.testinghierarchies.pricing;

import org.craftedsw.testinghierarchies.domain.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ShoppingBasket {
    
    private ConcurrentHashMap<Product, AtomicInteger> basket = new ConcurrentHashMap<Product, AtomicInteger>();
    
    public void add(Product product, int quantity) {
        basket.putIfAbsent(product, new AtomicInteger(0));
        basket.get(product).getAndAdd(quantity);
    }

    public Collection<Item> items() {
        Collection<Item> items = new ArrayList<Item>();
        for (Map.Entry<Product, AtomicInteger> basketEntry : basket.entrySet()) {
            items.add(new Item(basketEntry.getKey(), basketEntry.getValue().intValue()));
        }
        return items;
    }
    
    public static class Item {

        private Product product;
        private Integer quantity;

        public Item(Product product, Integer quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() {
            return this.product;
        }

        public Integer getQuantity() {
            return this.quantity;
        }
        
    }
}
