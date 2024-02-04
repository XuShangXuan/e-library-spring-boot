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
import com.esunbank.e_library_spring_boot.vo.BookDataCondition;
import com.esunbank.e_library_spring_boot.vo.GenericPageable;
import com.esunbank.e_library_spring_boot.vo.UsersInfoVo;

@Repository
public class BookDataDao {

	@PersistenceContext(name = "oracleEntityManager")
	private EntityManager entityManager;
	
	public int queryBookCountByBookDataCondition(BookDataCondition condition) {
		
		String isbn = null;
		String name = null;
		String author = null;
		String status = null;
		
		if (null != condition) {
			isbn = condition.getIsbn();
			name = condition.getBookNameKeyword();
			author = condition.getAuthor();
			status = condition.getStatus();
		}
		
		List<Object> params = new ArrayList<>();
		
		StringBuilder querySQL = new StringBuilder();
		querySQL.append(" SELECT COUNT(B.ISBN) FROM BOOK B ")
				.append(" JOIN INVENTORY I ON B.ISBN = I.ISBN ")
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
		
		Query query = entityManager.createNativeQuery(querySQL.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i + 1, params.get(i));
		}
		
		int bookTotalCount = ((BigDecimal) query.getSingleResult()).intValue();
		
		return bookTotalCount;
	}

	public List<BookInfoMapping> queryBookByBookDataCondition(BookDataCondition condition, GenericPageable pageable) {

		String isbn = null;
		String name = null;
		String author = null;
		String status = null;
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
		
		List<Object> params = new ArrayList<>();
		
		StringBuilder querySQL = new StringBuilder();
		querySQL.append(" SELECT ISBN, NAME, AUTHOR, INTRODUCTION, INVENTORY_ID, STATUS ")
				.append(" FROM ( ")
				.append(" SELECT B.ISBN, B.NAME, B.AUTHOR, B.INTRODUCTION, ")
				.append(" I.INVENTORY_ID, I.STATUS, ROW_NUMBER() OVER(ORDER BY I.INVENTORY_ID) NUM ")
				.append(" FROM BOOK B ")
				.append(" JOIN INVENTORY I ON B.ISBN = I.ISBN ")
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
		if (startRowNo > 0 && endRowNo > 0) {
			querySQL.append(" ) ")
					.append( " WHERE NUM BETWEEN ? AND ? ");
			params.add(startRowNo);
			params.add(endRowNo);
		}

		Query query = entityManager.createNativeQuery(querySQL.toString(), "BookInfoMapping");
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i + 1, params.get(i));
		}

		Stream<BookInfoMapping> bookListStream = query.getResultStream().map(b -> (BookInfoMapping) b);
		List<BookInfoMapping> bookList = bookListStream.collect(Collectors.toList());
		bookList.stream().forEach(System.out::println);

		return bookList;
	}
	
}
