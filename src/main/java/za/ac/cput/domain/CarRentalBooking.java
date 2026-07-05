package za.ac.cput.domain;
/* CarRentalBooking.java

   CarRentalBooking POJO class

   Author: Kabelo Moloko (230117015)

   Date: 21 June 2026
*/
import jakarta.persistence.*;
import za.ac.cput.util.IdGenerator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@PrimaryKeyJoinColumn(name = "booking_id")
public class CarRentalBooking extends TransportBooking {

    @Id
    private String rentalId;
    private String rentalCompany;
    private String carModel;
    private String carCategory;
    private String licensePlate;
    private LocalDateTime pickupDate;
    private LocalDateTime returnDate;
    private String pickupLocation;
    private String returnLocation;
    private boolean differentReturnLocation;
    private double rentalDays;
    private boolean insurance;
    private boolean gps;
    private boolean childSeat;
    private boolean additionalDriver;
    private String fuelPolicy;
    private int mileageLimit;
    private double securityDeposit;

    protected  CarRentalBooking(){}

    private CarRentalBooking(Builder builder) {
        // Booking fields
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

        // TransportBooking fields
        this.transportId = builder.transportId;
        this.provider = builder.provider;
        this.vehicleType = builder.vehicleType;
        this.bookingTime = builder.bookingTime;
        this.distance = builder.distance;
        this.specialInstructions = builder.specialInstructions;

        // Car rental specific fields
        this.rentalId = builder.rentalId;
        this.rentalCompany = builder.rentalCompany;
        this.carModel = builder.carModel;
        this.carCategory = builder.carCategory;
        this.licensePlate = builder.licensePlate;
        this.pickupDate = builder.pickupDate;
        this.returnDate = builder.returnDate;
        this.pickupLocation = builder.pickupLocation;
        this.returnLocation = builder.returnLocation;
        this.differentReturnLocation = builder.differentReturnLocation;
        this.rentalDays = builder.rentalDays;
        this.insurance = builder.insurance;
        this.gps = builder.gps;
        this.childSeat = builder.childSeat;
        this.additionalDriver = builder.additionalDriver;
        this.fuelPolicy = builder.fuelPolicy;
        this.mileageLimit = builder.mileageLimit;
        this.securityDeposit = builder.securityDeposit;
    }

    // Getters
    public String getRentalId() { return rentalId; }
    public String getRentalCompany() { return rentalCompany; }
    public String getCarModel() { return carModel; }
    public String getCarCategory() { return carCategory; }
    public String getLicensePlate() { return licensePlate; }
    public LocalDateTime getPickupDate() { return pickupDate; }
    public LocalDateTime getReturnDate() { return returnDate; }
    public String getPickupLocation() { return pickupLocation; }
    public String getReturnLocation() { return returnLocation; }
    public boolean isDifferentReturnLocation() { return differentReturnLocation; }
    public double getRentalDays() { return rentalDays; }
    public boolean isInsurance() { return insurance; }
    public boolean isGps() { return gps; }
    public boolean isChildSeat() { return childSeat; }
    public boolean isAdditionalDriver() { return additionalDriver; }
    public String getFuelPolicy() { return fuelPolicy; }
    public int getMileageLimit() { return mileageLimit; }
    public double getSecurityDeposit() { return securityDeposit; }

    // Business methods
    public long calculateRentalDays() {
        return ChronoUnit.DAYS.between(pickupDate, returnDate);
    }

    public boolean extendRental(LocalDateTime newReturnDate) {
        if (newReturnDate.isAfter(returnDate)) {
            this.returnDate = newReturnDate;
            this.lastModified = LocalDateTime.now();
            return true;
        }
        return false;
    }

    public Invoice returnCar() {
        // Calculate any additional charges
        return new Invoice.Builder().build();
    }

    public void reportIssue(String issue) {
        System.out.println("Issue reported for rental " + rentalId + ": " + issue);
    }

    @Override
    public Booking modifyBooking() {
        this.lastModified = LocalDateTime.now();
        return this;
    }

    @Override
    public String getBookingDetails() {
        return String.format("Car rental with %s: %s from %s to %s",
                rentalCompany, carModel, pickupDate.toLocalDate(), returnDate.toLocalDate());
    }

    @Override
    public Invoice generateInvoice() {
        return new Invoice.Builder().build();
    }

