package kr.co.iochord.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.iochord.beans.BoardInfoBean;
import kr.co.iochord.mapper.TopMenuMapper;

//같은 bean이지만 Dao를 담당하는 클래스임을 명시적으로 알려준다. 
@Repository
public class TopMenuDao {
	
	@Autowired
	private TopMenuMapper topMenuMapper;
	
	public List<BoardInfoBean> getTopMenuList(){
		List<BoardInfoBean> topMenuList = topMenuMapper.getTopMenuList();
		return topMenuList;
	}
}
