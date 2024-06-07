package com.estudos.rag.infrastructure.auth.service;

import com.estudos.rag.domain.entity.User;
import com.estudos.rag.infrastructure.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
    log.debug("Saving created user to DB");
    return userRepository.saveAndFlush(newUser);
  }

  public User getUserWithAuthenticationContext() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

    return userRepository.findBySocialAuthId(oauthUser.getAttribute("sub"));
  }
}
