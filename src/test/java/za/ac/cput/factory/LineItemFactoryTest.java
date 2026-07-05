package za.ac.cput.factory;
/* LineItemFactoryTest.java

   LineItem Factory Testing class

   Author: Somila Ndoboza (231157592)

   Date: 28 June 2026
*/
import org.junit.jupiter.api.*;
import za.ac.cput.domain.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LineItemFactoryTest {

    @Test
    @Order(1)
    @DisplayName("Should create line item")
    void createLineItem() {
        LineItem item = LineItemFactory.createLineItem("Flight Ticket", 2, 425.00);

        assertNotNull(item);
        assertEquals("Flight Ticket", item.getDescription());
        assertEquals(2, item.getQuantity());
        assertEquals(425.00, item.getUnitPrice());
        assertEquals(850.00, item.getTotal());

        System.out.println("=== Line Item ===");
        System.out.println("Description: " + item.getDescription());
        System.out.println("Quantity: " + item.getQuantity());
        System.out.println("Unit Price: " + item.getUnitPrice());
        System.out.println("Total: " + item.getTotal());
    }

    @Test
    @Order(2)
    @DisplayName("Should create line item with pre-calculated total")
    void createLineItemWithTotal() {
        LineItem item = LineItemFactory.createLineItemWithTotal("Hotel Night", 3, 200.00, 600.00);

        assertNotNull(item);
        assertEquals(600.00, item.getTotal());

        System.out.println("=== Line Item with Pre-calculated Total ===");
        System.out.println("Total: " + item.getTotal());
    }

    @Test
    @Order(3)
    @DisplayName("Should calculate total correctly")
    void testCalculateTotal() {
        LineItem item = LineItemFactory.createLineItem("Service", 3, 100.00);
        assertEquals(300.00, item.getTotal());

        System.out.println("✓ Total calculation correct: " + item.getTotal());
    }

    @Test
    @Order(4)
    @DisplayName("Should throw exception when quantity is not positive")
    void showExceptionWhenQuantityInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                LineItemFactory.createLineItem("Item", 0, 100.00)
        );
        System.out.println("✓ Invalid quantity correctly rejected");
    }

    @Test
    @Order(5)
    @DisplayName("Should throw exception when unit price is not positive")
    void showExceptionWhenUnitPriceInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                LineItemFactory.createLineItem("Item", 1, 0)
        );
        System.out.println("✓ Invalid unit price correctly rejected");
    }

    @Test
    @Order(6)
    @DisplayName("Should throw exception when description is empty")
    void showExceptionWhenDescriptionEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                LineItemFactory.createLineItem("", 1, 100.00)
        );
        System.out.println("✓ Empty description correctly rejected");
    }
}