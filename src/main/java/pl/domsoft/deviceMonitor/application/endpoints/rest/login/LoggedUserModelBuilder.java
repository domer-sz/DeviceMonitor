package pl.domsoft.deviceMonitor.application.endpoints.rest.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class LoggedUserModelBuilder {
    private String login;
    private List<GrantedAuthority> rolesNames;

    LoggedUserModelBuilder(Authentication authentication){
        login = authentication.getName();
        rolesNames = new ArrayList<>(authentication.getAuthorities());


    }

    public LoggedUserModel build() {
        return new LoggedUserModel(login, rolesNames);
    }
}