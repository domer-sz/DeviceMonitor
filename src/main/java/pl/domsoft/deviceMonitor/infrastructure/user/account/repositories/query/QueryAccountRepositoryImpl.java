package pl.domsoft.deviceMonitor.infrastructure.user.account.repositories.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.repositories.AppExceptionRepository;
import pl.domsoft.deviceMonitor.infrastructure.shared.ExceptionPersistable;
import pl.domsoft.deviceMonitor.infrastructure.user.account.entities.Account;
import pl.domsoft.deviceMonitor.infrastructure.user.account.model.AccountDetailsViewModel;
import pl.domsoft.deviceMonitor.infrastructure.user.account.model.AccountTableViewModel;

import java.util.stream.Collectors;

/**
 * Created by szymo on 11.06.2017.
 */
class QueryAccountRepositoryImpl implements CustomQueryAccountRepository, ExceptionPersistable{

    @Autowired
    private QueryAccountRepository queryAccountRepository;
    @Autowired
    private AppExceptionRepository appExceptionRepository;

    @Override
    public Page<AccountTableViewModel> findAllConverted(Pageable pageable) {
        final Page<Account> page = queryAccountRepository.findAll(pageable);
        return convertPage(page, pageable, Account.class);

    }

    @Override
    public AccountDetailsViewModel findOneConverted(long id) throws AppException {
        final Account one = this.queryAccountRepository.findOne(id);
        if(one == null) throw new AppException(
                "Brak wybranego elementu",
                "Próbujesz pobrać konto które nie istnieje",
                HttpStatus.BAD_REQUEST,
                getExcpRep());
        return new AccountDetailsViewModel(one);
    }

    private <T extends Account> Page<AccountTableViewModel> convertPage(Page<T> page, Pageable pageable, Class<T> clazz){
        return new PageImpl<>(
                page.getContent().stream().map(AccountTableViewModel::new).collect(Collectors.toList()),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    public AppExceptionRepository getExcpRep() {
        return appExceptionRepository;
    }
}
