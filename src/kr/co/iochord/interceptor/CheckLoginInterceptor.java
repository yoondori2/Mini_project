package kr.co.iochord.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.iochord.beans.UserBean;

public class CheckLoginInterceptor implements HandlerInterceptor {
	
	private UserBean loginUserBean;
	
	public CheckLoginInterceptor(UserBean loginUserBean) {
		this.loginUserBean = loginUserBean;
	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//로그인을 안했을 경우 저 주소로 요청해!라는 의미이다. 
		//false하게 되면 다음단계로 이동하지않고 여기서 끝나게 된다. 
		//true면 다음단계인 controller로 이동할 수 있게끔 했다. 
		if(loginUserBean.isUserLogin() == false) {
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/user/not_login");
			return false;
		}
		return true;
		
	}
}
