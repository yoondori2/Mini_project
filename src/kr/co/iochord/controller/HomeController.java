package kr.co.iochord.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.iochord.beans.UserBean;

@Controller
public class HomeController {
	
	
	//로그인 빈 필요할때는 이렇게 주입받아서 사용하면 된다. 이름으로 받을때는 @resource 를 사용한다.
	//주입되었는지 확인할 수 있다.
//	@Resource(name = "loginUserBean")
//	private UserBean loginUserBean;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "redirect:/main";
	}
}

