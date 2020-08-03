package com.cos.securityex01.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;


public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		//oAuth2User 정보를 어디에 담아서 무엇을 리턴하면 될까?
		// 1번 : Principaldetails에 OAuth2User 정보를 넣어 준다
		// 2번 : PrincipalDetails를 리턴한다
		System.out.println("oAuth2User: " + oAuth2User);	// 토큰을 통해 응답받은 회원정보
		System.out.println("userRequest.token(): " + userRequest.getAccessToken().getTokenValue());
		System.out.println("userRequest.getClientRegistration(): " + userRequest.getClientRegistration());
		System.out.println("userRequest: " + userRequest.getClass());
		try {
			
		} catch(Exception e) {
			
		}
		return super.loadUser(userRequest);
		// OAuth2User를 구현한 PrincipalDetails 객체를 반환하는 식으로 처리!
			
	}
	
	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		
		// 일반적으로는 로그인할 때 유저 정보는 User가 들고 있음
		// 1. OAuth2 로 로그인할 때 유저 정보 attributes <- 이거 구성해야 함
		// 2. DB에 이 사람 있나?
		
		// 있으면? update 해야 함
		
		// 없으면 insert 해야 함
		
		// return PrincipalDetails();
		return null;
	}
}
