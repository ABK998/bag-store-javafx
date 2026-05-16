package APPP;

import java.util.ArrayList;
import java.util.List;




public class CartManager {
    private static List<UserCart> userCarts = new ArrayList<UserCart>();

    public static void addToCart(User user, Product product, int quantity) {
        UserCart userCart = getUserCart(user);
        List<CartItem> items = userCart.getItems();
        boolean found = false;

        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            if (item.getProduct().equals(product)) {
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }

        if (!found) {
            items.add(new CartItem(product, quantity));
        }
    }

    public static void removeFromCart(User user, Product product) {
        UserCart userCart = getUserCart(user);
        List<CartItem> items = userCart.getItems();

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProduct().equals(product)) {
                items.remove(i);
                break;
            }
        }
    }

    public static List<CartItem> getCart(User user) {
        return getUserCart(user).getItems();
    }

    public static double getCartTotal(User user) {
        List<CartItem> items = getCart(user);
        double total = 0;

        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            total += item.getProduct().getPrice() * item.getQuantity();
        }

        return total;
    }

    public static void clearCart(User user) {
        getUserCart(user).getItems().clear();
    }

    public static void purchaseItems(User user) {
        List<CartItem> items = getCart(user);

        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            Product p = item.getProduct();
            p.setStock(p.getStock() - item.getQuantity());
        }

        clearCart(user);
    }

    public static UserCart getUserCart(User user) {
        for (int i = 0; i < userCarts.size(); i++) {
            if (userCarts.get(i).getUser().equals(user)) {
                return userCarts.get(i);
            }
        }

        UserCart newCart = new UserCart(user);
        userCarts.add(newCart);
        return newCart;
    }
}
