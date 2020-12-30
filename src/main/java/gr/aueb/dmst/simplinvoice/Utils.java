package gr.aueb.dmst.simplinvoice;

import gr.aueb.dmst.simplinvoice.model.CustomUserDetails;
import gr.aueb.dmst.simplinvoice.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;


public class Utils {

    public static User getUserFromWebRequest(WebRequest request) {
        return getUserFromPrincipal(((UsernamePasswordAuthenticationToken)request.getUserPrincipal()).getPrincipal());
    }

    public static User getUserFromHttpServletRequest(HttpServletRequest request) {
        return getUserFromPrincipal(((UsernamePasswordAuthenticationToken)request.getUserPrincipal()).getPrincipal());
    }

    public static User getUserFromPrincipal(Object principal) {

        if(principal instanceof CustomUserDetails)
            return ((CustomUserDetails)principal).getUser();

        return null;
    }

}
