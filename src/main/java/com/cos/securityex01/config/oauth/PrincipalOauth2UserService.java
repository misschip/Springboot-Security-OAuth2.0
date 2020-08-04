package com.cos.securityex01.config.oauth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.securityex01.config.auth.PrincipalDetails;
import com.cos.securityex01.config.oauth.provider.FaceBookUserInfo;
import com.cos.securityex01.config.oauth.provider.GoogleUserInfo;
import com.cos.securityex01.config.oauth.provider.OAuth2UserInfo;
import com.cos.securityex01.model.User;
import com.cos.securityex01.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	
	@Autowired
	private UserRepository userRepository;
	
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
		
		
		// return super.loadUser(userRequest);
		return processOAuth2User(userRequest, oAuth2User);
		// OAuth2User를 구현한 PrincipalDetails 객체를 반환하는 식으로 처리!
			
	}
	
	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		
		// 일반적으로는 로그인할 때 유저 정보는 User가 들고 있음
		// 1. OAuth2 로 로그인할 때 유저 정보 attributes <- 이거 구성해야 함
		// 2. DB에 이 사람 있나?
		
		// Attribute를 파싱해서 공통 객체로 묶는다. 관리가 편함
		OAuth2UserInfo oAuth2UserInfo = null;
		if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
		} else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			oAuth2UserInfo = new FaceBookUserInfo(oAuth2User.getAttributes());
		} else {
			System.out.println("우리는 구글과 페이스북만 지원해요");	// 사실은 이 경우는 예외를 발생시키는 게 바람직하다고
		}
		
//		System.out.println("oAuth2UserInfo.getProvider():" + oAuth2UserInfo.getProvider());
//		System.out.println("oAuth2UserInfo.getProviderId():" + oAuth2UserInfo.getProviderId());
		Optional<User> userOptional =
					userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
		
		User user;
		if (userOptional.isPresent()) {
			user = userOptional.get();
		} else {
			// user의 패스워드가  null이기 때문에 OAuth 유저는 일반적인 로그인을 할 수 없음
			user = User.builder()
					.username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
					.email(oAuth2UserInfo.getEmail())
					.role("ROLE_USER")
					.provider(oAuth2UserInfo.getProvider())
					.providerId(oAuth2UserInfo.getProviderId())
					.build();
			userRepository.save(user);
		}

		
		// PrincipalDetails 타입으로 반환하는 게 중요
		return new PrincipalDetails(user, oAuth2User.getAttributes());
		// return oAuth2User;
	}
}
