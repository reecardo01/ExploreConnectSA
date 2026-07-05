package za.ac.cput.factory;
/* HotelBookingFactory.java

   HotelBooking Factory class

   Author: Kabelo Moloko (230117015)

   Date: 28 June 2026
*/
import za.ac.cput.domain.*;
import za.ac.cput.util.Helper;
import za.ac.cput.util.IdGenerator;

import java.time.LocalDateTime;
import java.util.List;

public class HotelBookingFactory  {
    private static final IdGenerator idGenerator = new IdGenerator();
    /**
     * Creates a basic hotel booking
     */
    public static HotelBooking createHotelBooking(String hotelName, String location,
                                                  LocalDateTime checkIn, LocalDateTime checkOut,
                                                  Customer customer, Traveler traveler) {
        Helper.requireNotEmptyOrNull(hotelName, "Hotel Name");
        Helper.requireNotEmptyOrNull(location, "Location");
        Helper.requireNonNullDate(checkIn, "Check-in Date");
        Helper.requireNonNullDate(checkOut, "Check-out Date");
        Helper.requireDateRange(checkIn, checkOut, "Check-in", "Check-out");

        String bookingReference = idGenerator.generateBookingReference("HTL");

        return new HotelBooking.Builder()
                .setBookingReference(bookingReference)
                .setBookingDate(LocalDateTime.now())
                .setLastModified(LocalDateTime.now())
                .setBookedBy(customer)
                .setTravelers(traveler)
                .setCurrency("ZAR")
                .setHotelName(hotelName)
                .setLocation(location)
                .setCheckIn(checkIn)
                .setCheckOut(checkOut)
                .build();
    }

    /**
     * Creates a full hotel booking with room configuration
     */
    public static HotelBooking createFullHotelBooking(String hotelName, String location,
                                                      LocalDateTime checkIn, LocalDateTime checkOut,
                                                      RoomTypeAvailable roomType,
                                                      RoomTypeByOccupancy occupancy,
                                                      RoomTypeByLayout layout,
                                                      RoomTypeByBedSize bedSize,
                                                      int numberOfRooms,
                                                      Customer customer, Traveler traveler,
                                                      boolean breakfastIncluded,
                                                      boolean wifiIncluded) {
        HotelBooking hotel = createHotelBooking(hotelName, location, checkIn, checkOut,
                customer, traveler);

        Helper.requirePositive(numberOfRooms, "Number of Rooms");
        Helper.requireNonNull(roomType, "Room Type");
        Helper.requireNonNull(occupancy, "Occupancy");

        return new HotelBooking.Builder()
                .copy(hotel)
                .setRoomType(roomType)
                .setOccupancy(occupancy)
                .setLayout(layout)
                .setBedSize(bedSize)
                .setBreakfastIncluded(breakfastIncluded)
                .setWifiIncluded(wifiIncluded)
                .build();
    }

}