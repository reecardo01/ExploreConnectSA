package za.ac.cput.factory;
/* AdminFactoryTest.java

   Admin Factory Testing class

   Author: Zamandlovu C Ndlovu (211204803)

   Date: 28 June 2026
*/
import org.junit.jupiter.api.*;
import za.ac.cput.domain.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminFactoryTest {

    private Admin admin;

    @BeforeEach
    void setUp() {
        admin = AdminFactory.createFullAdmin(
                "Zama", "Ndlovu", "211204803@cput.ac.za", "securePassword123",
                "A211204803", "IT Dep", "all levels"
        );
    }

    @Test
    @Order(1)
    @DisplayName("Should create admin with all details")
    void createFullAdmin() {
        assertNotNull(admin);
        assertEquals("Zama", admin.getFirstName());
        assertEquals("Ndlovu", admin.getLastName());
        assertEquals("211204803@cput.ac.za", admin.getEmail());
        assertEquals("A211204803", admin.getEmpId());
        assertEquals("IT Dep", admin.getDepartment());
        assertEquals("all levels", admin.getAccessLevel());

        System.out.println("=== Admin Created Successfully ===");
        System.out.println("Full Name: " + admin.getFullName());
        System.out.println("Email: " + admin.getEmail());
        System.out.println("Employee ID: " + admin.getEmpId());
        System.out.println("Department: " + admin.getDepartment());
        System.out.println("Access Level: " + admin.getAccessLevel());
    }

    @Test
    @Order(2)
    @DisplayName("Should validate admin email domain")
    void validateAdminEmail() {
        String email = admin.getEmail();
        assertTrue(email.endsWith("@cput.ac.za"));

        System.out.println("=== Email Validation ===");
        System.out.println("Email: " + email);
        System.out.println("✓ CPUT Domain Valid");
    }

    @Test
    @Order(3)
    @DisplayName("Should validate employee ID format")
    void validateEmployeeId() {
        String empId = admin.getEmpId();
        assertTrue(empId.startsWith("A"));
        assertEquals(10, empId.length());

        System.out.println("=== Employee ID Validation ===");
        System.out.println("Employee ID: " + empId);
        System.out.println("✓ Format Valid (A + 9 digits)");
    }

    @Test
    @Order(4)
    @DisplayName("Should verify admin is active by default")
    void verifyAdminActive() {
        assertTrue(admin.isActive());
        System.out.println("✓ Admin is active by default");
    }

    @Test
    @Order(5)
    @DisplayName("Should get full name correctly")
    void testGetFullName() {
        String fullName = admin.getFullName();
        assertEquals("Zama Ndlovu", fullName);
        System.out.println("✓ Full Name: " + fullName);
    }

    @Test
    @Order(6)
    @DisplayName("Should throw exception when email is invalid")
    void showExceptionWhenEmailInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                AdminFactory.createAdmin("Zama", "Ndlovu", "invalid-email", "password", "A123")
        );
        System.out.println("✓ Invalid email correctly rejected");
    }

    @Test
    @Order(7)
    @DisplayName("Should throw exception when employee ID is empty")
    void showExceptionWhenEmpIdEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                AdminFactory.createAdmin("Zama", "Ndlovu", "test@email.com", "password", "")
        );
        System.out.println("✓ Empty employee ID correctly rejected");
    }

    @Test
    @Order(8)
    @DisplayName("Should create admin with custom hire date")
    void createAdminWithHireDate() {
        LocalDateTime hireDate = LocalDateTime.of(1999, 2, 2, 0, 0);
        Admin adminWithDate = AdminFactory.createAdminWithHireDate(
                "Zama", "Ndlovu", "test@email.com", "password",
                "A123", "IT", "SUPER", hireDate
        );

        assertNotNull(adminWithDate);
        assertEquals(hireDate, adminWithDate.getHireDate());
        System.out.println("✓ Admin with custom hire date created: " + adminWithDate.getHireDate());
    }
}