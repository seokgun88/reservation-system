package com.ys.reservation.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if(session.getAttribute("accessToken") == null 
				|| session.getAttribute("refreshToken") == null
				|| session.getAttribute("login") == null 
				|| session.getAttribute("user") == null) {
			session.invalidate();
			String pageAfterLogin = URLEncoder.encode(request.getRequestURI(), "UTF-8");
			response.sendRedirect("/login?pageAfterLogin=" + pageAfterLogin);
			return false;
		}
		return true;
	}
}
