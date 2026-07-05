package za.ac.cput.factory;
/* CustomerFactory.java

   Customer Factory class

   Author: Zamandlovu C Ndlovu (211204803)

   Date: 28 June 2026
*/
import za.ac.cput.domain.*;
import za.ac.cput.util.Helper;
import za.ac.cput.util.IdGenerator;

import java.time.LocalDateTime;

public class CustomerFactory {

    private static final IdGenerator idGenerator = new IdGenerator();

    public static Customer createCustomer(String firstName, String lastName,
                                          String email, String password) {
        Helper.requireNotEmptyOrNull(firstName, "First Name");
        Helper.requireNotEmptyOrNull(lastName, "Last Name");
        Helper.requireValidEmail(email, "Email");
        Helper.requireNotEmptyOrNull(password, "Password");

        Long userId = idGenerator.generateLongId();

        return new Customer.Builder()
                .setUserId(userId)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(password)
                .setRole(UserRole.CUSTOMER)
                .setActive(true)
                .setCreatedAt(LocalDateTime.now())
                .build();
    }


    public static Customer createCustomerWithIdentity(String firstName, String lastName,
                                                      String email, String password,
                                                      IdentityType identityType,
                                                      String identityNumber,
                                                      LocalDateTime dateOfBirth,
                                                      String nationality) {
        Helper.requireNotEmptyOrNull(firstName, "First Name");
        Helper.requireNotEmptyOrNull(lastName, "Last Name");
        Helper.requireValidEmail(email, "Email");
        Helper.requireNotEmptyOrNull(password, "Password");
        Helper.requireNonNull(identityType, "Identity Type");
        Helper.requireNotEmptyOrNull(identityNumber, "Identity Number");
        Helper.requireNonNullDate(dateOfBirth, "Date of Birth");
        Helper.requireNotEmptyOrNull(nationality, "Nationality");

        if (identityType == IdentityType.RSA_ID) {
            Helper.requireValidSouthAfricanId(identityNumber, "RSA ID");
        } else if (identityType == IdentityType.PASSPORT) {
            Helper.requireValidPassportNumber(identityNumber, "Passport Number");
        }

        Long userId = idGenerator.generateLongId();

        return new Customer.Builder()
                .setUserId(userId)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(password)
                .setRole(UserRole.CUSTOMER)
                .setActive(true)
                .setCreatedAt(LocalDateTime.now())
                .setIdentityType(identityType)
                .setIdentityNumber(identityNumber)
                .setDateOfBirth(dateOfBirth)
                .setNationality(nationality)
                .build();
    }


    public static Customer createCustomerWithLoyalty(String firstName, String lastName,
                                                     String email, String password,
                                                     IdentityType identityType,
                                                     String identityNumber,
                                                     LocalDateTime dateOfBirth,
                                                     String nationality,
                                                     LoyaltyProgram loyaltyProgram) {
        Helper.requireNonNull(loyaltyProgram, "Loyalty Program");

        Customer customer = createCustomerWithIdentity(firstName, lastName, email, password,
                identityType, identityNumber, dateOfBirth, nationality);

        return new Customer.Builder()
                .copy(customer)
                .setLoyaltyProgram(loyaltyProgram)
                .build();
    }


    public static Customer createCustomerWithLanguage(String firstName, String lastName,
                                                      String email, String password,
                                                      IdentityType identityType,
                                                      String identityNumber,
                                                      LocalDateTime dateOfBirth,
                                                      String nationality,
                                                      LanguageType preferredLanguage) {
        Helper.requireNonNull(preferredLanguage, "Preferred Language");

        Customer customer = createCustomerWithIdentity(firstName, lastName, email, password,
                identityType, identityNumber, dateOfBirth, nationality);

        return new Customer.Builder()
                .copy(customer)
                .setPreferredLanguage(preferredLanguage)
                .build();
    }


    public static Customer createFullCustomer(String firstName, String lastName,
                                              String email, String password,
                                              IdentityType identityType,
                                              String identityNumber,
                                              LocalDateTime dateOfBirth,
                                              String nationality,
                                              LanguageType preferredLanguage,
                                              LoyaltyProgram loyaltyProgram) {
        Customer customer = createCustomerWithIdentity(firstName, lastName, email, password,
                identityType, identityNumber, dateOfBirth, nationality);

        return new Customer.Builder()
                .copy(customer)
                .setPreferredLanguage(preferredLanguage)
                .setLoyaltyProgram(loyaltyProgram)
                .build();
    }

}