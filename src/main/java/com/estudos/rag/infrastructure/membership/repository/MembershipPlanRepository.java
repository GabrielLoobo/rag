package com.estudos.rag.infrastructure.membership.repository;

import com.estudos.rag.domain.entity.MembershipPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {
  MembershipPlan findFirstByOrderById();
}
