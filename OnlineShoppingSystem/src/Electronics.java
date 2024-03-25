public class Electronics extends Product{
        private String brand;
        private int warrantyPeriod;


        public Electronics(String productId, String productName, int availableItems, double price, String brand, int warrantyPeriod) {
            super(productId, productName, availableItems, price);
            this.brand = brand;
            this.warrantyPeriod = warrantyPeriod;
        }

        // Getters and Setters for Electronics-specific attributes

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public int getWarrantyPeriod() {
            return warrantyPeriod;
        }

        public void setWarrantyPeriod(int warrantyPeriod) {
            this.warrantyPeriod = warrantyPeriod;
        }



    @Override
        public void displayInfo() {
            System.out.println("Electronics - " + super.getProductName() + " - " +
                    "Brand: " + brand + ", Warranty: " + warrantyPeriod + " months");
        }
    }

