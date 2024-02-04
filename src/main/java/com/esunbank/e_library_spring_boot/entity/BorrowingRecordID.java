package com.esunbank.e_library_spring_boot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
@ToString
public class BorrowingRecordID implements Serializable {

	@Column(name = "USER_ID")
    private long userID;

	@Column(name = "INVENTORY_ID")
    private long inventoryID;
    
}

