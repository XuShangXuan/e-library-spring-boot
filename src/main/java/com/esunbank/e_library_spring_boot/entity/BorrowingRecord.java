package com.esunbank.e_library_spring_boot.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "BORROWING_RECORD", schema = "LOCAL")
public class BorrowingRecord implements Serializable{
	
	@EmbeddedId
    private BorrowingRecordID borrowingRecordID;

    @Column(name = "BORROWING_TIME", nullable = false)
    private LocalDateTime borrowingTime;

    @Column(name = "RETURN_TIME")
    private LocalDateTime returnTime;
	
}
