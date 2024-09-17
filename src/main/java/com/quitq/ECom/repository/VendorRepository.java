
package com.quitq.ECom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quitq.ECom.model.Vendor;

public interface VendorRepository extends JpaRepository<Vendor,Integer> {
@Query("select v from Vendor v join v.user u where u.id=?1")
	Vendor findByUserId(int uid);
/*a vendor will have multiple address in same city so we choose city name and extract only list of vendor with active address only
 * other wise duplicates entry will be there set status=active in your caller method */
@Query("select   v from AddressVendor av join av.address a join av.vendor v where a.city=?1 and av.status=?2")
List<Vendor> getListOfVendorInSameCity(String cityName,String status);

}
