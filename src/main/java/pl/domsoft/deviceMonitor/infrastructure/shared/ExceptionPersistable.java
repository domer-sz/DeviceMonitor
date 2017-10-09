package pl.domsoft.deviceMonitor.infrastructure.shared;

import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.repositories.AppExceptionRepository;

/**
 * Created by szymo on 11.09.2017.
 */
public interface ExceptionPersistable {
    AppExceptionRepository getExcpRep();
}
