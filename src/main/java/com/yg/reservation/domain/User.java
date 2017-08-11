package com.yg.reservation.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
	private int id;
	private String username;
	private String email;
	private String nickname;
	private String snsId;
	private int adminFlag;
}
