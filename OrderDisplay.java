import java.util.ArrayList;
import java.util.List;

public class OrderDisplay {
    private List<Order> drinks = new ArrayList<>();
    private List<Order> foods = new ArrayList<>();

    public void addItem(Order o) {
        if (o.getMenuItem() instanceof Minuman) drinks.add(o);
        else foods.add(o);
    }
    public List<Order> getDrinks() { return drinks; }
    public List<Order> getFoods() { return foods; }
}