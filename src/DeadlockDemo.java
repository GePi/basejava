import java.util.HashMap;
import java.util.Map;

public class DeadlockDemo {

    private static class Warehouse {
        private final Map<String, Integer> products = new HashMap<>();

        public Warehouse(Map<String, Integer> mapEntries) {
            products.putAll(mapEntries);
        }

        public synchronized void add(String productName, int units) {
            products.compute(productName, (K, V) -> (V == null) ? units : V + units);
        }

        public synchronized void moveToShoppingCart(String productName, int requestedUnits, ShoppingCart shoppingCart) {

            int availableUnits = products.getOrDefault(productName, 0);

            if (availableUnits == 0) {
                throw new RuntimeException("There are not enough product units");
            }

            if (availableUnits - requestedUnits > 0) {
                shoppingCart.add(productName, requestedUnits);
                products.put(productName, availableUnits - requestedUnits);
            } else {
                shoppingCart.add(productName, availableUnits);
                products.put(productName, 0);
            }
        }
    }

    public static class ShoppingCart {
        private final Map<String, Integer> products = new HashMap<>();

        public synchronized void add(String productName, int units) {
            products.compute(productName, (K, V) -> (V == null) ? units : V + units);
        }

        public synchronized void returnToWarehouse(String productName, int unitsNum, Warehouse warehouse) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            warehouse.add(productName, unitsNum);
            add(productName, -unitsNum);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var warehouse = new Warehouse(Map.of("Christmas tree", 20, "Snowman", 15, "World peace", 2, "Mandarin", 100500, "Champagne", 15, "Happiness", 2));
        var shoppingCart = new ShoppingCart();

        var clientClickThread1 = new Thread(() -> {
            warehouse.moveToShoppingCart("Christmas tree", 1, shoppingCart);
            warehouse.moveToShoppingCart("Champagne", 2, shoppingCart);
            shoppingCart.returnToWarehouse("Champagne", 1, warehouse);
        });

        var clientClickThread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            warehouse.moveToShoppingCart("Happiness", 3, shoppingCart);
            warehouse.moveToShoppingCart("World peace", 2, shoppingCart);
        });

        clientClickThread1.start();
        clientClickThread2.start();

        for (var i = 0; i < 10; i++) {
            Thread.sleep(1000);
            System.out.println("Order 1 state: " + clientClickThread1.getState());
            System.out.println("Order 2 state: " + clientClickThread1.getState());
        }
    }
}