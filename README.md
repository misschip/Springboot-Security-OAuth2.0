 #스프링 시큐리티  OAuth2

### MySQL DB 및 사용자 생성
```sql
create user 'cos'@'%' identified by 'cos1234';
GRANT ALL PRIVILEGES ON *.* TO 'cos'@'%';
create database security;
use security;
```

### Project Dependencies
 - Spring Boot DevTools
 - Lombok
 - Spring Data JPA
 - MySQL Driver
 - Spring Security
 - Spring Web
 


### SecurityConfig.java 권한 설정 방법

```java
//protected void configure(HttpSecurity http) 함수 내부에 권한 설정법 
     //.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    //.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
     .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
```

### 컨트롤러의 함수에 직접 설정 방법

//특정 주소 접급시 권한 및 인증을 위한 어노테이션 활성화 Securityconfig.java에설정 
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)

//컨트롤러에 어노테이션 거는법
    //@PostAuthorize("hasRole('ROLE_MANAGER')")
   //@PreAuthorize("hasRole('ROLE_MANAGER')")
   @Secured("ROLE_MANAGER")
   @GetMapping("/manager")
   public @ResponseBody String manager() 
      return "매니저 페이지 입니다.";
   
```