package pl.domsoft.deviceMonitor.appconfig.security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.domsoft.deviceMonitor.infrastructure.user.UserRole;
import pl.domsoft.deviceMonitor.infrastructure.user.account.entities.Account;
import pl.domsoft.deviceMonitor.infrastructure.user.account.repositories.query.QueryAccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by szymo on 09.06.2017.
 */
@Service
@ComponentScan
public class UserDetailsServiceImpl implements UserDetailsService{

    private final QueryAccountRepository accountRepository;

    public UserDetailsServiceImpl(QueryAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Account account = accountRepository.findByLogin(login);
        if(account == null){
            throw new UsernameNotFoundException("User with login : \""+login+"\" not found");
        }
        List<GrantedAuthority> authorities =
                buildUserAuthority(account.getRole());

        return buildUserForAuthentication(account, authorities);
    }


    /** Mapuje role użytkownika na GrantedAuthority
     * @param userRole - rola użytkownika
     * @return List<GrantedAuthority> grantedAuthorityList
     */
    private List<GrantedAuthority> buildUserAuthority(UserRole userRole) {
        final Set<SimpleGrantedAuthority> setAuths = userRole
                .getRoles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        return  new ArrayList<>(setAuths);
    }

    /** Metoda tworząca użytkownia Spring Security na podstawie UserEntry
     * @param account - encja użytkownika
     * @param authorities - role użytkownika
     * @return
     */
    private User buildUserForAuthentication(Account account,
                                            List<GrantedAuthority> authorities) {
        boolean active = account.isActive();
        return new User(account.getLogin(), account.getPassword(), active, true, true,
                true, authorities);
    }

}
