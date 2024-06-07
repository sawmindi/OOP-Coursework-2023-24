
import java.util.List;
import java.util.Scanner;

interface ShoppingManager {
    void addProduct(Product iteratorProduct);

    void deleteProduct(Scanner iteratorProduct);

    void removeProduct(Product iteratorProduct);

    void printProductList();

    List<Product> getAllProducts();

    void saveProductsToFile();

    Product findProductById(String iteratorProduct);
}
