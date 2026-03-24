package za.ac.cput.factory;

import za.ac.cput.domain.Address;
import za.ac.cput.util.Helper;

public class AddressFactory {

    // Basic Address with required fields
    public static Address createAddress(String streetNumber, String streetName,
                                        String city, String postalCode) {
        Helper.requireNotEmptyOrNull(streetNumber, "Street Number");
        Helper.requireNotEmptyOrNull(streetName, "Street Name");
        Helper.requireNotEmptyOrNull(city, "City");
        Helper.requireNotEmptyOrNull(postalCode, "Postal Code");

        return new Address.Builder(streetNumber, streetName, city, postalCode)
                .build();
    }

    // Full Address with all details
    public static Address createFullAddress(String streetNumber, String streetName,
                                            String suburb, String city,
                                            String province, String country,
                                            String postalCode, String addressType,
                                            boolean isDefault) {
        Address address = createAddress(streetNumber, streetName, city, postalCode);

        return new Address.Builder(streetNumber, streetName, city, postalCode)
                .setSuburb(suburb)
                .setProvince(province)
                .setCountry(country)
                .setAddressType(addressType)
                .setDefault(isDefault)
                .copy(address)
                .build();
    }

    // South African Address
    public static Address createSouthAfricanAddress(String streetNumber, String streetName,
                                                    String suburb, String city,
                                                    String province, String postalCode) {
        Helper.requireNotEmptyOrNull(province, "Province");

        return createFullAddress(streetNumber, streetName, suburb, city,
                province, "South Africa", postalCode, "HOME", false);
    }

}
