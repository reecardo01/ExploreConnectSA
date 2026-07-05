package za.ac.cput.domain;
/* ShuttleBooking.java

   ShuttleBooking POJO class

   Author: Kabelo Moloko (230117015)

   Date: 21 June 2026
*/
import jakarta.persistence.*;
import za.ac.cput.util.IdGenerator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@PrimaryKeyJoinColumn(name = "booking_id")
public class ShuttleBooking extends TransportBooking {

    @Id
    private String shuttleId;
    @Enumerated(EnumType.STRING)
    private ShuttleCompanies company;
    private String pickUpLocation;
    private String dropOffLocation;
    private LocalDateTime pickupTime;
    private LocalDateTime estimatedDropoffTime;
    private boolean isRoundTrip;
    private String returnPickupLocation;
    private LocalDateTime returnPickupTime;
    private int numberOfPassengers;
    private String vehicleModel;
    private String licensePlate;
    private String driverName;
    private String driverPhone;
    private boolean childSeat;
    private boolean wheelchairAccessible;
    private double estimatedDistance;

 protected ShuttleBooking(){}

    private ShuttleBooking(Builder builder) {

        this.bookingId = builder.bookingId;
        this.bookingReference = builder.bookingReference;
        this.bookingDate = builder.bookingDate;
        this.lastModified = builder.lastModified;
        this.status = builder.status;
        this.subtotal = builder.subtotal;
        this.taxes = builder.taxes;
        this.totalPrice = builder.totalPrice;
        this.currency = builder.currency;
        this.bookedBy = builder.bookedBy;
        this.travelers = builder.travelers;
        this.payment = builder.payment;
        this.cancellationPolicy = builder.cancellationPolicy;

        this.transportId = builder.transportId;
        this.provider = builder.provider;
        this.vehicleType = builder.vehicleType;
        this.bookingTime = builder.bookingTime;
        this.distance = builder.distance;
        this.specialInstructions = builder.specialInstructions;
        // Shuttle specific fields
        this.shuttleId = builder.shuttleId;
        this.company = builder.company;
        this.pickUpLocation = builder.pickUpLocation;
        this.dropOffLocation = builder.dropOffLocation;
        this.pickupTime = builder.pickupTime;
        this.estimatedDropoffTime = builder.estimatedDropoffTime;
        this.isRoundTrip = builder.isRoundTrip;
        this.returnPickupLocation = builder.returnPickupLocation;
        this.returnPickupTime = builder.returnPickupTime;
        this.numberOfPassengers = builder.numberOfPassengers;
        this.vehicleModel = builder.vehicleModel;
        this.licensePlate = builder.licensePlate;
        this.driverName = builder.driverName;
        this.driverPhone = builder.driverPhone;
        this.childSeat = builder.childSeat;
        this.wheelchairAccessible = builder.wheelchairAccessible;
        this.estimatedDistance = builder.estimatedDistance;
    }

    // Getters
    public String getShuttleId() { return shuttleId; }
    public ShuttleCompanies getCompany() { return company; }
    public String getPickUpLocation() { return pickUpLocation; }
    public String getDropOffLocation() { return dropOffLocation; }
    public LocalDateTime getPickupTime() { return pickupTime; }
    public LocalDateTime getEstimatedDropoffTime() { return estimatedDropoffTime; }
    public boolean isRoundTrip() { return isRoundTrip; }
    public String getReturnPickupLocation() { return returnPickupLocation; }
    public LocalDateTime getReturnPickupTime() { return returnPickupTime; }
    public int getNumberOfPassengers() { return numberOfPassengers; }
    public String getVehicleModel() { return vehicleModel; }
    public String getLicensePlate() { return licensePlate; }
    public String getDriverName() { return driverName; }
    public String getDriverPhone() { return driverPhone; }
    public boolean isChildSeat() { return childSeat; }
    public boolean isWheelchairAccessible() { return wheelchairAccessible; }
    public double getEstimatedDistance() { return estimatedDistance; }

    // Business methods
    public long calculateDuration() {
        return ChronoUnit.MINUTES.between(pickupTime, estimatedDropoffTime);
    }


