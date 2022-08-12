package kr.co.iochord.config;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.iochord.beans.UserBean;
import kr.co.iochord.interceptor.CheckLoginInterceptor;
import kr.co.iochord.interceptor.TopMenuInterceptor;
import kr.co.iochord.mapper.BoardMapper;
import kr.co.iochord.mapper.TopMenuMapper;
import kr.co.iochord.mapper.UserMapper;
import kr.co.iochord.service.TopMenuService;

// Spring MVC 프로젝트에 관련된 설정을 하는 클래스
@Configuration
// Controller 어노테이션이 셋팅되어 있는 클래스를 Controller로 등록한다.
@EnableWebMvc
// 스캔할 패키지를 지정한다.
@ComponentScan("kr.co.iochord.controller")
@ComponentScan("kr.co.iochord.dao")
@ComponentScan("kr.co.iochord.service")
//db.properties 의 파일 읽어오기 
@PropertySource("WEB-INF/properties/db.properties")
public class ServletAppContext implements WebMvcConfigurer{
	
	@Value("${db.classname}")
	private String db_classname;
	
	@Value("${db.url}")
	private String db_url;
	
	@Value("${db.username}")
	private String db_username;
	
	@Value("${db.password}")
	private String db_password;
	
	//topmenuservice 주입받기. 생성된 객체를 주입받는 것이기때문에 여러군데서 주입받아도 상관없다. 
	@Autowired
	private TopMenuService topmenuService;
	
	@Resource(name= "loginUserBean")
	private UserBean loginUserBean;
	
	// Controller의 메서드가 반환하는 jsp의 이름 앞뒤에 경로와 확장자를 붙혀주도록 설정한다.
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.configureViewResolvers(registry);
		registry.jsp("/WEB-INF/views/", ".jsp");
	}
	
	// 정적 파일의 경로를 매핑한다.
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/**").addResourceLocations("/resources/");
	}
	//데이터 베이스 접속 정보를 관리하는 Bean 
	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource source = new BasicDataSource();
		source.setDriverClassName(db_classname);
		source.setUrl(db_url);
		source.setUsername(db_username);
		source.setPassword(db_password);
		
		return source;
	}
	//쿼리문과 접속 정보를 관리하는 객체이다. 

	@Bean
	public SqlSessionFactory factory(BasicDataSource source) throws Exception{
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(source);
		SqlSessionFactory factory = factoryBean.getObject();
		return factory;
	}
	
	//쿼리문 실행을 위한 객체
	@Bean
	//factory에서 등록한 bean을 BoardMapper에서 주입받는 것이다.
	public MapperFactoryBean<BoardMapper> getBoardMapper(SqlSessionFactory factory) throws Exception{
		MapperFactoryBean<BoardMapper> factoryBean = new MapperFactoryBean<BoardMapper>(BoardMapper.class);
		factoryBean.setSqlSessionFactory(factory); 
		return factoryBean;
	}
	//Topmenu에서 bean을 주입받는 것이다. 
	@Bean
	public MapperFactoryBean<TopMenuMapper> getTopMenuMapper(SqlSessionFactory factory) throws Exception{
		MapperFactoryBean<TopMenuMapper> factoryBean = new MapperFactoryBean<TopMenuMapper>(TopMenuMapper.class);
		factoryBean.setSqlSessionFactory(factory); 
		return factoryBean;
	}
	//UserMapper에서 bean을 주입받는다. 
	@Bean
	public MapperFactoryBean<UserMapper> getUserMapper(SqlSessionFactory factory) throws Exception{
		MapperFactoryBean<UserMapper> factoryBean = new MapperFactoryBean<UserMapper>(UserMapper.class);
		factoryBean.setSqlSessionFactory(factory); 
		return factoryBean;
	}
	//interceptor로 등록하기 
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addInterceptors(registry);
		
		//top 메뉴에 등록하기 
		TopMenuInterceptor topMenuInterceptor = new TopMenuInterceptor(topmenuService,loginUserBean);
		
		InterceptorRegistration reg1 = registry.addInterceptor(topMenuInterceptor);
		//모든 요청 주소에 대해 인터셉터가 작동하게 된다. 
		reg1.addPathPatterns("/**");
		
		//로그인 여부 메뉴에 등록하기 
		CheckLoginInterceptor checkLoginInterceptor = new CheckLoginInterceptor(loginUserBean);
		
		InterceptorRegistration reg2 = registry.addInterceptor(checkLoginInterceptor);
		
		//정보 수정 눌렀을 때 interceptor 통과하도록 함. 
		reg2.addPathPatterns("/user/modify","/user/logout","/board/*");
		//main만 제외하겠다라는 의미
		reg2.excludePathPatterns("/board/main");
		
	}
	//message로 등록하면 db에 저장한 properties 읽어오지 못해서 오류남. 
	//별도로 관리하게끔 해준다.
	//error_message 와 db 메세지 따로 관리가 된다. 
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcePlaceHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource res = new ReloadableResourceBundleMessageSource();
		res.addBasenames("/WEB-INF/properties/error_message");
		return res;
	}
}










