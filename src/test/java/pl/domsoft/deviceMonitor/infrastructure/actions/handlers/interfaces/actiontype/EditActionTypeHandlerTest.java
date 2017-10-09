package pl.domsoft.deviceMonitor.infrastructure.actions.handlers.interfaces.actiontype;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.domsoft.IntegrationBaseTests;
import pl.domsoft.deviceMonitor.infrastructure.actions.commands.actiontype.EditActionTypeCommand;
import pl.domsoft.deviceMonitor.infrastructure.actions.entities.ActionType;
import pl.domsoft.deviceMonitor.infrastructure.actions.repositories.ActionTypeRepository;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;

import static org.junit.Assert.assertEquals;

/**
 * Created by szymo on 18.06.2017.
 */
public class EditActionTypeHandlerTest extends IntegrationBaseTests {

    @Autowired
    private CommandBus commandBus;

    @Autowired
    private ActionTypeRepository actionTypeRepository;

    private Long createdObjectId;

    @Before
    public void setUp() throws Exception {
        actionTypeRepository.deleteAll();
        ActionType actionType = new ActionType("przedEdycja", "przedEdycja");
        final ActionType save = actionTypeRepository.save(actionType);
        createdObjectId = save.getId();
    }

    @Test
    public void testIfEditCommandWillEditActionType() throws AppException {
        //given
        EditActionTypeCommand editActionTypeCommand =  new EditActionTypeCommand(createdObjectId, "poEdycji", "poEdycji");
        //when
        commandBus.sendCommand(editActionTypeCommand);
        //then
        final ActionType edited = actionTypeRepository.findOne(createdObjectId);
        assertEquals("poEdycji", editActionTypeCommand.getName());
        assertEquals("poEdycji", editActionTypeCommand.getContent());
    }

}