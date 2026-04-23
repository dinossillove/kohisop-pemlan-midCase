public class USD implements Currency {
    public String getCode() { return "USD"; }
    public double convertFromIDR(double amount) { return amount / 15.0; }
}