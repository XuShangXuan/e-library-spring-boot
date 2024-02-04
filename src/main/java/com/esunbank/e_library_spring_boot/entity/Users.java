package com.esunbank.e_library_spring_boot.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = { "userID" })
@ToString
@Entity
@Table(name = "USERS", schema = "LOCAL")
public class Users {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
    @SequenceGenerator(name = "USERS_SEQ", sequenceName = "USERS_SEQ", allocationSize = 1)
    @Column(name = "USER_ID")
    private int userID;

    @Column(name = "PHONE_NUMBER", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(name = "REGISTRATION_TIME", nullable = false)
    private LocalDateTime registrationTime;

    @Column(name = "LAST_LOGIN")
    private LocalDateTime lastLogin;
    
}
