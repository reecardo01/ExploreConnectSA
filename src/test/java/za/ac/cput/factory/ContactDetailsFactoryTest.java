package za.ac.cput.factory;

import org.junit.jupiter.api.*;
import za.ac.cput.domain.ContactDetails;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContactDetailsFactoryTest {

    private ContactDetails contact1;
    private ContactDetails contact2;

    @BeforeEach
    void setUp() {
        contact1 = ContactDetailsFactory.createContact("+27712345678", "john@email.com");
        contact2 = ContactDetailsFactory.createFullContact(
                "+27712345678", "john@email.com",
                "0211234567", "0217654321",
                "Jane Doe", "+27719876543"
        );
    }

    @Test
    @Order(1)
    @DisplayName("Should create basic contact details")
    void createContact() {
        assertNotNull(contact1);
        assertEquals("+27712345678", contact1.getCellNumber());
        assertEquals("john@email.com", contact1.getEmail());
        System.out.println("Basic Contact: " + contact1);
    }

    @Test
    @Order(2)
    @DisplayName("Should create full contact details with emergency contacts")
    void createFullContact() {
        assertNotNull(contact2);
        assertEquals("+27712345678",contact2.getCellNumber());
        assertEquals("john@email.com",contact2.getEmail());
        assertEquals("0211234567",contact2.getHomePhone());
        assertEquals("0217654321", contact2.getWorkPhone());
        assertEquals("Jane Doe", contact2.getEmergencyContact());
        assertEquals("+27719876543", contact2.getEmergencyPhone());
        System.out.println("Full Contact: " + contact2);
    }

    @Test
    @Order(3)
    @DisplayName("Should validate email format")
    void validateEmail() {
        assertTrue(contact1.validateEmail());
        assertTrue(contact2.validateEmail());
        System.out.println("Email validation passed");
    }

    @Test
    @Order(4)
    @DisplayName("Should validate phone number format")
    void validatePhone() {
        assertTrue(contact1.validatePhone());
        System.out.println("Phone validation passed");
    }

    @Test
    @Order(5)
    @DisplayName("Should throw exception when email is invalid")
    void showExceptionWhenEmailInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                ContactDetailsFactory.createContact("+27712345678", "invalid-email")
        );
    }

    @Test
    @Order(6)
    @DisplayName("Should throw exception when phone number is invalid")
    void showExceptionWhenPhoneInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                ContactDetailsFactory.createContact("123", "test@email.com")
        );
    }
}