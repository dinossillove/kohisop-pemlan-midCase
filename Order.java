public class Order {
    private MenuItem menuItem;
    private int quantity;

    public Order(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }
    public MenuItem getMenuItem() { return menuItem; }
    public int getQuantity() { return quantity; }
    public double getSubtotal() { return menuItem.getHarga() * quantity; }
    public double getTotalTax() { return menuItem.hitungPajak(quantity); }
}