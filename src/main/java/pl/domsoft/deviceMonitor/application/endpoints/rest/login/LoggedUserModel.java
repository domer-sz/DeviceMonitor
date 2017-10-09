package pl.domsoft.deviceMonitor.application.endpoints.rest.login;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * Created by szymo on 15.06.2017.
 * Obiekt przechowujący informacje o obecnie zalogowanym użytkowniku
 */
class LoggedUserModel {
    private final String login;
    private final List<GrantedAuthority> rolesNames;

    @JsonCreator
    LoggedUserModel(
            @JsonProperty("login") String login,
            @JsonProperty("rolesNames") List<GrantedAuthority> rolesNames
    ) {
        this.login = login;
        this.rolesNames = rolesNames;
    }

    public String getLogin() {
        return login;
    }

    public List<GrantedAuthority> getRolesNames() {
        return rolesNames;
    }

}
