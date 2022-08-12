package kr.co.iochord.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import kr.co.iochord.beans.UserBean;

// 프로젝트 작업시 사용할 bean을 정의하는 클래스
//데이터를 관리하는 목적으로 쓸때 쓰는 클래스 
@Configuration
public class RootAppContext {
	
	//userBean에서 따로 component 로 저장해도되지만 다양한 목적으로 쓰이기 때문에  이곳에 만들어둔다.  
	@Bean("loginUserBean")
	@SessionScope
	public UserBean loginUserBean() {
		return new UserBean();
	}
}
