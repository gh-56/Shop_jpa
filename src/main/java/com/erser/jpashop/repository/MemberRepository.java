package com.erser.jpashop.repository;

import com.erser.jpashop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 쿼리 메소드 : 이메일로 조회
    Member findByEmail(String email);
}
