package pl.domsoft.deviceMonitor.application.endpoints.rest.login;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.domsoft.deviceMonitor.infrastructure.user.UserRole;
import pl.domsoft.deviceMonitor.infrastructure.user.account.entities.Account;
import pl.domsoft.deviceMonitor.infrastructure.user.account.repositories.query.QueryAccountRepository;
import pl.domsoft.deviceMonitor.infrastructure.shared.utils.AuthenticationUtil;

import java.util.Date;

@Controller
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final QueryAccountRepository accountRepository;

    public AuthenticationController(QueryAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @GetMapping("/logout")
    public String logout(){
        return "logout";
    }

    @GetMapping("/loginuser")
    public String getLoginView(){
        configureDefaultAccounts();
        return "loginuser";
    }

    @GetMapping(value = {"/loggedUser", "/loggeduser"})
    @ResponseBody
    public LoggedUserModel getCurrentLoggedUser(){
        return new LoggedUserModelBuilder(AuthenticationUtil.getCurrentAuthentication()).build();
    }


    private void configureDefaultAccounts() {
        final BCryptPasswordEncoder coder = new BCryptPasswordEncoder();

        {
            Account deva = accountRepository.findByLogin("deva");
            if(deva == null){
               deva = new Account("deva", coder.encode("deva"), UserRole.ADMIN, new Date(), true);
               accountRepository.save(deva);
            }
        }

        {
            Account devs = accountRepository.findByLogin("devs");
            if (devs == null) {
                devs = new Account("devs", coder.encode("devs"), UserRole.SERVICEMAN, new Date(), true);
                accountRepository.save(devs);
            }
        }
        {
            Account devu = accountRepository.findByLogin("devu");
            if (devu == null) {
                devu = new Account("devu", coder.encode("devu"), UserRole.USER, new Date(), true);
                accountRepository.save(devu);
            }
        }
        {
            Account serviceman = accountRepository.findByLogin("serviceman");
            if (serviceman == null) {
                serviceman = new Account("serviceman", coder.encode("ghgspwfz"), UserRole.ADMIN, new Date(), true);
                accountRepository.save(serviceman);
            }
        }
    }
}
