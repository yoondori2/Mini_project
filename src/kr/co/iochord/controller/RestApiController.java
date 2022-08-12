package kr.co.iochord.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kr.co.iochord.service.UserService;

@RestController
public class RestApiController {
	
	@Autowired
	private UserService userService;

	//restApi 애서는 파라미터보다 path value로 더 많이 보내게 된다. 
	@GetMapping("user/checkUserIdExist/{user_id}")
	public String checkUserIdExist(@PathVariable String user_id) {
		
		boolean chk = userService.checkUserIdExist(user_id);
		
		//restcontroller의 method는 문자열이 아닌 다른걸 반환할때 잭슨 이용하여 jason으로 만들어 줘야한다. 
		//값 하나만 보낸다면 문자열로 보내는것이 가장 간단하다. 
		return chk + "";
	}
	
}
