# 게시판 만드는 과정 정리 
## Spring 관련 설정하기 
### 1. pom.xml 설정하기 
- 필요한 framework 다운받기 위해서 설정.
1. Maven project 관련된 세팅 하기
2. 라이브러리 설정하기(servlet-api, jsp-api, jsti,springWebMVC)
## SpringMVC Java로 세팅하기 
### 1. 테이블 만들기 
1. 게시판 테이블, 유저 테이블 만들기
2. 테이블과 동일하게 Beans 클래스 만들기 
### 2. Mybatis 설정하기 
- mybatis 관련 라이브러리는 다 설치했으므로 이제 설정만 해주면 된다. 
1. properties
- db.properties 파일을 만들고 classname, url,username,password 를 설정해준다. 
2. ServiceAppContext
- @PropertySource 어노테이션에 db.properties의 위치를 넣어주면, 프로퍼티 값이 자동으로 주입된다.
- @value 를 활용하여 변수에 프로퍼티 값 세팅하기. 
3. BasicDataSource 정의하기 
- BasicDataSource : 데이터 베이스 접속정보를 관리하고 있는 객체
4. SqlSessionFactory 정의하기
- SqlSessionFactory 는 접속, 쿼리관리등을 하고있는 객체 
- 나중에 쿼리를 실행할때 이것을 주입받아서 실행하게 된다. 
5. Mapper bean 정의하기
- 쿼리문을 만드는 Mapper를 정의한다. 
- 이 mapper를 주입받아 쿼리를 실행하게 된다. 
- 정의하기 전에 사용할 mapper(ex:userMapper, boardMapper 등)을 인터페이스로 구현한 후 이 타입으로 정의해준다. 
6. Mapper 주입
- 쿼리를 동작시켜야 하는 곳에서 Mapper를 주입받는다. (@Autowired 사용하여)
7. 쿼리 실행 
- 필요한 쿼리를 Mapper를 통해 실행한다. (ex: mapper.insert(data);) 


## Top menu 구성하기 
1. 상단 메뉴 중 게시판 메뉴를 구성한다. 
2. 데이터베이스에서 게시판 이름들을 가져와 메뉴를 구성한다. 
3. 상단 메뉴는 모든 요청에 대해 처리해야 하는 부분이므로 Interceptor에서 처리한다.

<details>
<summary>Interceptor란</summary>
<div markdown="1">
  - 확인하여 Controller의 메서드를 호출 하기 전이나 후에 다른 메서드를 호출 할 수 있도록 가로 채 가는 개념이다.</br>
- 로그인 여부 확인, 등급별 서비스 사용 권한 확인 등의 작업을 처리할 때 많이 사용합니다.</br>
- 여기선 controller 실행되기 전에 수행되야 하므로 prehandle를 사용할 것이다.
- preHandle : Controller의 메서드가 호출되기 전 호출된다. 이 메서드가 false를 반환하면 코드의 흐름이 중단된다.

</div>
</details>

