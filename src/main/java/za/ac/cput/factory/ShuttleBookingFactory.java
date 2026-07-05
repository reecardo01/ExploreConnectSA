package za.ac.cput.factory;
/* ShuttleBookingFactory.java

   ShuttleBooking Factory class

   Author: Kabelo Moloko (230117015)

   Date: 28 June 2026
*/
import za.ac.cput.domain.*;
import za.ac.cput.util.Helper;
import za.ac.cput.util.IdGenerator;

import java.time.LocalDateTime;

public class ShuttleBookingFactory {
    private static final IdGenerator idGenerator = new IdGenerator();
    /**
     * Creates a basic shuttle booking
     */
    public static ShuttleBooking createShuttleBooking(ShuttleCompanies company,
                                                      String pickUpLocation,
                                                      String dropOffLocation,
                                                      LocalDateTime pickupTime,
                                                      Customer customer,
                                                      Traveler traveler) {
        Helper.requireNonNull(company, "Company");
        Helper.requireNotEmptyOrNull(pickUpLocation, "Pickup Location");
        Helper.requireNotEmptyOrNull(dropOffLocation, "Dropoff Location");
        Helper.requireNonNullDate(pickupTime, "Pickup Time");

        String bookingReference = idGenerator.generateBookingReference("SHT");

        return new ShuttleBooking.Builder()
                .setBookingReference(bookingReference)
                .setBookingDate(LocalDateTime.now())
                .setLastModified(LocalDateTime.now())
                .setBookedBy(customer)
                .setTravelers(traveler)
                .setCurrency("ZAR")
                .setCompany(company)
                .setProvider(company.toString())
                .setVehicleType(VehicleType.SevenSeater)
                .setPickUpLocation(pickUpLocation)
                .setDropOffLocation(dropOffLocation)
                .setPickupTime(pickupTime)
                .build();
    }

    /**
     * Creates a shuttle with vehicle and driver details
     */
    public static ShuttleBooking createShuttleWithDriver(ShuttleCompanies company,
                                                         String pickUpLocation,
                                                         String dropOffLocation,
                                                         LocalDateTime pickupTime,
                                                         VehicleType vehicleType,
                                                         String vehicleModel,
                                                         String driverName,
                                                         String driverPhone,
                                                         Customer customer,
                                                         Traveler traveler) {
        ShuttleBooking shuttle = createShuttleBooking(company, pickUpLocation, dropOffLocation,
                pickupTime, customer, traveler);

        Helper.requireNotEmptyOrNull(driverName, "Driver Name");
        Helper.requireValidSouthAfricanPhone(driverPhone, "Driver Phone");

        return new ShuttleBooking.Builder()
                .copy(shuttle)
                .setVehicleType(vehicleType)
                .setVehicleModel(vehicleModel)
                .setDriverName(driverName)
                .setDriverPhone(driverPhone)
                .build();
    }

    /**
     * Creates a round trip shuttle
     */
    public static ShuttleBooking createRoundTripShuttle(ShuttleCompanies company,
                                                        String pickUpLocation,
                                                        String dropOffLocation,
                                                        LocalDateTime pickupTime,
                                                        LocalDateTime returnPickupTime,
                                                        Customer customer,
                                                        Traveler traveler) {
        ShuttleBooking shuttle = createShuttleBooking(company, pickUpLocation, dropOffLocation,
                pickupTime, customer, traveler);

        Helper.requireNonNullDate(returnPickupTime, "Return Pickup Time");

        return new ShuttleBooking.Builder()
                .copy(shuttle)
                .setRoundTrip(true)
                .setReturnPickupTime(returnPickupTime)
                .build();
    }

    /**
     * Creates an accessible shuttle
     */
    public static ShuttleBooking createAccessibleShuttle(ShuttleCompanies company,
                                                         String pickUpLocation,
                                                         String dropOffLocation,
                                                         LocalDateTime pickupTime,
                                                         boolean wheelchairAccessible,
                                                         boolean childSeat,
                                                         Customer customer,
                                                         Traveler traveler) {
        ShuttleBooking shuttle = createShuttleBooking(company, pickUpLocation, dropOffLocation,
                pickupTime, customer, traveler);

        return new ShuttleBooking.Builder()
                .copy(shuttle)
                .setWheelchairAccessible(wheelchairAccessible)
                .setChildSeat(childSeat)
                .build();
    }
}