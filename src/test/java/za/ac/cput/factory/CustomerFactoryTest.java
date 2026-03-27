package za.ac.cput.factory;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestMethodOrder;
import za.ac.cput.domain.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerFactoryTest {


    @Test
    @Order(1)
    @DisplayName("Should create basic customer")
    void createBasicCustomer() {
        Customer customer = CustomerFactory.createCustomer(
                "Zama", "Ndlovu", "zamandlovu@gmail.com", "password123"
        );

        assertNotNull(customer);
        assertEquals("Zama", customer.getFirstName());
        assertEquals("Ndlovu", customer.getLastName());

        System.out.println("Basic customer created: " + customer);
    }

    @Test
    @Order(2)
    @DisplayName("Should create customer with RSA ID")
    void createCustomerWithRSAId() {
        System.out.println("Starting test...");

        Customer customer = CustomerFactory.createCustomerWithIdentity(
                "Zama",
                "Ndlovu",
                "zamandlovu@gmail.com",
                "password123",
                IdentityType.RSA_ID,
                "9108191150087",
                LocalDateTime.of(1991, 8, 19, 0, 0),
                "South African"
        );

        assertNotNull(customer);
        assertEquals("Zama", customer.getFirstName());
        assertEquals("Ndlovu", customer.getLastName());
        assertEquals("zamandlovu@gmail.com", customer.getEmail());
        assertEquals(IdentityType.RSA_ID, customer.getIdentityType());
        assertEquals("9108191150087", customer.getIdentityNumber());

        System.out.println("Customer created successfully: " + customer);
    }

    @Test
    @Order(3)
    @DisplayName("Should throw exception when email is invalid")
    void showExceptionWhenEmailInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                CustomerFactory.createCustomer(
                        "Zama", "Ndlovu", "invalid-email", "password123"
                )
        );
        System.out.println("✓ Invalid email correctly rejected");
    }

    @Test
    @Order(4)
    @DisplayName("Should throw exception when RSA ID is invalid")
    void showExceptionWhenRSAIdInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                CustomerFactory.createCustomerWithIdentity(
                        "Zama", "Ndlovu", "zamandlovu@gmail.com", "password123",
                        IdentityType.RSA_ID, "12345", // Invalid ID
                        LocalDateTime.of(1991, 8, 19, 0, 0),
                        "South African"
                )
        );
        System.out.println("✓ Invalid RSA ID correctly rejected");
    }
}