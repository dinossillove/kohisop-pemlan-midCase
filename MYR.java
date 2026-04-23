public class MYR implements Currency {
    public String getCode() { return "MYR"; }
    public double convertFromIDR(double amount) { return amount / 4.0; }
}