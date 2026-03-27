package za.ac.cput.factory;

import org.junit.jupiter.api.*;
import za.ac.cput.domain.Address;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressFactoryTest {

    private Address address1;
    private Address address2;
    private Address address3;

    @BeforeEach
    void setUp() {
        address1 = AddressFactory.createAddress("123", "Main Street", "Cape Town", "8001");
        address2 = AddressFactory.createFullAddress("456", "Long Street", "City Centre", "Cape Town", "Western Cape", "South Africa", "8001", "HOME", true);
        address3 = AddressFactory.createSouthAfricanAddress("789", "Beach Road", "Sea Point", "Cape Town", "Western Cape", "8005");
    }

    @Test
    @Order(1)
    @DisplayName("Should create basic address")
    void createAddress() {
        assertNotNull(address1);
        assertEquals("123", address1.getStreetNumber());
        assertEquals("Main Street", address1.getStreetName());
        assertEquals("Cape Town", address1.getCity());
        assertEquals("8001", address1.getPostalCode());
        System.out.println("Basic Address: " + address1);
    }

    @Test
    @Order(2)
    @DisplayName("Should create full address with all details")
    void createFullAddress() {
        assertNotNull(address2);
        assertEquals("Western Cape", address2.getProvince());
        assertEquals("South Africa", address2.getCountry());
        assertEquals("HOME", address2.getAddressType());
        assertTrue(address2.isDefault());
        System.out.println("Full Address: " + address2);
    }

    @Test
    @Order(3)
    @DisplayName("Should create South African address")
    void createSouthAfricanAddress() {
        assertNotNull(address3);
        assertEquals("South Africa", address3.getCountry());
        assertEquals("789", address3.getStreetNumber());
        assertEquals("Beach Road", address3.getStreetName());
        System.out.println("SA Address: " + address3);
    }

    @Test
    @Order(4)
    @DisplayName("Should throw exception when street number is empty")
    void showExceptionWhenStreetNumberEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                AddressFactory.createAddress("", "Main St", "Cape Town", "8001")
        );
    }

    @Test
    @Order(5)
    @DisplayName("Should validate full address")
    void validateFullAddress() {
        assertTrue(address1.validateAddress());
        assertTrue(address2.validateAddress());
        System.out.println("Address validation passed");
    }
}