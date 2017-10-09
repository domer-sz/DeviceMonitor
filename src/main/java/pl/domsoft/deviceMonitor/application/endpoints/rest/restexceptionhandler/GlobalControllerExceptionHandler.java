package pl.domsoft.deviceMonitor.application.endpoints.rest.restexceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.repositories.AppExceptionRepository;
import pl.domsoft.deviceMonitor.infrastructure.shared.ExceptionPersistable;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by szymo on 01.05.2017.
 * Globalny exception handler dla endpointów restowych,
 * w przypadku gdy w kontrolerze wystąpi wyjątek zwraca AppExceptionDTO wraz z odpowiednim statusem
 */
@ControllerAdvice
class GlobalControllerExceptionHandler implements ExceptionPersistable{
    private static final Logger log = LoggerFactory.getLogger(AppException.class);

    private final AppExceptionRepository appExceptionRepository;

    GlobalControllerExceptionHandler(AppExceptionRepository appExceptionRepository) {
        this.appExceptionRepository = appExceptionRepository;
    }

    @ExceptionHandler({ AppException.class })
    public ResponseEntity<Object> handleAppException(AppException ex, WebRequest webReq, HttpServletRequest httpReq) {

        log.info("Przy wywołaniu metody: " + httpReq.getMethod() + " na adresie: " + httpReq.getRequestURL().toString() +
                " wystąpił wyjątek " + ex.getStatement() +"\n"+ ex.getStackInfo() );
        return new ResponseEntity<>(
                ex.toDTO(), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest webReq, HttpServletRequest httpReq) {
        final AppException appException = new AppException("Nieznany błąd", ex, getExcpRep());
        log.info("Przy wywołaniu metody: " + httpReq.getMethod() + " na adresie: " + httpReq.getRequestURL().toString() +
                " wystąpił wyjątek " + appException.getStatement() +"\n"+ appException.getStackInfo() );
        return new ResponseEntity<>(
                appException.toDTO(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public AppExceptionRepository getExcpRep() {
        return appExceptionRepository;
    }
}
