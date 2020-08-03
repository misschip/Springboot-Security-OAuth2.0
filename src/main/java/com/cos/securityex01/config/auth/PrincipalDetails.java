package com.cos.securityex01.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.securityex01.model.User;

import lombok.Data;

// 세션에 담길 객체
// Authentication 객체에 저장할 수 있는 유일한 타입. User가 아님에 주의!!
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

	private User user;
	private Map<String,Object> attributes;
	
	public PrincipalDetails(User user) {
		super();
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// 아래 4개 메서드들 false이면 로그인 불가
	
	@Override
	public boolean isAccountNonExpired() {
		return true;	// 원래는 동적으로 최종접속 시간을 보고 결정해야
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));	// "ROLE_USER", "ROLE_ADMIN"
		
		return authorities;
		
		// 이 메서드 구현에 참조한 사이트 : https://www.baeldung.com/spring-security-granted-authority-vs-role
	}


	// attribute는 리소스 서버로부터 받는 회원 정보
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}


	@Override
	public String getName() {	// 제공자 ID 반환
		return "제공자 ID";
	}

}
