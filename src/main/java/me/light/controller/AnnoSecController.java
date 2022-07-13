package me.light.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import me.light.exception.NotMatchUserIdException;
import me.light.model.MemberVO;
import me.light.security.CustomUser;

@Controller
public class AnnoSecController {
	
	@PreAuthorize("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
	@GetMapping("/anno/member")
	public String memberPage() {
		return "member/member";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/anno/myPage/{userId}")
	public String myPage(@PathVariable String userId, 
		   @AuthenticationPrincipal	CustomUser customUser, Model model) {
		MemberVO vo = customUser.getMemberVO();
		if(!vo.getUserId().equals(userId)) {
			throw new NotMatchUserIdException(); 
		}
		model.addAttribute("member", vo);
		return "member/myPage";	
	}
	
}
