

import java.io.Serializable;

public class Electronics extends Product implements Serializable {
    private String brand;
    private String warrantyPeriod;

    public Electronics() {
    }

    public Electronics(String productID, String productName, int availableItems, double price, String brand, String warrantyPeriod) {
        super(productID, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getProductType() {
        return "Electronics ";
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getWarrantyPeriod() {
        return this.warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
}
