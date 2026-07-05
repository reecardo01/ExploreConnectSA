package za.ac.cput.factory;
/* CreditCardDetailsFactoryTest.java

   CreditCardDetails Factory Testing class

   Author: Ricardo Mukwevho (222567023)

   Date: 28 June 2026
*/
import org.junit.jupiter.api.*;
import za.ac.cput.domain.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CreditCardDetailsFactoryTest {

    @Test
    @Order(1)
    @DisplayName("Should create credit card details")
    void createCreditCardDetails() {
        CreditCardDetails card = CreditCardDetailsFactory.createCreditCardDetails(
                "4111111111111111", "John Doe", "12/25"
        );

        assertNotNull(card);
        assertEquals("John Doe", card.getCardHolderName());
        assertEquals("1111", card.getLastFourDigits());
        assertEquals("Visa", card.getCardType());
        assertFalse(card.isDefault());

        System.out.println("=== Credit Card Details ===");
        System.out.println("Card Holder: " + card.getCardHolderName());
        System.out.println("Last Four: " + card.getLastFourDigits());
        System.out.println("Card Type: " + card.getCardType());
        System.out.println("Is Default: " + card.isDefault());
    }

    @Test
    @Order(2)
    @DisplayName("Should create default credit card")
    void createDefaultCreditCard() {
        CreditCardDetails card = CreditCardDetailsFactory.createDefaultCreditCard(
                "5555555555554444", "Jane Smith", "08/26"
        );

        assertNotNull(card);
        assertTrue(card.isDefault());

        System.out.println("=== Default Credit Card ===");
        System.out.println("Is Default: " + card.isDefault());
    }

    @Test
    @Order(3)
    @DisplayName("Should detect card type - Visa")
    void detectVisaCard() {
        CreditCardDetails card = CreditCardDetailsFactory.createCreditCardDetails(
                "4111111111111111", "John Doe", "12/25"
        );
        assertEquals("Visa", card.getCardType());
        System.out.println("✓ Visa detected correctly");
    }

    @Test
    @Order(4)
    @DisplayName("Should detect card type - Mastercard")
    void detectMastercard() {
        CreditCardDetails card = CreditCardDetailsFactory.createCreditCardDetails(
                "5555555555554444", "John Doe", "12/25"
        );
        assertEquals("Mastercard", card.getCardType());
        System.out.println("✓ Mastercard detected correctly");
    }

    @Test
    @Order(5)
    @DisplayName("Should validate card")
    void testValidateCard() {
        CreditCardDetails card = CreditCardDetailsFactory.createCreditCardDetails(
                "4111111111111111", "John Doe", "12/25"
        );
        assertTrue(card.validateCard());
        System.out.println("✓ Card validation passed");
    }

    @Test
    @Order(6)
    @DisplayName("Should mask card number")
    void testMaskCardNumber() {
        CreditCardDetails card = CreditCardDetailsFactory.createCreditCardDetails(
                "4111111111111111", "John Doe", "12/25"
        );
        String masked = card.maskCardNumber();
        assertEquals("**** **** **** 1111", masked);
        System.out.println("✓ Card masked: " + masked);
    }

    @Test
    @Order(7)
    @DisplayName("Should throw exception when card number is empty")
    void showExceptionWhenCardNumberEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                CreditCardDetailsFactory.createCreditCardDetails("", "John Doe", "12/25")
        );
        System.out.println("✓ Empty card number correctly rejected");
    }

    @Test
    @Order(8)
    @DisplayName("Should throw exception when expiry date is invalid")
    void showExceptionWhenExpiryDateInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                CreditCardDetailsFactory.createCreditCardDetails(
                        "4111111111111111", "John Doe", "13/25"
                )
        );
        System.out.println("✓ Invalid expiry date correctly rejected");
    }
}