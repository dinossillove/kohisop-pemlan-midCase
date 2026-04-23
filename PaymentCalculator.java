public class PaymentCalculator {
    private OrderDisplay order;
    private PaymentChannel channel;
    private Currency currency;

    public PaymentCalculator(OrderDisplay order, PaymentChannel channel, Currency currency) {
        this.order = order; this.channel = channel; this.currency = currency;
    }
    public OrderDisplay getOrder() { return order; }
    public PaymentChannel getChannel() { return channel; }
    public Currency getCurrency() { return currency; }
    
    public double getRawTotalInCurrency() {
        double total = 0;
        for (Order o : order.getDrinks()) total += o.getSubtotal();
        for (Order o : order.getFoods()) total += o.getSubtotal();
        return currency.convertFromIDR(total);
    }
    
    public double getTaxInCurrency() {
        double tax = 0;
        for (Order o : order.getDrinks()) tax += o.getTotalTax();
        for (Order o : order.getFoods()) tax += o.getTotalTax();
        return currency.convertFromIDR(tax);
    }
    
    public double getFinalTotalInCurrency() {
        double totalIDR = 0; double taxIDR = 0;
        for (Order o : order.getDrinks()) { totalIDR += o.getSubtotal(); taxIDR += o.getTotalTax(); }
        for (Order o : order.getFoods()) { totalIDR += o.getSubtotal(); taxIDR += o.getTotalTax(); }
        double totalWithTaxIDR = totalIDR + taxIDR;
        double discAmtIDR = totalWithTaxIDR * channel.getDiscount();
        return currency.convertFromIDR(totalWithTaxIDR - discAmtIDR + channel.getAdminFee());
    }
}