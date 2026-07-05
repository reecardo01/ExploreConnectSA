package za.ac.cput.domain;
/* FlightBooking.java

   FlightBooking POJO class

   Author: Kabelo Moloko (230117015)

   Date: 21 June 2026
*/
import jakarta.persistence.*;
import za.ac.cput.util.IdGenerator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Entity
@PrimaryKeyJoinColumn(name = "booking_id")
public class FlightBooking  extends Booking {
    @Id
    private String flightNumber;
    private String airline;
    @Enumerated(EnumType.STRING)
    private FJourney journeyType;
    private String fromLocation;
    private String toLocation;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String departureTerminal;
    private String arrivalTerminal;
    @Enumerated(EnumType.STRING)
    private FBookingClass bookingClass;
    private boolean isDirectFlight;
    private int stopOvers;
    @Enumerated(EnumType.STRING)
    private FlightType aircraftType;
    private String seatNumbers;
    @ElementCollection
    private List<String> mealPreferences;
    @ElementCollection
    private List<String> specialAssistance;
    private double baggageAllowance;
    private boolean travelInsurance;

    protected FlightBooking(){}

    private FlightBooking(Builder builder) {
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

        this.flightNumber = builder.flightNumber;
        this.airline = builder.airline;
        this.journeyType = builder.journeyType;
        this.fromLocation = builder.fromLocation;
        this.toLocation = builder.toLocation;
        this.departureTime = builder.departureTime;
        this.arrivalTime = builder.arrivalTime;
        this.departureTerminal = builder.departureTerminal;
        this.arrivalTerminal = builder.arrivalTerminal;
        this.bookingClass = builder.bookingClass;
        this.isDirectFlight = builder.isDirectFlight;
        this.stopOvers = builder.stopOvers;
        this.aircraftType = builder.aircraftType;
        this.seatNumbers = builder.seatNumbers;
        this.mealPreferences = builder.mealPreferences != null ? builder.mealPreferences : new ArrayList<>();
        this.specialAssistance = builder.specialAssistance != null ? builder.specialAssistance : new ArrayList<>();
        this.baggageAllowance = builder.baggageAllowance;
        this.travelInsurance = builder.travelInsurance;
    }

