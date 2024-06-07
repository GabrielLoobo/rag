package com.estudos.rag.infrastructure.auth.repository;

import com.estudos.rag.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);

  User findBySocialAuthId(String socialAuthId);
}
