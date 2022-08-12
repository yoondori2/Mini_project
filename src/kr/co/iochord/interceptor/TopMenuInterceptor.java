package kr.co.iochord.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.iochord.beans.BoardInfoBean;
import kr.co.iochord.beans.UserBean;
import kr.co.iochord.service.TopMenuService;

public class TopMenuInterceptor implements HandlerInterceptor{
	//주의 
	//인터셉터에서는 자동주입을 통해 빈을 주입 못받는다. 
	//인터셉터에서 사용할 객체는 이 객체를 인터셉터를 등록하는 쪽에서 반을 주입받은다음에 생성자로 넘겨받아야 한다. 
	//@Autowired
	private TopMenuService topMenuService;
	private UserBean loginUserBean;
	
	//객체 생성할때 객체 주소값을 받겠다라는 의미이다. 
	public TopMenuInterceptor(TopMenuService topMenuservice, UserBean loginUserBean) {
		this.topMenuService = topMenuservice;
		this.loginUserBean = loginUserBean;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		List<BoardInfoBean> topMenuList = topMenuService.getTopMenuList();
		request.setAttribute("topMenuList", topMenuList);
		request.setAttribute("loginUserBean", loginUserBean);
		return true;
	}
}
