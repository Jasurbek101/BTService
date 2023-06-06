package uz.BTService.btservice.exceptions;

public class RegionNotFoundException extends RuntimeException{
    public RegionNotFoundException(String message){
        super(message);
    }
}
