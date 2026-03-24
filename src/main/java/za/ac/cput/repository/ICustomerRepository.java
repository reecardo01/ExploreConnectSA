package za.ac.cput.repository;

import za.ac.cput.domain.Customer;

import java.util.List;

public interface ICustomerRepository extends IRepository<Customer, Long> {
    Customer findById(Long id);
    Customer findByEmail(String email);
    Customer findByIdentityNumber(String identityNumber);
    List<Customer> findByLastName(String lastName);
    List<Customer> findByLoyaltyTier(String tier);
    boolean existsByEmail(String email);
}
