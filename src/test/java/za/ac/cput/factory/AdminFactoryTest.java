package za.ac.cput.factory;

import org.junit.jupiter.api.*;
import za.ac.cput.domain.Admin;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminFactoryTest {

    private Admin admin1;
    private Admin admin2;

    @BeforeEach
    void setUp() {
        admin1 = AdminFactory.createAdmin("John", "Admin", "admin@email.com", "password123", "EMP001");
        admin2 = AdminFactory.createFullAdmin("Jane", "Supervisor", "super@email.com", "password123", "EMP002", "IT", "SUPER");
    }

    @Test
    @Order(1)
    @DisplayName("Should create basic admin")
    void createAdmin() {
        assertNotNull(admin1);
        assertEquals("John", admin1.getFirstName());
        assertEquals("Admin", admin1.getLastName());
        assertEquals("admin@email.com", admin1.getEmail());
        assertEquals("EMP001", admin1.getEmpId());
        System.out.println("Basic Admin: " + admin1);
    }

    @Test
    @Order(2)
    @DisplayName("Should create full admin with department and access level")
    void createFullAdmin() {
        assertNotNull(admin2);
        assertEquals("Jane", admin2.getFirstName());
        assertEquals("Supervisor", admin2.getLastName());
        assertEquals("IT", admin2.getDepartment());
        assertEquals("SUPER", admin2.getAccessLevel());
        System.out.println("Full Admin: " + admin2);
    }

    @Test
    @Order(3)
    @DisplayName("Should throw exception when email is invalid")
    void showExceptionWhenEmailInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                AdminFactory.createAdmin("John", "Doe", "invalid-email", "password", "EMP003")
        );
    }

    @Test
    @Order(4)
    @DisplayName("Should throw exception when employee ID is empty")
    void showExceptionWhenEmpIdEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                AdminFactory.createAdmin("John", "Doe", "john@email.com", "password", "")
        );
    }
}