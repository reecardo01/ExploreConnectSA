package za.ac.cput.factory;
/* HotelBookingFactoryTest.java

   HotelBooking Factory Testing class

   Author: Kabelo Moloko (230117015)

   Date: 28 June 2026
*/
import org.junit.jupiter.api.*;
import za.ac.cput.domain.*;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HotelBookingFactoryTest {

    private Customer customer;
    private Traveler traveler;

    @BeforeEach
    void setUp() {
        customer = CustomerFactory.createCustomer("John", "Doe", "john@email.com", "password123");
        traveler = TravelerFactory.createTravelerWithCounts(2, 0, 0);
    }

    @Test
    @Order(1)
    @DisplayName("Should create basic hotel booking")
    void createHotelBooking() {
        HotelBooking hotel = HotelBookingFactory.createHotelBooking(
                "Table Bay Hotel", "Cape Town",
                LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(34),
                customer, traveler
        );

        assertNotNull(hotel);
        assertEquals("Table Bay Hotel", hotel.getHotelName());
        assertEquals("Cape Town", hotel.getLocation());
        assertEquals(customer, hotel.getBookedBy());
        assertEquals(traveler, hotel.getTravelers());
        assertNotNull(hotel.getBookingReference());

        System.out.println("=== Basic Hotel Booking ===");
        System.out.println("Hotel: " + hotel.getHotelName());
        System.out.println("Location: " + hotel.getLocation());
        System.out.println("Booking Ref: " + hotel.getBookingReference());
    }

    @Test
    @Order(2)
    @DisplayName("Should create full hotel booking with room configuration")
    void createFullHotelBooking() {
        HotelBooking hotel = HotelBookingFactory.createFullHotelBooking(
                "The Silo Hotel", "Cape Town",
                LocalDateTime.now().plusDays(45), LocalDateTime.now().plusDays(48),
                RoomTypeAvailable.ExecutiveRoom, RoomTypeByOccupancy.Double,
                RoomTypeByLayout.Deluxe, RoomTypeByBedSize.King,
                2, customer, traveler, true, true
        );

        assertNotNull(hotel);
        assertEquals(RoomTypeAvailable.ExecutiveRoom, hotel.getRoomType());
        assertEquals(RoomTypeByOccupancy.Double, hotel.getOccupancy());
        assertEquals(RoomTypeByLayout.Deluxe, hotel.getLayout());
        assertEquals(RoomTypeByBedSize.King, hotel.getBedSize());
        assertTrue(hotel.isBreakfastIncluded());
        assertTrue(hotel.isWifiIncluded());

        System.out.println("=== Full Hotel Booking ===");
        System.out.println("Room Type: " + hotel.getRoomType());
        System.out.println("Occupancy: " + hotel.getOccupancy());
        System.out.println("Layout: " + hotel.getLayout());
        System.out.println("Breakfast: " + hotel.isBreakfastIncluded());
    }



    @Test
    @Order(3)
    @DisplayName("Should calculate number of nights")
    void testCalculateNights() {
        HotelBooking hotel = HotelBookingFactory.createHotelBooking(
                "Table Bay Hotel", "Cape Town",
                LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(34),
                customer, traveler
        );

        long nights = hotel.calculateNights();
        assertEquals(4, nights);

        System.out.println("=== Nights Calculation ===");
        System.out.println("Nights: " + nights);
    }

    @Test
    @Order(4)
    @DisplayName("Should request early check-in")
    void testRequestEarlyCheckIn() {
        HotelBooking hotel = HotelBookingFactory.createHotelBooking(
                "Table Bay Hotel", "Cape Town",
                LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(34),
                customer, traveler
        );

        LocalDateTime earlyTime = hotel.getCheckIn().minusHours(2);
        assertTrue(hotel.requestEarlyCheckIn(earlyTime));

        System.out.println("✓ Early check-in requested");
    }

    @Test
    @Order(5)
    @DisplayName("Should request late check-out")
    void testRequestLateCheckOut() {
        HotelBooking hotel = HotelBookingFactory.createHotelBooking(
                "Table Bay Hotel", "Cape Town",
                LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(34),
                customer, traveler
        );

        LocalDateTime lateTime = hotel.getCheckOut().plusHours(2);
        assertTrue(hotel.requestLateCheckOut(lateTime));

        System.out.println("✓ Late check-out requested");
    }

    @Test
    @Order(6)
    @DisplayName("Should add special request")
    void testAddSpecialRequest() {
        HotelBooking hotel = HotelBookingFactory.createHotelBooking(
                "Table Bay Hotel", "Cape Town",
                LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(34),
                customer, traveler
        );

        hotel.addSpecialRequest("Extra pillows");
        assertEquals(1, hotel.getSpecialRequests().size());

        System.out.println("✓ Special request added");
    }

    @Test
    @Order(7)
    @DisplayName("Should throw exception when check-in is after check-out")
    void showExceptionWhenCheckInAfterCheckOut() {
        assertThrows(IllegalArgumentException.class, () ->
                HotelBookingFactory.createHotelBooking(
                        "Hotel", "City",
                        LocalDateTime.now().plusDays(10), LocalDateTime.now().plusDays(5),
                        customer, traveler
                )
        );
        System.out.println("✓ Check-in after check-out correctly rejected");
    }

    @Test
    @Order(8)
    @DisplayName("Should throw exception when hotel name is empty")
    void showExceptionWhenHotelNameEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                HotelBookingFactory.createHotelBooking(
                        "", "City",
                        LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(34),
                        customer, traveler
                )
        );
        System.out.println("✓ Empty hotel name correctly rejected");
    }
}