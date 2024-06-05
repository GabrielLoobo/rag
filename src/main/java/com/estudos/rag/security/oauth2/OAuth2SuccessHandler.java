package com.estudos.rag.security.oauth2;

import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler { //
}
