package APPP;

import java.util.ArrayList;
import java.util.List;

public class UserCart {
    private User user;
    private List<CartItem> items;

    public UserCart(User user) {
        this.user = user;
        this.items = new ArrayList<CartItem>();
    }

    public User getUser() {
        return user;
    }

    public List<CartItem> getItems() {
        return items;
    }
}
