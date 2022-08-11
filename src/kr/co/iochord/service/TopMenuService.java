package kr.co.iochord.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iochord.beans.BoardInfoBean;
import kr.co.iochord.dao.TopMenuDao;

//dao 메소드 호출해서 필요한 처리를 해주는 곳 
@Service
public class TopMenuService {
	
	@Autowired
	private TopMenuDao topMenuDao;
	
	public List<BoardInfoBean> getTopMenuList(){
		List<BoardInfoBean> topMenuList = topMenuDao.getTopMenuList();
		return topMenuList;
	}
}
