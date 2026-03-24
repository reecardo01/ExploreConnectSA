package za.ac.cput.factory;

import za.ac.cput.domain.ContactDetails;
import za.ac.cput.util.Helper;

public class ContactDetailsFactory {

    // Basic Contact with required fields
    public static ContactDetails createContact(String cellNumber, String email) {
        Helper.requireNotEmptyOrNull(cellNumber, "Cell Number");
        Helper.requireNotEmptyOrNull(email, "Email");

        if (!Helper.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }

        Helper.isValidSouthAfricanPhone(cellNumber);

        return new ContactDetails.Builder(cellNumber, email)
                .build();
    }

    // Full Contact Details with emergency contacts
    public static ContactDetails createFullContact(String cellNumber, String email,
                                                   String homePhone, String workPhone,
                                                   String emergencyContact,
                                                   String emergencyPhone) {
        ContactDetails contact = createContact(cellNumber, email);

        return new ContactDetails.Builder(cellNumber, email)
                .setHomePhone(homePhone)
                .setWorkPhone(workPhone)
                .setEmergencyContact(emergencyContact)
                .setEmergencyPhone(emergencyPhone)
                .copy(contact)
                .build();
    }
}
