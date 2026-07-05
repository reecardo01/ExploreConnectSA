package za.ac.cput.factory;
/* FlightBookingFactoryTest.java

   FlightBooking Factory Testing class

   Author: Kabelo Moloko (230117015)

   Date: 28 June 2026
*/
import org.junit.jupiter.api.*;
import za.ac.cput.domain.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FlightBookingFactoryTest {

    private Customer customer;
    private Booking booking;
    private BillingAddress billingAddress;
    private Traveler traveler;

    @BeforeEach
    void setUp() {
        customer = CustomerFactory.createCustomer("John", "Doe", "john@email.com", "password123");
        traveler = TravelerFactory.createTravelerWithCounts(2, 1, 0);

        // ===== FIX: Use createFullFlightBooking which sets subtotal and taxes =====
        booking = FlightBookingFactory.createFullFlightBooking(
                "SA234", "South African Airways", "JFK", "LAX",
                LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(6),
                FJourney.ONE_WAY, FBookingClass.ECONOMY, FlightType.SOUTH_AFRICA_AIRWAYS,
                customer, traveler, null, 850.00, 127.50
        );

        // calculateTotal() is already called in createFullFlightBooking, but call it again to be safe
        booking.calculateTotal();

        billingAddress = BillingAddressFactory.createSouthAfricanBillingAddress(
                "John Doe", "123 Main St", "Cape Town", "8001", "Western Cape", "+27712345678"
        );
    }

    @Test
    @Order(1)
    @DisplayName("Should create basic flight booking")
    void createFlightBooking() {
        FlightBooking flight = FlightBookingFactory.createFlightBooking(
                "SA234", "South African Airways", "JFK", "LAX",
                LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(6),
                customer, traveler
        );

        assertNotNull(flight);
        assertEquals("SA234", flight.getFlightNumber());
        assertEquals("South African Airways", flight.getAirline());
        assertEquals("JFK", flight.getFromLocation());
        assertEquals("LAX", flight.getToLocation());
        assertEquals(customer, flight.getBookedBy());
        assertEquals(traveler, flight.getTravelers());
        assertNotNull(flight.getBookingReference());

        System.out.println("=== Basic Flight Booking ===");
        System.out.println("Flight: " + flight.getFlightNumber());
        System.out.println("Route: " + flight.getFromLocation() + " → " + flight.getToLocation());
        System.out.println("Booking Ref: " + flight.getBookingReference());
    }

    @Test
    @Order(2)
    @DisplayName("Should create full flight booking")
    void createFullFlightBooking() {
        CancellationPolicy policy = CancellationPolicyFactory.createFlexiblePolicy();

        FlightBooking flight = FlightBookingFactory.createFullFlightBooking(
                "SA456", "South African Airways", "JNB", "DUR",
                LocalDateTime.now().plusDays(14), LocalDateTime.now().plusDays(14).plusHours(1),
                FJourney.ONE_WAY, FBookingClass.BUSINESS, FlightType.SOUTH_AFRICA_AIRWAYS,
                customer, traveler, policy, 850.00, 127.50
        );

        assertNotNull(flight);
        assertEquals(FJourney.ONE_WAY, flight.getJourneyType());
        assertEquals(FBookingClass.BUSINESS, flight.getBookingClass());
        assertEquals(FlightType.SOUTH_AFRICA_AIRWAYS, flight.getAircraftType());
        assertEquals(850.00, flight.getSubtotal());
        assertEquals(127.50, flight.getTaxes());
        assertEquals(977.50, flight.getTotalPrice());

        System.out.println("=== Full Flight Booking ===");
        System.out.println("Journey Type: " + flight.getJourneyType());
        System.out.println("Class: " + flight.getBookingClass());
        System.out.println("Total: " + flight.getTotalPrice());
    }


    @Test
    @Order(3)
    @DisplayName("Should select seat")
    void testSelectSeat() {
        FlightBooking flight = FlightBookingFactory.createFlightBooking(
                "SA234", "South African Airways", "JFK", "LAX",
                LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(6),
                customer, traveler
        );

        assertTrue(flight.selectSeat("12A"));
        assertEquals("12A", flight.getSeatNumbers());

        System.out.println("=== Seat Selection ===");
        System.out.println("Selected Seat: " + flight.getSeatNumbers());
    }

    @Test
    @Order(4)
    @DisplayName("Should add meal preference")
    void testAddMealPreference() {
        FlightBooking flight = FlightBookingFactory.createFlightBooking(
                "SA234", "South African Airways", "JFK", "LAX",
                LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(6),
                customer, traveler
        );

        flight.addMealPreference("Vegetarian");
        assertFalse(flight.getMealPreferences().isEmpty());
        assertEquals("Vegetarian", flight.getMealPreferences().get(0));

        System.out.println("=== Meal Preference ===");
        System.out.println("Preference: " + flight.getMealPreferences().get(0));
    }

    @Test
    @Order(5)
    @DisplayName("Should calculate total price")
    void testCalculateTotal() {
        FlightBooking flight = FlightBookingFactory.createFullFlightBooking(
                "SA456", "South African Airways", "JNB", "DUR",
                LocalDateTime.now().plusDays(14), LocalDateTime.now().plusDays(14).plusHours(1),
                FJourney.ONE_WAY, FBookingClass.BUSINESS, FlightType.SOUTH_AFRICA_AIRWAYS,
                customer, traveler, null, 850.00, 127.50
        );

        flight.calculateTotal();
        assertEquals(977.50, flight.getTotalPrice());

        System.out.println("=== Total Calculation ===");
        System.out.println("Total: " + flight.getTotalPrice());
    }

    @Test
    @Order(6)
    @DisplayName("Should throw exception when departure is after arrival")
    void showExceptionWhenDepartureAfterArrival() {
        assertThrows(IllegalArgumentException.class, () ->
                FlightBookingFactory.createFlightBooking(
                        "SA234", "South African Airways", "JFK", "LAX",
                        LocalDateTime.now().plusDays(30).plusHours(6),
                        LocalDateTime.now().plusDays(30),
                        customer, traveler
                )
        );
        System.out.println("✓ Departure after arrival correctly rejected");
    }

    @Test
    @Order(7)
    @DisplayName("Should throw exception when flight number is empty")
    void showExceptionWhenFlightNumberEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                FlightBookingFactory.createFlightBooking(
                        "", "Airline", "JFK", "LAX",
                        LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(6),
                        customer, traveler
                )
        );
        System.out.println("✓ Empty flight number correctly rejected");
    }
}