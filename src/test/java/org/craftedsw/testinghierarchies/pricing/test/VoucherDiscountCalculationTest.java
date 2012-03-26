package org.craftedsw.testinghierarchies.pricing.test;

import org.craftedsw.testinghierarchies.pricing.VoucherDiscountCalculation;
import org.craftedsw.testinghierarchies.pricing.VoucherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VoucherDiscountCalculationTest {

    private static final String NO_VOUCHER = null;
    private static final String TWENTY_POUNDS_VOUCHER = "20";

    @Mock
    private VoucherService voucherService;
    private VoucherDiscountCalculation voucherDiscountCalculation;

    @Before
    public void initialise() {
        voucherDiscountCalculation = new VoucherDiscountCalculation(voucherService);
        when(voucherService.getVoucherValue(TWENTY_POUNDS_VOUCHER)).thenReturn(20D);
    }

    @Test
    public void
    should_not_apply_discount_if_no_voucher_is_received() {
        double returnedAmount = voucherDiscountCalculation.calculateVoucherDiscount(1000, NO_VOUCHER);

        assertThat(returnedAmount, is(1000D));
    }

    @Test public void
    should_subtract_voucher_value_from_total() {
        double returnedAmount = voucherDiscountCalculation.calculateVoucherDiscount(30D, TWENTY_POUNDS_VOUCHER);

        assertThat(returnedAmount, is(equalTo(10D)));
    }

    @Test public void
    shoudl_return_zero_if_voucher_value_is_higher_than_total() {
        double returnedAmount = voucherDiscountCalculation.calculateVoucherDiscount(10D, TWENTY_POUNDS_VOUCHER);

        assertThat(returnedAmount, is(equalTo(0D)));
    }

}
