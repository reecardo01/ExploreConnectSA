package za.ac.cput.factory;
/* FlightBookingFactory.java

   FlightBooking Factory class

   Author: Kabelo Moloko (230117015)

   Date: 28 June 2026
*/
import za.ac.cput.domain.*;
import za.ac.cput.util.Helper;
import za.ac.cput.util.IdGenerator;

import java.time.LocalDateTime;

public class FlightBookingFactory {

    private static final IdGenerator idGenerator = new IdGenerator();

    /**
     * Creates a basic flight booking
     */
    public static FlightBooking createFlightBooking(String flightNumber, String airline,
                                                    String fromLocation, String toLocation,
                                                    LocalDateTime departureTime,
                                                    LocalDateTime arrivalTime,
                                                    Customer customer, Traveler traveler) {
        // Validate required fields
        Helper.requireNotEmptyOrNull(flightNumber, "Flight Number");
        if (!Helper.isValidFlightNumber(flightNumber)) {
            throw new IllegalArgumentException("Invalid Flight Number format");
        }
        Helper.requireNotEmptyOrNull(airline, "Airline");
        Helper.requireNotEmptyOrNull(fromLocation, "From Location");
        Helper.requireNotEmptyOrNull(toLocation, "To Location");
        Helper.requireNonNullDate(departureTime, "Departure Time");
        Helper.requireNonNullDate(arrivalTime, "Arrival Time");
        Helper.requireDateRange(departureTime, arrivalTime, "Departure Time", "Arrival Time");
        Helper.requireNonNull(customer, "Customer");
        Helper.requireNonNull(traveler, "Traveler");

        String bookingReference = idGenerator.generateBookingReference("FLT");

        return new FlightBooking.Builder()
                .setBookingReference(bookingReference)
                .setFlightNumber(flightNumber)
                .setAirline(airline)
                .setFromLocation(fromLocation)
                .setToLocation(toLocation)
                .setDepartureTime(departureTime)
                .setArrivalTime(arrivalTime)
                .setBookedBy(customer)
                .setTravelers(traveler)
                .build();
    }

    /**
     * Creates a full flight booking with all details
     */
    public static FlightBooking createFullFlightBooking(String flightNumber, String airline,
                                                        String fromLocation, String toLocation,
                                                        LocalDateTime departureTime,
                                                        LocalDateTime arrivalTime,
                                                        FJourney journeyType,
                                                        FBookingClass bookingClass,
                                                        FlightType aircraftType,
                                                        Customer customer, Traveler traveler,
                                                        CancellationPolicy cancellationPolicy,
                                                        double subtotal, double taxes) {
        FlightBooking flight = createFlightBooking(flightNumber, airline, fromLocation,
                toLocation, departureTime, arrivalTime, customer, traveler);

        Helper.requireNonNull(journeyType, "Journey Type");
        Helper.requireNonNull(bookingClass, "Booking Class");
        Helper.requirePositive(subtotal, "Subtotal");
        Helper.requireNotNegative(taxes, "Taxes");

        return new FlightBooking.Builder()
                .copy(flight)
                .setJourneyType(journeyType)
                .setBookingClass(bookingClass)
                .setAircraftType(aircraftType)
                .setCancellationPolicy(cancellationPolicy)
                .setSubtotal(subtotal)
                .setTaxes(taxes)
                .setTotalPrice(subtotal + taxes)
                .build();
    }
}