    // Getters
    public String getFlightNumber() { return flightNumber; }
    public String getAirline() { return airline; }
    public FJourney getJourneyType() { return journeyType; }
    public String getFromLocation() { return fromLocation; }
    public String getToLocation() { return toLocation; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public String getDepartureTerminal() { return departureTerminal; }
    public String getArrivalTerminal() { return arrivalTerminal; }
    public FBookingClass getBookingClass() { return bookingClass; }
    public boolean isDirectFlight() { return isDirectFlight; }
    public int getStopOvers() { return stopOvers; }
    public FlightType getAircraftType() { return aircraftType; }
    public String getSeatNumbers() { return seatNumbers; }
    public List<String> getMealPreferences() { return mealPreferences; }
    public List<String> getSpecialAssistance() { return specialAssistance; }
    public double getBaggageAllowance() { return baggageAllowance; }
    public boolean isTravelInsurance() { return travelInsurance; }

    // Business methods
    public BoardingPass checkIn() {
        return new BoardingPass.Builder().build();
    }

    public boolean selectSeat(String seatNumber) {
        this.seatNumbers = seatNumber;
        return true;
    }

    public void addMealPreference(String preference) {
        this.mealPreferences.add(preference);
    }

    public long calculateFlightDuration() {
        return ChronoUnit.HOURS.between(departureTime, arrivalTime);
    }

    public boolean validateLuggage() {
        // Business rule: Economy: 20kg, Business: 30kg, First: 40kg
        double maxAllowance;
        switch (bookingClass) {
            case FIRST_CLASS: maxAllowance = 40; break;
            case BUSINESS: maxAllowance = 30; break;
            default: maxAllowance = 20;
        }
        return baggageAllowance <= maxAllowance;
    }

    @Override
    public Booking modifyBooking() {
        this.lastModified = LocalDateTime.now();
        return this;
    }

    @Override
    public String getBookingDetails() {
        return String.format("Flight %s: %s to %s on %s",
                flightNumber, fromLocation, toLocation, departureTime);
    }

    @Override
    public Invoice generateInvoice() {
        return new Invoice.Builder().build();
    }

    @Override
    public String toString() {
        return "FlightBooking{" +
                "bookingId=" + bookingId +
                ", flightNumber='" + flightNumber + '\'' +
                ", airline='" + airline + '\'' +
                ", fromLocation='" + fromLocation + '\'' +
                ", toLocation='" + toLocation + '\'' +
                ", departureTime=" + departureTime +
                ", bookingClass=" + bookingClass +
                ", totalPrice=" + totalPrice +
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

        // Flight specific fields
        private String flightNumber;
        private String airline;
        private FJourney journeyType;
        private String fromLocation;
        private String toLocation;
        private LocalDateTime departureTime;
        private LocalDateTime arrivalTime;
        private String departureTerminal;
        private String arrivalTerminal;
        private FBookingClass bookingClass;
        private boolean isDirectFlight;
        private int stopOvers;
        private FlightType aircraftType;
        private String seatNumbers;
        private List<String> mealPreferences;
        private List<String> specialAssistance;
        private double baggageAllowance;
        private boolean travelInsurance;


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

        public Builder setFlightNumber(String flightNumber) {
            this.flightNumber = flightNumber;
            return this;
        }

        public Builder setAirline(String airline) {
            this.airline = airline;
            return this;
        }

        public Builder setJourneyType(FJourney journeyType) {
            this.journeyType = journeyType;
            return this;
        }

        public Builder setFromLocation(String fromLocation) {
            this.fromLocation = fromLocation;
            return this;
        }

        public Builder setToLocation(String toLocation) {
            this.toLocation = toLocation;
            return this;
        }

        public Builder setDepartureTime(LocalDateTime departureTime) {
            this.departureTime = departureTime;
            return this;
        }

        public Builder setArrivalTime(LocalDateTime arrivalTime) {
            this.arrivalTime = arrivalTime;
            return this;
        }

        public Builder setDepartureTerminal(String departureTerminal) {
            this.departureTerminal = departureTerminal;
            return this;
        }

        public Builder setArrivalTerminal(String arrivalTerminal) {
            this.arrivalTerminal = arrivalTerminal;
            return this;
        }

        public Builder setBookingClass(FBookingClass bookingClass) {
            this.bookingClass = bookingClass;
            return this;
        }

        public Builder setDirectFlight(boolean directFlight) {
            isDirectFlight = directFlight;
            return this;
        }

        public Builder setStopOvers(int stopOvers) {
            this.stopOvers = stopOvers;
            return this;
        }

        public Builder setAircraftType(FlightType aircraftType) {
            this.aircraftType = aircraftType;
            return this;
        }

        public Builder setSeatNumbers(String seatNumbers) {
            this.seatNumbers = seatNumbers;
            return this;
        }

        public Builder setMealPreferences(List<String> mealPreferences) {
            this.mealPreferences = mealPreferences;
            return this;
        }

        public Builder setSpecialAssistance(List<String> specialAssistance) {
            this.specialAssistance = specialAssistance;
            return this;
        }

        public Builder setBaggageAllowance(double baggageAllowance) {
            this.baggageAllowance = baggageAllowance;
            return this;
        }

        public Builder setTravelInsurance(boolean travelInsurance) {
            this.travelInsurance = travelInsurance;
            return this;
        }

        public Builder copy(FlightBooking flightBooking) {
            this.bookingId = flightBooking.bookingId;
            this.bookingReference = flightBooking.bookingReference;
            this.bookingDate = flightBooking.bookingDate;
            this.lastModified = flightBooking.lastModified;
            this.status = flightBooking.status;
            this.subtotal = flightBooking.subtotal;
            this.taxes = flightBooking.taxes;
            this.totalPrice = flightBooking.totalPrice;
            this.currency = flightBooking.currency;
            this.bookedBy = flightBooking.bookedBy;
            this.travelers = flightBooking.travelers;
            this.payment = flightBooking.payment;
            this.cancellationPolicy = flightBooking.cancellationPolicy;

            this.flightNumber = flightBooking.flightNumber;
            this.airline = flightBooking.airline;
            this.journeyType = flightBooking.journeyType;
            this.fromLocation = flightBooking.fromLocation;
            this.toLocation = flightBooking.toLocation;
            this.departureTime = flightBooking.departureTime;
            this.arrivalTime = flightBooking.arrivalTime;
            this.departureTerminal = flightBooking.departureTerminal;
            this.arrivalTerminal = flightBooking.arrivalTerminal;
            this.bookingClass = flightBooking.bookingClass;
            this.isDirectFlight = flightBooking.isDirectFlight;
            this.stopOvers = flightBooking.stopOvers;
            this.aircraftType = flightBooking.aircraftType;
            this.seatNumbers = flightBooking.seatNumbers;
            this.mealPreferences = flightBooking.mealPreferences;
            this.specialAssistance = flightBooking.specialAssistance;
            this.baggageAllowance = flightBooking.baggageAllowance;
            this.travelInsurance = flightBooking.travelInsurance;
            return this;
        }

        public FlightBooking build() {
            return new FlightBooking(this);
        }
    }
}
