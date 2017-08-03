package com.ys.reservation.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.ys.reservation.domain.User;

public class AuthUserWebArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		AuthUser loginUser = parameter.getParameterAnnotation(AuthUser.class);
		if(loginUser == null) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mvcContainer, 
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		AuthUser loginUser = parameter.getParameterAnnotation(AuthUser.class);
		if(loginUser == null) {
			return WebArgumentResolver.UNRESOLVED;
		}
		
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		return user;
	}	
}
