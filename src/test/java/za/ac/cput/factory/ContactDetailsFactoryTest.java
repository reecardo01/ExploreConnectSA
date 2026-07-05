package za.ac.cput.factory;
/* ContactDetailsFactoryTest.java

   ContactDetails Factory Testing class

   Author: Zamandlovu C Ndlovu (211204803)

   Date: 28 June 2026
*/
import org.junit.jupiter.api.*;
import za.ac.cput.domain.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContactDetailsFactoryTest {

    private ContactDetails contact;

    @BeforeEach
    void setUp() {
        contact = ContactDetailsFactory.createFullContactDetails(
                "0717456628", "zamandlovu@gmail.com",
                "0312142828", "0733902727",
                "0727549706", "0817275196"
        );
    }

    @Test
    @Order(1)
    @DisplayName("Should create full contact details")
    void createFullContact() {
        assertNotNull(contact);
        assertEquals("0717456628", contact.getCellNumber());
        assertEquals("zamandlovu@gmail.com", contact.getEmail());
        assertEquals("0312142828", contact.getHomePhone());
        assertEquals("0733902727", contact.getWorkPhone());
        assertEquals("0727549706", contact.getEmergencyContact());
        assertEquals("0817275196", contact.getEmergencyPhone());

        System.out.println("=== Contact Details Created ===");
        System.out.println("Cell: " + contact.getCellNumber());
        System.out.println("Email: " + contact.getEmail());
        System.out.println("Home: " + contact.getHomePhone());
        System.out.println("Work: " + contact.getWorkPhone());
        System.out.println("Emergency Contact: " + contact.getEmergencyContact());
        System.out.println("Emergency Phone: " + contact.getEmergencyPhone());
    }

    @Test
    @Order(2)
    @DisplayName("Should create basic contact")
    void createBasicContact() {
        ContactDetails basicContact = ContactDetailsFactory.createContactDetails(
                "+27712345678", "john@email.com"
        );

        assertNotNull(basicContact);
        assertEquals("+27712345678", basicContact.getCellNumber());
        assertEquals("john@email.com", basicContact.getEmail());
        System.out.println("✓ Basic contact created");
    }

    @Test
    @Order(3)
    @DisplayName("Should validate email format")
    void validateEmail() {
        assertTrue(contact.validateEmail());
        System.out.println("✓ Email validation passed: " + contact.getEmail());
    }



    @Test
    @Order(4)
    @DisplayName("Should throw exception when email is invalid")
    void showExceptionWhenEmailInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                ContactDetailsFactory.createContactDetails("0717456628", "invalid-email")
        );
        System.out.println("✓ Invalid email correctly rejected");
    }

    @Test
    @Order(5)
    @DisplayName("Should throw exception when phone number is invalid")
    void showExceptionWhenPhoneInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                ContactDetailsFactory.createContactDetails("123", "test@email.com")
        );
        System.out.println("✓ Invalid phone number correctly rejected");
    }

    @Test
    @Order(6)
    @DisplayName("Should send notification")
    void sendNotification() {
        assertDoesNotThrow(() -> contact.sendNotification("Test notification"));
        System.out.println("✓ Notification sent successfully");
    }
}