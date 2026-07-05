package za.ac.cput.factory;
/* PaymentDetailsFactory.java

   PaymentDetails Factory class

   Author: Ricardo Mukwevho (222567023)

   Date: 28 June 2026
*/
import za.ac.cput.domain.PaymentMethod;
import za.ac.cput.domain.PaymentStatus;
import za.ac.cput.domain.PaymentDetails;
import za.ac.cput.domain.BillingAddress;
import za.ac.cput.domain.CreditCardDetails;
import za.ac.cput.util.Helper;
import za.ac.cput.util.IdGenerator;

import java.time.LocalDateTime;

public class PaymentDetailsFactory {

    private static final IdGenerator idGenerator = new IdGenerator();

    /**
     * Creates a basic payment
     */
    public static PaymentDetails createPayment(PaymentMethod method, double amount, String currency) {
        Helper.requireNonNull(method, "Payment Method");
        Helper.requirePositive(amount, "Amount");
        Helper.requireNotEmptyOrNull(currency, "Currency");

        Long paymentId = idGenerator.generateLongId();

        return new PaymentDetails.Builder()
                .setPaymentId(paymentId)
                .setMethod(method)
                .setAmount(amount)
                .setCurrency(currency)
                .setStatus(PaymentStatus.PENDING)
                .setPaymentDate(LocalDateTime.now())
                .setLastUpdated(LocalDateTime.now())
                .build();
    }

    /**
     * Creates a payment with billing address
     */
    public static PaymentDetails createPaymentWithAddress(PaymentMethod method, double amount,
                                                          String currency, BillingAddress billingAddress) {
        Helper.requireNonNull(billingAddress, "Billing Address");

        PaymentDetails payment = createPayment(method, amount, currency);

        return new PaymentDetails.Builder()
                .copy(payment)
                .setBillingAddress(billingAddress)
                .build();
    }

    /**
     * Creates a credit card payment
     */
    public static PaymentDetails createCreditCardPayment(double amount, String currency,
                                                         BillingAddress billingAddress,
                                                         CreditCardDetails creditCardDetails) {
        Helper.requireNonNull(creditCardDetails, "Credit Card Details");

        PaymentDetails payment = createPaymentWithAddress(PaymentMethod.CREDIT_CARD,
                amount, currency, billingAddress);

        return new PaymentDetails.Builder()
                .copy(payment)
                .setCreditCardDetails(creditCardDetails)
                .build();
    }

    /**
     * Creates a debit card payment
     */
    public static PaymentDetails createDebitCardPayment(double amount, String currency,
                                                        BillingAddress billingAddress,
                                                        CreditCardDetails debitCardDetails) {
        Helper.requireNonNull(debitCardDetails, "Debit Card Details");

        PaymentDetails payment = createPaymentWithAddress(PaymentMethod.DEBIT_CARD,
                amount, currency, billingAddress);

        return new PaymentDetails.Builder()
                .copy(payment)
                .setCreditCardDetails(debitCardDetails)
                .build();
    }

    /**
     * Creates an EFT payment
     */
    public static PaymentDetails createEFTPayment(double amount, String currency,
                                                  BillingAddress billingAddress) {
        return createPaymentWithAddress(PaymentMethod.EFT, amount, currency, billingAddress);
    }
}