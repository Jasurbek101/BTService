package uz.BTService.btservice.exceptions;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String massage) {
        super(massage);
    }
}