### 과정 
- 테이블에 맞게 beans 만들어 놓았기 때문에 mapper bean정의해서 주입, 쿼리실행까지 진행하면 된다. 
#### 1. TopMenuMapper 를 만들어 쿼리문을 작성한다. 
#### 2. ServletContext에 Mapper를 Bean으로 등록한다. (쿼리문 실행을 위해)
#### 3. 데이터베이스 접속해서 실제 데이터 처리하기 위해 dao 클래스를 만든다. 
1. dao역할을 하는 bean이므로 @Repository 를 적어준다.
2. @Autowired 사용하여 TopMenuMapper를 주입받아온다. 
3. ServletContext에 dao 패키지를 스캔하기 위해 @ComponentScan에 dao 패키지를 지정해준다. 
#### 4. controller에서 메소드를 호출하기 위해 Service 클래스를 만든다.
- Service: dao에서 메소드를 호출해서 필요한 처리를 해준다. 
1. @Service 달아주기 
2. @Autowired 사용하여 TopMenuDao 를 주입받아온다. 
3. ServletContext에 service 패키지를 스캔하기 위해 @ComponentScan에 service 패키지를 지정해준다. 
#### 5. Interceptor 에서 처리한다. 
1. Interceptor 클래스 만들어주기 (HandlerInterceptor 인터페이스를 구현한다)
2. ServletAppContext 에서 Interceptor 등록하기
 - 모든 요청에 대해서 Interceptor 를 거치게 하기 위해 /**로 적어준다. 
3. Interceptor 에서 service 가져오기
   1) 생성자로 객체 데려오기 
   2) prehandle() override해서 topMenuService 사용하여 쿼리문 실행하고 
    요청값에 topMenulist를 담아준다.
    
 <details>
<summary>왜 @Autowired로 주입을 받지 않는가?</summary>
  
<div markdown="1">
- 인터셉터에서는 자동주입을 통해 빈을 주입 받지 못한다. 그러므로 인터셉터에서 사용할 객체는 이 객체를 인터셉터에서 등록하는 쪽에서 빈을 주입받은 다음에 생성자로 넘겨 받아야 한다. 
- 생성자로 받으면 객체 생성할 때 객체 주소값을 받겠다라는 의미이다. 
</div>
</details>

4. top_menu.jsp 에서 request에 담아둔 topMenuList 를 forEach문을 이용하여 
  하나씩 게시판 이름을 담아준다. 


## 회원가입

### 유효성 검사 처리
- 기본제공 유효성 검사를 활용하되 추가적인 부분은 validator 을 사용한다.
- 비밀번호와 비밀번호 확인하는 부분은 기본제공 유효성 검사로 해결이 안되기 때문에 validator라는 클래스를 따로 만들어서 
  처리해주도록 한다. (오류 메세지는 properties 에 적도록 하자)
#### 과정 
1. join.jsp 에서 form 태그들 spring에서 제공하는 form태그로 다 바꿔주기

  <details>
  <summary>왜 spring 에서 제공하는 form태그로 바꿔야 하나?</summary>
  <div markdown="1">
  - Form 커스텀 태그를 활용하면 Model 객체에 들어있는 값을 form 요소에 주입 시킬 수 있다.
  - 회원 정보 수정 등 정보 수정페이지를 구성할 때 편하게 사용이 가능하다.
  </div>
  </details>

2. form태그에서 modelAttribute를 joinUserBean으로 등록하고 action에 user/join_pro로 가도록 적어준다. 
3.1 아이디, 이름 , 비밀번호 유효성 검사하기 
   1) UserController의 join메소드
     - UserBean을 여기서 주입받아서 ModelAttribute로 세팅해준다. 
     - join (@ModelAttribute("joinUserBean") UserBean joinUserBean )
    
   2) JSR-303 규격의 유효성 검사 라이브러리를 사용하여 유효성 검사를 한다. 
   
      (1)Bean에 어노테이션으로 설정하면 된다. 
       - 길이는 2~10이여 한다 --> @Size(min= 2, max = 10)
       - 정규식: 한글만 입력해야 하는 경우 -> @Pattern(regexp = "[가-힣]*")
   3) UserController에 @PostMapping("/join_pro")
   
     - 주입 받는 Bean에 @Valid를 설정하면 유효성 검사를 실시한다. 
     - 또한 유효성 검사 결과를 사용하고자 한다면 BindingResult 객체를 주입받아야          한다.(이 객체에 결과가 담기게 된다)
     - join_pro(@Valid @ModelAttribute("joinUserBean") UserBean joinUserBean,        BindingResult result) 
     - 분기처리하여 errors 없으면 join_success로 이동하기.
   4) error 메세지를 직접 설정해준다 
   
      <img width="449"  alt="유효성검사 메세지" src="https://user-images.githubusercontent.com/91296293/184537457-09ee47ab-0259-42c3-8ade-4ae8f89afda8.PNG">
   
      (1) error_message.properties 를 생성한다.</br>
      (2) 위의 메세지를 그대로 적어준다.
      (3) ServletAppContext 에 properties 를 등록해준다. 
      
      <img width="638" alt="에러메세지 등록" src="https://user-images.githubusercontent.com/91296293/184537580-ab11c3b8-92b5-480a-9c10-f44380084230.PNG">

3.2 비밀번호랑 비밀번호 확인 값 같은지 유효성 검사하기 
- 기본 제공 유효성 검사로 해결이 안되기 때문에 validator를 사용하여 처리하게 된다. 
   1) validator 인터페이스를 구현하여 UserValidator를 만든다. 
   2) 구현할 메소드들 override 하기 
   3) supports() 
      - 유효성 검사할 데이터를 가지고 있는 객체가 유효성 검사가 가능한지 확인한다.
      - return UserBean.class.isAssignableFrom(clazz);
      
   6) validate(Object target, Errors errors) </br>
   
       (1) validator 만들기 
      - target 을 UserBean타입으로 형변환하기 
      - if문 사용해서 errors.rejectValue에 셋팅하기(유효성 조건을 직접 만들어 검사할 때 사용한다.)
      - rejectValue( “프로퍼티이름“, “코드이름“)
      - user_pw와 user_pw2 같지 않을 경우 errors.rejectValue("user_pw","NotEquals"),errors.rejectValue("user_pw2","NotEquals") 담아주기
      - error_message 에 코드이름.bean객체이름.코드이름으로 적어준다.
      - <img width="389" alt="validator" src="https://user-images.githubusercontent.com/91296293/184538209-9a95750d-1164-4c06-8e3f-a11bf658613e.PNG">
       
       (2) UserController에 validator를 등록한다.
       - ```java
         @InitBinder protected void initBinder(WebDataBinder binder) { DataBeanValidator validator1 = new DataBeanValidator(); binder.setValidator(validator1);        }
         ```

    
### 아이디 중복 처리
- 데이터를 처리하는 경우에는 RestController를 사용한다. 
<details>
<summary>왜 RestController를 사용하는가?</summary>
<div markdown="1">
- Spring MVC에서 Controller를 구성할 때 @Controller를 사용하면 return 하는 값은 사용할 JSP를 지정하게 된다.
  - @RestController를 통해 return하게 되면 그 값 자체를 브라우저로 전달하는 응답결과로 생성해서 보낸다.</br>
** view 반환이 아니라 데이터를 반환해야 하는 경우에는 RestController를 쓰는 것이 좋다.  
</div>
</details>
- 클라이언트에서는 Ajax 통신을 사용한다. </br>

#### 과정 

1. UserMapper 만들어서 쿼리문 만들기 
2. ServletAppContext 에 UserMapper bean으로 만들어주기. 
3. UserDao에서 @Autowired로 mapper를 주입받는다.
4. UserService에서 @Autowired 로 Dao 주입받아오기.</br>
  - boolean타입으로 true false 값 받아오기
5. RestController 만들기 
  - Service를 @Autowired 로 주입받기 
  - ** restApi의 경우 클라이언트가 파라미터보다는 pathValue 로 값을 많이 보낸다
  - @GetMapping("pathvalue")
  
   <img width="572" alt="RestController" src="https://user-images.githubusercontent.com/91296293/184539209-0950444e-e227-49e6-abb4-3a9c61c52d63.PNG">

6. join.jsp에서 중복확인 누르면 메소드 호출하기</br>

    1) onclick=checkUserIdExist()</br>
    2) checkUserIdExist()</br>
       - ajax로 처리하기 (url, type, dataType, success 적어주기)</br>
       - 유효성 검사를 실시 했는지 여부를 form 태그안에 담아주기</br>
       - <form:hidden path="userIdExist" /> </br>
    3) UserBean에 boolean타입으로 userIdExist 를 만들어준다.  </br> 
       - 이때 기본값으로 생성자를 이용하여 false를 세팅해준다. </br>
       - setter,getter 메소드 만들기 </br>
    4) validator에 userIdExist 가 false이면 위의 유효성검사에서 했듯이 에러메세지를 등록해준다. </br>
        - UserValidator, erros_message.properties</br>
    5) 중복확인할때 다시 아이디 입력하면 값이 true로 되므로 이것을 막기위해 join.jsp에 input태그 user_id 에 onkeyup으로 눌렀다 떼면 저절로 값 false로 바뀌게 만들어주기 </br>
    
       
### 입력한 내용 저장하기 
#### 과정

1. UserMapper에 쿼리문 만들기 (@Insert) 
2. UserDao에서 Mapper @Autowired로 주입받기
3. UserService에서 Dao @Autowired로 주입받기
4. UserController에서 Service주입받아서 쿼리실행하기.
5. join_success.jsp 로 이동 (alert창으로 가입완료 띄워주고 login.jsp로 이동하기)
---
## 로그인
### 로그인 처리
- 사용자 로그인 기능을 구현한다.
- 아이디, 비밀번호 유효성 검사를 시행한다. 
- 로그인 성공시 사용자 정보를 세션에 저장한다. 

### 과정
1. 
---
## 로그아웃 
- session에 로그인여부 값을 false로 변경해준다. 
---
## 로그인 확인 처리
- 직접 주소치면 들어갈 수 있기때문에 이 부분을 해결해야하기 위한 처리이다,. 
- 로그인을 하지 않으면 접근을 하지 못하도록 하는 처리이다.</br>
-> Interceptor에서 로그인 여부를 확인하고 로그인 하였을 경우에만 다음 단계 진행하도록 한다.
---
## 정보 수정 처리 
- 로그인한 사용자의 정보 수정하는 기능 처리하기.
