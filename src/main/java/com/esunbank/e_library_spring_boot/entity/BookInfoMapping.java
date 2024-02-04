package com.esunbank.e_library_spring_boot.entity;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SqlResultSetMapping(
    name = "BookInfoMapping",
    entities={
        @EntityResult(
	        entityClass = com.esunbank.e_library_spring_boot.entity.BookInfoMapping.class,
	        fields = {
	        	@FieldResult(name="isbn", column="ISBN"),
	            @FieldResult(name="name",  column="NAME"),
	        	@FieldResult(name="author",  column="AUTHOR"),
	            @FieldResult(name="introduction",  column="INTRODUCTION"),
	            @FieldResult(name="inventoryID",  column="INVENTORY_ID"),
	            @FieldResult(name="status",  column="STATUS")
	        }
        )
    }
) 

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class BookInfoMapping {

	@Id
	private String isbn;
	
	private String name;

	private String author;

	private String introduction;
	
	private long inventoryID;
	
	private String status;
	
}
