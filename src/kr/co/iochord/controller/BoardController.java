package kr.co.iochord.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//controller 역할하는 bean이다. 
@Controller
@RequestMapping("/board")
public class BoardController {
	
	@GetMapping("/main")
	public String main() {
		return "board/main"; //? 이유
	}
	@GetMapping("read")
	public String read() {
		return "board/read";
	}
	@GetMapping("write")
	public String write() {
		return "board/write";
	}
	@GetMapping("modify")
	public String modify() {
		return "board/modify";
	}
	@GetMapping("delete")
	public String delete() {
		return "board/delete";
	}
}
