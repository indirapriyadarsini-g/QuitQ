package com.quitq.ECom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quitq.ECom.model.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {
@Query(" select a from Address a where a.city=?1 and a.landmark=?2 and a.pincode=?3 and a.state=?4 and a.streetdetails=?5")
	Optional<Address> findByAll(String city, String landmark, int pincode, String state, String streetdetails);
@Query("select  av.status,a.city,a.state,a.landmark,a.streetdetails,a.pincode from AddressVendor av join av.address a join av.vendor v where v.id=?1")
List<Object[]> findAddressOfParticularVendor(int id);

}