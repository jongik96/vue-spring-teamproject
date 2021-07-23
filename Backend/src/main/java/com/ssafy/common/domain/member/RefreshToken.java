package com.ssafy.common.domain.member;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
@Entity
public class RefreshToken {

    @Id
    private String memberEmail;
    private String refreshToken;

    public RefreshToken updateValue(String token) {
        this.refreshToken = token;
        return this;
    }

    @Builder
    public RefreshToken(String key, String value) {
        this.memberEmail = key;
        this.refreshToken = value;
    }
}
