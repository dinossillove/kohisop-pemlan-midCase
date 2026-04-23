public class JPY implements Currency {
    public String getCode() { return "JPY"; }
    public double convertFromIDR(double amount) { return amount * 10.0; }
}
