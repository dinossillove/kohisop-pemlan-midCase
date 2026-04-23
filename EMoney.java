public class EMoney implements PaymentChannel {
    public String getName() { return "eMoney"; }
    public double getDiscount() { return 0.07; }
    public double getAdminFee() { return 20.0; }
}