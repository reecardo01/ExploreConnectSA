package za.ac.cput.domain;
/* Booking.java

   Booking POJO class

   Author: Zamandlovu C Ndlovu (211204803)

   Date: 21 June 2026
*/
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public abstract class Booking {
    @Id
    protected Long bookingId;
    protected String bookingReference;
    protected LocalDateTime bookingDate;
    protected LocalDateTime lastModified;

    @Enumerated(EnumType.STRING)
    protected BookingStatus status;
    protected double subtotal;
    protected double taxes;
    protected double totalPrice;
    protected String currency;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "booked_by_id")
    protected Customer bookedBy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "traveler_id")
    protected Traveler travelers;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    protected PaymentDetails payment;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cancellation_policy_id")
    protected CancellationPolicy cancellationPolicy;

    protected Booking(){}

    protected Booking(Builder builder) {
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
    }
    // Getters
    public Long getBookingId() { return bookingId; }
    public String getBookingReference() { return bookingReference; }
    public LocalDateTime getBookingDate() { return bookingDate; }
    public LocalDateTime getLastModified() { return lastModified; }
    public BookingStatus getStatus() { return status; }
    public double getSubtotal() { return subtotal; }
    public double getTaxes() { return taxes; }
    public double getTotalPrice() { return totalPrice; }
    public String getCurrency() { return currency; }
    public Customer getBookedBy() { return bookedBy; }
    public Traveler getTravelers() { return travelers; }
    public PaymentDetails getPayment() { return payment; }
    public CancellationPolicy getCancellationPolicy() { return cancellationPolicy; }

    // Business methods
    public double calculateTotal() {
        this.totalPrice = subtotal + taxes;
        return totalPrice;
    }

    public BookingConfirmation confirmBooking() {
        this.status = BookingStatus.CONFIRMED;
        this.lastModified = LocalDateTime.now();
        return new BookingConfirmation.Builder().build();
    }

    public boolean cancelBooking() {
        if (cancellationPolicy != null && cancellationPolicy.isEligible(bookingDate, LocalDateTime.now())) {
            this.status = BookingStatus.CANCELLED;
            this.lastModified = LocalDateTime.now();
            return true;
        }
        return false;
    }

    public abstract Booking modifyBooking();
    public abstract String getBookingDetails();
    public abstract Invoice generateInvoice();

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", bookingReference='" + bookingReference + '\'' +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public abstract static class Builder {
        protected Long bookingId;
        protected String bookingReference;
        protected LocalDateTime bookingDate;
        protected LocalDateTime lastModified;
        protected BookingStatus status;
        protected double subtotal;
        protected double taxes;
        protected double totalPrice;
        protected String currency;
        protected Customer bookedBy;
        protected Traveler travelers;
        protected PaymentDetails payment;
        protected CancellationPolicy cancellationPolicy;

        public Builder() {
            this.bookingDate = LocalDateTime.now();
            this.lastModified = LocalDateTime.now();
            this.status = BookingStatus.PENDING;
            this.currency = "ZAR";
        }

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

        public Builder copy(Booking booking){
            this.bookingId = booking.bookingId;
            this.bookingReference = booking.bookingReference;
            this.bookingDate = booking.bookingDate;
            this.lastModified = booking.lastModified;
            this.status = booking.status;
            this.subtotal = booking.subtotal;
            this.taxes = booking.taxes;
            this.totalPrice = booking.totalPrice;
            this.currency = booking.currency;
            this.bookedBy = booking.bookedBy;
            this.travelers = booking.travelers;
            this.payment = booking.payment;
            this.cancellationPolicy = booking.cancellationPolicy;
            return this;
        }
        public abstract Booking build();
    }
}
