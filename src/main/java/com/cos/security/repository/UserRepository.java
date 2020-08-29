package com.cos.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security.model.User;


//CRUD 함수를 JpaRepository가 들고있음
public interface UserRepository extends JpaRepository<User,Integer> {
	//findBy 규칙 //Username 문법
	//Select * form user where username = ?
	public User findByUsername(String username);
																					//User에 관하여 Integer타입이 Primary key (관리)
	
}
