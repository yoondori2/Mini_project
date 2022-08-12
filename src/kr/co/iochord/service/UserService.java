package kr.co.iochord.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iochord.beans.UserBean;
import kr.co.iochord.dao.UserDao;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	//session에 담아주기 위해서 login session 빈 주입 받아오기
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	
	public boolean checkUserIdExist(String user_id) {
		
		String user_name = userDao.checkUserIdExist(user_id);
		//아무것도 못 받으면 사용할 수 있는 아이디라는 뜻이다. 
		
		if(user_name == null) {
			return true;
		}else {
			return false;
		}
	}
	//가입 정보 저장하기 
	public void addUserInfo(UserBean joinUserBean) {
		userDao.addUserInfo(joinUserBean);
	}
	
	//로그인 정보 가져오기 
	public void getLoginUserBean (UserBean tempLoginUserBean) {
		
		UserBean tempLoginUserBean2 = userDao.getLoginUserBean(tempLoginUserBean);
		
		if(tempLoginUserBean2 != null) {
			loginUserBean.setUser_idx(tempLoginUserBean2.getUser_idx());
			loginUserBean.setUser_name(tempLoginUserBean2.getUser_name());
			loginUserBean.setUserLogin(true);
		}
	}
	public void getModifyUserInfo (UserBean modifyUserBean) {
		UserBean tempModifyUserBean = userDao.getModifyUserInfo(loginUserBean.getUser_idx());
		
		modifyUserBean.setUser_id(tempModifyUserBean.getUser_id());
		modifyUserBean.setUser_name(tempModifyUserBean.getUser_name());
		modifyUserBean.setUser_idx(tempModifyUserBean.getUser_idx());
	}
	public void modifyUserInfo(UserBean modifyUserBean) {
		modifyUserBean.setUser_idx(loginUserBean.getUser_idx());
		
		userDao.modifyUserInfo(modifyUserBean);
	}
	
}
