package za.ac.cput.factory;
/* CancellationPolicyFactory.java

   CancellationPolicy Factory class

   Author: Entle Mayezo	(230076238)

   Date: 28 June 2026
*/
import za.ac.cput.domain.CancellationPolicy;
import za.ac.cput.util.Helper;
import za.ac.cput.util.IdGenerator;

public class CancellationPolicyFactory {

    private static final IdGenerator idGenerator = new IdGenerator();

    /**
     * Creates a basic cancellation policy
     */
    public static CancellationPolicy createCancellationPolicy(String policyName,
                                                              double refundPercentage) {
        Helper.requireNotEmptyOrNull(policyName, "Policy Name");
        Helper.requireInRange(refundPercentage, 0, 100, "Refund Percentage");

        String policyId = idGenerator.generateId("POL");

        return new CancellationPolicy.Builder()
                .setPolicyId(policyId)
                .setPolicyName(policyName)
                .setRefundPercentage(refundPercentage)
                .setHoursBeforeCancellation(24)
                .setAllowsModification(true)
                .setTerms("Standard cancellation policy applies.")
                .build();
    }

    /**
     * Creates a flexible policy (100% refund up to 48 hours)
     */
    public static CancellationPolicy createFlexiblePolicy() {
        String policyId = idGenerator.generateId("POL");

        return new CancellationPolicy.Builder()
                .setPolicyId(policyId)
                .setPolicyName("Flexible")
                .setRefundPercentage(100.0)
                .setHoursBeforeCancellation(48)
                .setAllowsModification(true)
                .setTerms("Full refund if cancelled 48 hours before booking")
                .build();
    }

    /**
     * Creates a standard policy (50% refund up to 24 hours)
     */
    public static CancellationPolicy createStandardPolicy() {
        String policyId = idGenerator.generateId("POL");

        return new CancellationPolicy.Builder()
                .setPolicyId(policyId)
                .setPolicyName("Standard")
                .setRefundPercentage(50.0)
                .setHoursBeforeCancellation(24)
                .setAllowsModification(true)
                .setTerms("50% refund if cancelled 24 hours before booking")
                .build();
    }

    /**
     * Creates a strict policy (no refund)
     */
    public static CancellationPolicy createStrictPolicy() {
        String policyId = idGenerator.generateId("POL");

        return new CancellationPolicy.Builder()
                .setPolicyId(policyId)
                .setPolicyName("Strict")
                .setRefundPercentage(0.0)
                .setHoursBeforeCancellation(0)
                .setAllowsModification(false)
                .setTerms("No refunds for cancellations")
                .build();
    }

    /**
     * Creates a custom cancellation policy
     */
    public static CancellationPolicy createCustomPolicy(String policyName, double refundPercentage,
                                                        int hoursBeforeCancellation, boolean allowsModification,
                                                        String terms) {
        Helper.requireNotEmptyOrNull(policyName, "Policy Name");
        Helper.requireInRange(refundPercentage, 0, 100, "Refund Percentage");
        Helper.requireNotNegative(hoursBeforeCancellation, "Hours Before Cancellation");
        Helper.requireNotEmptyOrNull(terms, "Terms");

        String policyId = idGenerator.generateId("POL");

        return new CancellationPolicy.Builder()
                .setPolicyId(policyId)
                .setPolicyName(policyName)
                .setRefundPercentage(refundPercentage)
                .setHoursBeforeCancellation(hoursBeforeCancellation)
                .setAllowsModification(allowsModification)
                .setTerms(terms)
                .build();
    }
}