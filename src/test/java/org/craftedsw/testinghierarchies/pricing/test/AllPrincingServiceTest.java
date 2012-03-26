package org.craftedsw.testinghierarchies.pricing.test;

import org.craftedsw.testinghierarchies.domain.User;
import org.craftedsw.testinghierarchies.pricing.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.craftedsw.testinghierarchies.builder.ProductBuilder.aProduct;
import static org.craftedsw.testinghierarchies.builder.ShoppingBasketBuilder.aShoppingBasket;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AllPrincingServiceTest {

    private static final String NO_VOUCHER = "";
    private static final String THREE_POUNDS_VOUCHER = "12345234003";
    private static final String FIFTEEN_POUNDS_VOUCHER = "12324342015";
    public static final User PRIME_USER = new User();

    @Mock private VoucherService voucherService;
    @Mock private VoucherDiscountCalculation voucherDiscountCalculation;
    @Mock private PrimeUserDiscountCalculation primeUserDiscountCalculation;

    private PricingService standardPricingService;
    private PricingService boxingDayPricingService;
    private ShoppingBasket shoppingBasket;

    @Before
    public void setUp() throws Exception {
        standardPricingService = new PricingService(new StandardPriceCalculation(), voucherDiscountCalculation,
                primeUserDiscountCalculation);

        boxingDayPricingService = new PricingService(new BoxingDayPriceCalculation(), voucherDiscountCalculation,
                primeUserDiscountCalculation);
    }

    @Test public void
    should_have_total_of_zero_if_basket_is_empty() {
        shoppingBasket = aShoppingBasket().build();
        
        double price = standardPricingService.calculatePrice(shoppingBasket, new User(), NO_VOUCHER);

        assertThat(price, is(0D));
    }
    
    @Test public void
    should_return_product_price_when_no_other_discount_applies() {
        when(voucherDiscountCalculation.calculateVoucherDiscount(10D, NO_VOUCHER)).thenReturn(10D);
        shoppingBasket = aShoppingBasket()
                                .with(aProduct().costing(10).build())
                                .build();
        
        double price = standardPricingService.calculatePrice(shoppingBasket, new User(), NO_VOUCHER);
        assertThat(price, is(10D));
    }

    @Test public void
    should_apply_prime_user_discount() {
        when(voucherDiscountCalculation.calculateVoucherDiscount(10, NO_VOUCHER)).thenReturn(10D);
        when(primeUserDiscountCalculation.calculateDiscount(PRIME_USER)).thenReturn(10D);
        shoppingBasket = aShoppingBasket()
                                .with(aProduct().costing(10).build())
                                .build();
        
        User user = new User();
        user.setPrime(true);

        double price = standardPricingService.calculatePrice(shoppingBasket, PRIME_USER, NO_VOUCHER);
        assertThat(price, is(9D));
    }
    
    @Test public void
    should_apply_voucher_discount() {
        when(voucherDiscountCalculation.calculateVoucherDiscount(10, THREE_POUNDS_VOUCHER)).thenReturn(7D);

        shoppingBasket = aShoppingBasket()
                                .with(aProduct().costing(10).build())
                                .build();

        double price = standardPricingService.calculatePrice(shoppingBasket, new User(), THREE_POUNDS_VOUCHER);
        assertThat(price, is(7D));
    }

    @Test public void
    should_return_zero_when_voucher_discount_is_bigger_than_total() {
        when(voucherService.getVoucherValue(FIFTEEN_POUNDS_VOUCHER)).thenReturn(15D);

        shoppingBasket = aShoppingBasket()
                                .with(aProduct().costing(10).build())
                                .build();

        double price = standardPricingService.calculatePrice(shoppingBasket, new User(), FIFTEEN_POUNDS_VOUCHER);
        assertThat(price, is(0D));
    }
    
    @Test public void
    should_calculate_price_when_more_than_one_unit_of_same_product() {
        when(voucherDiscountCalculation.calculateVoucherDiscount(30D, NO_VOUCHER)).thenReturn(30D);
        shoppingBasket = aShoppingBasket()
                                .with(3, aProduct().costing(10).build())
                                .build();

        double price = standardPricingService.calculatePrice(shoppingBasket, new User(), NO_VOUCHER);
        assertThat(price, is(30D));
    }
    
    @Test public void
    should_calculate_price_for_multiple_products() {    
        when(voucherDiscountCalculation.calculateVoucherDiscount(70, NO_VOUCHER)).thenReturn(70D);
        shoppingBasket = aShoppingBasket()
                            .with(3, aProduct().costing(10).build())
                            .with(2, aProduct().costing(20).build())
                            .build();

        double price = standardPricingService.calculatePrice(shoppingBasket, new User(), NO_VOUCHER);
        assertThat(price, is(70D));
    }

    @Test public void
    should_give_boxing_day_discount() {
        when(voucherDiscountCalculation.calculateVoucherDiscount(6, NO_VOUCHER)).thenReturn(6D);
        shoppingBasket = aShoppingBasket()
                                .with(aProduct().costing(10).build())
                                .build();

        double price = boxingDayPricingService.calculatePrice(shoppingBasket, new User(), NO_VOUCHER);
        assertThat(price, is(6D));
    }

}
