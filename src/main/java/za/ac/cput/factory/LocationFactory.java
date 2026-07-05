package za.ac.cput.factory;
/* LocationFactory.java

   Location Factory class

   Author: Somila Ndoboza (231157592)

   Date: 28 June 2026
*/
import za.ac.cput.domain.*;
import za.ac.cput.util.Helper;
import za.ac.cput.util.IdGenerator;

import java.time.LocalDateTime;

public class LocationFactory {


    /**
     * Creates a location with coordinates
     */
    public static Location createLocation(double latitude, double longitude) {
        Helper.requireInRange(latitude, -90, 90, "Latitude");
        Helper.requireInRange(longitude, -180, 180, "Longitude");



        return new Location.Builder()
                .setLatitude(latitude)
                .setLongitude(longitude)
                .setTimestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Creates a location with address
     */
    public static Location createLocationWithAddress(double latitude, double longitude,
                                                     String address) {
        Helper.requireNotEmptyOrNull(address, "Address");

        Location location = createLocation(latitude, longitude);

        return new Location.Builder()
                .copy(location)
                .setAddress(address)
                .build();
    }

    /**
     * Creates a location with custom timestamp
     */
    public static Location createLocationWithTimestamp(double latitude, double longitude,
                                                       String address, LocalDateTime timestamp) {
        Helper.requireNonNullDate(timestamp, "Timestamp");

        Location location = createLocationWithAddress(latitude, longitude, address);

        return new Location.Builder()
                .copy(location)
                .setTimestamp(timestamp)
                .build();
    }

    /**
     * Creates Cape Town International Airport location
     */
    public static Location createCapeTownAirport() {
        return createLocationWithAddress(-33.9694, 18.5977,
                "Cape Town International Airport, Matroosfontein, Cape Town, 7490");
    }

    /**
     * Creates OR Tambo International Airport location
     */
    public static Location createORTamboAirport() {
        return createLocationWithAddress(-26.1392, 28.2460,
                "O.R. Tambo International Airport, Kempton Park, Johannesburg, 1627");
    }
}