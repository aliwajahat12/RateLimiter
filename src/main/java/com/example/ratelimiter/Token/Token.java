package com.example.ratelimiter.Token;

import java.util.UUID;

public class Token {
    String id;

    public Token() {
        this.id = UUID.randomUUID().toString();
    }
}
