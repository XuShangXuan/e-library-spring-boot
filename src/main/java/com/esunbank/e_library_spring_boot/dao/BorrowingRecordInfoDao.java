package com.esunbank.e_library_spring_boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esunbank.e_library_spring_boot.entity.BorrowingRecord;
import com.esunbank.e_library_spring_boot.entity.BorrowingRecordID;
import com.esunbank.e_library_spring_boot.entity.Users;

@Repository
public interface BorrowingRecordInfoDao extends JpaRepository<BorrowingRecord, BorrowingRecordID> {

}
