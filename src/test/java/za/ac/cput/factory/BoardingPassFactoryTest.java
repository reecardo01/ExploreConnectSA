package za.ac.cput.factory;
/* BoardingPassFactoryTest.java

   BoardingPass Factory Testing class

   Author: Somila Ndoboza (231157592)

   Date: 28 June 2026
*/
import org.junit.jupiter.api.*;
import za.ac.cput.domain.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoardingPassFactoryTest {

    private FlightBooking flightBooking;

    @BeforeEach
    void setUp() {
        Customer customer = CustomerFactory.createCustomer("John", "Doe", "john@email.com", "password123");
        Traveler traveler = TravelerFactory.createTravelerWithCounts(1, 0, 0);

        flightBooking = FlightBookingFactory.createFlightBooking(
                "SA234", "South African Airways", "JFK", "LAX",
                LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(6),
                customer, traveler
        );
        flightBooking.selectSeat("12A");
    }

    @Test
    @Order(1)
    @DisplayName("Should create boarding pass from flight booking")
    void createBoardingPass() {
        BoardingPass pass = BoardingPassFactory.createBoardingPass(flightBooking);

        assertNotNull(pass);
        assertEquals(flightBooking.getBookingReference(), pass.getBookingReference());
        assertEquals(flightBooking.getFlightNumber(), pass.getFlightNumber());
        assertEquals(flightBooking.getSeatNumbers(), pass.getSeatNumber());
        assertNotNull(pass.getGate());
        assertNotNull(pass.getBoardingTime());
        assertNotNull(pass.getQrCode());

        System.out.println("=== Boarding Pass ===");
        System.out.println("Flight: " + pass.getFlightNumber());
        System.out.println("Seat: " + pass.getSeatNumber());
        System.out.println("Gate: " + pass.getGate());
        System.out.println("Boarding Time: " + pass.getBoardingTime());
        System.out.println("QR Code: " + pass.getQrCode());
    }

    @Test
    @Order(2)
    @DisplayName("Should create boarding pass with specific gate")
    void createBoardingPassWithGate() {
        BoardingPass pass = BoardingPassFactory.createBoardingPassWithGate(flightBooking, "B24");

        assertNotNull(pass);
        assertEquals("B24", pass.getGate());

        System.out.println("=== Boarding Pass with Gate ===");
        System.out.println("Gate: " + pass.getGate());
    }

    @Test
    @Order(3)
    @DisplayName("Should create boarding pass with seat")
    void createBoardingPassWithSeat() {
        BoardingPass pass = BoardingPassFactory.createBoardingPassWithSeat(flightBooking, "14B");

        assertNotNull(pass);
        assertEquals("14B", pass.getSeatNumber());

        System.out.println("=== Boarding Pass with Seat ===");
        System.out.println("Seat: " + pass.getSeatNumber());
    }

    @Test
    @Order(4)
    @DisplayName("Should create boarding pass for specific flight")
    void createBoardingPassForFlight() {
        LocalDateTime boardingTime = LocalDateTime.now().plusHours(2);
        BoardingPass pass = BoardingPassFactory.createBoardingPassForFlight(
                "BKG-123456", "SA123", "12A", "A1", boardingTime
        );

        assertNotNull(pass);
        assertEquals("BKG-123456", pass.getBookingReference());
        assertEquals("SA123", pass.getFlightNumber());
        assertEquals("12A", pass.getSeatNumber());
        assertEquals("A1", pass.getGate());
        assertEquals(boardingTime, pass.getBoardingTime());

        System.out.println("=== Boarding Pass for Specific Flight ===");
        System.out.println("Booking Ref: " + pass.getBookingReference());
        System.out.println("Flight: " + pass.getFlightNumber());
        System.out.println("Gate: " + pass.getGate());
    }

    @Test
    @Order(5)
    @DisplayName("Should generate QR code")
    void testGenerateQR() {
        BoardingPass pass = BoardingPassFactory.createBoardingPass(flightBooking);
        pass.generate();

        assertNotNull(pass.getQrCode());
        assertTrue(pass.getQrCode().startsWith("BP-"));

        System.out.println("=== QR Code Generation ===");
        System.out.println("QR Code: " + pass.getQrCode());
    }

    @Test
    @Order(6)
    @DisplayName("Should validate boarding pass")
    void testValidate() {
        BoardingPass pass = BoardingPassFactory.createBoardingPass(flightBooking);
        pass.generate();

        assertTrue(pass.validate());

        System.out.println("✓ Boarding pass validation passed");
    }

    @Test
    @Order(7)
    @DisplayName("Should throw exception when flight booking is null")
    void showExceptionWhenFlightNull() {
        assertThrows(IllegalArgumentException.class, () ->
                BoardingPassFactory.createBoardingPass(null)
        );
        System.out.println("✓ Null flight booking correctly rejected");
    }

    @Test
    @Order(8)
    @DisplayName("Should throw exception when booking reference is empty")
    void showExceptionWhenBookingRefEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                BoardingPassFactory.createBoardingPassForFlight(
                        "", "SA123", "12A", "A1", LocalDateTime.now()
                )
        );
        System.out.println("✓ Empty booking reference correctly rejected");
    }

    @Test
    @Order(9)
    @DisplayName("Should throw exception when flight number is empty")
    void showExceptionWhenFlightNumberEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                BoardingPassFactory.createBoardingPassForFlight(
                        "BKG-123", "", "12A", "A1", LocalDateTime.now()
                )
        );
        System.out.println("✓ Empty flight number correctly rejected");
    }
}