package za.ac.cput.factory;
/* CancellationPolicyFactoryTest.java

   CancellationPolicy Factory Testing class

   Author: Entle Mayezo	(230076238)

   Date: 28 June 2026
*/
import org.junit.jupiter.api.*;
import za.ac.cput.domain.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CancellationPolicyFactoryTest {

    @Test
    @Order(1)
    @DisplayName("Should create flexible policy")
    void createFlexiblePolicy() {
        CancellationPolicy policy = CancellationPolicyFactory.createFlexiblePolicy();

        assertNotNull(policy);
        assertEquals("Flexible", policy.getPolicyName());
        assertEquals(100.0, policy.getRefundPercentage());
        assertEquals(48, policy.getHoursBeforeCancellation());
        assertTrue(policy.isAllowsModification());

        System.out.println("=== Flexible Policy ===");
        System.out.println("Refund: " + policy.getRefundPercentage() + "%");
        System.out.println("Hours Before: " + policy.getHoursBeforeCancellation());
        System.out.println("Allows Modification: " + policy.isAllowsModification());
    }

    @Test
    @Order(2)
    @DisplayName("Should create standard policy")
    void createStandardPolicy() {
        CancellationPolicy policy = CancellationPolicyFactory.createStandardPolicy();

        assertNotNull(policy);
        assertEquals("Standard", policy.getPolicyName());
        assertEquals(50.0, policy.getRefundPercentage());
        assertEquals(24, policy.getHoursBeforeCancellation());
        assertTrue(policy.isAllowsModification());

        System.out.println("=== Standard Policy ===");
        System.out.println("Refund: " + policy.getRefundPercentage() + "%");
        System.out.println("Hours Before: " + policy.getHoursBeforeCancellation());
    }

    @Test
    @Order(3)
    @DisplayName("Should create strict policy")
    void createStrictPolicy() {
        CancellationPolicy policy = CancellationPolicyFactory.createStrictPolicy();

        assertNotNull(policy);
        assertEquals("Strict", policy.getPolicyName());
        assertEquals(0.0, policy.getRefundPercentage());
        assertEquals(0, policy.getHoursBeforeCancellation());
        assertFalse(policy.isAllowsModification());

        System.out.println("=== Strict Policy ===");
        System.out.println("Refund: " + policy.getRefundPercentage() + "%");
        System.out.println("Allows Modification: " + policy.isAllowsModification());
    }

    @Test
    @Order(4)
    @DisplayName("Should create custom policy")
    void createCustomPolicy() {
        CancellationPolicy policy = CancellationPolicyFactory.createCustomPolicy(
                "Custom", 75.0, 12, true, "Custom terms"
        );

        assertNotNull(policy);
        assertEquals("Custom", policy.getPolicyName());
        assertEquals(75.0, policy.getRefundPercentage());
        assertEquals(12, policy.getHoursBeforeCancellation());
        assertTrue(policy.isAllowsModification());
        assertEquals("Custom terms", policy.getTerms());

        System.out.println("=== Custom Policy ===");
        System.out.println("Name: " + policy.getPolicyName());
        System.out.println("Refund: " + policy.getRefundPercentage() + "%");
        System.out.println("Terms: " + policy.getTerms());
    }

    @Test
    @Order(5)
    @DisplayName("Should calculate refund correctly")
    void testCalculateRefund() {
        CancellationPolicy policy = CancellationPolicyFactory.createFlexiblePolicy();
        double refund = policy.calculateRefund(1000.00);
        assertEquals(1000.00, refund);

        CancellationPolicy standard = CancellationPolicyFactory.createStandardPolicy();
        assertEquals(500.00, standard.calculateRefund(1000.00));

        CancellationPolicy strict = CancellationPolicyFactory.createStrictPolicy();
        assertEquals(0.00, strict.calculateRefund(1000.00));

        System.out.println("✓ Refund calculations correct");
    }

    @Test
    @Order(6)
    @DisplayName("Should throw exception when refund percentage exceeds 100")
    void showExceptionWhenRefundExceeds100() {
        assertThrows(IllegalArgumentException.class, () ->
                CancellationPolicyFactory.createCancellationPolicy("Invalid", 150.0)
        );
        System.out.println("✓ Refund > 100% correctly rejected");
    }

    @Test
    @Order(7)
    @DisplayName("Should throw exception when policy name is empty")
    void showExceptionWhenPolicyNameEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                CancellationPolicyFactory.createCancellationPolicy("", 50.0)
        );
        System.out.println("✓ Empty policy name correctly rejected");
    }
}