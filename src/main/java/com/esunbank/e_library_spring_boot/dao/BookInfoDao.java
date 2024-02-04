package com.esunbank.e_library_spring_boot.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esunbank.e_library_spring_boot.entity.Book;

@Repository
public interface BookInfoDao extends JpaRepository<Book, String> {
}
