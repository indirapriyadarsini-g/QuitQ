package com.quitq.ECom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quitq.ECom.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	@Query("select u from User u where u.username = ?1")
//	@Query(nativeQuery = true, value ="select * from user where username = ?1" )
	User getUserByUsername(String username);
	
	@Query("select u from User u where u.id=?1")
	Optional<User> getUserById(int id);
}