    public void contactDriver() {
        // Implementation would initiate call or message to driver
        System.out.println("Contacting driver " + driverName + " at " + driverPhone);
    }

    public boolean rateDriver(int rating) {
        return rating >= 1 && rating <= 5;
    }

    @Override
    public Booking modifyBooking() {
        this.lastModified = LocalDateTime.now();
        return this;
    }

    @Override
    public String getBookingDetails() {
        return String.format("Shuttle with %s from %s to %s at %s",
                company, pickUpLocation, dropOffLocation, pickupTime);
    }

    @Override
    public Invoice generateInvoice() {
        return new Invoice.Builder().build();
    }

    @Override
    public String toString() {
        return "ShuttleBooking{" +
                "bookingId=" + bookingId +
                ", company=" + company +
                ", pickUpLocation='" + pickUpLocation + '\'' +
                ", dropOffLocation='" + dropOffLocation + '\'' +
                ", pickupTime=" + pickupTime +
                ", driverName='" + driverName + '\'' +
                '}';
    }

    public static class Builder {
        // Booking fields
        private Long bookingId;
        private String bookingReference;
        private LocalDateTime bookingDate;
        private LocalDateTime lastModified;
        private BookingStatus status;
        private double subtotal;
        private double taxes;
        private double totalPrice;
        private String currency;
        private Customer bookedBy;
        private Traveler travelers;
        private PaymentDetails payment;
        private CancellationPolicy cancellationPolicy;

        // TransportBooking fields
        private String transportId;
        private String provider;
        private VehicleType vehicleType;
        private LocalDateTime bookingTime;
        private double distance;
        private String specialInstructions;

        // Shuttle specific fields
        private String shuttleId;
        private ShuttleCompanies company;
        private String pickUpLocation;
        private String dropOffLocation;
        private LocalDateTime pickupTime;
        private LocalDateTime estimatedDropoffTime;
        private boolean isRoundTrip;
        private String returnPickupLocation;
        private LocalDateTime returnPickupTime;
        private int numberOfPassengers;
        private String vehicleModel;
        private String licensePlate;
        private String driverName;
        private String driverPhone;
        private boolean childSeat;
        private boolean wheelchairAccessible;
        private double estimatedDistance;

