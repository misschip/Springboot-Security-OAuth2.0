package com.cos.securityex01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.securityex01.config.oauth.PrincipalOauth2UserService;


@Configuration	// IoC 빈(bean)을 등록
@EnableWebSecurity	// 필터 체인에 등록 & 관리 시작
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)	// 특정 주소 접근시 권한 및 인증을 미리 체크하겠다. // 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	// 아래 메서드는 딱히 둘 데가 없어서 여기 둠. CommonConfig 등에 넣어도 됨
	@Bean	// return new ... 객체가 IoC 된다
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Override	// http로 들어오는 요청에 대해 처리함
	protected void configure(HttpSecurity http) throws Exception {
		// super.configure(http);	// 이게 있으면 기본 로그인 화면으로 보냄
		// 기본으로 있던 super.configure(http) 코드를 지우는 게 중요
		// 무조건 기본 로그인으로 보내는 낚아채기 방지
		
		// 한정된 부분만 잠기고
		// 나머지는 허용
		
		http.csrf().disable(); // csrf 설정이 기본값. GET을 제외한 POST, DELETE 등 메서드로 접근시 csrf 토큰이 필요함
		
		http.authorizeRequests()
			.antMatchers("/user/**")	// 이 페이지들은
			.authenticated()						// 인증된 사용자라야 접근 가능하고
			//.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")	// hasAnyRole("ROLE_ADMIN")도 가능한 듯
			//.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")	// 일부 예외를 허용할 때는 ignoring()을 쓰면 됨
			.anyRequest()				// 나머지는
			.permitAll()				// 아무나 접근 가능
		.and()			//  세션 1
			.formLogin()		// x-www-form-urlencoded로만 데이터 받을 수
			.loginPage("/login")
			.loginProcessingUrl("/loginProc")	// loginProc 쪽으로 보내는 것은 ajax로는 부적절함
			.defaultSuccessUrl("/")
		.and()			// 세션 2
			.oauth2Login()
			.loginPage("/login")
			.userInfoEndpoint()
			.userService(new PrincipalOauth2UserService());
			

		// 한정된 부분만 풀고
		// 나머지는 잠금.
//		http.authorizeRequests()
//			.antMatchers("/")	// 첫 페이지만
//			.permitAll()		// 인증없이 페이지 볼수 있게 하고
//			.anyRequest()	// 그 외는 인증된 사용자만 접근 가능
//			.authenticated();
	}


}
