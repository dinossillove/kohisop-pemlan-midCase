package controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;
import model.Order;

public class KitchenProcessor {
    private static final int DEFAULT_BATCH_SIZE = 3;

    private final int batchSize;
    private int customerCount;
    private final List<Order> pendingFoods;
    private final Stack<Order> pendingDrinks;

    public KitchenProcessor() {
        this(DEFAULT_BATCH_SIZE);
    }

    public KitchenProcessor(int batchSize) {
        this.batchSize = Math.max(1, batchSize);
        this.customerCount = 0;
        this.pendingFoods = new ArrayList<>();
        this.pendingDrinks = new Stack<>();
    }

    public void submitOrder(List<Order> drinks, List<Order> foods) {
        if (drinks != null) {
            for (Order order : drinks) {
                pendingDrinks.push(order);
            }
        }
        if (foods != null) {
            pendingFoods.addAll(foods);
        }
        customerCount++;
    }

    public boolean isReadyToProcess() {
        return customerCount >= batchSize;
    }

    public void processOrders() {
        if (!isReadyToProcess()) {
            System.out.printf("Belum ada %d pelanggan yang selesai. Pesanan dapur belum diproses.%n", batchSize);
            return;
        }

        PriorityQueue<Order> foodQueue = new PriorityQueue<>(Comparator.comparingDouble((Order o) -> o.getMenuItem().getHarga()).reversed());
        foodQueue.addAll(pendingFoods);

        System.out.println("\n=== PROSES PESANAN TIM DAPUR ===");

        System.out.println("\n--- TIM MAKANAN (Prioritas Harga) ---");
        if (foodQueue.isEmpty()) {
            System.out.println("Tidak ada pesanan makanan.");
        } else {
            while (!foodQueue.isEmpty()) {
                Order order = foodQueue.poll();
                System.out.printf("%s x %d (Rp %.0f)\n", order.getMenuItem().getNama(), order.getQuantity(), order.getMenuItem().getHarga());
            }
        }

        System.out.println("\n--- TIM MINUMAN (Last-Ordered-First-Served) ---");
        if (pendingDrinks.isEmpty()) {
            System.out.println("Tidak ada pesanan minuman.");
        } else {
            while (!pendingDrinks.isEmpty()) {
                Order order = pendingDrinks.pop();
                System.out.printf("%s x %d (Rp %.0f)\n", order.getMenuItem().getNama(), order.getQuantity(), order.getMenuItem().getHarga());
            }
        }

        clearPendingOrders();
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void clearPendingOrders() {
        pendingFoods.clear();
        pendingDrinks.clear();
        customerCount = 0;
    }
}
