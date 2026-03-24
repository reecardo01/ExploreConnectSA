package za.ac.cput.repository;

import za.ac.cput.domain.Address;
import za.ac.cput.domain.ContactDetails;

import java.util.List;

public interface IAddressRepository extends IRepository<Address, Long> {
    Address findById(Long id);
    List<Address> findByCustomerId(Long customerId);
    List<Address> findByCity(String city);
    List<Address> findByPostalCode(String postalCode);
    Address findDefaultAddressByCustomerId(Long customerId);
}
