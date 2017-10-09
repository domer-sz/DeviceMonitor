package pl.domsoft.deviceMonitor.infrastructure.base.exceptions.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppExceptionEntity;

/**
 * Created by szymo on 11.09.2017.
 */
@Repository
public interface AppExceptionRepository extends CrudRepository<AppExceptionEntity, Long> {
}
