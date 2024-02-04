package com.esunbank.e_library_spring_boot.vo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
@ToString
public class BorrowBookVo {
	
	private int userID;
	
	private long inventoryID;
	
	private LocalDateTime returnTime;
	
//	private int quantity;
	
}
