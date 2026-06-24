package view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Minuman;
import model.Order;

public class OrderDisplay {
    private List<Order> drinks = new ArrayList<>();
    private List<Order> foods = new ArrayList<>();

    public void addItem(Order o) {
        if (o.getMenuItem() instanceof Minuman) drinks.add(o);
        else foods.add(o);
        sortAll();
    }

    private void sortAll() {
        drinks.sort(Comparator.comparingDouble(o -> o.getMenuItem().getHarga()));
        foods.sort(Comparator.comparingDouble(o -> o.getMenuItem().getHarga()));
    }

    public List<Order> getDrinks() { return drinks; }
    public List<Order> getFoods() { return foods; }
    public void clear() {
        drinks.clear();
        foods.clear();
    }
}
