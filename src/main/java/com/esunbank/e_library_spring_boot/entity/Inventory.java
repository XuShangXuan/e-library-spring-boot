package com.esunbank.e_library_spring_boot.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "INVENTORY")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVENTORY_ID")
    private long inventoryID;

    @Column(name = "ISBN", unique = true, nullable = false)
    private String isbn;

    @Column(name = "STORE_TIME", nullable = false)
    private LocalDateTime storeTime;

    @Column(name = "STATUS", nullable = false)
    private String status;

}
