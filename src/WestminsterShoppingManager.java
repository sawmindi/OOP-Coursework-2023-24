import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    private static final int MAX_PRODUCTS = 50;
    private ArrayList<Product> productList = new ArrayList();

    public ArrayList<Product> getProductList() {
        return this.productList;
    }

    public WestminsterShoppingManager() {
        this.loadProductsFromFile();
    }

    public void displayMenu() {
        System.out.println("WELCOME TO WESTMINSTER SHOPPING MANAGER");
        System.out.println("   ******   Menu   ******   ");
        System.out.println("1. Add a new product");
        System.out.println("2. Delete a product");
        System.out.println("3. Print product list");
        System.out.println("4. Save to file");
        System.out.println("5. Load products from file");
        System.out.println("6. Open GUI");
        System.out.println("0. Exit");
        System.out.println();
    }

    void addProductFromConsole(Scanner sc) {
        boolean validProductType = false;

        do {
            try {
                System.out.print("Enter product type (C or c for Clothing / E or e for Electronics): ");
                String productType = sc.next();
                if (!productType.equalsIgnoreCase("e") && !productType.equalsIgnoreCase("c")) {
                    System.out.println("Invalid input. Please enter 'C' for Clothing or 'E' for Electronics.");
                } else {
                    validProductType = true;

                    while(true) {
                        PrintStream printStream = System.out;
                        String upperCase = productType.toUpperCase();
                        printStream.print("Enter the product id (e.g: " + upperCase + "1 to " 
                                + productType.toUpperCase() + "50 or " 
                                + productType.toLowerCase() + "1 to " 
                                + productType.toLowerCase() + "50): ");
                        String productID = sc.next();
                        if (productID.matches("(?i)([ce][1-9]|[ce][1-4][0-9]|[ec]50)")) {
                            System.out.print("Enter the product name: ");
                            String name = sc.next();

                            int availableItem;
                            while(true) {
                                try {
                                    System.out.print("Enter the available items: ");
                                    availableItem = sc.nextInt();
                                    break;
                                } catch (InputMismatchException inputException1) {
                                    System.out.println("Invalid input. Please enter a valid integer for available items.");
                                    sc.nextLine();
                                }
                            }

                            double price;
                            while(true) {
                                try {
                                    System.out.print("Enter price: ");
                                    price = sc.nextDouble();
                                    break;
                                } catch (InputMismatchException inputException2) {
                                    System.out.println("Invalid input. Please enter a valid price.");
                                    sc.nextLine();
                                }
                            }

                            String warrantyPeriod;
                            if (productType.equalsIgnoreCase("c")) {
                                String[] validSizes = new String[]{"xs", "s", "m", "l", "xl", "xxl"};

                                do {
                                    System.out.print("Enter valid size (xs, s, m, l, xl, xxl): ");
                                    warrantyPeriod = sc.next().toLowerCase();
                                    if (Arrays.asList(validSizes).contains(warrantyPeriod)) {
                                        break;
                                    }

                                    System.out.println("Invalid input. Please enter a valid size.");
                                } while(!Arrays.asList(validSizes).contains(warrantyPeriod));

                                System.out.print("Enter color: ");
                                String color = sc.next();
                                System.out.println();
                                Product product = new Clothing(productID, name, availableItem, price, warrantyPeriod, color);
                                this.addProduct(product);
                            } else if (productType.equalsIgnoreCase("e")) {
                                System.out.print("Enter the brand here: ");
                                String brand = sc.next();
                                sc = new Scanner(System.in);
                                warrantyPeriod = null;
                                boolean validInput = false;

                                while(!validInput) {
                                    System.out.print("Enter warranty period (e.g: '3 years' or '3 months'): ");
                                    warrantyPeriod = sc.nextLine().trim();
                                    if (!warrantyPeriod.matches("\\d+\\s*(years?|months?)")) {
                                        System.out.println("Invalid input. Please enter a valid warranty period (e.g: '3 years' or '3 months').\n");
                                    } else {
                                        validInput = true;
                                    }
                                }

                                Product product = new Electronics(productID, name, availableItem, price, brand, warrantyPeriod);
                                this.addProduct(product);
                            }
                            break;
                        }

                        printStream = System.out;
                        upperCase = productType.toUpperCase();
                        printStream.println("Invalid input. Please enter a valid product ID (e.g: " + upperCase + "1 to "
                                + productType.toUpperCase() + "50 or "
                                + productType.toLowerCase() + "1 to "
                                + productType.toLowerCase() + "50).");
                    }
                }
            } catch (InputMismatchException inputException3) {
                System.out.println("Invalid input. Please enter the correct data type.");
                sc.nextLine();
            }
        } while(!validProductType);

    }

    public void addProduct(Product product) {
        if (product != null) {
            if (this.productList.size() < 50) {
                this.productList.add(product);
                System.out.println("Product added successfully: " + product.getProductName());
                System.out.println();
            } else {
                System.out.println("Cannot add more products. Maximum limit reached.");
            }
        } else {
            System.out.println("Cannot add a null product.");
        }

    }

    public void deleteProduct(Scanner sc) {
        while(true) {
            try {
                System.out.print("Enter the product id to delete (e.g.,  E1 to E50 / e1 to e50 or C1 to C50 / c1 to c50): ");
                String productIdToDelete = sc.next();
                System.out.println();
                Product productToDelete;
                if (productIdToDelete.matches("(?i)([ce][1-9]|[ce][1-4][0-9]|[ce]50)")) {
                    productToDelete = this.findProductById(productIdToDelete);
                    if (productToDelete != null) {
                        this.removeProduct(productToDelete);
                        System.out.println("Product deleted successfully.");
                        System.out.println();
                    } else {
                        System.out.println("Product not found: " + productIdToDelete);
                    }
                } else {
                    if (!productIdToDelete.matches("(?i)([ce]'5''0')")) {
                        System.out.println("Invalid input. Please enter a valid product ID (e.g.,  E1 to E50).");
                        continue;
                    }

                    productToDelete = this.findProductById(productIdToDelete);
                    if (productToDelete != null) {
                        this.removeProduct(productToDelete);
                        System.out.println("Product deleted successfully.");
                    } else {
                        System.out.println("Product not found: " + productIdToDelete);
                        System.out.println();
                    }
                }
            } catch (InputMismatchException inputException4) {
                System.out.println("Invalid input. Please enter the correct ID.");
                sc.nextLine();
            }

            return;
        }
    }

    public void removeProduct(Product product) {
        if (this.productList.remove(product)) {
            System.out.println("Product removed: " + product.getProductName());
            System.out.println("Total number of products left: " + this.productList.size());
        } else {
            System.out.println("Product not found: " + product.getProductName());
        }

    }

    public void printProductList() {
        System.out.println("Product List:");

        try {
            List<Product> productList = this.getAllProducts();
            productList.sort(Comparator.comparing(Product::getProductName));
            Iterator pL = productList.iterator();

            while(pL.hasNext()) {
                Product product = (Product)pL.next();
                PrintStream printStream = System.out;
                String upperCase = product.getProductType();
                printStream.print("Type: " + upperCase + " ID: " + product.getProductId()
                        + ", Name: " + product.getProductName()
                        + ", Available Items: " + product.getAvailableItems()
                        + ", Price: " + product.getPrice());

                if (product instanceof Clothing clothing) {
                    printStream = System.out;
                    upperCase = clothing.getSize();
                    printStream.println(", Size: " + upperCase + ", Color: " + clothing.getColor());
                    System.out.println();
                } else if (product instanceof Electronics electronics) {
                    printStream = System.out;
                    upperCase = electronics.getBrand();
                    printStream.println(", Brand: " + upperCase + ", Warranty Period: " + electronics.getWarrantyPeriod() + " years");
                    System.out.println();
                } else {
                    System.out.println();
                }
            }
        } catch (Exception exception1) {
            System.out.println("An error occurred while processing the product list: " + exception1.getMessage());
            System.out.println();
        }

    }

    public List<Product> getAllProducts() {
        return new ArrayList(this.productList);
    }

    public void saveProductsToFile() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("products.txt"));

            try {
                ArrayList<Product> productsToSave = new ArrayList();
                Iterator file1 = this.productList.iterator();

                while(true) {
                    if (!file1.hasNext()) {
                        oos.writeObject(productsToSave);
                        System.out.println("Products saved to file successfully.");
                        System.out.println();
                        break;
                    }

                    Product product = (Product)file1.next();
                    if (product instanceof Clothing || product instanceof Electronics) {
                        productsToSave.add(product);
                    }
                }
            } catch (Throwable exception1) {
                try {
                    oos.close();
                } catch (Throwable throwable) {
                    exception1.addSuppressed(throwable);
                }

                throw exception1;
            }

            oos.close();
        } catch (IOException error) {
            System.out.println("Error saving products to file: " + error.getMessage());
        }

    }

    public void loadProductsFromFile() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("products.txt"));

            try {
                Object obj = ois.readObject();
                if (obj instanceof ArrayList<?>) {
                    ArrayList<?> loadedProducts = (ArrayList<?>) obj;
                    if (loadedProducts.size() > 0 && loadedProducts.get(0) instanceof Product) {
                        this.productList.clear();
                        this.productList.addAll((ArrayList<Product>) loadedProducts);
                        System.out.println("Products loaded from file.");
                        System.out.println();
                    }
                }
            } catch (Throwable throwable) {
                try {
                    ois.close();
                } catch (Throwable errors) {
                    throwable.addSuppressed(errors);
                }

                throw throwable;
            }

            ois.close();
        } catch (ClassNotFoundException | IOException exception1) {
            System.out.println("Error loading products from file: " + exception1.getMessage());
        }

    }

    public Product findProductById(String productId) {
        Iterator pL = this.productList.iterator();

        Product product;
        do {
            if (!pL.hasNext()) {
                return null;
            }

            product = (Product)pL.next();
        } while(!product.getProductId().equals(productId));

        return product;
    }
}
