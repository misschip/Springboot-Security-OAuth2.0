package com.cos.securityex01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.securityex01.config.auth.PrincipalDetails;
import com.cos.securityex01.model.User;
import com.cos.securityex01.repository.UserRepository;


@Controller
public class IndexController {
	
	@Autowired	// 패스워드 암호화
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping({"","/"})
	public @ResponseBody String index() {
		return "인덱스 페이지입니다";
	}
	
	
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principal) {
		System.out.println(principal);
		// System.out.println(principal.getUser().getRole());	// null
		// System.out.println(principal.getAuthorities());	// ROLE_USER 등이 출력되도록! 숙제
		return "user 페이지입니다";
	}
	
	
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin 페이지입니다";
	}
	
	
	// @PostAuthorize("hasRole('ROLE_MANAGER')")		// 이 어노테이션 쓸 때는 SecurityConfig에 @EnableGlobalMethodSecurity(prePostEnabled = true) 붙여줘야
	// @PreAuthorize("hasRole('ROLE_MANAGER')")	// 컨트롤러 들어오기 전에 작동
	@Secured("ROLE_MANAGER")		// 위 것과 둘 중 하나 쓰는 걸로
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "매니져 페이지입니다";
	}
	
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	
	@GetMapping("/join")
	public String join() {
		return "join";
	}
	
	@PostMapping("/joinProc")
	public String joinProc(User user) {
		System.out.println("회원가입 진행 : " + user);
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole("ROLE_USER");
		userRepository.save(user);
		return "redirect:/";
	}
}
