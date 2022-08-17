# 리뷰 

## 1. 보완할 점 
1. SpringMVC를 Mybatis를 사용해서 개발할꺼면 왜 Java로 세팅을 하는가? </br>
-> jar파일로 바꾸면 Java는 파일을 읽을 수가 없다. Mybatis 를 쓸거면 xml로 설정해주는것이 좋다.
2. Java에서는 Bean을 만들때 언더바보다 CamelCase를 쓰는것이 좋다.(자바가 Camel로 설정되어있으므로)
3. 패스워드는 특수문자까지 넣게 유효성 검사를 진행해야 한다. 
4. 패스워드 처리할때 암호화를 해서 넣어야 한다. (DB의 기본)
5. error_message 처리할때 다국어처리까지 들어가야 한다. (영문인지 국문인지에 따라 나오는 메세지도 다르게 바꿔주어야 한다) 

## 2. Q&A

[1. Interceptor와 Filter의 차이는?](#Interceptor와-Filter의-차이) 

[2.Session 과 Cookie의 차이는?](#Session과-Cookie의-차이)

[3.SessionScope와 ApplicationScope의 차이는?](#SessionScope와-ApplicationScope의-차이)

[4.@Autowired 동작하는 원리](#@Autowired)

---

## Interceptor와 Filter의 차이
- 공통 프로세스 고민에서부터 나온게 Interceptor, Filter,AOP이다.
- 스프링에서 사용되는 Filter, Interceptor, AOP 세 가지 기능은 모두 무슨 행동을 하기전에 먼저 실행하거나, 실행한 후에 추가적인 행동을 할 때 사용되는 기능들이다.

<img width="624" alt="interceptor,filter,aop차이" src="https://user-images.githubusercontent.com/91296293/184882707-8a71c803-63ff-421d-8318-c08ecba40bb5.PNG">

- Interceptor와 Filter는 Servlet 단위에서 실행된다. <> 반면 AOP는 메소드 앞에 Proxy패턴의 형태로 실행된다.
- 실행순서를 보면 Filter가 가장 밖에 있고 그안에 Interceptor, 그안에 AOP가 있는 형태이다.

### 1. Filter
- 말그대로 요청과 응답을 거른뒤 정제하는 역할을 한다.서블릿 필터는 DispatcherServlet 이전에 실행이 되는데 필터가 동작하도록 지정된 자원의 앞단에서 요청내용을 변경하거나,  여러가지 체크를 수행할 수 있다.또한 자원의 처리가 끝난 후 응답내용에 대해서도 변경하는 처리를 할 수가 있다.
- 보통 web.xml에 등록하고, 일반적으로 인코딩 변환 처리, XSS방어 등의 요청에 대한 처리로 사용된다.

### 2. Interceptor 
- 요청에 대한 작업 전/후로 가로챈다고 보면 된다.필터는 스프링 컨텍스트 외부에 존재하여 스프링과 무관한 자원에 대해 동작한다. 하지만 인터셉터는 스프링의 DistpatcherServlet이 컨트롤러를 호출하기 전, 후로 끼어들기 때문에 스프링 컨텍스트(Context, 영역) 내부에서 Controller(Handler)에 관한 요청과 응답에 대해 처리한다.
-인터셉터는 여러 개를 사용할 수 있고 로그인 체크, 권한체크, 프로그램 실행시간 계산작업 로그확인 등의 업무처리를 한다. 

### 3. AOP
- OOP를 보완하기 위해 나온 개념 객체 지향의 프로그래밍을 했을 때 중복을 줄일 수 없는 부분을 줄이기 위해 종단면(관점)에서 바라보고 처리한다.주로 '로깅', '트랜잭션', '에러 처리'등 비즈니스단의 메서드에서 조금 더 세밀하게 조정하고 싶을 때 사용한다.

### 4. Filter, Interceptor, Aop의 차이 

- Aop는 Interceptor나 Filter와는 달리 메소드 전후의 지점에 자유롭게 설정이 가능하다.
- Interceptor와 Filter는 주소로 대상을 구분해서 걸러내야하는 반면, AOP는 주소, 파라미터, 애노테이션 등 다양한 방법으로 대상을 지정할 수 있다.
#### Aop vs Interceptor
- AOP의 Advice와 HandlerInterceptor의 가장 큰 차이는 파라미터의 차이다.
- Advice의 경우 JoinPoint나 ProceedingJoinPoint 등을 활용해서 호출한다.반면 HandlerInterceptor는 Filter와 유사하게 HttpServletRequest, HttpServletResponse를 파라미터로 사용한다.

## Session과 Cookie의 차이

## Cookie & Session
### 1.Cookie
- 웹 브라우저가 보관하고 있는 데이터로, 웹 서버에 요청을 보낼 때 쿠키를 헤더에 담아서 전송한다. 
- 웹 사이트에 요청을 전송할 때 서버에 의해 사용자의 컴퓨터에 저장되는 정보를 의미한다.

#### <과정> 

1.사용자가 접속한다. 

2.쿠키허락할것인지 물어본다. 

= 너 컴퓨터 메모리 써도 되니?! 

3.사용자가 접속과 동시에 요청한다. 

 요청할때 많은 정보를 다 담고있다(접속위치 등) request 에 담고있다. 그때 전송이될때  request의 header가 같이 날라간다. 그래서 header날라갈때 cookie도 함께 전송된다. 여기에 자신의 정보를 저장해두는데 만약 그 정보가 있을경우 응용해서 자기들의 기능을 보여주게된다.
 
#### Cookie 장단점
- 장점: 클라이언트의 폴더에 정보가 저장되기 때문에 서버의 부하를 줄일 수 있다. 
- 단점: 보안의 위협을 받을 수 있고 "쿠키차단"시 무용지물이 된다.  

### 2. Session
- 일정 시간동안 같은 사용자(브라우저)로 부터 들어오는 일련의 요구를 하나의 상태로 보고 그 상태를 일정하게 유지시키는 기술이다. 
- 방문자의 요청에 따른 정보를 웹서버가 세션 아이디 파일을 만들어 서비스가 돌아가고 있는 서버에 저장하는 것이다. 

#### <과정>
1. 클라이언트가 서버로 접속을 시도한다.
2. 서버는 접근한 클라이언트의 request-header field인 쿠키를 확인해 클라이언트가 해당 session-id를 보내왔는지 확인한다.
3. 만약 클라이언트로부터 발송된 session-id가 없다면, 서버는 session-id를 생성해 클라이언트에게 response-header field인 set cookie 값으로 session-id를 발행한다.

#### Session 장단점 
- 장점
 - 각 클라이언트들에게 고유 ID를 부여한다. 
 - 세션 ID로 클라이언트를 구분해서 클라이언트의 요구에 맞는 서비스를 제공할 수 있다. 
- 단점
 - 서버에 저장되기 때문에 서버에 처리를 요구하는 부하와 저장 공간을 필요로 한다. 
        

|          |                        Cookie                        |     Session      |
| :------: | :--------------------------------------------------: | :--------------: |
| 저장위치 |                        Client                        |      Server      |
| 저장형식 |                         Text                         |      Object      |
| 만료시점 | 쿠키 저장시 설정<br />(설정 없으면 브라우저 종료 시) | 정확한 시점 모름 |
|  리소스  |                 클라이언트의 리소스                  |  서버의 리소스   |
| 용량제한 |           한 도메인 당 20개, 한 쿠키당 4KB           |     제한없음     |



#### 저장 위치

- 쿠키 : 클라이언트의 웹 브라우저가 지정하는 메모리 or 하드디스크
- 세션 : 서버의 메모리에 저장



#### 만료 시점

- 쿠키 : 저장할 때 expires 속성을 정의해 무효화시키면 삭제될 날짜 정할 수 있음
- 세션 : 클라이언트가 로그아웃하거나, 설정 시간동안 반응이 없으면 무효화 되기 때문에 정확한 시점 알 수 없음

#### 리소스

- 쿠키 : 클라이언트에 저장되고 클라이언트의 메모리를 사용하기 때문에 서버 자원 사용하지 않음
- 세션 : 세션은 서버에 저장되고, 서버 메모리로 로딩 되기 때문에 세션이 생길 때마다 리소스를 차지함

#### 용량 제한

- 쿠키 : 클라이언트도 모르게 접속되는 사이트에 의하여 설정될 수 있기 때문에 쿠키로 인해 문제가 발생하는 걸 막고자 한 도메인당 20개, 하나의 쿠키 당 4KB로 제한해 둠
- 세션 : 클라이언트가 접속하면 서버에 의해 생성되므로 개수나 용량 제한 없음



## SessionScope와 ApplicationScope의 차이 
### SessionScope
- Session 은 브라우저가 최초로 서버에 요청을 하게 되면 브라우저당 하나씩 메모리 공간을 서버에서 할당하게 된다.
- 이 메모리 영역은 브라우저당 하나씩 지정되며 요청이 새롭게 발생하더라도 같은 메모리 공간을 사용하게 된다.
- 이 영역은 **브라우저가 종료**될 때 까지 서버에서 사용할 수 있다.
-sessionScope 에서 session 영역에 저장되어 있는 데이터를 사용할 수 있다.

### ApplicationScope
- **서버가 가동될 때부터 서버가 종료되는 시점까지의 범위**를 Application Scope라고 부른다.
- Application Scope 동안 사용할 수 있는 메모리 영역이 만들어지며 Servlet Context 라는 클래스 타입의 객체로 관리됨
- ServletContext에 저장된 데이터나 객체는 서버가 종료되기 전까지 서버는 웹브라우저에 관계없이 동일한 메모리 공간을 사용하게 된다.

## @Autowired

### @Autowired란? 
- @Autowired는 의존성을 "타입"을 통해 찾아 주입해주는 역할을 해준다
### 동작원리
- @Autowired 애노테이션은 BeanPostProcessor라는 라이프 사이클 인터페이스의 구현체인 AutowiredAnnotationBeanPostProcessor에 의해 의존성 주입이 이루어진다.
- BeanPostProcessor는 빈의 initializing(초기화) 라이프 사이클 이전, 이후에 필요한 부가 작업을 할 수 있는 라이프 사이클 콜백이다.
- 그리고 BeanPostProcessor의 구현체인 AutowiredAnnotationBeanPostProcessor가 빈의 초기화 라이프 사이클 이전, 즉 빈이 생성되기 전에 @Autowired가 붙어있으면 해당하는 빈을 찾아서 주입해주는 작업을 하는 것이다.
- AutowiredAnnotationBeanPostProcessor는 하나의 빈으로써 spring IoC 컨테이너에 등록되어 있다.

- **BeanFactory(ApplicationContext)는 BeanPostProcessor 타입의 빈 = AutowiredAnnotationBeanPostProcessor 빈을 꺼내 일반적인 빈들 = @Autowired로 의존성 주입이 필요한 빈들에게 @Autowired를 처리하는 로직을 적용한다.**

#### 같은 타입의 Bean이 여러개일때 @Autowired를 사용한다면?
- 만약 동일한 interface를 구현한 여러개의 클래스가 있고 해당 인터페이스에 @Autowired를 사용하면 어떻게 될까? 
- 그 클래스들중에 어떤것을 골라야할지 몰라서 **오류가 난다.** (->그래서 mapper를 interface로 구현한것에 대해 질문을 하셨을것이라 생각한다. xml에 적어주는것이 좋다.)</br>

<details>
<summary>꼭 쓰고 싶을 경우 3가지 해결방법</summary>
<div markdown="1">
  
1. @Primary 를 사용한다. 
- 주입받을 객체가 있는 곳에 이 어노테이션을 써주면 된다. 
- 이렇게 붙여주면 해당 bean을 주입하게 된다. 
  
2 여러개의 bean을 리스트로 다 받으면 된다. 
- ex) List<BookRepository> bookRepository;
  
3. @Qualifier("bean id")를 사용하는 방법이다. 
- @Autowired와 함께 @qualifier도 같이 적어주면 된다.
  
</div>
</details>

---
출처:
https://sejinik.tistory.com/252
https://stupidsecurity.tistory.com/7
https://bepoz-study-diary.tistory.com/38
https://goddaehee.tistory.com/154
https://atoz-develop.tistory.com/entry/Spring-Autowired-%EB%8F%99%EC%9E%91-%EC%9B%90%EB%A6%AC-BeanPostProcessor
