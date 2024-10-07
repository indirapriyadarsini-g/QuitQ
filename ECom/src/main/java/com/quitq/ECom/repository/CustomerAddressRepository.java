package com.quitq.ECom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quitq.ECom.model.Address;
import com.quitq.ECom.model.CustomerAddress;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress,Integer>{

	@Query("select ca.address from CustomerAddress ca join ca.customer c join c.userInfo u where u.username = ?1")
	List<Address> getAddressByUsername(String name);

}
