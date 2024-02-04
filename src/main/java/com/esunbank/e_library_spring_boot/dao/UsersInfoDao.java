package com.esunbank.e_library_spring_boot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esunbank.e_library_spring_boot.entity.Users;

@Repository
public interface UsersInfoDao extends JpaRepository<Users, Integer>{
	
	List<Users> findByPhoneNumber(String phoneNumber);
	
}
