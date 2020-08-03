package com.cos.securityex01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.securityex01.model.User;

// mybatis와 다른 점 : JpaRepository를 extends를 함 (기본적인 메서드들을 전부 들고 있음. 잘 찾아써야!)
// JpaRepository를 상속하면 자동 컴포넌트 스캔되므로 어노테이션 불필요
public interface UserRepository extends JpaRepository<User, Integer> {
	
	// JPA Naming 전략
	// SELECT * FROM user WHERE username = 1? 자동 생성
	User findByUsername(String username);

	// SELECT * FROM user WHERE username = 1? AND password = 2? 자동 생성
//	User findByUsernameAndPassword(String username, String password);
//	
//	
//	@Query(value = "select * from user", nativeQuery =  true)
//	User find마음대로();
}

// JPA Query Creation
// https://papababo.tistory.com/272
