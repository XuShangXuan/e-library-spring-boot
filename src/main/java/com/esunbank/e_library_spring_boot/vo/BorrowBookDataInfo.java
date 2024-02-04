package com.esunbank.e_library_spring_boot.vo;

import java.util.List;

import com.esunbank.e_library_spring_boot.entity.BorrowBookInfoMapping;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BorrowBookDataInfo {

	List<BorrowBookInfoMapping> borrowBookList;

	private GenericPageable genericPageable;
	
}
