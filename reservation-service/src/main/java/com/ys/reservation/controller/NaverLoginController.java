package com.ys.reservation.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.ys.reservation.domain.NaverLoginResponse;
import com.ys.reservation.domain.NaverProfile;
import com.ys.reservation.domain.NaverProfileResponse;
import com.ys.reservation.domain.User;
import com.ys.reservation.service.UserService;

@Controller
@PropertySource("classpath:/application.properties")
public class NaverLoginController {
	@Value("${naver.openapi.clientid}")
	private String clientId;
	@Value("${naver.openapi.clientsecret}")
	private String clientSecret;
	@Value("${naver.oauth2.callback}")
	private String oauth2callback;
	private final String NAVER_OAUTH_URL = "https://nid.naver.com/oauth2.0";
	private final String NAVER_GET_PROFILE_URL = "https://openapi.naver.com/v1/nid/me";
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/login")
	public String login(@RequestParam String pageAfterLogin, HttpSession session) 
			throws UnsupportedEncodingException {
		// CSRF 방지를 위한 상태 토큰 생성 코드
		// 상태 토큰은 추후 검증을 위해 세션에 저장되어야 한다.
		// 상태 토큰으로 사용할 랜덤 문자열 생성
		SecureRandom random = new SecureRandom();
		String state = new BigInteger(130, random).toString(32);

		// 세션 또는 별도의 저장 공간에 상태 토큰을 저장
		session.setAttribute("state", state);
		String callback = URLEncoder.encode(
				oauth2callback + "?pageAfterLogin=" + pageAfterLogin, "UTF-8");

		return "redirect:" + NAVER_OAUTH_URL + "/authorize?client_id=" + clientId
				+ "&response_type=code&redirect_uri=" + callback + "&state=" + state;
	}

	@GetMapping("/oauth2callback")
	public String oauth2callback(HttpSession session, @RequestParam String pageAfterLogin,
			@RequestParam String state, @RequestParam String code) {
		// CSRF 방지를 위한 상태 토큰 검증 검증
		// 세션 또는 별도의 저장 공간에 저장된 상태 토큰과 콜백으로 전달받은 state 파라미터의 값이 일치해야 함
		// 세션 또는 별도의 저장 공간에서 상태 토큰을 가져옴
		String storedState = (String) session.getAttribute("state");
		if (!state.equals(storedState)) {
			return "redirect:/";
		} else {
			String url = NAVER_OAUTH_URL + "/token?client_id=" + clientId + "&client_secret=" + clientSecret
					+ "&grant_type=authorization_code&state=" + storedState + "&code=" + code;
			RestTemplate restTemplate = new RestTemplate();
			NaverLoginResponse loginResponse = restTemplate.getForObject(url, NaverLoginResponse.class);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", loginResponse.getTokenType() + " " + loginResponse.getAccessToken());
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			ResponseEntity<NaverProfileResponse> profileResponseEntity = 
					restTemplate.exchange(
						NAVER_GET_PROFILE_URL, 
						HttpMethod.GET,
						entity, 
						NaverProfileResponse.class
					);
			NaverProfileResponse profileResponse = profileResponseEntity.getBody();
			NaverProfile profile = profileResponse.getResponse();

			User user = userService.create(profile);

			session.setAttribute("accessToken", loginResponse.getAccessToken());
			session.setAttribute("refreshToken", loginResponse.getRefreshToken());
			session.setAttribute("login", true);
			session.setAttribute("user", user);
			return "redirect:" + pageAfterLogin;
		}
	}
}
