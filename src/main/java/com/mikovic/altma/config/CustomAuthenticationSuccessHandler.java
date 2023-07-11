package com.mikovic.altma.config;


import com.mikovic.altma.modeles.User;
import com.mikovic.altma.payload.AuthResponse;
import com.mikovic.altma.security.TokenProvider;
import com.mikovic.altma.security.UserPrincipal;
import com.mikovic.altma.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private UserService userService;

    @Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Autowired
	private TokenProvider tokenProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		String email = userPrincipal.getEmail();
		User user = userService.findByEmail(email);
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		String token = tokenProvider.createToken(authentication);
		ResponseEntity.ok(new AuthResponse(token));
		if(!request.getHeader("referer").contains("login")) {
			response.sendRedirect(request.getHeader("referer"));
		} else {
			response.sendRedirect(request.getContextPath() + "/");
		}
	}
}
