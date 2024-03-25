public class Clothing extends Product{
    private String size;
    private String color;


    public Clothing(String productId, String productName, int availableItems, double price, String size, String color) {
        super(productId, productName, availableItems, price);
        this.size = size;
        this.color = color;
    }

    // Getters and Setters for Clothing-specific attributes

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }



    @Override
    public void displayInfo() {
        System.out.println("Clothing - " + super.getProductName() + " - " +
                "Size: " + size + ", Color: " + color);
    }
}