package com.estudos.rag.infrastructure.auth.service;

import com.estudos.rag.domain.entity.User;
import com.estudos.rag.infrastructure.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public User findByUsername(String email) {
    return userRepository.findByUsername(email);
  }

  public User createUser(User newUser) {
    log.debug("Saving created backoffice user to DB");
    return userRepository.saveAndFlush(newUser);
  }
}
