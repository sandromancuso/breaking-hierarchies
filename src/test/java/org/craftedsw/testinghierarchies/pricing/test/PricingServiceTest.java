package org.craftedsw.testinghierarchies.pricing.test;

import org.craftedsw.testinghierarchies.domain.Product;
import org.craftedsw.testinghierarchies.domain.User;
import org.craftedsw.testinghierarchies.pricing.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.craftedsw.testinghierarchies.builder.ProductBuilder.aProduct;
import static org.craftedsw.testinghierarchies.builder.ShoppingBasketBuilder.aShoppingBasket;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PricingServiceTest {

    private static final String NO_VOUCHER = "";
    private static final String FIVE_POUNDS_VOUCHER = "5";
    public static final User PRIME_USER = new User();

    private PricingService pricingService;
    private ShoppingBasket shoppingBasket;

    @Mock private PriceCalculation priceCalculation;
    @Mock private VoucherDiscountCalculation voucherDiscountCalculation;
    @Mock private PrimeUserDiscountCalculation primeUserDiscountCalculation;

    @Before 
    public void initialise() {
        pricingService = new PricingService(priceCalculation, voucherDiscountCalculation, primeUserDiscountCalculation);
    }

    @Test public void
    should_calculate_price_of_all_products() {
        Product book = aProduct().named("book").costing(10).build();
        Product kindle = aProduct().named("kindle").costing(80).build();
        shoppingBasket = aShoppingBasket()
                                .with(2, book)
                                .with(3, kindle)
                                .build();

        double price = pricingService.calculatePrice(shoppingBasket, new User(), NO_VOUCHER);

        verify(priceCalculation, times(1)).calculateProductPrice(book, 2);
        verify(priceCalculation, times(1)).calculateProductPrice(kindle, 3);
    }

    @Test public void
    should_calculate_voucher_discount() {
        Product book = aProduct().named("book").costing(10).build();
        when(priceCalculation.calculateProductPrice(book, 2)).thenReturn(20D);
        shoppingBasket = aShoppingBasket()
                                .with(2, book)
                                .build();

        double price = pricingService.calculatePrice(shoppingBasket, new User(), FIVE_POUNDS_VOUCHER);
        
        verify(voucherDiscountCalculation, times(1)).calculateVoucherDiscount(20, FIVE_POUNDS_VOUCHER);
    }
    
    @Test public void 
    should_calculate_prime_user_discount() {
        Product book = aProduct().named("book").costing(10).build();
        when(primeUserDiscountCalculation.calculateDiscount(any(User.class))).thenReturn(10D);
        shoppingBasket = aShoppingBasket().with(1, book).build();
        
        double price = pricingService.calculatePrice(shoppingBasket, PRIME_USER, NO_VOUCHER);        
       
        verify(primeUserDiscountCalculation, times(1)).calculateDiscount(PRIME_USER);
    }
    
}
