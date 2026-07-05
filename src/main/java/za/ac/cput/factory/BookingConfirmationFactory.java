package za.ac.cput.factory;
/* BookingConfirmationFactory.java

   BookinConfirmation Factory class

   Author: Entle Mayezo	(230076238)

   Date: 28 June 2026
*/
import za.ac.cput.domain.Booking;
import za.ac.cput.domain.BookingConfirmation;
import za.ac.cput.util.Helper;
import za.ac.cput.util.IdGenerator;

import java.time.LocalDateTime;

public class BookingConfirmationFactory {

    private static final IdGenerator idGenerator = new IdGenerator();

    /**
     * Creates a booking confirmation from a booking
     */
    public static BookingConfirmation createBookingConfirmation(Booking booking) {
        Helper.requireNonNull(booking, "Booking");

        String confirmationNumber = idGenerator.generateId("CNF");
        String qrCode = "QR-" + idGenerator.generateUUID().substring(0, 8).toUpperCase();

        return new BookingConfirmation.Builder()
                .setConfirmationNumber(confirmationNumber)
                .setQrCode(qrCode)
                .setConfirmationTime(LocalDateTime.now())
                .setBooking(booking)
                .setTermsAndConditions("Standard terms and conditions apply. Please review before travel.")
                .build();
    }

    /**
     * Creates a booking confirmation with custom terms
     */
    public static BookingConfirmation createBookingConfirmationWithTerms(Booking booking,
                                                                         String termsAndConditions) {
        Helper.requireNotEmptyOrNull(termsAndConditions, "Terms and Conditions");

        BookingConfirmation confirmation = createBookingConfirmation(booking);

        return new BookingConfirmation.Builder()
                .copy(confirmation)
                .setTermsAndConditions(termsAndConditions)
                .build();
    }
}