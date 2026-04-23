public interface Currency {
    String getCode();
    double convertFromIDR(double amount);
}