    @Override
    public String toString() {
        return "CarRentalBooking{" +
                "bookingId=" + bookingId +
                ", rentalCompany='" + rentalCompany + '\'' +
                ", carModel='" + carModel + '\'' +
                ", pickupDate=" + pickupDate +
                ", returnDate=" + returnDate +
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

        // Car rental specific fields
        private String rentalId;
        private String rentalCompany;
        private String carModel;
        private String carCategory;
        private String licensePlate;
        private LocalDateTime pickupDate;
        private LocalDateTime returnDate;
        private String pickupLocation;
        private String returnLocation;
        private boolean differentReturnLocation;
        private double rentalDays;
        private boolean insurance;
        private boolean gps;
        private boolean childSeat;
        private boolean additionalDriver;
        private String fuelPolicy;
        private int mileageLimit;
        private double securityDeposit;


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

        public Builder setCurrency(String currency) {
            this.currency = currency;
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

        public Builder setBookingTime(LocalDateTime bookingTime) {
            this.bookingTime = bookingTime;
            return this;
        }

        public Builder setRentalId(String rentalId) {
            this.rentalId = rentalId;
            return this;
        }

        public Builder setRentalCompany(String rentalCompany) {
            this.rentalCompany = rentalCompany;
            return this;
        }

        public Builder setCarModel(String carModel) {
            this.carModel = carModel;
            return this;
        }

        public Builder setPickupDate(LocalDateTime pickupDate) {
            this.pickupDate = pickupDate;
            return this;
        }

        public Builder setReturnDate(LocalDateTime returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public Builder setCarCategory(String carCategory) {
            this.carCategory = carCategory;
            return this;
        }

        public Builder setVehicleType(VehicleType vehicleType) {
            this.vehicleType = vehicleType;
            return this;
        }

        public Builder setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public Builder setPickupLocation(String pickupLocation) {
            this.pickupLocation = pickupLocation;
            return this;
        }

        public Builder setReturnLocation(String returnLocation) {
            this.returnLocation = returnLocation;
            return this;
        }

        public Builder setDifferentReturnLocation(boolean differentReturnLocation) {
            this.differentReturnLocation = differentReturnLocation;
            return this;
        }

        public Builder setRentalDays(double rentalDays) {
            this.rentalDays = rentalDays;
            return this;
        }

        public Builder setInsurance(boolean insurance) {
            this.insurance = insurance;
            return this;
        }

        public Builder setGps(boolean gps) {
            this.gps = gps;
            return this;
        }

        public Builder setChildSeat(boolean childSeat) {
            this.childSeat = childSeat;
            return this;
        }

        public Builder setAdditionalDriver(boolean additionalDriver) {
            this.additionalDriver = additionalDriver;
            return this;
        }

        public Builder setFuelPolicy(String fuelPolicy) {
            this.fuelPolicy = fuelPolicy;
            return this;
        }

        public Builder setMileageLimit(int mileageLimit) {
            this.mileageLimit = mileageLimit;
            return this;
        }

        public Builder setSecurityDeposit(double securityDeposit) {
            this.securityDeposit = securityDeposit;
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

        public Builder copy(CarRentalBooking carRental) {
            this.bookingId = carRental.bookingId;
            this.bookingReference = carRental.bookingReference;
            this.bookingDate = carRental.bookingDate;
            this.lastModified = carRental.lastModified;
            this.status = carRental.status;
            this.subtotal = carRental.subtotal;
            this.taxes = carRental.taxes;
            this.totalPrice = carRental.totalPrice;
            this.currency = carRental.currency;
            this.bookedBy = carRental.bookedBy;
            this.travelers = carRental.travelers;
            this.payment = carRental.payment;
            this.cancellationPolicy = carRental.cancellationPolicy;

            this.transportId = carRental.transportId;
            this.provider = carRental.provider;
            this.vehicleType = carRental.vehicleType;
            this.bookingTime = carRental.bookingTime;
            this.distance = carRental.distance;
            this.specialInstructions = carRental.specialInstructions;

            this.rentalId = carRental.rentalId;
            this.rentalCompany = carRental.rentalCompany;
            this.carModel = carRental.carModel;
            this.carCategory = carRental.carCategory;
            this.licensePlate = carRental.licensePlate;
            this.pickupDate = carRental.pickupDate;
            this.returnDate = carRental.returnDate;
            this.pickupLocation = carRental.pickupLocation;
            this.returnLocation = carRental.returnLocation;
            this.differentReturnLocation = carRental.differentReturnLocation;
            this.rentalDays = carRental.rentalDays;
            this.insurance = carRental.insurance;
            this.gps = carRental.gps;
            this.childSeat = carRental.childSeat;
            this.additionalDriver = carRental.additionalDriver;
            this.fuelPolicy = carRental.fuelPolicy;
            this.mileageLimit = carRental.mileageLimit;
            this.securityDeposit = carRental.securityDeposit;
            return this;
        }

        public CarRentalBooking build() {
            return new CarRentalBooking(this);
        }
    }
}
