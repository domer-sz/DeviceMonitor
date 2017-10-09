package pl.domsoft.deviceMonitor.infrastructure.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by szymo on 10.06.2017.
 *
 */
public class AuthenticationUtil {
    /**
     * Zwraca obiekt autentykacji obecnie zalogowanego użutkownika lub użytkownika anonimowego gdy nie jest zalogowany
     */
    public static Authentication getCurrentAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
