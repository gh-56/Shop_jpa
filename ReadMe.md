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
      - 