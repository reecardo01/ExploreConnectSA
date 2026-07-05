package za.ac.cput.domain;
/* User.java

   User POJO class

   Author: Zamandlovu C Ndlovu (211204803)

   Date: 21 June 2026
*/
import jakarta.persistence.*;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long userId;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;

    @Enumerated(EnumType.STRING)
    protected UserRole role;
    protected boolean isActive;
    protected LocalDateTime createdAt;
    protected LocalDateTime lastLogin;

    protected User() {}

    protected User(Builder builder) {
        this.userId = builder.userId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.password = builder.password;
        this.role = builder.role;
        this.isActive = builder.isActive;
        this.createdAt = builder.createdAt;
        this.lastLogin = builder.lastLogin;
    }
    // Abstract builder will be in concrete classes

    public Long getUserId() {return userId;}
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public UserRole getRole() { return role; }
    public boolean isActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getLastLogin() { return lastLogin; }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserId='" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", lastLogin=" + lastLogin +
                '}';
    }
    public abstract static class Builder {
        protected Long userId;
        protected String firstName;
        protected String lastName;
        protected String email;
        protected String password;
        protected UserRole role;
        protected boolean isActive = true;
        protected LocalDateTime createdAt = LocalDateTime.now();
        protected LocalDateTime lastLogin;

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setRole(UserRole role) {
            this.role = role;
            return this;
        }

        public Builder setActive(boolean active) {
            isActive = active;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setLastLogin(LocalDateTime lastLogin) {
            this.lastLogin = lastLogin;
            return this;
        }

        public Builder copy(User user){
            this.userId = user.userId;
            this.firstName = user.firstName;
            this.lastName = user.lastName;
            this.email = user.email;
            this.password = user.password;
            this.role = user.role;
            this.isActive = user.isActive;
            this.createdAt = user.createdAt;
            this.lastLogin = user.lastLogin;
           return this;
        }

        public abstract User build();
    }
}

