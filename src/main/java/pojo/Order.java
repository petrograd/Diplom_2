package pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Order {
    public List<String> ingredients;

    public Order() {
    }

    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public static Order getOrderWithHashAdded(String hash) {
        Order order = new Order();
        order.ingredients = new ArrayList<>();
        order.ingredients.add(hash);
        return order;
    }

    public static Order getRandomOrder(List<String> availableIngr, int quantity) {
        Order order = new Order();
        order.ingredients = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < quantity; i++) {
            order.ingredients.add(availableIngr.get(random.nextInt(availableIngr.size())));
        }
        return order;
    }

}
