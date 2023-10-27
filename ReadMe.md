# JPA

- 객체와 관계형 데이터베이스의 패러다임 불일치를 해결하기 위한 기술
- 자바 Object <-> ORM(Object-Relational-Mapping) <-> RDB
- 대표적인 구현체 : Hibernate
- Jakarta(Java) Persistance(영속성) API
- JPA 사용시 장점
  - 특정 데이터베이스에 종속되지 않음
  - 데이터베이스 설계 -> 객체지향적 설계
  - 유지보수, 재사용성, 생산성 증가
- 단점
  - 쿼리가 복잡하다.
    - 쿼리 메소드, Native Query, JPQL, QueryDsl
  - 성능 저하
  - 자동 생성 쿼리 (의도하지 않은 작동)
  - 학습 시간, 학습 곡선이 굉장히 가파르다.

- 영속성 컨텍스트
  - 앱과 DB 사이에 중간 계층
    - 사용 시 장점
      - 캐시
      - 동일성 보장
      - 트랜잭션
      - 변경 감지

- Spring Boot Data JPA
- Entity
  - 테이블에 대응하는 클래스
- 엔티티 매니저 (EntityManager)
  - 앱이 실행되면, 엔티티 매니저 팩토리 -> Entity Manager 생성
  - 영속성 컨택스트에 접근해서 데이터베이스 작업을 제공
- 엔티티 생명주기
  - new
    - 영속성 컨텍스트와 관련없는 새로 생긴 상태
  - managed
    - 영속성 컨텍스트에서 관리되는 상태
  - detached
    - 영속성 컨텍스트에서 분리된 상태
  - removed
    - 영속성 컨텍스트에서 삭제된 상태
- 엔티티 매니저 메소드
  - find()
    - 영속성 컨텍스트에서 엔티티를 검색
  - persist()
    - 저장
  - remove()
    - 삭제
  - flush()
    - 저장된 내용을 데이터베이스에 반영
# 쿼리 메소드

## JPQL @Query
- JPQL은 SQL과 유사, 검색대상 엔티티 (DB가 아님)
- 문법 유사 -> 결국은 SQL로 변환된다.
- 특징 : 특정 공급업체에 종속적이지 않고 추상화 되어 있다.
- 별칭(alias) 필수
- SQL 키워드는 대소문자 구분하지 않는다.
- 엔티티, 필드는 대소문자 구분한다.

## Querydsl
- @Query 애노테이션 사용시 단점
  - 개발자가 문자열을 잘못 입력하면 컴파일 시점에 에러를 잡을 수 없다.
    - 실행해보기 전에는 에러를 알 수 없다.
- 장점
  - 고정된 SQL문이 아니라, 동적인 쿼리 생성 가능.
  - 비슷한 쿼리 재사용 가능, 조립 가능, 가독성 향상.
  - 자바 소스코드로 작성하여, 컴파일 시점 오류 발견
  - IDE의 자동완성 기능을 이용할 수 있음

# Thymeleaf
- 동적 뷰 템플릿
- 서버 사이드 렌더링 SSR
  - 백엔드 서버에서 HTML을 동적으로 렌더링 
  - 완성된 화면을 전달함
- Natural Template
  - 순수 HTML을 최대한 유지하는 특징
  - 웹 브라우저에서 파일을 직접 열어도 내용 확인 가능
- 스프링 통합
  - 스프링의 다양한 기능을 사용할 수 있게 통합 지원
- 기본 문법
  - ${...} : 변수 표현식
  - *{...} : 선택 변수 표현식
  - #{...} : 메세지 표현식
  - @{...} : 링크 URL 표현식
  - ~{...} : 조각(fragment) 표현식
- XML 네임스페이스 사용
  - `<html xmlns:th="http://www.thymeleaf.org">`
  - th:text
  - th:utext : 특수문자 등 태그를 입력하고 싶을 때(이스케이프 문자)
  - th:each : 반복문
  - th:if : 조건문
  - th:object : 폼 태그에서 스프링과 바인딩 될 도메인 객체
  - th:field : 인풋 태그에서 바인딩된 객체의 입력 필드
    - id, name, value 속성의 설정이 간편하게 진행됨.
  - 리터럴 대체 : "|hello ${user.name}|"

# Spring Security
- 인증 Authentication
  - 적합한 사용자인가?
  - 사용자의 신원을 확인하는 과정(이름, 암호, 생체인식, 토큰)
  - 로그인 자격 증명 -> 검증 -> 인증
- 인가 Authorization
  - 적합한 권한을 가지고 있는가?
  - 특정 자원에 대한 권한, 리소스 접근 권한 확인하는 과정

