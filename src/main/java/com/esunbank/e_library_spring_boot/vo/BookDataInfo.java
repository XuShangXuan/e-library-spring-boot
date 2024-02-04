package com.esunbank.e_library_spring_boot.vo;

import java.util.List;

import com.esunbank.e_library_spring_boot.entity.BookInfoMapping;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BookDataInfo {

	List<BookInfoMapping> bookList;

	private GenericPageable genericPageable;
	
}