        public Builder setBookingId(Long bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder setBookingReference(String bookingReference) {
            this.bookingReference = bookingReference;
            return this;
        }

        public Builder setBookingDate(LocalDateTime bookingDate) {
            this.bookingDate = bookingDate;
            return this;
        }

        public Builder setLastModified(LocalDateTime lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        public Builder setStatus(BookingStatus status) {
            this.status = status;
            return this;
        }

        public Builder setSubtotal(double subtotal) {
            this.subtotal = subtotal;
            return this;
        }

        public Builder setTaxes(double taxes) {
            this.taxes = taxes;
            return this;
        }

        public Builder setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder setCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder setBookedBy(Customer bookedBy) {
            this.bookedBy = bookedBy;
            return this;
        }

        public Builder setTravelers(Traveler travelers) {
            this.travelers = travelers;
            return this;
        }

        public Builder setPayment(PaymentDetails payment) {
            this.payment = payment;
            return this;
        }

        public Builder setCancellationPolicy(CancellationPolicy cancellationPolicy) {
            this.cancellationPolicy = cancellationPolicy;
            return this;
        }

        public Builder setTransportId(String transportId) {
            this.transportId = transportId;
            return this;
        }

        public Builder setProvider(String provider) {
            this.provider = provider;
            return this;
        }

        public Builder setVehicleType(VehicleType vehicleType) {
            this.vehicleType = vehicleType;
            return this;
        }

        public Builder setBookingTime(LocalDateTime bookingTime) {
            this.bookingTime = bookingTime;
            return this;
        }

        public Builder setDistance(double distance) {
            this.distance = distance;
            return this;
        }

        public Builder setSpecialInstructions(String specialInstructions) {
            this.specialInstructions = specialInstructions;
            return this;
        }

        public Builder setShuttleId(String shuttleId) {
            this.shuttleId = shuttleId;
            return this;
        }

        public Builder setCompany(ShuttleCompanies company) {
            this.company = company;
            return this;
        }

        public Builder setPickUpLocation(String pickUpLocation) {
            this.pickUpLocation = pickUpLocation;
            return this;
        }

        public Builder setDropOffLocation(String dropOffLocation) {
            this.dropOffLocation = dropOffLocation;
            return this;
        }

        public Builder setPickupTime(LocalDateTime pickupTime) {
            this.pickupTime = pickupTime;
            return this;
        }

        public Builder setEstimatedDropoffTime(LocalDateTime estimatedDropoffTime) {
            this.estimatedDropoffTime = estimatedDropoffTime;
            return this;
        }

        public Builder setRoundTrip(boolean roundTrip) {
            isRoundTrip = roundTrip;
            return this;
        }

        public Builder setReturnPickupLocation(String returnPickupLocation) {
            this.returnPickupLocation = returnPickupLocation;
            return this;
        }

        public Builder setReturnPickupTime(LocalDateTime returnPickupTime) {
            this.returnPickupTime = returnPickupTime;
            return this;
        }

        public Builder setNumberOfPassengers(int numberOfPassengers) {
            this.numberOfPassengers = numberOfPassengers;
            return this;
        }

        public Builder setVehicleModel(String vehicleModel) {
            this.vehicleModel = vehicleModel;
            return this;
        }

        public Builder setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public Builder setDriverName(String driverName) {
            this.driverName = driverName;
            return this;
        }

        public Builder setDriverPhone(String driverPhone) {
            this.driverPhone = driverPhone;
            return this;
        }

        public Builder setChildSeat(boolean childSeat) {
            this.childSeat = childSeat;
            return this;
        }

        public Builder setWheelchairAccessible(boolean wheelchairAccessible) {
            this.wheelchairAccessible = wheelchairAccessible;
            return this;
        }

        public Builder setEstimatedDistance(double estimatedDistance) {
            this.estimatedDistance = estimatedDistance;
            return this;
        }

        public Builder copy(ShuttleBooking shuttleBooking) {
            this.bookingId = shuttleBooking.bookingId;
            this.bookingReference = shuttleBooking.bookingReference;
            this.bookingDate = shuttleBooking.bookingDate;
            this.lastModified = shuttleBooking.lastModified;
            this.status = shuttleBooking.status;
            this.subtotal = shuttleBooking.subtotal;
            this.taxes = shuttleBooking.taxes;
            this.totalPrice = shuttleBooking.totalPrice;
            this.currency = shuttleBooking.currency;
            this.bookedBy = shuttleBooking.bookedBy;
            this.travelers = shuttleBooking.travelers;
            this.payment = shuttleBooking.payment;
            this.cancellationPolicy = shuttleBooking.cancellationPolicy;

            this.transportId = shuttleBooking.transportId;
            this.provider = shuttleBooking.provider;
            this.vehicleType = shuttleBooking.vehicleType;
            this.bookingTime = shuttleBooking.bookingTime;
            this.distance = shuttleBooking.distance;
            this.specialInstructions = shuttleBooking.specialInstructions;

            this.shuttleId = shuttleBooking.shuttleId;
            this.company = shuttleBooking.company;
            this.pickUpLocation = shuttleBooking.pickUpLocation;
            this.dropOffLocation = shuttleBooking.dropOffLocation;
            this.pickupTime = shuttleBooking.pickupTime;
            this.estimatedDropoffTime = shuttleBooking.estimatedDropoffTime;
            this.isRoundTrip = shuttleBooking.isRoundTrip;
            this.returnPickupLocation = shuttleBooking.returnPickupLocation;
            this.returnPickupTime = shuttleBooking.returnPickupTime;
            this.numberOfPassengers = shuttleBooking.numberOfPassengers;
            this.vehicleModel = shuttleBooking.vehicleModel;
            this.licensePlate = shuttleBooking.licensePlate;
            this.driverName = shuttleBooking.driverName;
            this.driverPhone = shuttleBooking.driverPhone;
            this.childSeat = shuttleBooking.childSeat;
            this.wheelchairAccessible = shuttleBooking.wheelchairAccessible;
            this.estimatedDistance = shuttleBooking.estimatedDistance;
            return this;
        }

        public ShuttleBooking build() {
            return new ShuttleBooking(this);
        }
    }
}
