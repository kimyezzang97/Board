package com.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Refresh {

    @Id
    @Column(name = "refresh_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshId;

    @Column(name = "username")
    private String username;

    @Column(name = "refresh_token")
    private String refreshToken;

    private Timestamp expiration;

    @Builder
    public Refresh(String username, String refreshToken, Timestamp expiration){
        this.username = username;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }

}
