package pl.domsoft.deviceMonitor.infrastructure.actions.handlers.interfaces.action;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.domsoft.IntegrationBaseTests;
import pl.domsoft.deviceMonitor.infrastructure.actions.commands.action.CreateActionCommand;
import pl.domsoft.deviceMonitor.infrastructure.actions.entities.Action;
import pl.domsoft.deviceMonitor.infrastructure.actions.entities.ActionType;
import pl.domsoft.deviceMonitor.infrastructure.actions.repositories.ActionRepository;
import pl.domsoft.deviceMonitor.infrastructure.actions.repositories.ActionTypeRepository;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by szymo on 18.06.2017.
 */
public class CreateActionHandlerTest extends IntegrationBaseTests {

    @Autowired
    private ActionTypeRepository actionTypeRepository;

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private CommandBus commandBus;

    private Long createdActionTypeId;
    private ActionType createdActionType;

    @Before
    public void setUp() throws Exception {
        actionTypeRepository.deleteAll();
        ActionType actionType = new ActionType("przedEdycja", "przedEdycja");
        final ActionType save = actionTypeRepository.save(actionType);
        createdActionType = save;
        createdActionTypeId = save.getId();
    }

    @Test
    public void testIfCreateActionCommandWillCreateAction() throws AppException {
        //given
        CreateActionCommand createActionCommand = new CreateActionCommand(createdActionTypeId, Arrays.asList("dev_1"), "additionalParams");
        //when
        commandBus.sendCommand(createActionCommand);
        //then
        final Action createdAction = actionRepository.findAll().stream().findAny().get();
        assertNotNull(createdAction);
        assertEquals(createdActionType.getContent(), createdAction.getContent());
        assertEquals(createdActionType.getName(), createdAction.getName());
        assertNotNull(createdAction.getCreateTime());
        assertNull(createdAction.getExecutionTime());
        assertFalse(createdAction.isExecuted());
        assertEquals("dev_1", createdAction.getDeviceId());
    }

}