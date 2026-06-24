package view;

import controller.PaymentCalculator;
import model.Currency;
import model.Membership;
import model.Order;
import model.PaymentChannel;

public class ReceiptPrinter {
    private PaymentCalculator calc;
    private Membership membership;
    private int pointsUsed;
    private int pointsBefore;
    private int pointsEarned;

    public ReceiptPrinter(PaymentCalculator calc) {
        this(calc, null, 0, 0, 0);
    }

    public ReceiptPrinter(PaymentCalculator calc, Membership membership, int pointsUsed, int pointsBefore, int pointsEarned) {
        this.calc = calc;
        this.membership = membership;
        this.pointsUsed = pointsUsed;
        this.pointsBefore = pointsBefore;
        this.pointsEarned = pointsEarned;
    }
    
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
        if (membership != null) {
            System.out.println("=============================================");
            System.out.println("Membership:");
            System.out.printf("Kode Member          : %s\n", membership.getKodeMember());
            System.out.printf("Nama Member          : %s\n", membership.getNamaMember());
            System.out.printf("Poin Sebelum         : %d\n", pointsBefore);
            System.out.printf("Poin Digunakan       : %d (= %.2f IDR)\n", pointsUsed, pointsUsed * 2.0);
            System.out.printf("Poin Didapat         : %d%s\n", pointsEarned, membership.hasCodeWithA() ? " (x2 bonus kode A)" : "");
            System.out.printf("Poin Setelah         : %d\n", membership.getPoin());
        }
        System.out.printf("TOTAL TAGIHAN AKHIR   : %.2f %s\n", calc.getFinalTotalInCurrency(), curr.getCode());
        System.out.println("=============================================");
        System.out.println("      Terima kasih dan silakan datang kembali!      ");
        System.out.println("=============================================");
    }
}
