package com.fushioncode.blogapitestingwithswagger.exception;

public class NoFavoritePostException extends RuntimeException{
    public NoFavoritePostException(String message) {
        super(message);
    }
}
