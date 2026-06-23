package model;

public class EMoney implements PaymentChannel {
    private double saldo = 500;
    public String getName() {
        return "eMoney";
    }
    public double getDiscount() {
        return 0.07;
    }
    public double getAdminFee() {
        return 20.0;
    }
    public double getSaldo() {
        return saldo;
    }
}