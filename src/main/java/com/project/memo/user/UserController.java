package com.project.memo.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	//회원가입 화면
	@GetMapping("/user/signup/view")
	public String signupView() {
			
		return "user/signup";
	}
	
	// 로그인 화면
	@GetMapping("/user/signin/view")
	public String signinView() {
		
		return "user/signin";
	}
	
	// 로그아웃
	@GetMapping("/user/sign/out")
	public String signOut(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.removeAttribute("userId");
		session.removeAttribute("userLoginId");
		session.removeAttribute("userName");
		
		return "redirect:/user/signin/view";
	}

}
