package za.ac.cput.factory;
/* InvoiceFactory.java

   Invoice Factory class

   Author: Ricardo Mukwevho (222567023)

   Date: 28 June 2026
*/
import za.ac.cput.domain.*;
import za.ac.cput.util.Helper;
import za.ac.cput.util.IdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InvoiceFactory {

    private static final IdGenerator idGenerator = new IdGenerator();

    // Creates an invoice from a booking
     
    public static Invoice createInvoice(Booking booking) {
        Helper.requireNonNull(booking, "Booking");

        String invoiceNumber = idGenerator.generateId("INV");

        // Create a default line item
        LineItem defaultItem = LineItemFactory.createLineItem(
                "Booking: " + booking.getBookingDetails(),
                1,
                booking.getSubtotal()
        );

        List<LineItem> items = new ArrayList<>();
        items.add(defaultItem);

        Invoice invoice = new Invoice.Builder()
                .setInvoiceNumber(invoiceNumber)
                .setIssueDate(LocalDateTime.now())
                .setDueDate(LocalDateTime.now().plusDays(7))
                .setItems(items)
                .setPaymentTerms("Due within 7 days")
                .build();

        invoice.calculateTotals();
        return invoice;
    }

    // Creates an invoice with billing address
     
    public static Invoice createInvoiceWithAddress(Booking booking, BillingAddress billingAddress) {
        Helper.requireNonNull(billingAddress, "Billing Address");

        Invoice invoice = createInvoice(booking);

        return new Invoice.Builder()
                .copy(invoice)
                .build();
    }

    /**
     * Creates an invoice with custom items
     */
    public static Invoice createInvoiceWithItems(Booking booking, List<LineItem> items) {
        Helper.requireNonNull(items, "Items");
        Helper.requireNotEmpty(items, "Items");

        String invoiceNumber = idGenerator.generateId("INV");

        Invoice invoice = new Invoice.Builder()
                .setInvoiceNumber(invoiceNumber)
                .setIssueDate(LocalDateTime.now())
                .setDueDate(LocalDateTime.now().plusDays(7))
                .setItems(items)
                .setPaymentTerms("Due within 7 days")
                .build();

        invoice.calculateTotals();
        return invoice;
    }

    //Creates an invoice with custom payment terms
     
    public static Invoice createInvoiceWithTerms(Booking booking, BillingAddress billingAddress,
                                                 String paymentTerms) {
        Helper.requireNotEmptyOrNull(paymentTerms, "Payment Terms");

        Invoice invoice = createInvoiceWithAddress(booking, billingAddress);

        return new Invoice.Builder()
                .copy(invoice)
                .setPaymentTerms(paymentTerms)
                .build();
    }
}
