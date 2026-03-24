package za.ac.cput.factory;
import za.ac.cput.domain.Admin;
import za.ac.cput.util.Helper;

public class AdminFactory {
    public static Admin createAdmin(String firstName, String lastName,
                                    String email, String password,
                                    String empId) {
        Helper.requireNotEmptyOrNull(firstName, "First Name");
        Helper.requireNotEmptyOrNull(lastName, "Last Name");
        Helper.requireNotEmptyOrNull(email, "Email");
        Helper.requireNotEmptyOrNull(password, "Password");
        Helper.requireNotEmptyOrNull(empId, "Employee ID");

        if (!Helper.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }

        return new Admin.Builder(firstName, lastName, email, password, empId)
                .build();
    }

    // Admin with department and access level
    public static Admin createFullAdmin(String firstName, String lastName,
                                        String email, String password,
                                        String empId, String department,
                                        String accessLevel) {
        Helper.requireNotEmptyOrNull(department, "Department");
        Helper.requireNotEmptyOrNull(accessLevel, "Access Level");

        Admin admin = createAdmin(firstName, lastName, email, password, empId);

        return new Admin.Builder(firstName, lastName, email, password, empId)
                .setDepartment(department)
                .setAccessLevel(accessLevel)
                .copy(admin)
                .build();
    }
}
