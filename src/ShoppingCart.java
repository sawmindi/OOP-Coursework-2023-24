
import java.io.Serializable;
import java.util.*;

public class ShoppingCart implements Serializable {
    private ArrayList<Product> items = new ArrayList();
    private Map<String, Integer> categoryCounts = new HashMap();
    private boolean firstPurchase = true;

    public ShoppingCart() {
    }

    public ArrayList<Product> getItems() {
        return new ArrayList(this.items);
    }

    public double calculateTotalPrice() {
        double totalPrice = 0.0;

        Product item;
        for(Iterator totalP = this.items.iterator(); totalP.hasNext(); totalPrice += item.getPrice()) {
            item = (Product)totalP.next();
        }

        totalPrice -= this.calculateCategoryDiscount();
        totalPrice -= this.calculateFirstPurchaseDiscount();
        return Math.max(totalPrice, 0.0);
    }

    private double calculateCategoryDiscount() {
        double categoryDiscount = 0.0;
        Iterator totalP = this.categoryCounts.entrySet().iterator();

        while(totalP.hasNext()) {
            Map.Entry<String, Integer> entry = (Map.Entry)totalP.next();
            int itemCount = (Integer)entry.getValue();
            if (itemCount >= 3) {
                categoryDiscount += (double)itemCount * 0.2 * this.items.stream().filter((product) -> {
                    return product.getProductType().equalsIgnoreCase((String)entry.getKey());
                }).mapToDouble(Product::getPrice).sum();
            }
        }

        return categoryDiscount;
    }

    private double calculateFirstPurchaseDiscount() {
        if (this.firstPurchase) {
            this.firstPurchase = false;
            return 0.1 * this.items.stream().mapToDouble(Product::getPrice).sum();
        } else {
            return 0.0;
        }
    }

    public void addProduct(Product product) {
    }

    public ArrayList<Product> getCartItems() {
        return null;
    }

    public Collection<Object> addItem() {
        return null;
    }
}
