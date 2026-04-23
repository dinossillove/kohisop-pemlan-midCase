public class EUR implements Currency {
    public String getCode() { return "EUR"; }
    public double convertFromIDR(double amount) { return amount / 14.0; }
}