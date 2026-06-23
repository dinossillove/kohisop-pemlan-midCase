package model;

public class QRIS implements PaymentChannel {
    private double saldo = 500;
    public String getName() {
        return "QRIS";
    }
    public double getDiscount() {
        return 0.05;
    }
    public double getAdminFee() {
        return 0.0;
    }
    public double getSaldo() {
        return saldo;
    }
}