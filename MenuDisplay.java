import java.util.List;

public class MenuDisplay {
    private List<MenuItem> items;
    public MenuDisplay(List<MenuItem> items) { this.items = items; }

    public void showDrinkMenu() {
        System.out.println("=== MENU MINUMAN ===");
        for (MenuItem item : items) if (item instanceof Minuman) System.out.printf("%-5s | %-35s | Rp %.0f\n", item.getKode(), item.getNama(), item.getHarga());
    }
    public void showFoodMenu() {
        System.out.println("=== MENU MAKANAN ===");
        for (MenuItem item : items) if (item instanceof Makanan) System.out.printf("%-5s | %-35s | Rp %.0f\n", item.getKode(), item.getNama(), item.getHarga());
    }
    public MenuItem getItemByCode(String code) {
        for (MenuItem item : items) if (item.getKode().equalsIgnoreCase(code)) return item;
        return null;
    }
    public void showOrderTable(List<Order> drinks, List<Order> foods) {
        System.out.println("\n--- Pesanan Saat Ini ---");
        for (Order o : drinks) System.out.printf("%-30s x %d\n", o.getMenuItem().getNama(), o.getQuantity());
        for (Order o : foods) System.out.printf("%-30s x %d\n", o.getMenuItem().getNama(), o.getQuantity());
        System.out.println("------------------------\n");
    }
}