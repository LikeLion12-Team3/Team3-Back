package com.likelion.byuldajul.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id; //import jakarta.persistence.Id; 아님
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash(value = "tokens")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token implements Serializable {

    @Id
    private String email;

    private String refreshToken;

    @Builder
    public Token(String email, String refreshToken) {
        this.email = email;
        this.refreshToken = refreshToken;
    }
}

