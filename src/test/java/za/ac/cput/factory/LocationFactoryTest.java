package za.ac.cput.factory;
/* LocationFactoryTest.java

   Location Factory Testing class

   Author: Somila Ndoboza (231157592)

   Date: 28 June 2026
*/
import org.junit.jupiter.api.*;
import za.ac.cput.domain.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LocationFactoryTest {

    @Test
    @Order(1)
    @DisplayName("Should create location with coordinates")
    void createLocation() {
        Location location = LocationFactory.createLocation(-33.9249, 18.4241);

        assertNotNull(location);
        assertEquals(-33.9249, location.getLatitude());
        assertEquals(18.4241, location.getLongitude());
        assertNotNull(location.getTimestamp());

        System.out.println("=== Location with Coordinates ===");
        System.out.println("Latitude: " + location.getLatitude());
        System.out.println("Longitude: " + location.getLongitude());
        System.out.println("Timestamp: " + location.getTimestamp());
    }

    @Test
    @Order(2)
    @DisplayName("Should create location with address")
    void createLocationWithAddress() {
        Location location = LocationFactory.createLocationWithAddress(
                -33.9249, 18.4241, "Cape Town City Centre"
        );

        assertNotNull(location);
        assertEquals("Cape Town City Centre", location.getAddress());

        System.out.println("=== Location with Address ===");
        System.out.println("Address: " + location.getAddress());
    }

    @Test
    @Order(3)
    @DisplayName("Should create location with custom timestamp")
    void createLocationWithTimestamp() {
        LocalDateTime customTime = LocalDateTime.of(2024, 1, 1, 10, 30);
        Location location = LocationFactory.createLocationWithTimestamp(
                -33.9249, 18.4241, "Cape Town City Centre", customTime
        );

        assertNotNull(location);
        assertEquals(customTime, location.getTimestamp());

        System.out.println("=== Location with Custom Timestamp ===");
        System.out.println("Timestamp: " + location.getTimestamp());
    }

    @Test
    @Order(4)
    @DisplayName("Should create Cape Town Airport location")
    void createCapeTownAirport() {
        Location location = LocationFactory.createCapeTownAirport();

        assertNotNull(location);
        assertEquals(-33.9694, location.getLatitude());
        assertEquals(18.5977, location.getLongitude());
        assertTrue(location.getAddress().contains("Cape Town International Airport"));

        System.out.println("=== Cape Town Airport ===");
        System.out.println("Address: " + location.getAddress());
    }

    @Test
    @Order(5)
    @DisplayName("Should create OR Tambo Airport location")
    void createORTamboAirport() {
        Location location = LocationFactory.createORTamboAirport();

        assertNotNull(location);
        assertEquals(-26.1392, location.getLatitude());
        assertEquals(28.2460, location.getLongitude());
        assertTrue(location.getAddress().contains("O.R. Tambo International Airport"));

        System.out.println("=== OR Tambo Airport ===");
        System.out.println("Address: " + location.getAddress());
    }

    @Test
    @Order(6)
    @DisplayName("Should calculate distance between locations")
    void testCalculateDistance() {
        Location capeTown = LocationFactory.createCapeTownAirport();
        Location orTambo = LocationFactory.createORTamboAirport();

        double distance = capeTown.calculateDistance(orTambo);
        assertTrue(distance > 1200 && distance < 1300);

        System.out.println("=== Distance Calculation ===");
        System.out.println("Distance: " + distance + " km");
    }



    @Test
    @Order(7)
    @DisplayName("Should throw exception when latitude is out of range")
    void showExceptionWhenLatitudeInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                LocationFactory.createLocation(100, 0)
        );
        System.out.println("✓ Invalid latitude correctly rejected");
    }

    @Test
    @Order(8)
    @DisplayName("Should throw exception when longitude is out of range")
    void showExceptionWhenLongitudeInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                LocationFactory.createLocation(0, 200)
        );
        System.out.println("✓ Invalid longitude correctly rejected");
    }
}