package com.cos.securityex01.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

// JPA 사용
// ORM - Object Relation Mapping
// Java model -> DB table

@Data
@Entity		// 데이터베이스 테이블의 모델
public class User {
	@Id	// primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// 어느 DB를 쓰느냐에 따라 키값 생성을 하는 방식 선택
	private int id;
	private String username;
	private String password;
	private String email;
	private String role;	// 회원가입시 기본권한 ROLE_USER, ROLE_ADMIN은 따로 줄 것
	@CreationTimestamp
	private Timestamp createDate;
}

// 오라클은 시퀀스
// mysql은 auto increment