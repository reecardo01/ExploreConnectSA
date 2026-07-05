package za.ac.cput.factory;
/* BillingAddressFactory.java

   BillingAddress Factory class

   Author: Entle Mayezo	(230076238)

   Date: 28 June 2026
*/
import za.ac.cput.domain.BillingAddress;
import za.ac.cput.util.Helper;

public class BillingAddressFactory {

    /**
     * Creates a basic billing address
     */
    public static BillingAddress createBillingAddress(String fullName, String addressLine1,
                                                      String city, String postalCode) {
        Helper.requireNotEmptyOrNull(fullName, "Full Name");
        Helper.requireNotEmptyOrNull(addressLine1, "Address Line 1");
        Helper.requireNotEmptyOrNull(city, "City");
        Helper.requireNotEmptyOrNull(postalCode, "Postal Code");

        return new BillingAddress.Builder()
                .setFullName(fullName)
                .setAddressLine1(addressLine1)
                .setCity(city)
                .setPostalCode(postalCode)
                .setCountry("South Africa")
                .build();
    }

    /**
     * Creates a full billing address
     */
    public static BillingAddress createFullBillingAddress(String fullName, String addressLine1,
                                                          String addressLine2, String city,
                                                          String state, String postalCode,
                                                          String country, String phone) {
        BillingAddress address = createBillingAddress(fullName, addressLine1, city, postalCode);

        return new BillingAddress.Builder()
                .copy(address)
                .setAddressLine2(addressLine2)
                .setState(state)
                .setCountry(country != null ? country : "South Africa")
                .setPhone(phone)
                .build();
    }

    /**
     * Creates a South African billing address
     */
    public static BillingAddress createSouthAfricanBillingAddress(String fullName,
                                                                  String addressLine1,
                                                                  String city,
                                                                  String postalCode,
                                                                  String province,
                                                                  String phone) {
        BillingAddress address = createBillingAddress(fullName, addressLine1, city, postalCode);

        return new BillingAddress.Builder()
                .copy(address)
                .setState(province)
                .setCountry("South Africa")
                .setPhone(phone)
                .build();
    }
}