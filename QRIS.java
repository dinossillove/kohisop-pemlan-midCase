public class QRIS implements PaymentChannel {
    public String getName() { return "QRIS"; }
    public double getDiscount() { return 0.05; }
    public double getAdminFee() { return 0.0; }
}