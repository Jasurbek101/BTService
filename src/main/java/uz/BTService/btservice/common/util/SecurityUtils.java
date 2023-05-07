package uz.BTService.btservice.common.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {
    public static String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            if(token.getPrincipal() instanceof UserDetails){
                UserDetails userDetails = (UserDetails) token.getPrincipal();
                return userDetails.getUsername();
            }
        }
        return null;
    }
}
