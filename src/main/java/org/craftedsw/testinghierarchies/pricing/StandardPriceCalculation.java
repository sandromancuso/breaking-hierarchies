package org.craftedsw.testinghierarchies.pricing;

import org.craftedsw.testinghierarchies.domain.Product;

public class StandardPriceCalculation implements PriceCalculation {

    @Override
    public double calculateProductPrice(Product product, int quantity) {
        return product.getPrice() * quantity;
    }
}