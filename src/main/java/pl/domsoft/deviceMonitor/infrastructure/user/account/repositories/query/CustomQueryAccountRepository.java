package pl.domsoft.deviceMonitor.infrastructure.user.account.repositories.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.user.account.model.AccountDetailsViewModel;
import pl.domsoft.deviceMonitor.infrastructure.user.account.model.AccountTableViewModel;

/**
 * Created by szymo on 11.06.2017.
 */
interface CustomQueryAccountRepository {
    @Transactional(readOnly = true)
    Page<AccountTableViewModel> findAllConverted(Pageable pageable);

    AccountDetailsViewModel findOneConverted(long id) throws AppException;
}
