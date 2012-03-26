package org.craftedsw.testinghierarchies.pricing;

import org.craftedsw.testinghierarchies.domain.User;

public class PricingService {

    private PriceCalculation priceCalculation;
    private VoucherDiscountCalculation voucherDiscountCalculation;
    private PrimeUserDiscountCalculation primeUserDiscountCalculation;

    public PricingService(PriceCalculation priceCalculation, VoucherDiscountCalculation voucherDiscountCalculation,
                          PrimeUserDiscountCalculation primeUserDiscountCalculation) {
        this.priceCalculation = priceCalculation;
        this.voucherDiscountCalculation = voucherDiscountCalculation;
        this.primeUserDiscountCalculation = primeUserDiscountCalculation;
    }

    public double calculatePrice(ShoppingBasket shoppingBasket, User user, String voucher) {
        double total = getTotalValueFor(shoppingBasket);
        total = applyVoucherDiscount(voucher, total);
        return totalAfterUserDiscount(total, userDiscount(user));
    }

    private double userDiscount(User user) {
        return primeUserDiscountCalculation.calculateDiscount(user);
    }

    private double applyVoucherDiscount(String voucher, double total) {
        return voucherDiscountCalculation.calculateVoucherDiscount(total, voucher);
    }

    private double totalAfterUserDiscount(double total, double discount) {
        return total * ((100 - discount) / 100);
    }

    private double getTotalValueFor(ShoppingBasket shoppingBasket) {
        double total = 0;
        for (ShoppingBasket.Item item : shoppingBasket.items()) {
            total += priceCalculation.calculateProductPrice(item.getProduct(), item.getQuantity());
        }
        return total;
    }

}
