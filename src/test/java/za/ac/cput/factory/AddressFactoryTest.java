package za.ac.cput.factory;
/* AddressFactoryTest.java

   Address Factory Testing class

   Author: Zamandlovu C Ndlovu (211204803)

   Date: 28 June 2026
*/
import org.junit.jupiter.api.*;
import za.ac.cput.domain.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressFactoryTest {

    private Address postalAddress;

    @BeforeEach
    void setUp() {
        postalAddress = AddressFactory.createFullAddress(
                "14m", "Hoffman Street", "Bellville South", "Cape Town",
                "Western Cape", "7750", "South Africa",
                "POSTAL", true
        );
    }

    @Test
    @Order(1)
    @DisplayName("Should create postal address with all details")
    void createPostalAddress() {
        assertNotNull(postalAddress);
        assertEquals("14m", postalAddress.getStreetNumber());
        assertEquals("Hoffman Street", postalAddress.getStreetName());
        assertEquals("Bellville South", postalAddress.getSuburb());
        assertEquals("Cape Town", postalAddress.getCity());
        assertEquals("Western Cape", postalAddress.getProvince());
        assertEquals("South Africa", postalAddress.getCountry());
        assertEquals("7750", postalAddress.getPostalCode());
        assertEquals("POSTAL", postalAddress.getAddressType());
        assertTrue(postalAddress.isDefault());

        System.out.println("=== Postal Address Created ===");
        System.out.println("Full Address: " + postalAddress.getFullAddress());
        System.out.println("Address Type: " + postalAddress.getAddressType());
        System.out.println("Is Default: " + postalAddress.isDefault());
    }

    @Test
    @Order(2)
    @DisplayName("Should create basic address")
    void createBasicAddress() {
        Address basicAddress = AddressFactory.createAddress(
                "123", "Main Street", "City Centre", "Cape Town",
                "Western Cape", "8001", "South Africa"
        );

        assertNotNull(basicAddress);
        assertEquals("123", basicAddress.getStreetNumber());
        assertEquals("Main Street", basicAddress.getStreetName());
        assertEquals("Cape Town", basicAddress.getCity());

        System.out.println("=== Basic Address ===");
        System.out.println("Address: " + basicAddress.getFullAddress());
    }



    @Test
    @Order(3)
    @DisplayName("Should validate address format")
    void validateAddressFormat() {
        assertTrue(postalAddress.validateAddress());
        assertEquals(4, postalAddress.getPostalCode().length());

        System.out.println("=== Address Validation ===");
        System.out.println("Postal Code: " + postalAddress.getPostalCode() + " (Valid)");
        System.out.println("✓ Address format is valid");
    }

    @Test
    @Order(4)
    @DisplayName("Should get full address string")
    void getFullAddress() {
        String fullAddress = postalAddress.getFullAddress();
        String expected = "14m Hoffman Street, Bellville South, Cape Town, Western Cape, 7750";

        assertEquals(expected, fullAddress);
        System.out.println("Full Address: " + fullAddress);
    }

    @Test
    @Order(5)
    @DisplayName("Should throw exception when street number is empty")
    void showExceptionWhenStreetNumberEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                AddressFactory.createAddress("", "Hoffman Street", "Bellville South",
                        "Cape Town", "Western Cape", "7750", "South Africa")
        );
        System.out.println("✓ Empty street number correctly rejected");
    }

    @Test
    @Order(6)
    @DisplayName("Should throw exception when postal code is invalid")
    void showExceptionWhenPostalCodeInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                AddressFactory.createAddress("14m", "Hoffman Street", "Bellville South",
                        "Cape Town", "Western Cape", "123", "South Africa")
        );
        System.out.println("✓ Invalid postal code correctly rejected");
    }
}