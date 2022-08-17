package com.dawidpater.project.carrental.validator;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserRoleValdation {
    public static final  String[] ALL_ROLES = {"USER","ADMIN","MANAGER"};

    private UserRoleValdation(){}

    public static List<String> getCurrentUserRoles(HttpServletRequest request){

        List<String> userRoles = new ArrayList<>(ALL_ROLES.length);
        for(String role : ALL_ROLES) {
            if(request.isUserInRole(role)) {
                userRoles.add(role);
            }
        }
        return userRoles;
    }
}
