package com.quitq.ECom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quitq.ECom.model.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer>{
	@Query("select u from User u where u.username = ?1")
	UserInfo getUserInfoByUsername(String username);
}
