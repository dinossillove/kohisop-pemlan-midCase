public class ReceiptPrinter {
    private PaymentCalculator calc;
    public ReceiptPrinter(PaymentCalculator calc) { this.calc = calc; }
    
    public void printHeader() {
        System.out.println("\n=============================================");
        System.out.println("             NOTA KOHISOP                    ");
        System.out.println("=============================================");
    }
    
    public void printOrderLines() {
        Currency curr = calc.getCurrency();
        System.out.println("=== MINUMAN ===");
        for (Order o : calc.getOrder().getDrinks()) System.out.printf("%s - %s\n   %d x %.2f %s | Sub: %.2f | Tax: %.2f\n", o.getMenuItem().getKode(), o.getMenuItem().getNama(), o.getQuantity(), curr.convertFromIDR(o.getMenuItem().getHarga()), curr.getCode(), curr.convertFromIDR(o.getSubtotal()), curr.convertFromIDR(o.getTotalTax()));
        System.out.println("\n=== MAKANAN ===");
        for (Order o : calc.getOrder().getFoods()) System.out.printf("%s - %s\n   %d x %.2f %s | Sub: %.2f | Tax: %.2f\n", o.getMenuItem().getKode(), o.getMenuItem().getNama(), o.getQuantity(), curr.convertFromIDR(o.getMenuItem().getHarga()), curr.getCode(), curr.convertFromIDR(o.getSubtotal()), curr.convertFromIDR(o.getTotalTax()));
    }
    
    public void printTotals() {
        Currency curr = calc.getCurrency();
        PaymentChannel ch = calc.getChannel();
        double subtotal = calc.getRawTotalInCurrency();
        double tax = calc.getTaxInCurrency();
        
        System.out.println("---------------------------------------------");
        System.out.printf("Total (Luar Pajak)    : %.2f %s\n", subtotal, curr.getCode());
        System.out.printf("Total (+ Pajak)       : %.2f %s\n", (subtotal + tax), curr.getCode());
        System.out.printf("Diskon (%-6s)      : -%.2f %s\n", ch.getName(), (subtotal + tax) * ch.getDiscount(), curr.getCode());
        if (ch.getAdminFee() > 0) System.out.printf("Biaya Admin           : +%.2f %s\n", curr.convertFromIDR(ch.getAdminFee()), curr.getCode());
        System.out.println("---------------------------------------------");
        System.out.printf("TOTAL TAGIHAN AKHIR   : %.2f %s\n", calc.getFinalTotalInCurrency(), curr.getCode());
        System.out.println("=============================================");
        System.out.println("      Terima kasih dan silakan datang kembali     ");
        System.out.println("=============================================");
    }
}