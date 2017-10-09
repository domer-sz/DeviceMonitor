package pl.domsoft.deviceMonitor.infrastructure.user.account.repositories.query;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.domsoft.deviceMonitor.infrastructure.user.account.entities.Account;

/**
 * Created by szymo on 11.06.2017.
 */
@Repository
public interface QueryAccountRepository extends PagingAndSortingRepository<Account, Long>, CustomQueryAccountRepository{
    Account findByLogin(String login);
}