- Spring MVC 흐름
  1. Request(요청)
  2. Spring Security (필터 체인)
  3. Dispatcher Servlet
  4. Controller (url 매핑이 맞는 컨트롤러)

- Password Encoder
  - 비밀번호를 암호화하여 DB에 저장

- 보안
  - CSRF (Cross Site Request Forgery)
    - 인터넷 사용자가 자신의 의지와 무관하게
    - 공격자가 의도한 행위를 특정 웹사이트에 요청하게 만드는 공격
    - 스프링 시큐리티에서 보안 토큰 사용하여 방어 CSRF token
      - form 태그에서 post 요청을 받게되면, csrf 필터 작동
      - 접합한 CSRF token이 있어야, 다음 단계로 진행 가능
      - 적법한 홈페이지 폼에서 요청이 왔다는 것을 확인
  - CORS 
  
- MockMVC
  - 스프링 프레임워크의 테스트 기능
  - 모의 웹 애플리케이션 환경 제공
  - 컨트롤러 테스트
    - HTTP 요청에 대한 시뮬레이션
    - 요청 및 응답 검증 : 상태코드, 헤더, 본문 등을 검증할 수 있음
  - 사용
    - @AutoConfigureMockMvc 애노테이션
    - MockMvc 객체
    - perform 메서드를 사용해서 실행
    - get, post 등 http 메서드 요청 가능
    - andExpect 메서드를 통해 테스트 및 검증
    - andDo : 기타 메서드 사용 가능(print(), log())
    - isOk : 상태코드 200번, isNotFound : 상태코드 404번
  - 의존성 추가(security) : spring-security-test 추가

- 스프링 시큐리티 사용자 로그인/로그아웃, 인증 인터페이스
- UserDetailsService : 데이터베이스에서 회원 정보를 가져오는 역할
  - loadUserByUsername() : 회원정보 조회, 권한을 갖는 클래스 반환
  - UserDetails : 회원 정보를 담고 있음. 

# 연관 관계 매핑
- 연관 관계 매핑의 종류
  - 다중성
    - 1:1 @OneToOne (단방향 *)
    - 1:N @OneToMany (단방향은 실무에서 잘 사용하지 않는다.)
    - N:1 @ManyToOne (단방향 ***)
    - N:M @ManyToMany (다대다. 실무에서 잘 사용하지 않는다. 가운데 테이블을 하나 더 생성해 다대일 관계 2개를 연결)
  - 방향
    - 단방향
      - 기본적으로 단방향 매핑
    - 양방향
      - 참조가 필요하지 않은 경우, 굳이 양방향을 하지 않아도 된다
      - 엔티티 복잡도 증가
  - 연관 관계의 주인
    - 양방향의 경우, 외래키를 어디서 관리하는지. 관리 주체.
- ERD (Entity - Relation Diagram)

# 영속성 전이
  - Persistence Cascade
  - JPA에서 한 엔티티의 변경이 다른 엔티티에 어떻게 전파되는지 정의
  - CASCADE 종류
    - ALL : 모든 변경사항을 전파
    - PERSIST : 새로운 엔티티 생성을 전파
    - MERGE : 엔티티 병경을 전파
    - REMOVE : 엔티티 삭제를 전파
  - 고아 객체 제거(Orphan Removal)
    - 고아 객체 : 부모 엔티티와 관계가 끊어져 더 이상 필요하지 않은 자식 엔티티
    - @OneToMany 등의 애노테이션에서 orphanRemoval = true 설정
    - 부모와 관련이 제거되었을 때 자동으로 제거되어 데이터베이스에 연동됨. 
    - 주의사항 : 실제로 필요 없는 경우에만 활성화
    
# 지연(lazy) 로딩 vs 즉시(eager) 로딩
- 지연 로딩(LAZY) : 데이터가 실제로 사용될 때까지 데이터를 로드하지 않고, 요청이 될 때 비로소, DB에서 데이터를 가져온다.
- 즉시 로딩(EAGER) : 객체를 모드할 때 관련된 모든 데이터를 즉시 가져온다.
- 즉시 로딩을 할 경우 불필요한 데이터를 로드할 수 있기 때문에, 성능 최적화를 위해서 실무에서는 LAZY 지연 로딩을 사용하도록 한다.
- JPA 프레임워크에서 @ManyToOne 등의 관계에 fetch = FetchType.Lazy를 설정해야 한다.

# Auditing
- 엔티티에서 생성, 변경을 할 때 생성, 변경한 사람, 시간을 추적하고 싶을 때 사용한다.
  - 등록시간
  - 수정시간
  - 등록자
  - 수정자


