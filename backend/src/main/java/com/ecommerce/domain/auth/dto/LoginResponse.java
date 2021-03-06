package com.ecommerce.domain.auth.dto;

import com.ecommerce.domain.user.domain.User;
import com.ecommerce.domain.user.domain.UserStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class LoginResponse implements Serializable {

    private Long id;
    private String email;
    private String profileImage;
    private String name;
    private String jwtToken;
    private UserStatus status;


    public LoginResponse(final User user, final String jwtToken) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
        this.name = user.getName();
        this.jwtToken = jwtToken;
        this.status = user.getStatus();
    }

}
