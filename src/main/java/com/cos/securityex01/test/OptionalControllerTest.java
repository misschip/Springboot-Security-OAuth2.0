package com.cos.securityex01.test;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.securityex01.model.User;
import com.cos.securityex01.repository.UserRepository;

@RestController
public class OptionalControllerTest {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/test/user/{id}")
	public User 옵셔널_유저찾기(@PathVariable int id) {
		// 첫번째 방법
//		Optional<User> userOptional = userRepository.findById(id);
//		User user;
//		if (userOptional.isPresent()) {
//			user = userOptional.get();
//		} else {
//			user = new User();
//		}
		
		// 두번째 방법
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//
//			@Override
//			public User get() {
//				return User.builder().username("아무개").email("아무개@nate.com").build();	// findById() 반환값이 null일 때 기본 객체
//			}
//		});
		
		// 세번째 방법 - 두번째 방법을 람다식으로 바꿈
//		User user = userRepository.findById(id).orElseGet(() -> 
//					User.builder().username("아무개").email("아무개@nate.com").build());	// findById() 반환값이 null일 때 기본 객체
		
		// 네법째 방법
//		User user = userRepository.findById(id)
//				.orElseThrow(new Supplier<NullPointerException>() {
//
//					@Override
//					public NullPointerException get() {
//						return new NullPointerException("값이 없음");
//					}
//				});
		
		// 다섯번째 방법 - 네번째 방법을 람다식으로
		User user = userRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("값이 없음"));


		
		
		
		return user;
	}
}
