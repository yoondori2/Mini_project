package kr.co.iochord.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.co.iochord.beans.UserBean;

//
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return UserBean.class.isAssignableFrom(clazz);
	}

	// UserBean타입의 validator를 만들었기때문에 다 여기서 통과하게 됨.
	// but 로그인버튼 누르면 오류가 생김 validator를 얘또한 반드시 통과하기 때문에 밑에 isUserIdExist 부분에서 오류가
	// 난다.
	@Override
	public void validate(Object target, Errors errors) {
		UserBean userBean = (UserBean) target;

		String beanName = errors.getObjectName();
		//System.out.println(beanName);// 로그인 누르면 tempLoginUserBean이 됨.
		// 이름으로 분기처리하면 위의 오류 해결할 수 있다.
		//id체크는 로그인할때 하는것이므로 분기처리를 추가로 해준다.
		if (beanName.equals("joinUserBean") || beanName.equals("modifyUserBean")) {

			if (userBean.getUser_pw().equals(userBean.getUser_pw2()) == false) {
				errors.rejectValue("user_pw", "NotEquals");
				errors.rejectValue("user_pw2", "NotEquals");
			}
		}
			
			if(beanName.equals("joinUserBean")) {
				
				// boolean타입은 get대신 is가 붙는다.
				// 아이디 유효성 검사
				if (userBean.isUserIdExist() == false) {
					errors.rejectValue("user_id", "DontCheckUserIdExist");
				}
			}
		

	}

}
