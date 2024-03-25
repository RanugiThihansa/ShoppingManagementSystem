import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WestminsterShoppingManagerTest {
    private WestminsterShoppingManager shoppingManager;

    @BeforeEach
    void setUp() {
        shoppingManager = new WestminsterShoppingManager();
    }

    @Test
    void addProductTest() {
        // You can use ByteArrayInputStream to simulate user input in tests
        String input = "C\n123\nTestClothing\n10\n50.0\nM\nBlue\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        shoppingManager.addProduct();

        List<Product> productList = WestminsterShoppingManager.getProductList();
        assertEquals(1, productList.size());
        assertTrue(productList.get(0) instanceof Clothing);
    }

    @Test
    void deleteProductTest() {
        // Assuming there's a product to delete
        Clothing clothing = new Clothing("123", "TestClothing", 10, 50.0, "M", "Blue");
        WestminsterShoppingManager.getProductList().add(clothing);

        // You can use ByteArrayInputStream to simulate user input in tests
        String input = "123\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        shoppingManager.deleteProduct();

        List<Product> productList = WestminsterShoppingManager.getProductList();
        assertEquals(0, productList.size());
    }

}
