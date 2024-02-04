package com.esunbank.e_library_spring_boot.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.esunbank.e_library_spring_boot.entity.BookInfoMapping;
import com.esunbank.e_library_spring_boot.entity.BorrowBookInfoMapping;
import com.esunbank.e_library_spring_boot.vo.BookDataCondition;
import com.esunbank.e_library_spring_boot.vo.GenericPageable;
import com.esunbank.e_library_spring_boot.vo.UsersInfoVo;

@Repository
public class BorrowingRecordDataDao {

	@PersistenceContext(name = "oracleEntityManager")
	private EntityManager entityManager;
	
public int queryBorrowBookCountByBookDataCondition(BookDataCondition condition, UsersInfoVo usersInfo) {
		
		String isbn = null;
		String name = null;
		String author = null;
		String status = null;
		Integer userID = null;
		
		if (null != condition) {
			isbn = condition.getIsbn();
			name = condition.getBookNameKeyword();
			author = condition.getAuthor();
			status = condition.getStatus();
		}
		
		if (null != usersInfo) {
			userID = usersInfo.getUserID();
		}
		
		List<Object> params = new ArrayList<>();
		
		StringBuilder querySQL = new StringBuilder();
		querySQL.append(" SELECT COUNT(B.ISBN) FROM BORROWING_RECORD BR ")
				.append(" JOIN INVENTORY I ON BR.INVENTORY_ID = I.INVENTORY_ID ")
				.append(" JOIN BOOK B ON I.ISBN = B.ISBN ")
				.append(" WHERE B.ISBN IS NOT NULL ");
		if (null != isbn && !isbn.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND B.ISBN = ? ");
			params.add(isbn);
		}
		if (null != name && !name.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND B.NAME = ? ");
			params.add(name);
		}
		if (null != author && !author.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND B.AUTHOR = ? ");
			params.add(author);
		}
		if (null != status && !status.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND I.STATUS = ? ");
			params.add(status);
		}
		if (null != userID) {
			querySQL.append(" AND BR.USER_ID = ? ");
			params.add(userID);
		}
		
		Query query = entityManager.createNativeQuery(querySQL.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i + 1, params.get(i));
		}
		
		int bookTotalCount = ((BigDecimal) query.getSingleResult()).intValue();
		
		return bookTotalCount;
	}

	public List<BorrowBookInfoMapping> queryBorrowBookByBookDataCondition(BookDataCondition condition, GenericPageable pageable, UsersInfoVo usersInfo) {
	
		String isbn = null;
		String name = null;
		String author = null;
		String status = null;
		Integer userID = null;
		Integer startRowNo = null;
		Integer endRowNo = null;
		
		if (null != condition) {
			isbn = condition.getIsbn();
			name = condition.getBookNameKeyword();
			author = condition.getAuthor();
			status = condition.getStatus();
			startRowNo = condition.getStartRowNo();
			endRowNo = condition.getEndRowNo();
		}
		
		if (null != usersInfo) {
			userID = usersInfo.getUserID();
		}
		
		List<Object> params = new ArrayList<>();
		
		StringBuilder querySQL = new StringBuilder();
		querySQL.append(" SELECT ISBN, NAME, AUTHOR, INTRODUCTION, INVENTORY_ID, STATUS ")
				.append(" FROM ( ")
				.append(" SELECT B.ISBN, B.NAME, B.AUTHOR, B.INTRODUCTION, I.INVENTORY_ID, ")
				.append(" I.STATUS, ROW_NUMBER() OVER(ORDER BY I.INVENTORY_ID) NUM ")
				.append(" FROM BORROWING_RECORD BR ")
				.append(" JOIN INVENTORY I ON BR.INVENTORY_ID = I.INVENTORY_ID ")
				.append(" JOIN BOOK B ON I.ISBN = B.ISBN ")
				.append(" WHERE B.ISBN IS NOT NULL ");
		if (null != isbn && !isbn.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND B.ISBN = ? ");
			params.add(isbn);
		}
		if (null != name && !name.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND UPPER(B.NAME) LIKE UPPER(?) ");
			params.add("%" + name + "%");
		}
		if (null != author && !author.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND UPPER(B.AUTHOR) LIKE UPPER(?) ");
			params.add("%" + author + "%");
		}
		if (null != status && !status.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND I.STATUS = ? ");
			params.add(status);
		}
		if (null != userID) {
			querySQL.append(" AND BR.USER_ID = ? ");
			params.add(userID);
		}
		if (startRowNo > 0 && endRowNo > 0) {
			querySQL.append(" ) ")
					.append( " WHERE NUM BETWEEN ? AND ? ");
			params.add(startRowNo);
			params.add(endRowNo);
		}
	
		Query query = entityManager.createNativeQuery(querySQL.toString(), "BorrowBookInfoMapping");
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i + 1, params.get(i));
		}
	
		Stream<BorrowBookInfoMapping> borrowBookListStream = query.getResultStream().map(b -> (BorrowBookInfoMapping) b);
		List<BorrowBookInfoMapping> borrowBookList = borrowBookListStream.collect(Collectors.toList());
		borrowBookList.stream().forEach(System.out::println);
	
		return borrowBookList;
	}
	
}
