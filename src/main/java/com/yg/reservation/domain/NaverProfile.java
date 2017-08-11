package com.yg.reservation.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverProfile {
	private String email;
    private String nickname;
    @JsonProperty("profile_image")
    private String profileImage;
    private String age;
    private String gender;
    private String id;
    private String name;
    private String birthday;
}
