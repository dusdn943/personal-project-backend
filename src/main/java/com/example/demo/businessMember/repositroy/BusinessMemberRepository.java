package com.example.demo.businessMember.repositroy;

import com.example.demo.businessMember.entity.BusinessMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessMemberRepository extends JpaRepository<BusinessMember, Long> {
    Optional<BusinessMember> findByEmail(String email);
}
