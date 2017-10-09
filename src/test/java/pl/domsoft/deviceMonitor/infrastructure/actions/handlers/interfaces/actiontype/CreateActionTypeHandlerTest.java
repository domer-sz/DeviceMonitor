package pl.domsoft.deviceMonitor.infrastructure.actions.handlers.interfaces.actiontype;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.domsoft.IntegrationBaseTests;
import pl.domsoft.deviceMonitor.infrastructure.actions.commands.actiontype.CreateActionTypeCommand;
import pl.domsoft.deviceMonitor.infrastructure.actions.entities.ActionType;
import pl.domsoft.deviceMonitor.infrastructure.actions.repositories.ActionTypeRepository;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by szymo on 18.06.2017.
 */
public class CreateActionTypeHandlerTest extends IntegrationBaseTests {

    @Autowired
    private ActionTypeRepository actionTypeRepository;

    @Autowired
    private CommandBus commandBus;

    @Before
    public void setUp() throws Exception {
            actionTypeRepository.deleteAll();
    }

    @Test
    public void testIfCommandCreateAction() throws AppException {
        //given
        final String content = "ydvfd6sf65s8gdds675^%^%^%^%^Tvdydvfd6sf65s8gdds675^%^%^%^%^Tvdydvfd6sf65s8gdds675^%^%^%^%^Tvdydvfd6sf65s8gdds675^%^%^%^%^Tvdydvfd6sf65s8gdds675^%^%^%^%^Tvdydvfd6sf65s8gdds675^%^%^%^%^Tvdydvfd6sf65s8gdds675^%^%^%^%^Tvdydvfd6sf65s8gdds675^%^%^%^%^Tvdydvfd6sf65s8gdds675^%^%^%^%^Tvdydvfd6sf65s8gdds675^%^%^%^%^Tvdydvfd6sf65s8gdds675^%^%^%^%^Tvdydvfd6sf65s8gdds675^%^%^%^%^Tvdydvfd6sf65s8gdds675^%^%^%^%^Tvdydvfd6sf65s8gdds675^%^%^%^%^Tvdydvfd6sf65s8gdds675^%^%^%^%^Tvdydvfd6sf65s8gdds675^%^%^%^%^Tvdydvfd6sf65s8gdds675^%^%^%^%^Tvdydvfd6sf65s8gdds675^%^%^%^%^Tvdydvfd6sf65s8gdds675^%^%^%^%^Tvd";
        CreateActionTypeCommand createActionTypeCommand = new CreateActionTypeCommand("akcja1", content);
        //when
        final ActionType createdCommand = commandBus.sendCommand(createActionTypeCommand, ActionType.class);
        //then
        assertNotNull(createActionTypeCommand);
        List<ActionType> types = takeAllActionTypesAsList();
        assertEquals(1L, types.size());
        assertEquals("akcja1", createdCommand.getName());
        assertEquals(content, createdCommand.getContent());
    }

    private List<ActionType> takeAllActionTypesAsList() {
        final Iterable<ActionType> all = actionTypeRepository.findAll();
        List<ActionType> types = new ArrayList<>();
        for (ActionType a: all) {
            types.add(a);
        }
        return types;
    }

}