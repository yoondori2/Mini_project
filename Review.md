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

[2.Session 과 Cookie의 차이는?](#Session-과-Cookie의-차이)

[3.SessionScope와 ApplicationScope의 차이는?](#SessionScope와-ApplicationScope의-차이)

---

## Interceptor와 Filter의 차이
## Session과 Cookie의 차이
## SessionScope와 ApplicationScope의 차이 
