package com.dawidpater.project.carrental.validator;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserRoleValdation {
    public final static String[] ALL_ROLES = {"USER","ADMIN","MANAGER"};

    public static List<String> getCurrentUserRoles(HttpServletRequest request){

        List userRoles = new ArrayList(ALL_ROLES.length);
        for(String role : ALL_ROLES) {
            if(request.isUserInRole(role)) {
                userRoles.add(role);
            }
        }
        return userRoles;
    }
}
