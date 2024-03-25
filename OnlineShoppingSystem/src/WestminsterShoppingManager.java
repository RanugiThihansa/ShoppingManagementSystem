import java.util.*;
import java.io.*;
import javax.swing.*;

public class WestminsterShoppingManager implements ShoppingManager {
    private static final ArrayList<Product> productsList = new ArrayList<>();

    public static List<Product> getProductList() {
        return productsList;
    }

    public void displayMenu() {
        System.out.println("\n*****WESTMINSTER SHOPPING MANAGER*****\n");
        boolean loop = true;

        while (loop) {
            System.out.println("Choose an option");
            System.out.println("1. Add new product");
            System.out.println("2. Delete product");
            System.out.println("3. Print products list");
            System.out.println("4. Save products to a file");
            System.out.println("5. Load products from file");
            System.out.println("6. Open the GUI");
            System.out.println("7. Exit");

            System.out.print("Select option: ");

            Scanner scanner = new Scanner(System.in);
            try {
                int userOption = scanner.nextInt();

                switch (userOption) {
                    case 1:
                        addProduct();
                        break;
                    case 2:
                        deleteProduct();
                        break;
                    case 3:
                        printProductList();
                        break;
                    case 4:
                        saveToFile();
                        break;
                    case 5:
                        loadFromFile();
                        break;
                    case 6:
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                new UserLogin();
                            }
                        });

                        break;
                    case 7:
                        System.out.println("You have Chosen to exit the program!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                        break;
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a valid option.");
                scanner.nextLine();
                displayMenu();
            }
        }

    }

    @Override
    public void addProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You have chosen to add products.");
        System.out.println("Product Types:\n    Electronics - E \n     Clothing - C");
        System.out.println("Enter product Type (E or C)");
        try {

            String type = scanner.next().toUpperCase();


            if (type.equals("C")) {
                System.out.println("\nYou have chosen Clothing.");

                System.out.print("Enter product ID: ");
                String productId = scanner.next();
                System.out.print("Enter product name: ");
                String productName = scanner.next();
                System.out.print("Enter number of available items: ");
                int availableItems = scanner.nextInt();
                System.out.print("Enter Price: ");
                double price = scanner.nextDouble();
                System.out.print("Enter size(S/M/L/XL): ");
                String size = scanner.next();
                System.out.print("Enter Color: ");
                String color = scanner.next();


                if (productsList.size() >= 50) {
                    System.out.println("products reached it's limits.\n");
                } else {
                    Product clothing = new Clothing(productId, productName, availableItems, price, size, color);
                    productsList.add(clothing);
                    System.out.println("Products successfully added.\n");
                }
            } else if (type.equals("E")) {
                System.out.println("\nYou have chosen Electronics.");

                System.out.print("Enter product ID: ");
                String productId = scanner.next();
                System.out.print("Enter product name: ");
                String productName = scanner.next();
                System.out.print("Enter number of available items: ");
                int availableItems = scanner.nextInt();
                System.out.print("Enter Price: ");
                double price = scanner.nextDouble();
                System.out.print("Enter the brand: ");
                String brand = scanner.next();
                System.out.print("Enter the warranty period (Number of years): ");
                int warrantyPeriod = scanner.nextInt();


                if (productsList.size() >= 50) {
                    System.out.println("products reached it's limits.\n");
                } else {
                    Product electronic = new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);
                    productsList.add(electronic);
                    System.out.println("Products successfully added.\n");
                }

            } else {
                System.out.println("Invalid product type.\n");
                addProduct();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid option.");
            scanner.nextLine();
            addProduct();
        }


    }


    @Override
    public void deleteProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the product ID to delete: ");
        try
        {

    String productIdToDelete = scanner.next();

    Product productSearch = null;
        for(
    Product product :productsList)

    {
        if (product.getProductId().equals(productIdToDelete)) {
            productSearch = product;
            break;
        }
    }

        if(productSearch !=null)

    {
        System.out.println("Product successfully deleted.\nDeleted Product Information:");
        System.out.println("Type: " + (productSearch instanceof Clothing ? "Clothing" : "Electronics")); //https://www.w3schools.com/java/ref_keyword_instanceof.asp
        System.out.println("ID: " + productSearch.getProductId());
        System.out.println("Name: " + productSearch.getProductName());


        productsList.remove(productSearch);

        System.out.println("Total number of products left: " + productsList.size());
    } else

    {
        System.out.println("Product with ID " + productIdToDelete + " not found.");
}
        }catch(InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid product ID.");
            scanner.nextLine(); // Consume the invalid input
            deleteProduct();

        }
    }



    @Override
    public void printProductList() {

        Collections.sort(productsList, Comparator.comparing(Product::getProductId));
        //https://www.geeksforgeeks.org/collections-sort-java-examples/
        //https://www.baeldung.com/java-8-comparator-comparing

        System.out.println("Product List:");

        for (Product product : productsList) {

            String productType = (product instanceof Clothing) ? "Clothing" : "Electronics";

            System.out.println("Type: " + productType);
            System.out.println("ID: " + product.getProductId());
            System.out.println("Name: " + product.getProductName());
            System.out.println("Available Items: " + product.getAvailableItems());
            System.out.println("Price: " + product.getPrice());

            if (product instanceof Clothing) {
                Clothing clothingProduct = (Clothing) product;
                System.out.println("Size: " + clothingProduct.getSize());
                System.out.println("Color: " + clothingProduct.getColor());
            }
            else{
                Electronics electronicProduct = (Electronics) product;
                System.out.println("Brand: " + electronicProduct.getBrand());
                System.out.println("Warranty Period: " + electronicProduct.getWarrantyPeriod());
            }
            System.out.println();
        }
    }

    @Override
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("products.txt"))) {
            for (Product product : productsList) {
                if (product instanceof Clothing) {
                    Clothing clothingProduct = (Clothing) product;
                    writer.println(String.format("Clothing,%s,%s,%d,%.2f,%s,%s",
                            clothingProduct.getProductId(),
                            clothingProduct.getProductName(),
                            clothingProduct.getAvailableItems(),
                            clothingProduct.getPrice(),
                            clothingProduct.getSize(),
                            clothingProduct.getColor()));
                } else {
                    Electronics electronicProduct = (Electronics) product;
                    writer.println(String.format("Electronics,%s,%s,%d,%.2f,%s,%d",
                            electronicProduct.getProductId(),
                            electronicProduct.getProductName(),
                            electronicProduct.getAvailableItems(),
                            electronicProduct.getPrice(),
                            electronicProduct.getBrand(),
                            electronicProduct.getWarrantyPeriod()));
                }
            }
            System.out.println("Products successfully saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving products to file: " + e.getMessage());
        }

    }

    @Override
    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals("Clothing")) {
                    Clothing clothingProduct = new Clothing(
                            parts[1],
                            parts[2],
                            Integer.parseInt(parts[3]),
                            (int) Double.parseDouble(parts[4]),
                            parts[5],
                            parts[6]);
                    productsList.add(clothingProduct);
                } else if (parts[0].equals("Electronics")) {
                    Electronics electronicProduct = new Electronics(
                            parts[1],
                            parts[2],
                            Integer.parseInt(parts[3]),
                            (int) Double.parseDouble(parts[4]),
                            parts[5],
                            Integer.parseInt(parts[6]));
                    productsList.add(electronicProduct);
                }
            }
            System.out.println("Products successfully loaded from file.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading products from file: " + e.getMessage());
        }

        }

}
