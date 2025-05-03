package br.com.nlw.events.exception;

public class UserInviteeNotFound extends RuntimeException {
    public UserInviteeNotFound(String message) {
        super(message);
    }
}
