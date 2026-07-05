package za.ac.cput.factory;
/* BookingConfirmationFactoryTest.java

   BookinConfirmation Factory Testing class

   Author: Entle Mayezo	(230076238)

   Date: 28 June 2026
*/
import org.junit.jupiter.api.*;
import za.ac.cput.domain.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingConfirmationFactoryTest {

    private Booking booking;

    @BeforeEach
    void setUp() {
        Customer customer = CustomerFactory.createCustomer("John", "Doe", "john@email.com", "password123");
        Traveler traveler = TravelerFactory.createTravelerWithCounts(2, 0, 0);

        booking = FlightBookingFactory.createFlightBooking(
                "SA234", "South African Airways", "JFK", "LAX",
                LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(6),
                customer, traveler
        );
    }

    @Test
    @Order(1)
    @DisplayName("Should create booking confirmation from booking")
    void createBookingConfirmation() {
        BookingConfirmation confirmation = BookingConfirmationFactory.createBookingConfirmation(booking);

        assertNotNull(confirmation);
        assertNotNull(confirmation.getConfirmationNumber());
        assertNotNull(confirmation.getQrCode());
        assertEquals(booking, confirmation.getBooking());
        assertNotNull(confirmation.getConfirmationTime());
        assertNotNull(confirmation.getTermsAndConditions());

        System.out.println("=== Booking Confirmation ===");
        System.out.println("Confirmation Number: " + confirmation.getConfirmationNumber());
        System.out.println("QR Code: " + confirmation.getQrCode());
        System.out.println("Booking Reference: " + booking.getBookingReference());
    }

    @Test
    @Order(2)
    @DisplayName("Should create booking confirmation with custom terms")
    void createBookingConfirmationWithTerms() {
        String customTerms = "Custom cancellation policy applies. Full refund within 7 days.";
        BookingConfirmation confirmation = BookingConfirmationFactory.createBookingConfirmationWithTerms(
                booking, customTerms
        );

        assertNotNull(confirmation);
        assertEquals(customTerms, confirmation.getTermsAndConditions());

        System.out.println("=== Confirmation with Custom Terms ===");
        System.out.println("Terms: " + confirmation.getTermsAndConditions());
    }

    @Test
    @Order(3)
    @DisplayName("Should throw exception when booking is null")
    void showExceptionWhenBookingNull() {
        assertThrows(IllegalArgumentException.class, () ->
                BookingConfirmationFactory.createBookingConfirmation(null)
        );
        System.out.println("✓ Null booking correctly rejected");
    }

    @Test
    @Order(4)
    @DisplayName("Should print confirmation")
    void testPrintConfirmation() {
        BookingConfirmation confirmation = BookingConfirmationFactory.createBookingConfirmation(booking);
        assertDoesNotThrow(confirmation::printConfirmation);
        System.out.println("✓ Confirmation printed");
    }

    @Test
    @Order(5)
    @DisplayName("Should email confirmation")
    void testEmailConfirmation() {
        BookingConfirmation confirmation = BookingConfirmationFactory.createBookingConfirmation(booking);
        assertDoesNotThrow(confirmation::emailConfirmation);
        System.out.println("✓ Confirmation emailed");
    }
}