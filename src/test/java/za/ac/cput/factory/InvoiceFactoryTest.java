package za.ac.cput.factory;
/* InvoiceFactoryTest.java

   Invoice Factory Testing class

   Author: Ricardo Mukwevho (222567023)

   Date: 28 June 2026
*/
import org.junit.jupiter.api.*;
import za.ac.cput.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InvoiceFactoryTest {

    private Booking booking;
    private BillingAddress billingAddress;

    @BeforeEach
    void setUp() {
        Customer customer = CustomerFactory.createCustomer("John", "Doe", "john@email.com", "password123");
        Traveler traveler = TravelerFactory.createTravelerWithCounts(2, 0, 0);

        // ===== FIX: Use createFullFlightBooking with all details =====
        booking = FlightBookingFactory.createFullFlightBooking(
                "SA234", "South African Airways", "JFK", "LAX",
                LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(6),
                FJourney.ONE_WAY, FBookingClass.ECONOMY, FlightType.SOUTH_AFRICA_AIRWAYS,
                customer, traveler, CancellationPolicyFactory.createFlexiblePolicy(),
                850.00, 127.50
        );
        booking.calculateTotal();

        billingAddress = BillingAddressFactory.createSouthAfricanBillingAddress(
                "John Doe", "123 Main St", "Cape Town", "8001", "Western Cape", "+27712345678"
        );
    }

    @Test
    @Order(1)
    @DisplayName("Should create invoice from booking")
    void createInvoice() {
        Invoice invoice = InvoiceFactory.createInvoice(booking);

        assertNotNull(invoice);
        assertNotNull(invoice.getInvoiceNumber());
        assertNotNull(invoice.getIssueDate());
        assertNotNull(invoice.getDueDate());
        assertFalse(invoice.getItems().isEmpty());
        assertNotNull(invoice.getPaymentTerms());

        System.out.println("=== Invoice ===");
        System.out.println("Invoice Number: " + invoice.getInvoiceNumber());
        System.out.println("Issue Date: " + invoice.getIssueDate());
        System.out.println("Due Date: " + invoice.getDueDate());
        System.out.println("Items: " + invoice.getItems().size());
    }

    @Test
    @Order(2)
    @DisplayName("Should create invoice with billing address")
    void createInvoiceWithAddress() {
        Invoice invoice = InvoiceFactory.createInvoiceWithAddress(booking, billingAddress);

        assertNotNull(invoice);
        // Billing address is not stored directly in Invoice in the current design
        System.out.println("✓ Invoice with address created");
    }

    @Test
    @Order(3)
    @DisplayName("Should create invoice with custom items")
    void createInvoiceWithItems() {
        List<LineItem> items = new ArrayList<>();
        items.add(LineItemFactory.createLineItem("Flight Ticket", 2, 425.00));
        items.add(LineItemFactory.createLineItem("Baggage Fee", 1, 50.00));

        Invoice invoice = InvoiceFactory.createInvoiceWithItems(booking, items);

        assertNotNull(invoice);
        assertEquals(2, invoice.getItems().size());
        invoice.calculateTotals();

        System.out.println("=== Invoice with Custom Items ===");
        System.out.println("Items: " + invoice.getItems().size());
        System.out.println("Total: " + invoice.getTotal());
    }

    @Test
    @Order(4)
    @DisplayName("Should calculate totals correctly")
    void testCalculateTotal() {
        Invoice invoice = InvoiceFactory.createInvoice(booking);
        invoice.calculateTotals();

        assertEquals(977.50, invoice.getTotal());
        assertEquals(127.50, invoice.getTax());

        System.out.println("=== Total Calculation ===");
        System.out.println("Subtotal: " + invoice.getTotal());
        System.out.println("Tax (15%): " + invoice.getTax());
        System.out.println("Total: " + invoice.getTotal());
    }

    @Test
    @Order(5)
    @DisplayName("Should throw exception when booking is null")
    void showExceptionWhenBookingNull() {
        assertThrows(IllegalArgumentException.class, () ->
                InvoiceFactory.createInvoice(null)
        );
        System.out.println("✓ Null booking correctly rejected");
    }

    @Test
    @Order(6)
    @DisplayName("Should generate PDF")
    void testGeneratePDF() {
        Invoice invoice = InvoiceFactory.createInvoice(booking);
        assertDoesNotThrow(invoice::generatePDF);
        System.out.println("✓ PDF generated");
    }

    @Test
    @Order(7)
    @DisplayName("Should send invoice via email")
    void testSendViaEmail() {
        Invoice invoice = InvoiceFactory.createInvoice(booking);
        assertDoesNotThrow(invoice::sendViaEmail);
        System.out.println("✓ Invoice emailed");
    }
}