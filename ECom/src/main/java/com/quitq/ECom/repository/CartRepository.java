package com.quitq.ECom.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quitq.ECom.model.Cart;
import com.quitq.ECom.model.Customer;


@Repository
public interface CartRepository extends JpaRepository<Cart,Integer>{

	/*@Query("select c from Cart c where customer_id = ?1")
	Optional<Cart> getProductByCustomerId(int id);
*/
	@Query("select c from Cart c where c.customer= ?1")
	Optional<Cart> getCartByCustomer(Customer customer);

	@Query("select c from Cart c join c.customer cust join cust.userInfo u where u.username = ?1")
	Optional<Cart> getCartByUsername(String name);

}
