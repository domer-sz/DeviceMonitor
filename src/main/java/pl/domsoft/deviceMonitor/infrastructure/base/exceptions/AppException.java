package pl.domsoft.deviceMonitor.infrastructure.base.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.repositories.AppExceptionRepository;
import pl.domsoft.deviceMonitor.infrastructure.shared.ExceptionPersistable;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by szymo on 22.04.2017.
 * Podstawowy wrapper na wyjątki używane w aplikacji
 */
public class AppException extends Exception{
    private static final Logger log = LoggerFactory.getLogger(AppException.class);
    private final String statement;
    private final String stackInfo;
    private final HttpStatus httpStatus;

    public AppException(String message, String stackInfo, AppExceptionRepository appExceptionRepository) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.statement = message;
        this.stackInfo = stackInfo;
        log.info("Wystąpił wyjątek : " + statement + "\n" + stackInfo);
        persistException(appExceptionRepository);
    }

    public AppException(String message, String stackInfo, HttpStatus httpStatus, AppExceptionRepository appExceptionRepository) {
        super(message);
        this.httpStatus = httpStatus;
        this.statement = message;
        this.stackInfo = stackInfo;
        log.info("Wystąpił wyjątek : " + statement + "\n" + stackInfo);
        persistException(appExceptionRepository);
    }

    public AppException(String message, Exception e, AppExceptionRepository appExceptionRepository) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.statement = message;
        this.stackInfo = this.getStackTreeAsString(e);
        log.info("Wystąpił wyjątek : " + statement + " \n" + getStackTreeAsString(e));
        persistException(appExceptionRepository);
    }

    public AppException(String message, Exception e, HttpStatus httpStatus, AppExceptionRepository appExceptionRepository) {
        super(message);
        this.httpStatus = httpStatus;
        this.statement = message;
        this.stackInfo = this.getStackTreeAsString(e);
        log.info("Wystąpił wyjątek : " + statement + " \n" + getStackTreeAsString(e));
        persistException(appExceptionRepository);
    }

    public AppException(String message, AppExceptionRepository appExceptionRepository) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.statement = message;
        this.stackInfo = this.getStackTreeAsString(this);
        log.info("Wystąpił wyjątek : " + statement + "\n" + getStackTreeAsString(this));
        persistException(appExceptionRepository);
    }

    public AppException(String message, HttpStatus httpStatus, AppExceptionRepository appExceptionRepository) {
        super(message);
        this.httpStatus = httpStatus;
        this.statement = message;
        this.stackInfo = this.getStackTreeAsString(this);
        log.info("Wystąpił wyjątek : " + statement + "\n" + getStackTreeAsString(this));
        persistException(appExceptionRepository);
    }

    private void persistException(AppExceptionRepository appExceptionRepository) {
        if(appExceptionRepository != null) {
            try {
                appExceptionRepository.save(new AppExceptionEntity(statement, stackInfo));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public AppExceptionDTO toDTO(){
        return new AppExceptionDTO(this);
    }


    public String getStatement() {
        return statement;
    }

    /**
     * zwraca stacktree wyjątku jako string
     * @return sformatowany do postaci stringa stacktrace
     */
    public String getStackInfo() {
        return stackInfo;
    }

    public HttpStatus getHttpStatus() { return httpStatus; }

    private String getStackTreeAsString(Exception e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
