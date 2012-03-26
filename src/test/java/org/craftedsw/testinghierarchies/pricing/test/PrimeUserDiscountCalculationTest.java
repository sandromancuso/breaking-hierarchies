package org.craftedsw.testinghierarchies.pricing.test;

import org.craftedsw.testinghierarchies.domain.User;
import org.craftedsw.testinghierarchies.pricing.PrimeUserDiscountCalculation;
import org.junit.Before;
import org.junit.Test;

import static org.craftedsw.testinghierarchies.pricing.PrimeUserDiscountCalculation.PRIME_DISCOUNT;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PrimeUserDiscountCalculationTest {

    private PrimeUserDiscountCalculation primeUserDiscountCalculation;
    private static final User NON_PRIME_USER = new User();
    private static final User PRIME_USER = new User();

    @Before
    public void initialise() {
        this.primeUserDiscountCalculation = new PrimeUserDiscountCalculation();
        PRIME_USER.setPrime(true);
    }

    @Test
    public void
    should_not_return_any_discount_when_user_is_not_prime() {
        double discount = primeUserDiscountCalculation.calculateDiscount(NON_PRIME_USER);

        assertThat(discount, is(0D));
    }

    @Test public void
    should_return_prime_discount_when_user_is_prime() {
        double discount = primeUserDiscountCalculation.calculateDiscount(PRIME_USER);

        assertThat(discount, is(PRIME_DISCOUNT));
    }


}
