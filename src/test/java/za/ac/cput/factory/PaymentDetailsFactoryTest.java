package za.ac.cput.factory;
/* PaymentDetailsFactoryTest.java

   PaymentDetails Factory Testing class

   Author: Ricardo Mukwevho (222567023)

   Date: 28 June 2026
*/
import org.junit.jupiter.api.*;
import za.ac.cput.domain.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentDetailsFactoryTest {

    private BillingAddress billingAddress;
    private CreditCardDetails creditCard;

    @BeforeEach
    void setUp() {
        billingAddress = BillingAddressFactory.createSouthAfricanBillingAddress(
                "John Doe", "123 Main St", "Cape Town", "8001", "Western Cape", "+27712345678"
        );

        creditCard = CreditCardDetailsFactory.createCreditCardDetails(
                "4111111111111111", "John Doe", "12/25"
        );
    }

    @Test
    @Order(1)
    @DisplayName("Should create basic payment")
    void createPayment() {
        PaymentDetails payment = PaymentDetailsFactory.createPayment(
                PaymentMethod.CREDIT_CARD, 500.00, "ZAR"
        );

        assertNotNull(payment);
        assertEquals(PaymentMethod.CREDIT_CARD, payment.getMethod());
        assertEquals(500.00, payment.getAmount());
        assertEquals("ZAR", payment.getCurrency());
        assertEquals(PaymentStatus.PENDING, payment.getStatus());

        System.out.println("=== Basic Payment ===");
        System.out.println("Method: " + payment.getMethod());
        System.out.println("Amount: " + payment.getAmount());
        System.out.println("Currency: " + payment.getCurrency());
        System.out.println("Status: " + payment.getStatus());
    }

    @Test
    @Order(2)
    @DisplayName("Should create payment with billing address")
    void createPaymentWithAddress() {
        PaymentDetails payment = PaymentDetailsFactory.createPaymentWithAddress(
                PaymentMethod.EFT, 1000.00, "ZAR", billingAddress
        );

        assertNotNull(payment);
        assertNotNull(payment.getBillingAddress());
        assertEquals("John Doe", payment.getBillingAddress().getFullName());

        System.out.println("=== Payment with Address ===");
        System.out.println("Billing Address: " + payment.getBillingAddress().getFullName());
    }

    @Test
    @Order(3)
    @DisplayName("Should create credit card payment")
    void createCreditCardPayment() {
        PaymentDetails payment = PaymentDetailsFactory.createCreditCardPayment(
                750.00, "ZAR", billingAddress, creditCard
        );

        assertNotNull(payment);
        assertEquals(PaymentMethod.CREDIT_CARD, payment.getMethod());
        assertNotNull(payment.getCreditCardDetails());
        assertEquals("1111", payment.getCreditCardDetails().getLastFourDigits());

        System.out.println("=== Credit Card Payment ===");
        System.out.println("Card Last Four: " + payment.getCreditCardDetails().getLastFourDigits());
        System.out.println("Amount: " + payment.getAmount());
    }

    @Test
    @Order(4)
    @DisplayName("Should create EFT payment")
    void createEFTPayment() {
        PaymentDetails payment = PaymentDetailsFactory.createEFTPayment(
                500.00, "ZAR", billingAddress
        );

        assertNotNull(payment);
        assertEquals(PaymentMethod.EFT, payment.getMethod());

        System.out.println("=== EFT Payment ===");
        System.out.println("Method: " + payment.getMethod());
    }

    @Test
    @Order(5)
    @DisplayName("Should process payment")
    void testProcessPayment() {
        PaymentDetails payment = PaymentDetailsFactory.createPayment(
                PaymentMethod.CREDIT_CARD, 500.00, "ZAR"
        );

        // ===== FIX: Call processPayment() before assertions =====
        assertTrue(payment.processPayment());

        assertEquals(PaymentStatus.PAID, payment.getStatus());
        assertNotNull(payment.getTransactionId());

        System.out.println("=== Processed Payment ===");
        System.out.println("Transaction ID: " + payment.getTransactionId());
        System.out.println("Status: " + payment.getStatus());
    }

    @Test
    @Order(6)
    @DisplayName("Should validate payment")
    void testValidatePayment() {
        PaymentDetails payment = PaymentDetailsFactory.createCreditCardPayment(
                750.00, "ZAR", billingAddress, creditCard
        );
        assertTrue(payment.validatePayment());
        System.out.println("✓ Payment validation passed");
    }

    @Test
    @Order(7)
    @DisplayName("Should refund payment")
    void testRefundPayment() {
        PaymentDetails payment = PaymentDetailsFactory.createPayment(
                PaymentMethod.CREDIT_CARD, 500.00, "ZAR"
        );

        // ===== FIX: Process the payment first =====
        payment.processPayment();
        assertEquals(PaymentStatus.PAID, payment.getStatus()); // Verify it's paid first

        // Now refund
        assertTrue(payment.refund());
        assertEquals(PaymentStatus.REFUNDED, payment.getStatus());

        System.out.println("✓ Payment refunded: " + payment.getStatus());
    }

    @Test
    @Order(8)
    @DisplayName("Should generate receipt")
    void testGenerateReceipt() {
        PaymentDetails payment = PaymentDetailsFactory.createPayment(
                PaymentMethod.CREDIT_CARD, 500.00, "ZAR"
        );
        String receiptUrl = payment.generateReceipt();
        assertNotNull(receiptUrl);

        System.out.println("✓ Receipt generated: " + receiptUrl);
    }

    @Test
    @Order(9)
    @DisplayName("Should throw exception when amount is zero")
    void showExceptionWhenAmountZero() {
        assertThrows(IllegalArgumentException.class, () ->
                PaymentDetailsFactory.createPayment(PaymentMethod.CREDIT_CARD, 0, "ZAR")
        );
        System.out.println("✓ Zero amount correctly rejected");
    }

    @Test
    @Order(10)
    @DisplayName("Should throw exception when currency is empty")
    void showExceptionWhenCurrencyEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                PaymentDetailsFactory.createPayment(PaymentMethod.CREDIT_CARD, 500.00, "")
        );
        System.out.println("✓ Empty currency correctly rejected");
    }
}
