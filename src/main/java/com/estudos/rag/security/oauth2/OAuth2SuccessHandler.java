package com.estudos.rag.security.oauth2;

import com.estudos.rag.domain.entity.MembershipPlan;
import com.estudos.rag.domain.entity.User;
import com.estudos.rag.infrastructure.auth.repository.UserRepository;
import com.estudos.rag.infrastructure.auth.service.UserService;
import com.estudos.rag.infrastructure.membership.service.MembershipService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
  @Autowired
  private UserService userService;

  @Autowired
  private MembershipService membershipService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
    createUserIfDoesNotExist(oauthUser);

    super.onAuthenticationSuccess(request, response, authentication);
  }

  void createUserIfDoesNotExist(OAuth2User oAuth2User) {
    User user = userService.findByUsername(oAuth2User.getAttribute("email"));

    if (user == null) {
      User newUser = new User();
      newUser.setUsername(oAuth2User.getAttribute("email"));
      newUser.setName(oAuth2User.getAttribute("name"));
      newUser.setSocialAuthId(oAuth2User.getAttribute("sub"));

      MembershipPlan defaultPlan = membershipService.getDefaultPlan();
      newUser.setMembershipPlan(defaultPlan);

      userService.createUser(newUser);
    }
  }
}
