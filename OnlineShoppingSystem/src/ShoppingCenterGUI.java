import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCenterGUI extends JFrame {

    private DefaultTableModel cartTableModel;
    private JTable productTable;
    private DefaultTableModel productTableModel;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel categoryNameLabel;
    private JLabel detailsLabel;
    private JLabel priceLabel;
    private JLabel availableItemsLabel;
    private JLabel totalLabel;
    private JLabel firstPurchaseDis;
    private JLabel threeItemDis;
    private JLabel finalTotalLabel;

    private  UserLogin userList=new UserLogin();

    private Map<String, Integer> categoryCountMap;

    public ShoppingCenterGUI() {
        userList.setVisible(false);
        setTitle("Westminster Shopping Center");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(700, 510);
        //setLocationRelativeTo(null);
        setLocation(300, 200);

        JLabel categoryLabel = new JLabel("Product Category:");

        String[] categories = {"All", "Electronics", "Clothing"};
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);

        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String selectedCategory = (String) categoryComboBox.getSelectedItem();


                productTableModel.setRowCount(0);

                for (Product product : WestminsterShoppingManager.getProductList()) {
                    if (selectedCategory.equals("All") || getCategory(product).equals(selectedCategory)) {
                        addProductRow(product);
                    }
                }
            }
        });



        JButton cartButton = new JButton("Shopping Cart");

        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openShoppingCartFrame();
            }
        });



        JPanel categoryPanel = new JPanel();
        categoryPanel.setPreferredSize(new Dimension(400, 70));
        categoryPanel.add(categoryLabel);
        categoryPanel.add(categoryComboBox);
        categoryPanel.add(cartButton);



        productTableModel = new DefaultTableModel();
        productTableModel.addColumn("Product ID");
        productTableModel.addColumn("Name");
        productTableModel.addColumn("Category");
        productTableModel.addColumn("Price");
        productTableModel.addColumn("Info");

        productTable = new JTable(productTableModel);
        productTable.setShowGrid(true);
        productTable.setGridColor(Color.BLACK);
        productTable.setRowHeight(30);
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        productTable.setPreferredScrollableViewportSize(new Dimension(650, 150));

        for (Product product : WestminsterShoppingManager.getProductList()) {
            addProductRow(product);
        }



        JPanel detailsPanel = new JPanel(new GridLayout(0, 1));
        JLabel detailTitle=new JLabel("Selected Product Details: \n");
        idLabel=new JLabel();
        nameLabel=new JLabel();
        categoryNameLabel=new JLabel();
        priceLabel=new JLabel();
        detailsLabel = new JLabel();
        availableItemsLabel=new JLabel();


        detailsPanel.add(detailTitle);
        detailsPanel.add(idLabel);
        detailsPanel.add(nameLabel);
        detailsPanel.add(categoryNameLabel);
        detailsPanel.add(priceLabel);
        detailsPanel.add(detailsLabel);
        detailsPanel.add(availableItemsLabel);

        // Add a list selection listener to the table
        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = productTable.getSelectedRow();
                    if (selectedRow != -1) {
                        showProductDetails(WestminsterShoppingManager.getProductList().get(selectedRow));
                    }
                }
            }
        });

        JButton addToCartButton = new JButton("Add to Shopping Cart");

        addToCartButton.setLayout(new BorderLayout());

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    Product selectedProduct = WestminsterShoppingManager.getProductList().get(selectedRow);
                    addToShoppingCart(selectedProduct);
                    updateLabels(totalLabel,firstPurchaseDis,finalTotalLabel);

                }
            }
        });

        detailsPanel.add(addToCartButton);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(categoryPanel, BorderLayout.NORTH);
        inputPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);

        Container container = getContentPane();
        container.setLayout(new FlowLayout());
        container.add(inputPanel, BorderLayout.NORTH);
        container.add(detailsPanel, BorderLayout.WEST);


        setVisible(true);


    }


    private void addProductRow(Product product) {
        // Add a new row to the table with the product information
        Object[] row = {product.getProductId(),
                        product.getProductName(),
                        getCategory(product),
                        product.getPrice(),
                        getProductInfo(product)};
        productTableModel.addRow(row);



    }

    private String getProductInfo(Product product) {
        if (product instanceof Clothing) {
            Clothing clothes = (Clothing) product;
            return String.format(" %s, %s", clothes.getSize(), clothes.getColor());
    }else {
            Electronics electronics=(Electronics) product;
            return String.format(" %s, %s", electronics.getBrand(),electronics.getWarrantyPeriod());
        }


    }

    private String getCategory(Product product) {
        if (product instanceof Clothing) {
            return "Clothing";
        }else {
            return "Electronics";
        }


    }
    private void showProductDetails(Product product) {
        idLabel.setText("Product ID: " + product.getProductId());
        nameLabel.setText("Name: " + product.getProductName());
        categoryNameLabel.setText("Category: "+ getCategory(product));
        priceLabel.setText("Price: " + product.getPrice());
        if (product instanceof Clothing){
        detailsLabel.setText("Size & Colour: "+getProductInfo(product));
        }else{
            detailsLabel.setText("Brand & Warranty years: "+getProductInfo(product));
        }

        availableItemsLabel.setText("Available Items: "+product.getAvailableItems());
    }

    private void openShoppingCartFrame() {
        JFrame shoppingCartFrame = new JFrame("Shopping Cart");
        shoppingCartFrame.setSize(400, 350);
        //shoppingCartFrame.setLocationRelativeTo(null);


        shoppingCartFrame.setLocation(1020, 200);

        // Assign the new DefaultTableModel to the cartTableModel instance variable
        cartTableModel = new DefaultTableModel();
        cartTableModel.addColumn("Product");
        cartTableModel.addColumn("Quantity");
        cartTableModel.addColumn("Price");

        JTable cartTable = new JTable(cartTableModel);
        cartTable.setRowHeight(50);
        JScrollPane scrollPane = new JScrollPane(cartTable);
        cartTable.setPreferredScrollableViewportSize(new Dimension(300, 150));


        totalLabel = new JLabel("Total:  0.00 $");
        firstPurchaseDis=new JLabel("First Purchase Discount(10%):  0.00 $");
        threeItemDis=new JLabel("Three Items Discount(20%):  0.00 $");
        finalTotalLabel = new JLabel("<html><b>Final total:  0.00 $</b></html>");

        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        finalTotalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        threeItemDis.setHorizontalAlignment(SwingConstants.RIGHT);
        finalTotalLabel.setHorizontalAlignment(SwingConstants.RIGHT);



        JPanel cartPanel = new JPanel(new GridLayout(0, 1));
        //cartPanel.setPreferredSize(new Dimension(100, 50));
        cartPanel.add(scrollPane);

        categoryCountMap = new HashMap<>();

        JPanel totalPanel=new JPanel(new GridLayout(0, 1));
        totalPanel.add(totalLabel);
        totalPanel.add(firstPurchaseDis);
        totalPanel.add(threeItemDis);
        totalPanel.add(finalTotalLabel);

        //shoppingCartFrame.setLayout(new GridLayout(1, 1));
        cartPanel.setPreferredSize(new Dimension(350, 200));
        shoppingCartFrame.setLayout(new FlowLayout());
        shoppingCartFrame.add(cartPanel,BorderLayout.NORTH);
        shoppingCartFrame.add(totalPanel,BorderLayout.SOUTH);

        shoppingCartFrame.setVisible(true);
    }



    private void addToShoppingCart(Product product) {

        if (product.getAvailableItems() > 0) {
            product.decreaseAvailableItems();

            // Check if the product is already in the cart
            boolean productAlreadyInCart = false;
            for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                if (cartTableModel.getValueAt(i, 0).equals("<html>"+product.getProductId()+"<br>"+product.getProductName()+"<br>"+getProductInfo(product)+"<html>")){
                    // Product is already in the cart, update the quantity and total price
                    int currentQuantity = (int) cartTableModel.getValueAt(i, 1);
                    double currentTotalPrice = (double) cartTableModel.getValueAt(i, 2);

                    cartTableModel.setValueAt(currentQuantity + 1, i, 1);
                    cartTableModel.setValueAt(currentTotalPrice + product.getPrice(), i, 2);

                    productAlreadyInCart = true;
                    break;
                }
            }

            if (!productAlreadyInCart) {
                // Product is not in the cart, add a new row
                String productDetails=("<html>"+product.getProductId()+"<br>"+product.getProductName()+"<br>"+getProductInfo(product)+"<html>");
                Object[] row = {productDetails, 1, product.getPrice()};
                cartTableModel.addRow(row);
            }

            String category = getCategory(product);
            categoryCountMap.put(category, categoryCountMap.getOrDefault(category, 0) + 1);

            availableItemsLabel.setText("Available Items: " + product.getAvailableItems());

        } else {
            JOptionPane.showMessageDialog(this, "Sorry, the product is out of stock.", "Out of Stock", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateLabels(JLabel totalLabel,JLabel firstPurchaseDis,JLabel finalTotalLabel) {
        double total = 0.0;
        double firstDis=0.0;
        double finalTotal=0.0;
        double threeItemDiscount = 0.0;

        for (int i = 0; i < cartTableModel.getRowCount(); i++) {
            total += (double) cartTableModel.getValueAt(i, 2);
            if (userList.firstPurchase==true) {
                firstDis = total * 0.10;
            }
            for (int count : categoryCountMap.values()) {
                // Check if the user has at least three items of the same category
                if (count >= 3) {
                    threeItemDiscount = total * 0.20;
                    break;
                }
            }

            finalTotal= total-(firstDis+threeItemDiscount);
        }
        totalLabel.setText("Total:  " + String.format("%.2f", total)+" $");
        firstPurchaseDis.setText("First Purchase Discount(10%):  -"+ String.format("%.2f", firstDis)+" $");
        threeItemDis.setText("Three Items Discount(20%):  -" + String.format("%.2f", threeItemDiscount)+" $");
        finalTotalLabel.setText("<html><b>Final total:  </b>"+ String.format("%.2f", finalTotal)+" $</html>");

    }

}

