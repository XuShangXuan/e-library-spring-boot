package com.esunbank.e_library_spring_boot.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BookDataCondition {
	
	private String isbn;
	private String bookNameKeyword;
	private String author;
	private String status;
	private Integer startRowNo;
	private Integer endRowNo;
	
}
