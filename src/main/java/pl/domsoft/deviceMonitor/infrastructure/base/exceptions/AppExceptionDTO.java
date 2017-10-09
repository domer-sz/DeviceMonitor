package pl.domsoft.deviceMonitor.infrastructure.base.exceptions;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by szymo on 01.05.2017.
 * Obiekt transportowy dla wszystkich wyjątków w aplikacji
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AppExceptionDTO {
    private final String statement;
    private final String stackInfo;

    private AppExceptionDTO(){
        stackInfo = null;
        statement = null;
    }

    @JsonCreator
    public AppExceptionDTO(@JsonProperty("statement") String statement, @JsonProperty("stackInfo") String stackInfo) {
        this.statement = statement;
        this.stackInfo = stackInfo;
    }

    AppExceptionDTO(AppException e) {
        this.statement = e.getStatement();
        this.stackInfo = e.getStackInfo();
    }
}
