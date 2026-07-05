package za.ac.cput.factory;
/* CustomerFactoryTest.java

   Customer Factory Testing class

   Author: Zamandlovu C Ndlovu (211204803)

   Date: 28 June 2026
*/
import org.junit.jupiter.api.*;
import za.ac.cput.domain.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerFactoryTest {

    private Customer customer;
    private LoyaltyProgram loyaltyProgram;

    @BeforeEach
    void setUp() {
        loyaltyProgram = LoyaltyProgramFactory.createLoyaltyProgramWithPoints(500);

        customer = CustomerFactory.createCustomerWithIdentity(
                "Zama", "Ndlovu", "zamandlovu@gmail.com", "password123",
                IdentityType.RSA_ID, "9108191150087",
                LocalDateTime.of(1991, 8, 19, 0, 0), "South African"
        );
    }

    @Test
    @Order(1)
    @DisplayName("Should create customer with RSA ID")
    void createCustomerWithRSAId() {
        assertNotNull(customer);
        assertEquals("Zama", customer.getFirstName());
        assertEquals("Ndlovu", customer.getLastName());
        assertEquals("zamandlovu@gmail.com", customer.getEmail());
        assertEquals(IdentityType.RSA_ID, customer.getIdentityType());
        assertEquals("9108191150087", customer.getIdentityNumber());
        assertEquals(LocalDateTime.of(1991, 8, 19, 0, 0), customer.getDateOfBirth());
        assertEquals("South African", customer.getNationality());

        System.out.println("=== Customer Created Successfully ===");
        System.out.println("Full Name: " + customer.getFullName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("ID Type: " + customer.getIdentityType());
        System.out.println("ID Number: " + customer.getIdentityNumber());
        System.out.println("Date of Birth: " + customer.getDateOfBirth());
        System.out.println("Nationality: " + customer.getNationality());
    }

    @Test
    @Order(2)
    @DisplayName("Should validate RSA ID format")
    void validateRSAId() {
        String idNumber = customer.getIdentityNumber();
        assertNotNull(idNumber);
        assertEquals(13, idNumber.length());

        String year = idNumber.substring(0, 2);
        String month = idNumber.substring(2, 4);
        String day = idNumber.substring(4, 6);

        assertEquals("91", year);
        assertEquals("08", month);
        assertEquals("19", day);

        System.out.println("=== RSA ID Validation ===");
        System.out.println("ID Number: " + idNumber);
        System.out.println("Birth Year: 19" + year);
        System.out.println("Birth Month: " + month);
        System.out.println("Birth Day: " + day);
        System.out.println("✓ RSA ID format is valid");
    }

    @Test
    @Order(3)
    @DisplayName("Should create customer with loyalty program")
    void createCustomerWithLoyalty() {
        Customer loyalCustomer = CustomerFactory.createCustomerWithLoyalty(
                "Zama", "Ndlovu", "zamandlovu@gmail.com", "password123",
                IdentityType.RSA_ID, "9108191150087",
                LocalDateTime.of(1991, 8, 19, 0, 0), "South African",
                loyaltyProgram
        );

        assertNotNull(loyalCustomer);
        assertNotNull(loyalCustomer.getLoyaltyProgram());
        assertEquals(500, loyalCustomer.getLoyaltyProgram().getPoints());

        System.out.println("=== Customer with Loyalty ===");
        System.out.println("Loyalty Points: " + loyalCustomer.getLoyaltyProgram().getPoints());
        System.out.println("Loyalty Tier: " + loyalCustomer.getLoyaltyProgram().getTier());
    }

    @Test
    @Order(4)
    @DisplayName("Should create customer with preferred language")
    void createCustomerWithLanguage() {
        Customer languageCustomer = CustomerFactory.createCustomerWithLanguage(
                "Zama", "Ndlovu", "zamandlovu@gmail.com", "password123",
                IdentityType.RSA_ID, "9108191150087",
                LocalDateTime.of(1991, 8, 19, 0, 0), "South African",
                LanguageType.ENGLISH
        );

        assertNotNull(languageCustomer);
        assertEquals(LanguageType.ENGLISH, languageCustomer.getPreferredLanguage());

        System.out.println("=== Customer with Language ===");
        System.out.println("Preferred Language: " + languageCustomer.getPreferredLanguage());
    }

    @Test
    @Order(5)
    @DisplayName("Should create full customer")
    void createFullCustomer() {
        Customer fullCustomer = CustomerFactory.createFullCustomer(
                "Zama", "Ndlovu", "zamandlovu@gmail.com", "password123",
                IdentityType.RSA_ID, "9108191150087",
                LocalDateTime.of(1991, 8, 19, 0, 0), "South African",
                LanguageType.ENGLISH, loyaltyProgram
        );

        assertNotNull(fullCustomer);
        assertEquals(LanguageType.ENGLISH, fullCustomer.getPreferredLanguage());
        assertNotNull(fullCustomer.getLoyaltyProgram());
        assertEquals(500, fullCustomer.getLoyaltyProgram().getPoints());

        System.out.println("=== Full Customer ===");
        System.out.println("Name: " + fullCustomer.getFullName());
        System.out.println("Language: " + fullCustomer.getPreferredLanguage());
        System.out.println("Loyalty Points: " + fullCustomer.getLoyaltyProgram().getPoints());
    }

    @Test
    @Order(6)
    @DisplayName("Should throw exception when RSA ID is invalid")
    void showExceptionWhenInvalidRSAId() {
        assertThrows(IllegalArgumentException.class, () ->
                CustomerFactory.createCustomerWithIdentity(
                        "Test", "User", "test@email.com", "password",
                        IdentityType.RSA_ID, "12345",
                        LocalDateTime.now(), "South African"
                )
        );
        System.out.println("✓ Invalid RSA ID correctly rejected");
    }

    @Test
    @Order(7)
    @DisplayName("Should throw exception when email is invalid")
    void showExceptionWhenEmailInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                CustomerFactory.createCustomerWithIdentity(
                        "Test", "User", "invalid-email", "password",
                        IdentityType.RSA_ID, "9108191150087",
                        LocalDateTime.now(), "South African"
                )
        );
        System.out.println("✓ Invalid email correctly rejected");
    }
    @Test
    @Order(8)
    @DisplayName("Complete end-to-end factory integration test")
    void completeIntegrationTest() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("     FACTORY INTEGRATION TEST - COMPLETE BOOKING FLOW");
        System.out.println("=".repeat(80));

        // 1. Create Customer
        System.out.println("\n STEP 1: Create Customer");
        Customer customer = CustomerFactory.createCustomerWithIdentity(
                "Zama", "Ndlovu", "zamandlovu@gmail.com", "password123",
                IdentityType.RSA_ID, "9108191150087",
                LocalDateTime.of(1991, 8, 19, 0, 0), "South African"
        );
        assertNotNull(customer);
        System.out.println("✓ Customer created: " + customer.getFullName());

        // 2. Create Contact Details
        System.out.println("\n STEP 2: Create Contact Details");
        ContactDetails contact = ContactDetailsFactory.createFullContactDetails(
                "0717456628", "zamandlovu@gmail.com",
                "0312142828", "0733902727",
                "0727549706", "0817275196"
        );
        assertNotNull(contact);
        System.out.println("✓ Contact created: " + contact.getEmail());

        // 3. Create Address
        System.out.println("\n STEP 3: Create Address");
        Address address = AddressFactory.createFullAddress(
                "14m", "Hoffman Street", "Bellville South", "Cape Town",
                "Western Cape", "7750", "South Africa",
                "POSTAL", true
        );
        assertNotNull(address);
        System.out.println("✓ Address created: " + address.getFullAddress());

        // 4. Create Traveler
        System.out.println("\n STEP 4: Create Traveler");
        Traveler traveler = TravelerFactory.createTravelerWithCounts(2, 1, 0);
        assertNotNull(traveler);
        System.out.println("✓ Traveler created: " + traveler.getTotalTravelers() + " passengers");

        // 5. Create Loyalty Program
        System.out.println("\n STEP 5: Create Loyalty Program");
        LoyaltyProgram loyalty = LoyaltyProgramFactory.createLoyaltyProgramWithPoints(500);
        assertNotNull(loyalty);
        System.out.println("✓ Loyalty Program created: " + loyalty.getPoints() + " points");

        // 6. Create Flight Booking
        System.out.println("\n STEP 6: Create Flight Booking");
        CancellationPolicy policy = CancellationPolicyFactory.createFlexiblePolicy();
        FlightBooking flight = FlightBookingFactory.createFullFlightBooking(
                "SA234", "South African Airways", "JFK", "LAX",
                LocalDateTime.now().plusDays(30), LocalDateTime.now().plusDays(30).plusHours(6),
                FJourney.ONE_WAY, FBookingClass.ECONOMY, FlightType.SOUTH_AFRICA_AIRWAYS,
                customer, traveler, policy, 850.00, 127.50
        );
        assertNotNull(flight);
        flight.calculateTotal();
        System.out.println("✓ Flight Booking created: " + flight.getBookingReference());
        System.out.println("  Total: R" + flight.getTotalPrice());

        // 7. Create Billing Address
        System.out.println("\n STEP 7: Create Billing Address");
        BillingAddress billingAddress = BillingAddressFactory.createSouthAfricanBillingAddress(
                "Zama Ndlovu", "14m Hoffman Street", "Cape Town", "7750", "Western Cape", "0717456628"
        );
        assertNotNull(billingAddress);
        System.out.println("✓ Billing Address created");

        // 8. Create Credit Card
        System.out.println("\n STEP 8: Create Credit Card");
        CreditCardDetails card = CreditCardDetailsFactory.createCreditCardDetails(
                "4111111111111111", "Zama Ndlovu", "12/25"
        );
        assertNotNull(card);
        System.out.println("✓ Credit Card created: " + card.getCardType() + " ending in " + card.getLastFourDigits());



        // 9. Create Booking Confirmation
        System.out.println("\n STEP 11: Create Booking Confirmation");
        BookingConfirmation confirmation = BookingConfirmationFactory.createBookingConfirmation(flight);
        assertNotNull(confirmation);
        System.out.println("✓ Booking Confirmation: " + confirmation.getConfirmationNumber());

        // 10. Create Boarding Pass
        System.out.println("\n STEP 12: Create Boarding Pass");
        flight.selectSeat("12A");
        BoardingPass boardingPass = BoardingPassFactory.createBoardingPass(flight);
        boardingPass.generate();
        assertNotNull(boardingPass);
        System.out.println("✓ Boarding Pass generated: " + boardingPass.getFlightNumber());
        System.out.println("  Seat: " + boardingPass.getSeatNumber());
        System.out.println("  Gate: " + boardingPass.getGate());

        // 11. Create Review
        System.out.println("\n STEP 13: Create Review");
        Review review = ReviewFactory.createServiceReview(
                5, "Excellent service! The flight was on time and comfortable.",
                customer, "FLIGHT", flight.getFlightNumber(), flight
        );
        assertNotNull(review);
        System.out.println("✓ Review submitted: " + review.getRating() + " stars");

        // Final Summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("     🎉 ALL FACTORIES INTEGRATION TEST PASSED SUCCESSFULLY!");
        System.out.println("=".repeat(80));
        System.out.println("\n📊 SUMMARY:");
        System.out.println("  • Customer: " + customer.getFullName());
        System.out.println("  • Booking: " + flight.getBookingReference());
        System.out.println("  • Total Cost: R" + flight.getTotalPrice());
        System.out.println("  • Confirmation: " + confirmation.getConfirmationNumber());
        System.out.println("  • Review: " + review.getRating() + " stars");
        System.out.println("\n✅ All 13 steps completed successfully!");
        System.out.println("=".repeat(80));
    }
}