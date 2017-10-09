package pl.domsoft.deviceMonitor.infrastructure.actions.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.domsoft.deviceMonitor.infrastructure.actions.entities.Action;
import pl.domsoft.deviceMonitor.infrastructure.actions.entities.ActionLog;

import java.util.Collection;

/**
 * Created by szymo on 18.06.2017.
 */
@Repository
public interface ActionLogRepository extends PagingAndSortingRepository<ActionLog, Long>{
    Collection<ActionLog> findAll();
}
