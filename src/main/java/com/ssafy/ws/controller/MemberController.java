package com.ssafy.ws.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.ws.model.dto.Member;
import com.ssafy.ws.model.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService service;
	
	@GetMapping("/regist")
	private String showRegistForm() {
		return "register-form";
	}
	
	@PostMapping("/regist")
	private String regist(@ModelAttribute Member member, Model model) {
		service.signIn(member);
		String msg = "등록되었습니다. 로그인 후 사용해주세요.";
    	model.addAttribute("alertMsg", msg);
    	
        return "redirect:/";
	}
	
	@GetMapping("/login")
	private String showLoginForm() {
		return "login-form";
	}
	
	@PostMapping("/login")
	private String login(HttpSession session, HttpServletResponse response,
					@RequestParam String id, @RequestParam String password, @RequestParam(required = false) String rememberMe) 
			throws Exception {
		Member member = new Member();
		member.setId(id);
		member.setPassword(password);
		
		member = service.login(member);
		
        if (member == null) {
            throw new Exception("틀린 아이디 또는 비밀번호입니다.");
        }
        
        session.setAttribute("id", member.getId());
        session.setAttribute("loginUser", member);
        
        if (rememberMe == null) {
            setupCookie("rememberMe", "bye", 0, null, response);
        } else {
            setupCookie("rememberMe", id, 60 * 60 * 24 * 365, null, response);
        }

        return "redirect:/";
	}
	
	@GetMapping("/logout")
	private String logout(HttpSession session) {
		session.invalidate();
        return "redirect:/";
	}
	
	@GetMapping("/my-page")
	private String showMyPage() {
		return "mypage-form";
	}
	
	@PostMapping("/my-page")
	private String updateMember(@ModelAttribute Member member, HttpSession session) {
		String id = ((Member)session.getAttribute("loginUser")).getId();
		member.setId(id);
		service.updateInfo(member);
        return "redirect:/";
	}
	
	@GetMapping("/delete")
	private String deleteMember(Model model, HttpSession session, @RequestParam String password) {
		Member member = new Member();
		String id = ((Member)session.getAttribute("loginUser")).getId();
		member.setId(id);
		member.setPassword(password);
		int result = service.unRegister(member);
		String msg = "탈퇴를 실패하였습니다";
    	
    	if (result == 1) {
    		msg = "탈퇴가 완료 되었습니다";
    	}
		
    	model.addAttribute("alertMsg", msg);
    	session.removeAttribute("loginUser");
    	
        return "redirect:/";
	}
	
	@PostMapping("/find-password")
	private String findPwById(@RequestParam String id, @RequestParam String email, @RequestParam String name, Model model) {
		Member member = new Member();
		member.setId(id);
		member.setEmail(email);
		member.setName(name);
		
		member = service.findPassword(member);
		model.addAttribute("password", member.getPassword());
		return "login-form";
	}
	
	@GetMapping("/admin-page")
	private String findMemberAll(Model model) {
		List<Member> memberList = service.findAll();
		model.addAttribute("membersList", memberList);
		return "admin-page";
	}
	
    private Cookie setupCookie(String name, String value, int maxAge, String path, HttpServletResponse resp) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        if (path != null) {
            cookie.setPath(path);
        }
        resp.addCookie(cookie);
        return cookie;
    }
}
