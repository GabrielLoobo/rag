package com.estudos.rag.infrastructure.membership.service;

import com.estudos.rag.domain.entity.MembershipPlan;
import com.estudos.rag.infrastructure.membership.repository.MembershipPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MembershipService {
  private final MembershipPlanRepository membershipPlanRepository;

  public MembershipPlan getDefaultPlan(){
    return membershipPlanRepository.findFirstByOrderById();
  }
}
