package org.craftedsw.testinghierarchies.pricing;

public class VoucherDiscountCalculation {
    private VoucherService voucherService;

    public VoucherDiscountCalculation(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public double calculateVoucherDiscount(double total, String voucher) {
        double voucherValue = voucherService.getVoucherValue(voucher);
        double totalAfterValue = total - voucherValue;
        return (totalAfterValue > 0) ? totalAfterValue : 0;
    }
}
