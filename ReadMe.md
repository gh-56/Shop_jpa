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
# JPQL
- JPQL은 SQL과 유사, 검색대상 엔티티 (DB가 아님)
- 문법 유사 -> 결국은 SQL로 변환된다.
- 특징 : 특정 공급업체에 종속적이지 않고 추상화 되어 있다.
- 별칭(alias) 필수
- SQL 키워드는 대소문자 구분하지 않는다.
- 엔티티, 필드는 대소문자 구분한다.