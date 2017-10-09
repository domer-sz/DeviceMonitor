package pl.domsoft.deviceMonitor.infrastructure.user.account.handlers.impl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.domsoft.IntegrationBaseTests;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.user.UserRole;
import pl.domsoft.deviceMonitor.infrastructure.user.account.commands.ChangeAccountActivityCommand;
import pl.domsoft.deviceMonitor.infrastructure.user.account.entities.Account;
import pl.domsoft.deviceMonitor.infrastructure.user.account.repositories.query.QueryAccountRepository;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by szymo on 25.07.2017.
 */
public class ChangeAccountActivityCommandHandlerImplTest extends IntegrationBaseTests{

    @Autowired
    private QueryAccountRepository queryAccountRepository;
    @Autowired
    private CommandBus commandBus;


    @Before
    public void setUp(){
        queryAccountRepository.deleteAll();;
    }

    @Test
    public void dont_change_activity_to_when_changed_user_is_admin() throws Exception {
        //given
        Account acc = new Account("acc", "acc", UserRole.ADMIN, new Date(), false);
        queryAccountRepository.save(acc);
        Long accId = acc.getId();
        ChangeAccountActivityCommand command = new ChangeAccountActivityCommand(accId, true);

        //when
        commandBus.sendCommand(command);

        //then
        final Account account = queryAccountRepository.findOne(accId);
        assertEquals(false, account.isActive());

    }

    @Test
    public void change_activity_to_active() throws Exception {
        //given
        Account acc = new Account("acc", "acc", UserRole.SERVICEMAN, new Date(), false);
        queryAccountRepository.save(acc);
        Long accId = acc.getId();
        ChangeAccountActivityCommand command = new ChangeAccountActivityCommand(accId, true);

        //when
        commandBus.sendCommand(command);

        //then
        final Account account = queryAccountRepository.findOne(accId);
        assertEquals(true, account.isActive());

    }

    @Test
    public void change_activity_to_not_active() throws Exception {
        //given
        Account acc = new Account("acc", "acc", UserRole.SERVICEMAN, new Date(), true);
        queryAccountRepository.save(acc);
        Long accId = acc.getId();
        ChangeAccountActivityCommand command = new ChangeAccountActivityCommand(accId, false);

        //when
        commandBus.sendCommand(command);

        //then
        final Account account = queryAccountRepository.findOne(accId);
        assertEquals(false, account.isActive());

    }

}