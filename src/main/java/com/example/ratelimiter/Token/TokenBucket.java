package com.example.ratelimiter.Token;


import java.util.ArrayList;

public class TokenBucket {
    private final ArrayList<Token> tokens;
    private final int tokenLimit = 10;

    public int getTokensSize() {
        return tokens.size();
    }

    public TokenBucket() {
        tokens = new ArrayList<>();
        for (int i = 0; i < tokenLimit; i++) {
            tokens.add(new Token());
        }
    }

    public TokenBucket addToken() {
        if (tokens.size() < tokenLimit) {
            tokens.add(new Token());
        }
        return this;
    }

    public TokenBucket removeToken() {
        tokens.removeLast();
        return this;
    }

    public boolean canRemoveToken() {
        return tokens.size() > 0;
    }

}
