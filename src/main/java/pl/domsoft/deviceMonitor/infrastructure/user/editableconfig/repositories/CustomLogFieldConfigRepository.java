package pl.domsoft.deviceMonitor.infrastructure.user.editableconfig.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.domsoft.deviceMonitor.infrastructure.user.editableconfig.entities.CustomLogFieldConfig;

import java.util.List;
import java.util.Optional;

/**
 * Created by szymo on 05.06.2017.
 */
@Repository
public interface CustomLogFieldConfigRepository extends CrudRepository<CustomLogFieldConfig, Long>{
    Optional<CustomLogFieldConfig> findOneByViewName(String viewname);
    @Query("SELECT cfc FROM CustomLogFieldConfig AS cfc ORDER BY cfc.viewName ASC")
    List<CustomLogFieldConfig> findAllOrderByViewNameDesc();

}
