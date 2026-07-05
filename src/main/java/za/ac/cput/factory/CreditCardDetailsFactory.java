package za.ac.cput.factory;
/* CreditCardDetailsFactory.java

   CreditCardDetails Factory class

   Author: Ricardo Mukwevho (222567023)

   Date: 28 June 2026
*/
import za.ac.cput.domain.CreditCardDetails;
import za.ac.cput.util.Helper;

public class CreditCardDetailsFactory {

    /**
     * Creates credit card details
     */
    public static CreditCardDetails createCreditCardDetails(String cardNumber,
                                                            String cardHolderName,
                                                            String expiryDate) {
        Helper.requireNotEmptyOrNull(cardNumber, "Card Number");
        Helper.requireNotEmptyOrNull(cardHolderName, "Card Holder Name");
        Helper.requireNotEmptyOrNull(expiryDate, "Expiry Date");

        // Remove spaces and dashes
        cardNumber = cardNumber.replaceAll("[\\s-]", "");

        if (cardNumber.length() < 13 || cardNumber.length() > 19) {
            throw new IllegalArgumentException("Card number must be between 13 and 19 digits");
        }
        if (!cardNumber.matches("\\d+")) {
            throw new IllegalArgumentException("Card number must contain only digits");
        }
        if (!expiryDate.matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
            throw new IllegalArgumentException("Expiry date must be in MM/YY format");
        }

        return new CreditCardDetails.Builder()
                .set(cardNumber, cardHolderName, expiryDate)
                .setDefault(false)
                .build();
    }

    /**
     * Creates a default credit card
     */
    public static CreditCardDetails createDefaultCreditCard(String cardNumber,
                                                            String cardHolderName,
                                                            String expiryDate) {
        CreditCardDetails card = createCreditCardDetails(cardNumber, cardHolderName, expiryDate);

        return new CreditCardDetails.Builder()
                .copy(card)
                .setDefault(true)
                .build();
    }
}