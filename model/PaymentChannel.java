package model;

public interface PaymentChannel {
    String getName();
    double getDiscount();
    double getAdminFee();
}
