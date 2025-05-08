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

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService service;

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
	
	@GetMapping("/admin-page")
	private String findMemberAll(Model model) {
		List<Member> memberList = service.findAll();
		model.addAttribute("membersList", memberList);
		return "admin-page";
	}
}
