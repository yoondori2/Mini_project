package kr.co.iochord.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.iochord.beans.UserBean;
import kr.co.iochord.service.UserService;
import kr.co.iochord.validator.UserValidator;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;

	//@RequestParam은 실패했을때 실패했다는 창뜨게 하기 위해 실패한 결과를 갖고 가야한다. 그러기 위해선 실패했다는 정보를
	//파라미터로 받아야 함. 파라미터로 받고 model에 담아서 전달한다. 
	@GetMapping("/login")
	public String login(@ModelAttribute("tempLoginUserBean") UserBean tempLoginUserBean,
			@RequestParam(value = "fail",defaultValue = "false" )boolean fail,Model model) {
		model.addAttribute("fail",fail);
		return "user/login";
	}

	@PostMapping("/login_pro")
	public String login_pro(@Valid @ModelAttribute("tempLoginUserBean") UserBean tempLoginUserBean, BindingResult result) {
		if(result.hasErrors()) {
			return "user/login";
		}
		
		
		userService.getLoginUserBean(tempLoginUserBean);
		
		//로그인이 되어있다면 
		if(loginUserBean.isUserLogin() == true) {
			return "user/login_success";
		}else {
			return "user/login_fail";
		}
	}

	@GetMapping("/join")
	public String join(@ModelAttribute("joinUserBean") UserBean joinUserBean) {
		return "user/join";
	}

	// @valid - 유효성 검사 joinUserBean가져오고 결과값도 가져오기
	@PostMapping("/join_pro")
	public String join_pro(@Valid @ModelAttribute("joinUserBean") UserBean joinUserBean, BindingResult result) {
		// 유효성검사에 문제가 있다면 join페이지로 다시 이동
		if (result.hasErrors()) {
			return "user/join";
		}
		userService.addUserInfo(joinUserBean);

		return "user/join_success";
	}

	@GetMapping("/modify")
	public String modify(@ModelAttribute("modifyUserBean") UserBean modifyUserBean) {
		userService.getModifyUserInfo(modifyUserBean);
		return "user/modify";
	}
	@PostMapping("/modify_pro")
	public String modify_pro (@Valid @ModelAttribute("modifyUserBean") UserBean modifyUserBean, BindingResult result) {
		if(result.hasErrors()) {
			return "user/modify";
		}
		
		userService.modifyUserInfo(modifyUserBean);
		
		return "user/modify_success";
	}


	@GetMapping("/logout")
	public String logout() {
		loginUserBean.setUserLogin(false);
		return "user/logout";
	}
	@GetMapping("/not_login")
	public String not_login() {
		return "user/not_login";
	}

	// 비밀번호와 비밀번호 확인에 적은 숫자가 동일한지 확인해준다.
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		UserValidator validator1 = new UserValidator();
		binder.addValidators(validator1);
	}
}
