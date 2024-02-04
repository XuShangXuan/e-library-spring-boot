package com.esunbank.e_library_spring_boot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "BOOK")
public class Book {

    @Id
    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @Column(name = "INTRODUCTION", nullable = false)
    private String introduction;

    @OneToOne
    @JoinColumn(name = "ISBN")
    private Inventory inventory;

}

