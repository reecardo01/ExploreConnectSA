package za.ac.cput.factory;
/* BoardingPassFactory.java

   BoardingPass Factory class

   Author: Somila Ndoboza (231157592)

   Date: 28 June 2026
*/
import za.ac.cput.domain.*;
import za.ac.cput.util.Helper;
import za.ac.cput.util.IdGenerator;

import java.time.LocalDateTime;

public class BoardingPassFactory {

    private static final IdGenerator idGenerator = new IdGenerator();

    /**
     * Creates a boarding pass from a flight booking
     */
    public static BoardingPass createBoardingPass(FlightBooking flightBooking) {
        Helper.requireNonNull(flightBooking, "Flight Booking");

        String qrCode = "BP-" + idGenerator.generateUUID().substring(0, 8).toUpperCase();

        BoardingPass pass = new BoardingPass.Builder()
                .setBookingReference(flightBooking.getBookingReference())
                .setFlightNumber(flightBooking.getFlightNumber())
                .setSeatNumber(flightBooking.getSeatNumbers() != null ?
                        flightBooking.getSeatNumbers() : "TBA")
                .setGate("A" + (int)(Math.random() * 10))
                .setBoardingTime(flightBooking.getDepartureTime().minusHours(2))
                .setQrCode(qrCode)
                .build();

        pass.generate();
        return pass;
    }

    /**
     * Creates a boarding pass with specific gate
     */
    public static BoardingPass createBoardingPassWithGate(FlightBooking flightBooking,
                                                          String gate) {
        Helper.requireNotEmptyOrNull(gate, "Gate");

        BoardingPass pass = createBoardingPass(flightBooking);

        return new BoardingPass.Builder()
                .copy(pass)
                .setGate(gate)
                .build();
    }

    /**
     * Creates a boarding pass with assigned seat
     */
    public static BoardingPass createBoardingPassWithSeat(FlightBooking flightBooking,
                                                          String seatNumber) {
        Helper.requireNotEmptyOrNull(seatNumber, "Seat Number");

        BoardingPass pass = createBoardingPass(flightBooking);

        return new BoardingPass.Builder()
                .copy(pass)
                .setSeatNumber(seatNumber)
                .build();
    }

    /**
     * Creates a boarding pass for a specific flight number
     */
    public static BoardingPass createBoardingPassForFlight(String bookingReference,
                                                           String flightNumber,
                                                           String seatNumber,
                                                           String gate,
                                                           LocalDateTime boardingTime) {
        Helper.requireNotEmptyOrNull(bookingReference, "Booking Reference");
        Helper.requireNotEmptyOrNull(flightNumber, "Flight Number");
        Helper.requireNotEmptyOrNull(seatNumber, "Seat Number");
        Helper.requireNotEmptyOrNull(gate, "Gate");
        Helper.requireNonNullDate(boardingTime, "Boarding Time");

        String qrCode = "BP-" + idGenerator.generateUUID().substring(0, 8).toUpperCase();

        return new BoardingPass.Builder()
                .setBookingReference(bookingReference)
                .setFlightNumber(flightNumber)
                .setSeatNumber(seatNumber)
                .setGate(gate)
                .setBoardingTime(boardingTime)
                .setQrCode(qrCode)
                .build();
    }
}