package org.craftedsw.testinghierarchies.pricing;

import org.craftedsw.testinghierarchies.domain.Product;

public interface PriceCalculation {
    double calculateProductPrice(Product product, int quantity);
}
