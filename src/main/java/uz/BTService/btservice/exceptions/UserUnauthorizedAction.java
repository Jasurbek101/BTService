package uz.BTService.btservice.exceptions;

public class UserUnauthorizedAction extends RuntimeException{
    public UserUnauthorizedAction(String message) {
        super(message);
    }
}
