import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public Main() {
    }

    private static void openGui(WestminsterShoppingManager shoppingManager) {
        new MyGui(shoppingManager);
    }

    public static void main(String[] args) {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Scanner sc = new Scanner(System.in);

        int choice;
        do {
            shoppingManager.displayMenu();

            try {
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 0:
                        shoppingManager.saveProductsToFile();
                        System.out.println("Exiting...Have a good day!");
                        break;
                    case 1:
                        shoppingManager.addProductFromConsole(sc);
                        break;
                    case 2:
                        shoppingManager.deleteProduct(sc);
                        break;
                    case 3:
                        shoppingManager.printProductList();
                        break;
                    case 4:
                        shoppingManager.saveProductsToFile();
                        break;
                    case 5:
                        shoppingManager.loadProductsFromFile();
                        break;
                    case 6:
                        openGui(shoppingManager);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        System.out.println();
                }
            } catch (InputMismatchException throwable) {
                System.err.println("Invalid input. Please enter a valid integer.");
                System.out.println();
                sc.nextLine();
                choice = -1;
            }
        } while(choice != 0);

        sc.close();
    }
}
