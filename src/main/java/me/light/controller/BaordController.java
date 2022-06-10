package me.light.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import me.light.model.Board;
import me.light.model.Criteria;
import me.light.model.PageMaker;
import me.light.service.BoardService;

@Controller
@RequestMapping("/board")
public class BaordController {
	
	@Autowired
	BoardService service; 
	
	@GetMapping("/list")
	public String boardList(Model model, Criteria criteria) {
		PageMaker pageMaker = new PageMaker(criteria, service.totalCount(criteria)); 
		model.addAttribute("list",service.getList(criteria));
		model.addAttribute("pageMaker", pageMaker);
		return "board/list";
	}
	
	@GetMapping("/get")
	public String get(Long bno, Model model) {
		model.addAttribute("board",service.get(bno));
		return "board/get";
	}
	
	
	@GetMapping("/modify")
	public String modifyForm(Long bno, Model model) {
		model.addAttribute("board", service.get(bno));
		return "board/modify";
	}
	
	@PostMapping("/modify")
	public String modify(Board board, RedirectAttributes rttr) {
		service.modify(board);
		rttr.addFlashAttribute("message", board.getBno());
		return "redirect:list";
	}
	
	@PostMapping("/remove")
	public String modify(Long bno, RedirectAttributes rttr) {
		service.remove(bno);
		rttr.addFlashAttribute("message", bno);
		return "redirect:list";
	}
	
	@GetMapping("/register")
	public String registerForm() {
		return "board/register";
	}
	
	@PostMapping("/register")
	public String register(Board board, RedirectAttributes rttr) {
		service.register(board);
		System.out.println("****************************************");
		System.out.println(board.getBno());
		System.out.println("****************************************");
		System.out.println("****************************************");
		rttr.addFlashAttribute("message", board.getBno());
		return "redirect:list";
	}
	
	
}
