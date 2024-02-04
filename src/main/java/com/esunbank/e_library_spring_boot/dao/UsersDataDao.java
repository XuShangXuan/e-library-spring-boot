package com.esunbank.e_library_spring_boot.dao;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.esunbank.e_library_spring_boot.entity.Users;

@Repository
public class UsersDataDao {

	@PersistenceContext(name = "oracleEntityManager")
	private EntityManager entityManager;

	public List<Users> findByPhoneNumber(String inputPhoneNumber) {

		StringBuilder querySQL = new StringBuilder();
		querySQL.append(" SELECT USER_ID, PHONE_NUMBER, PASSWORD, USER_NAME, REGISTRATION_TIME, LAST_LOGIN ")
				.append(" FROM USERS").append(" WHERE PHONE_NUMBER = ?");

		Query query = entityManager.createNativeQuery(querySQL.toString(), Users.class);
		int position = 1;
		query.setParameter(position++, inputPhoneNumber);

		Stream<Users> usersStream = query.getResultStream().map(u -> (Users) u);
		List<Users> usersList = usersStream.collect(Collectors.toList());
		usersList.stream().forEach(System.out::println);

		return usersList;
	}
	
}
