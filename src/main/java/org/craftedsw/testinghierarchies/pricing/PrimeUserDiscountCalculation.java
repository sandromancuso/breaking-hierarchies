package org.craftedsw.testinghierarchies.pricing;

import org.craftedsw.testinghierarchies.domain.User;

public class PrimeUserDiscountCalculation {

    public static final double PRIME_DISCOUNT = 10;

    public double calculateDiscount(User user) {
        return (user.isPrime())
                    ? PRIME_DISCOUNT
                    : 0;
    }
}
