package za.ac.cput.factory;
/* ShuttleBookingFactoryTest.java

   ShuttleBooking Factory Testing class

   Author: Kabelo Moloko (230117015)

   Date: 28 June 2026
*/
import org.junit.jupiter.api.*;
import za.ac.cput.domain.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShuttleBookingFactoryTest {

    private Customer customer;
    private Traveler traveler;

    @BeforeEach
    void setUp() {
        customer = CustomerFactory.createCustomer("John", "Doe", "john@email.com", "password123");
        traveler = TravelerFactory.createTravelerWithCounts(3, 0, 0);
    }

    @Test
    @Order(1)
    @DisplayName("Should create basic shuttle booking")
    void createShuttleBooking() {
        ShuttleBooking shuttle = ShuttleBookingFactory.createShuttleBooking(
                ShuttleCompanies.nbktravel, "Cape Town Airport", "Table Bay Hotel",
                LocalDateTime.now().plusDays(7).withHour(10).withMinute(0),
                customer, traveler
        );

        assertNotNull(shuttle);
        assertEquals(ShuttleCompanies.nbktravel, shuttle.getCompany());
        assertEquals("Cape Town Airport", shuttle.getPickUpLocation());
        assertEquals("Table Bay Hotel", shuttle.getDropOffLocation());
        assertEquals(customer, shuttle.getBookedBy());
        assertNotNull(shuttle.getBookingReference());

        System.out.println("=== Basic Shuttle Booking ===");
        System.out.println("Company: " + shuttle.getCompany());
        System.out.println("Pickup: " + shuttle.getPickUpLocation());
        System.out.println("Dropoff: " + shuttle.getDropOffLocation());
        System.out.println("Booking Ref: " + shuttle.getBookingReference());
    }

    @Test
    @Order(2)
    @DisplayName("Should create shuttle with driver details")
    void createShuttleWithDriver() {
        ShuttleBooking shuttle = ShuttleBookingFactory.createShuttleWithDriver(
                ShuttleCompanies.Morkel, "Sandton", "OR Tambo Airport",
                LocalDateTime.now().plusDays(3).withHour(8).withMinute(0),
                VehicleType.SevenSeater, "Toyota Quantum", "Thabo", "+27712345678",
                customer, traveler
        );

        assertNotNull(shuttle);
        assertEquals(VehicleType.SevenSeater, shuttle.getVehicleType());
        assertEquals("Toyota Quantum", shuttle.getVehicleModel());
        assertEquals("Thabo", shuttle.getDriverName());
        assertEquals("+27712345678", shuttle.getDriverPhone());

        System.out.println("=== Shuttle with Driver ===");
        System.out.println("Vehicle: " + shuttle.getVehicleModel());
        System.out.println("Driver: " + shuttle.getDriverName());
        System.out.println("Driver Phone: " + shuttle.getDriverPhone());
    }

    @Test
    @Order(3)
    @DisplayName("Should create round trip shuttle")
    void createRoundTripShuttle() {
        ShuttleBooking shuttle = ShuttleBookingFactory.createRoundTripShuttle(
                ShuttleCompanies.tedblack, "Cape Town Airport", "V&A Waterfront",
                LocalDateTime.now().plusDays(7).withHour(10).withMinute(0),
                LocalDateTime.now().plusDays(10).withHour(15).withMinute(0),
                customer, traveler
        );

        assertNotNull(shuttle);
        assertTrue(shuttle.isRoundTrip());
        assertNotNull(shuttle.getReturnPickupTime());

        System.out.println("=== Round Trip Shuttle ===");
        System.out.println("Round Trip: " + shuttle.isRoundTrip());
        System.out.println("Return Time: " + shuttle.getReturnPickupTime());
    }

    @Test
    @Order(4)
    @DisplayName("Should create accessible shuttle")
    void createAccessibleShuttle() {
        ShuttleBooking shuttle = ShuttleBookingFactory.createAccessibleShuttle(
                ShuttleCompanies.Supershuttles, "Hotel", "Airport",
                LocalDateTime.now().plusDays(2).withHour(9).withMinute(0),
                true, true, customer, traveler
        );

        assertNotNull(shuttle);
        assertTrue(shuttle.isWheelchairAccessible());
        assertTrue(shuttle.isChildSeat());

        System.out.println("=== Accessible Shuttle ===");
        System.out.println("Wheelchair Accessible: " + shuttle.isWheelchairAccessible());
        System.out.println("Child Seat: " + shuttle.isChildSeat());
    }



    @Test
    @Order(5)
    @DisplayName("Should contact driver")
    void testContactDriver() {
        ShuttleBooking shuttle = ShuttleBookingFactory.createShuttleWithDriver(
                ShuttleCompanies.Morkel, "Sandton", "OR Tambo Airport",
                LocalDateTime.now().plusDays(3).withHour(8).withMinute(0),
                VehicleType.SevenSeater, "Toyota Quantum", "Thabo", "+27712345678",
                customer, traveler
        );

        assertDoesNotThrow(shuttle::contactDriver);
        System.out.println("✓ Driver contact initiated");
    }

    @Test
    @Order(6)
    @DisplayName("Should rate driver")
    void testRateDriver() {
        ShuttleBooking shuttle = ShuttleBookingFactory.createShuttleWithDriver(
                ShuttleCompanies.Morkel, "Sandton", "OR Tambo Airport",
                LocalDateTime.now().plusDays(3).withHour(8).withMinute(0),
                VehicleType.SevenSeater, "Toyota Quantum", "Thabo", "+27712345678",
                customer, traveler
        );

        assertTrue(shuttle.rateDriver(5));
        System.out.println("✓ Driver rated successfully");
    }

    @Test
    @Order(7)
    @DisplayName("Should throw exception when pickup location is empty")
    void showExceptionWhenPickupLocationEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                ShuttleBookingFactory.createShuttleBooking(
                        ShuttleCompanies.nbktravel, "", "Dropoff",
                        LocalDateTime.now().plusDays(7), customer, traveler
                )
        );
        System.out.println("✓ Empty pickup location correctly rejected");
    }

    @Test
    @Order(8)
    @DisplayName("Should throw exception when pickup time is null")
    void showExceptionWhenPickupTimeNull() {
        assertThrows(IllegalArgumentException.class, () ->
                ShuttleBookingFactory.createShuttleBooking(
                        ShuttleCompanies.nbktravel, "Pickup", "Dropoff",
                        null, customer, traveler
                )
        );
        System.out.println("✓ Null pickup time correctly rejected");
    }
}