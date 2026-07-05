package za.ac.cput.factory;
/* BillingAddressFactoryTest.java

   BillingAddress Factory Testing class

   Author: Entle Mayezo	(230076238)

   Date: 28 June 2026
*/
import org.junit.jupiter.api.*;
import za.ac.cput.domain.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BillingAddressFactoryTest {

    @Test
    @Order(1)
    @DisplayName("Should create basic billing address")
    void createBillingAddress() {
        BillingAddress address = BillingAddressFactory.createBillingAddress(
                "John Doe", "123 Main St", "Cape Town", "8001"
        );

        assertNotNull(address);
        assertEquals("John Doe", address.getFullName());
        assertEquals("123 Main St", address.getAddressLine1());
        assertEquals("Cape Town", address.getCity());
        assertEquals("8001", address.getPostalCode());
        assertEquals("South Africa", address.getCountry());

        System.out.println("=== Basic Billing Address ===");
        System.out.println("Full Name: " + address.getFullName());
        System.out.println("Address: " + address.getAddressLine1());
        System.out.println("City: " + address.getCity());
        System.out.println("Country: " + address.getCountry());
    }

    @Test
    @Order(2)
    @DisplayName("Should create full billing address")
    void createFullBillingAddress() {
        BillingAddress address = BillingAddressFactory.createFullBillingAddress(
                "Jane Smith", "456 Long St", "Apt 4B", "Cape Town",
                "Western Cape", "8001", "South Africa", "+27712345678"
        );

        assertNotNull(address);
        assertEquals("Apt 4B", address.getAddressLine2());
        assertEquals("Western Cape", address.getState());
        assertEquals("South Africa", address.getCountry());
        assertEquals("+27712345678", address.getPhone());

        System.out.println("=== Full Billing Address ===");
        System.out.println("Address Line 2: " + address.getAddressLine2());
        System.out.println("State: " + address.getState());
        System.out.println("Phone: " + address.getPhone());
    }

    @Test
    @Order(3)
    @DisplayName("Should create South African billing address")
    void createSouthAfricanBillingAddress() {
        BillingAddress address = BillingAddressFactory.createSouthAfricanBillingAddress(
                "Peter Parker", "789 Beach Rd", "Cape Town", "8005",
                "Western Cape", "+27719876543"
        );

        assertNotNull(address);
        assertEquals("South Africa", address.getCountry());
        assertEquals("Western Cape", address.getState());

        System.out.println("=== South African Billing Address ===");
        System.out.println("Country: " + address.getCountry());
        System.out.println("State: " + address.getState());
    }

    @Test
    @Order(4)
    @DisplayName("Should throw exception when full name is empty")
    void showExceptionWhenFullNameEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                BillingAddressFactory.createBillingAddress("", "123 Main St", "Cape Town", "8001")
        );
        System.out.println("✓ Empty full name correctly rejected");
    }

    @Test
    @Order(5)
    @DisplayName("Should throw exception when address line 1 is empty")
    void showExceptionWhenAddressLine1Empty() {
        assertThrows(IllegalArgumentException.class, () ->
                BillingAddressFactory.createBillingAddress("John Doe", "", "Cape Town", "8001")
        );
        System.out.println("✓ Empty address line 1 correctly rejected");
    }

    @Test
    @Order(6)
    @DisplayName("Should throw exception when city is empty")
    void showExceptionWhenCityEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                BillingAddressFactory.createBillingAddress("John Doe", "123 Main St", "", "8001")
        );
        System.out.println("✓ Empty city correctly rejected");
    }
}