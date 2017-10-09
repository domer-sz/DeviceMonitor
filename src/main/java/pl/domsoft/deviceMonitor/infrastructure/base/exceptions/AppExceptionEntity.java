package pl.domsoft.deviceMonitor.infrastructure.base.exceptions;

import pl.domsoft.deviceMonitor.infrastructure.base.model.entities.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by szymo on 11.09.2017.
 */
@Entity
@Table(name = "app_exception")
public class AppExceptionEntity extends BaseEntity{

    @Column(name = "statement")
    private String statement;
    @Column(name = "stack_info")
    @Lob
    private String stackInfo;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    AppExceptionEntity() {
    }

    public AppExceptionEntity(String statement, String stackInfo) {
        this.statement = statement;
        this.stackInfo = stackInfo;
        date = new Date();
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getStackInfo() {
        return stackInfo;
    }

    public void setStackInfo(String stackInfo) {
        this.stackInfo = stackInfo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
