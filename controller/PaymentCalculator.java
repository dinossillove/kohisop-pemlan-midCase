package controller;

import model.Currency;
import model.Membership;
import model.Order;
import model.PaymentChannel;
import view.OrderDisplay;

public class PaymentCalculator {
    private OrderDisplay order;
    private PaymentChannel channel;
    private Currency currency;
    private Membership membership;
    private Integer redeemedPoints = null;

    public PaymentCalculator(OrderDisplay order, PaymentChannel channel, Currency currency) {
        this(order, channel, currency, null);
    }

    public PaymentCalculator(OrderDisplay order, PaymentChannel channel, Currency currency, Membership membership) {
        this.order = order;
        this.channel = channel;
        this.currency = currency;
        this.membership = membership;
    }

    public OrderDisplay getOrder() { return order; }
    public PaymentChannel getChannel() { return channel; }
    public Currency getCurrency() { return currency; }
    public Membership getMembership() { return membership; }
    
    public double getRawTotalIDR() {
        double total = 0;
        for (Order o : order.getDrinks()) total += o.getSubtotal();
        for (Order o : order.getFoods()) total += o.getSubtotal();
        return total;
    }

    public double getRawTotalInCurrency() {
        return currency.convertFromIDR(getRawTotalIDR());
    }
    
    public double getTaxIDR() {
        double tax = 0;
        for (Order o : order.getDrinks()) tax += o.getTotalTax();
        for (Order o : order.getFoods()) tax += o.getTotalTax();
        return tax;
    }

    public double getTaxInCurrency() {
        return currency.convertFromIDR(getTaxIDR());
    }

    public double getFinalTotalIDRBeforePoints() {
        double totalIDR = getRawTotalIDR();
        double taxIDR = getTaxIDR();
        double totalWithTaxIDR = totalIDR + taxIDR;
        double discAmtIDR = totalWithTaxIDR * channel.getDiscount();
        return totalWithTaxIDR - discAmtIDR + channel.getAdminFee();
    }

    public int getPointsRedeemed() {
        if (membership == null) {
            return 0;
        }
        if (redeemedPoints == null) {
            redeemedPoints = membership.redeemPoints(getFinalTotalIDRBeforePoints());
        }
        return redeemedPoints;
    }

    public double getPointsRedemptionValueIDR() {
        return getPointsRedeemed() * 2.0;
    }

    public double getFinalTotalInCurrency() {
        double totalWithPointsIDR = getFinalTotalIDRBeforePoints() - getPointsRedemptionValueIDR();
        if (totalWithPointsIDR < 0) {
            totalWithPointsIDR = 0;
        }
        return currency.convertFromIDR(totalWithPointsIDR);
    }
}
