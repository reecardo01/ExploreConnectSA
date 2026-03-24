package za.ac.cput.repository;

import za.ac.cput.domain.ContactDetails;


public interface IContactDetailsRepository extends IRepository<ContactDetails, Long> {
    ContactDetails findById(Long id);
    ContactDetails findByEmail(String email);
    ContactDetails findByCellNumber(String cellNumber);
    ContactDetails findByCustomerId(Long customerId);
}
