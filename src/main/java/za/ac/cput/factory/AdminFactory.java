package za.ac.cput.factory;
/* AdminFactory.java

   Admin Factory class

   Author: Zamandlovu C Ndlovu (211204803)

   Date: 28 June 2026
*/
import za.ac.cput.domain.*;
import za.ac.cput.util.Helper;
import za.ac.cput.util.IdGenerator;

import java.time.LocalDateTime;

public class AdminFactory {

    private static final IdGenerator idGenerator = new IdGenerator();


    public static Admin createAdmin(String firstName, String lastName,
                                    String email, String password,
                                    String empId) {
        Helper.requireNotEmptyOrNull(firstName, "First Name");
        Helper.requireNotEmptyOrNull(lastName, "Last Name");
        Helper.requireValidEmail(email, "Email");
        Helper.requireNotEmptyOrNull(password, "Password");
        Helper.requireNotEmptyOrNull(empId, "Employee ID");

        Long userId = idGenerator.generateLongId();

        return new Admin.Builder()
                .setUserId(userId)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(password)
                .setRole(UserRole.ADMIN)
                .setActive(true)
                .setCreatedAt(LocalDateTime.now())
                .setEmpId(empId)
                .setHireDate(LocalDateTime.now())
                .build();
    }


    public static Admin createFullAdmin(String firstName, String lastName,
                                        String email, String password,
                                        String empId, String department,
                                        String accessLevel) {
        Helper.requireNotEmptyOrNull(department, "Department");
        Helper.requireNotEmptyOrNull(accessLevel, "Access Level");

        Admin admin = createAdmin(firstName, lastName, email, password, empId);

        return new Admin.Builder()
                .copy(admin)
                .setDepartment(department)
                .setAccessLevel(accessLevel)
                .build();
    }


    public static Admin createAdminWithHireDate(String firstName, String lastName,
                                                String email, String password,
                                                String empId, String department,
                                                String accessLevel,
                                                LocalDateTime hireDate) {
        Helper.requireNonNullDate(hireDate, "Hire Date");

        Admin admin = createFullAdmin(firstName, lastName, email, password,
                empId, department, accessLevel);

        return new Admin.Builder()
                .copy(admin)
                .setHireDate(hireDate)
                .build();
    }
}