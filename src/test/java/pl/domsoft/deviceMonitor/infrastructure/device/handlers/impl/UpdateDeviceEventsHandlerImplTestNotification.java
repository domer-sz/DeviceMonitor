package pl.domsoft.deviceMonitor.infrastructure.device.handlers.impl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.domsoft.IntegrationBaseTests;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.UpdateDeviceEventsCommand;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceConfig;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.embendable.Contact;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.embendable.Localisation;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceAccident;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceBreak;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceOverview;
import pl.domsoft.deviceMonitor.infrastructure.device.handlers.interfaces.UpdateDeviceEventsHandler;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceState;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceconfig.DeviceConfigRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.DeviceAccidentRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.DeviceBreakRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.DeviceEventRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.DeviceOverviewRepository;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by szymo on 29.05.2017.
 */
public class UpdateDeviceEventsHandlerImplTestNotification extends IntegrationBaseTests {

    @Autowired
    private DeviceEventRepository deviceEventRepository;

    @Autowired
    private DeviceBreakRepository deviceBreakRepository;

    @Autowired
    private DeviceOverviewRepository deviceOverviewRepository;

    @Autowired
    private DeviceAccidentRepository deviceAccidentRepository;
    
    @Autowired
    private UpdateDeviceEventsHandler updateDeviceEventsHandler;

    @Autowired
    private DeviceConfigRepository deviceConfigRepository;

    @Before
    public void setUp() throws Exception {
        deviceEventRepository.deleteAll();
        deviceConfigRepository.deleteAll();
    }

    //break
    @Test
    public void testIfDeviceBreakWillStartIfThereAreNoAnyBreak() throws Exception {
    //given
        UpdateDeviceEventsCommand command = prepareConfigAndStates();
    //when
        updateDeviceEventsHandler.handle(command);
    //then
        //wygenerowana jeden event
        assertEquals(1, new ArrayList<DeviceBreak>(deviceBreakRepository.findAll()).size());
        //ten even to przerwa z otwartym zakresem daty
        assertNotEquals(null, new ArrayList<>(deviceEventRepository.findAll()).get(0).getStartDate());
        assertEquals(null,    new ArrayList<>(deviceEventRepository.findAll()).get(0).getEndDate());

    }

    private UpdateDeviceEventsCommand prepareConfigAndStates() throws InterruptedException {
        DeviceState oldState = new DeviceState("678", true, false, false, 67, new Date());
        Thread.sleep(5);
        DeviceState newState = new DeviceState("678", false, false, false, 67, new Date());
        UpdateDeviceEventsCommand command = new UpdateDeviceEventsCommand(oldState, newState);
        DeviceConfig config = new DeviceConfig("678", "", new Localisation("","","","","","", ""), new Contact());
        deviceConfigRepository.save(config);
        return command;
    }

    @Test
    public void testIfDeviceBreakWillStartIfThereAreClosedBreak() throws Exception {
        //given
        DeviceState oldState = new DeviceState("6787", true, false, false, 67, new Date());
        Thread.sleep(5);
        DeviceState newState = new DeviceState("6787", false, false, false, 67, new Date());
        UpdateDeviceEventsCommand command = new UpdateDeviceEventsCommand(oldState, newState);
        deviceBreakRepository.save(new DeviceBreak("6787", new Date(), new Date(), "comment"));
        DeviceConfig config = new DeviceConfig("6787", "", new Localisation("","","","","","", ""), new Contact());
        deviceConfigRepository.save(config);

        //when
        updateDeviceEventsHandler.handle(command);
        //then
        //wygenerowana jeden event, czyli w sumie z poprzednim są dwa
        assertEquals(new ArrayList<DeviceBreak>(deviceBreakRepository.findAll()).size() , 2);
        //ten even to przerwa z otwartym zakresem daty
        assertNotEquals(null, new ArrayList<>(deviceEventRepository.findAll()).get(1).getStartDate());
        assertEquals(null, new ArrayList<>(deviceEventRepository.findAll()).get(1).getEndDate());

    }

    @Test
    public void testIfDeviceBreakEventWillNotChange() throws Exception {
        //given
        DeviceState oldState = new DeviceState("6787", false, false, false, 67, new Date());
        Thread.sleep(5);
        DeviceState newState = new DeviceState("6787", false, false, false, 67, new Date());
        UpdateDeviceEventsCommand command = new UpdateDeviceEventsCommand(oldState, newState);
        deviceBreakRepository.save(new DeviceBreak("6787", new Date(), null, "comment"));

        //when
        updateDeviceEventsHandler.handle(command);
        //then
        //nie wygenerowano eventu, czyli powinien byc jeden otwarty
        assertEquals(new ArrayList<DeviceBreak>(deviceBreakRepository.findAll()).size() , 1);
        //ten even to przerwa z otwartym zakresem daty
        assertNotEquals(null, new ArrayList<>(deviceEventRepository.findAll()).get(0).getStartDate());
        assertEquals(null, new ArrayList<>(deviceEventRepository.findAll()).get(0).getEndDate());

    }

    @Test
    public void testIfDeviceBreakEventWillStopIfStateChangeToOn() throws Exception {
        //given
        DeviceState oldState = new DeviceState("6787", false, false, false, 67, new Date());
        Thread.sleep(5);
        DeviceState newState = new DeviceState("6787", true, false, false, 67, new Date());
        UpdateDeviceEventsCommand command = new UpdateDeviceEventsCommand(oldState, newState);
        deviceBreakRepository.save(new DeviceBreak("6787", new Date(), null, "comment"));

        //when
        updateDeviceEventsHandler.handle(command);
        //then
        //nie wygenerowano eventu, czyli powinien byc jeden
        assertEquals(new ArrayList<DeviceBreak>(deviceBreakRepository.findAll()).size() , 1);
        //ten even to przerwa z zamkniętym zakresem daty
        assertNotEquals(null, new ArrayList<>(deviceEventRepository.findAll()).get(0).getStartDate());
        assertNotEquals(null, new ArrayList<>(deviceEventRepository.findAll()).get(0).getEndDate());

    }

    @Test
    public void testIfDeviceBreakEventWillStopIfStateChangeToOnWhereThereAreFewClosedEvents() throws Exception {
        //given
        DeviceState oldState = new DeviceState("6787", false, false, false, 67, new Date());
        Thread.sleep(5);
        DeviceState newState = new DeviceState("6787", true, false, false, 67, new Date());
        UpdateDeviceEventsCommand command = new UpdateDeviceEventsCommand(oldState, newState);
        deviceBreakRepository.save(new DeviceBreak("6787", new Date(), new Date(), "comment"));
        deviceBreakRepository.save(new DeviceBreak("6787", new Date(), new Date(), "comment"));
        deviceBreakRepository.save(new DeviceBreak("6787", new Date(), new Date(), "comment"));
        deviceBreakRepository.save(new DeviceBreak("6787", new Date(), null, "comment"));

        //when
        updateDeviceEventsHandler.handle(command);
        //then
        //nie wygenerowano eventu, czyli powinino być 4
        assertEquals(new ArrayList<DeviceBreak>(deviceBreakRepository.findAll()).size() , 4);
        //ten even to przerwa z zamkniętym zakresem daty
        assertNotEquals(null, new ArrayList<>(deviceEventRepository.findAll()).get(0).getStartDate());
        assertNotEquals(null, new ArrayList<>(deviceEventRepository.findAll()).get(0).getEndDate());
        assertNotEquals(null, new ArrayList<>(deviceEventRepository.findAll()).get(1).getStartDate());
        assertNotEquals(null, new ArrayList<>(deviceEventRepository.findAll()).get(1).getEndDate());
        assertNotEquals(null, new ArrayList<>(deviceEventRepository.findAll()).get(2).getStartDate());
        assertNotEquals(null, new ArrayList<>(deviceEventRepository.findAll()).get(2).getEndDate());
        assertNotEquals(null, new ArrayList<>(deviceEventRepository.findAll()).get(3).getStartDate());
        assertNotEquals(null, new ArrayList<>(deviceEventRepository.findAll()).get(3).getEndDate());

    }

    //overview
    @Test
    public void testIfDeviceOverviewWillStartIfThereAreNoAnyOverview() throws Exception {
        //given
        DeviceState oldState = new DeviceState("678", true, false, false, 67, new Date());
        Thread.sleep(5);
        DeviceState newState = new DeviceState("678", true, true, false, 67, new Date());
        UpdateDeviceEventsCommand command = new UpdateDeviceEventsCommand(oldState, newState);

        //when
        updateDeviceEventsHandler.handle(command);
        //then
        //wygenerowana jeden event
        assertEquals(1, new ArrayList<>(deviceOverviewRepository.findAll()).size());
        //ten even to przeglad z otwartym zakresem daty
        assertNotEquals(null, new ArrayList<>(deviceEventRepository.findAll()).get(0).getStartDate());
        assertEquals(null,    new ArrayList<>(deviceEventRepository.findAll()).get(0).getEndDate());

    }

    @Test
    public void testIfDeviceOverviewWillStartIfThereAreClosedOverview() throws Exception {
        //given
        DeviceState oldState = new DeviceState("6787", true, false, false, 67, new Date());
        Thread.sleep(5);
        DeviceState newState = new DeviceState("6787", true, true, false, 67, new Date());
        UpdateDeviceEventsCommand command = new UpdateDeviceEventsCommand(oldState, newState);
        deviceOverviewRepository.save(new DeviceOverview("6787", new Date(), new Date(), "comment"));

        //when
        updateDeviceEventsHandler.handle(command);
        //then
        //wygenerowana jeden event, czyli w sumie z poprzednim są dwa
        assertEquals(new ArrayList<>(deviceOverviewRepository.findAll()).size() , 2);
        //ten even to przeglad z otwartym zakresem daty
        assertNotEquals(null, new ArrayList<>(deviceEventRepository.findAll()).get(1).getStartDate());
        assertEquals(null, new ArrayList<>(deviceEventRepository.findAll()).get(1).getEndDate());

    }

    @Test
    public void testIfDeviceOverviewEventWillNotChange() throws Exception {
        //given
        DeviceState oldState = new DeviceState("6787", true, true, false, 67, new Date());
        Thread.sleep(5);
        DeviceState newState = new DeviceState("6787", true, true, false, 67, new Date());
        UpdateDeviceEventsCommand command = new UpdateDeviceEventsCommand(oldState, newState);
        deviceOverviewRepository.save(new DeviceOverview("6787", new Date(), null, "comment"));

        //when
        updateDeviceEventsHandler.handle(command);
        //then
        //nie wygenerowano eventu, czyli powinien byc jeden otwarty
        assertEquals(new ArrayList<>(deviceOverviewRepository.findAll()).size() , 1);
        //ten even to przeglad z otwartym zakresem daty
        assertNotEquals(null, new ArrayList<>(deviceOverviewRepository.findAll()).get(0).getStartDate());
        assertEquals(null, new ArrayList<>(deviceOverviewRepository.findAll()).get(0).getEndDate());

    }

    @Test
    public void testIfDeviceOverviewEventWillStopIfStateChangeToNotOverview() throws Exception {
        //given
        DeviceState oldState = new DeviceState("6787", true, true, false, 67, new Date());
        Thread.sleep(5);
        DeviceState newState = new DeviceState("6787", true, false, false, 67, new Date());
        UpdateDeviceEventsCommand command = new UpdateDeviceEventsCommand(oldState, newState);
        deviceOverviewRepository.save(new DeviceOverview("6787", new Date(), null, "comment"));

        //when
        updateDeviceEventsHandler.handle(command);
        //then
        //nie wygenerowano eventu, czyli powinien byc jeden
        assertEquals(new ArrayList<>(deviceOverviewRepository.findAll()).size() , 1);
        //ten even to przeglad z zamkniętym zakresem daty
        assertNotEquals(null, new ArrayList<>(deviceOverviewRepository.findAll()).get(0).getStartDate());
        assertNotEquals(null, new ArrayList<>(deviceOverviewRepository.findAll()).get(0).getEndDate());

    }

    @Test
    public void testIfDeviceOverviewEventWillStopIfStateChangeToNotOverviewWhereThereAreFewClosedEvents() throws Exception {
        //given
        DeviceState oldState = new DeviceState("6787", true, true, false, 67, new Date());
        Thread.sleep(5);
        DeviceState newState = new DeviceState("6787", true, false, false, 67, new Date());
        UpdateDeviceEventsCommand command = new UpdateDeviceEventsCommand(oldState, newState);
        deviceOverviewRepository.save(new DeviceOverview("6787", new Date(), new Date(), "comment"));
        deviceOverviewRepository.save(new DeviceOverview("6787", new Date(), new Date(), "comment"));
        deviceOverviewRepository.save(new DeviceOverview("6787", new Date(), new Date(), "comment"));
        deviceOverviewRepository.save(new DeviceOverview("6787", new Date(), null, "comment"));

        //when
        updateDeviceEventsHandler.handle(command);
        //then
        //nie wygenerowano eventu, czyli powinino być 4
        assertEquals(4, new ArrayList<>(deviceOverviewRepository.findAll()).size() );
        //ten even to przerwa z zamkniętym zakresem daty
        assertNotEquals(null, new ArrayList<>(deviceOverviewRepository.findAll()).get(0).getStartDate());
        assertNotEquals(null, new ArrayList<>(deviceOverviewRepository.findAll()).get(0).getEndDate());
        assertNotEquals(null, new ArrayList<>(deviceOverviewRepository.findAll()).get(1).getStartDate());
        assertNotEquals(null, new ArrayList<>(deviceEventRepository.findAll()).get(1).getEndDate());
        assertNotEquals(null, new ArrayList<>(deviceOverviewRepository.findAll()).get(2).getStartDate());
        assertNotEquals(null, new ArrayList<>(deviceOverviewRepository.findAll()).get(2).getEndDate());
        assertNotEquals(null, new ArrayList<>(deviceOverviewRepository.findAll()).get(3).getStartDate());
        assertNotEquals(null, new ArrayList<>(deviceOverviewRepository.findAll()).get(3).getEndDate());

    }

    //accident
    @Test
    public void testIfDeviceAccidentWillStartIfThereAreNoAnyAccident() throws Exception {
        //given
        DeviceState oldState = new DeviceState("678", true, false, false, 67, new Date());
        Thread.sleep(5);
        DeviceState newState = new DeviceState("678", true, false, true, 67, new Date());
        UpdateDeviceEventsCommand command = new UpdateDeviceEventsCommand(oldState, newState);
        DeviceConfig config = new DeviceConfig("678", "", new Localisation("","","","","","", ""), new Contact());
        deviceConfigRepository.save(config);
        //when
        updateDeviceEventsHandler.handle(command);
        //then
        //wygenerowana jeden event
        assertEquals(1, new ArrayList<>(deviceAccidentRepository.findAll()).size());
        //ten even to przeglad z otwartym zakresem daty
        assertNotEquals(null, new ArrayList<>(deviceAccidentRepository.findAll()).get(0).getStartDate());
        assertEquals(null,    new ArrayList<>(deviceAccidentRepository.findAll()).get(0).getEndDate());

    }

    @Test
    public void testIfDeviceAccidentWillStartIfThereAreClosedOverview() throws Exception {
        //given
        DeviceState oldState = new DeviceState("6787", true, false, false, 67, new Date());
        Thread.sleep(5);
        DeviceState newState = new DeviceState("6787", true, false, true, 67, new Date());
        UpdateDeviceEventsCommand command = new UpdateDeviceEventsCommand(oldState, newState);
        deviceAccidentRepository.save(new DeviceAccident("6787", new Date(), new Date(), "comment"));
        DeviceConfig config = new DeviceConfig("6787", "", new Localisation("","","","","","", ""), new Contact());
        deviceConfigRepository.save(config);
        //when
        updateDeviceEventsHandler.handle(command);
        //then
        //wygenerowana jeden event, czyli w sumie z poprzednim są dwa
        assertEquals(new ArrayList<>(deviceAccidentRepository.findAll()).size() , 2);
        //ten even to przeglad z otwartym zakresem daty
        assertNotEquals(null, new ArrayList<>(deviceAccidentRepository.findAll()).get(1).getStartDate());
        assertEquals(null, new ArrayList<>(deviceAccidentRepository.findAll()).get(1).getEndDate());

    }

    @Test
    public void testIfDeviceAccidentEventWillNotChange() throws Exception {
        //given
        DeviceState oldState = new DeviceState("6787", true, false, true, 67, new Date());
        Thread.sleep(5);
        DeviceState newState = new DeviceState("6787", true, false, true, 67, new Date());
        UpdateDeviceEventsCommand command = new UpdateDeviceEventsCommand(oldState, newState);
        deviceAccidentRepository.save(new DeviceAccident("6787", new Date(), null, "comment"));

        //when
        updateDeviceEventsHandler.handle(command);
        //then
        //nie wygenerowano eventu, czyli powinien byc jeden otwarty
        assertEquals(new ArrayList<>(deviceAccidentRepository.findAll()).size() , 1);
        //ten even to przeglad z otwartym zakresem daty
        assertNotEquals(null, new ArrayList<>(deviceAccidentRepository.findAll()).get(0).getStartDate());
        assertEquals(null, new ArrayList<>(deviceAccidentRepository.findAll()).get(0).getEndDate());

    }

    @Test
    public void testIfDeviceAccidentEventWillStopIfStateChangeToNotAccident() throws Exception {
        //given
        DeviceState oldState = new DeviceState("6787", true, false, true, 67, new Date());
        Thread.sleep(5);
        DeviceState newState = new DeviceState("6787", true, false, false, 67, new Date());
        UpdateDeviceEventsCommand command = new UpdateDeviceEventsCommand(oldState, newState);
        deviceAccidentRepository.save(new DeviceAccident("6787", new Date(), null, "comment"));

        //when
        updateDeviceEventsHandler.handle(command);
        //then
        //nie wygenerowano eventu, czyli powinien byc jeden
        assertEquals(new ArrayList<>(deviceAccidentRepository.findAll()).size() , 1);
        //ten even to przeglad z zamkniętym zakresem daty
        assertNotEquals(null, new ArrayList<>(deviceAccidentRepository.findAll()).get(0).getStartDate());
        assertNotEquals(null, new ArrayList<>(deviceAccidentRepository.findAll()).get(0).getEndDate());

    }

    @Test
    public void testIfDeviceAccidentEventWillStopIfStateChangeToNotAccidentWhereThereAreFewClosedEvents() throws Exception {
        //given
        DeviceState oldState = new DeviceState("6787", true, false, true, 67, new Date());
        Thread.sleep(5);
        DeviceState newState = new DeviceState("6787", true, false, false, 67, new Date());
        UpdateDeviceEventsCommand command = new UpdateDeviceEventsCommand(oldState, newState);
        deviceAccidentRepository.save(new DeviceAccident("6787", new Date(), new Date(), "comment"));
        deviceAccidentRepository.save(new DeviceAccident("6787", new Date(), new Date(), "comment"));
        deviceAccidentRepository.save(new DeviceAccident("6787", new Date(), new Date(), "comment"));
        deviceAccidentRepository.save(new DeviceAccident("6787", new Date(), null, "comment"));

        //when
        updateDeviceEventsHandler.handle(command);
        //then
        //nie wygenerowano eventu, czyli powinino być 4
        assertEquals(4, new ArrayList<>(deviceAccidentRepository.findAll()).size() );
        //ten even to przerwa z zamkniętym zakresem daty
        assertNotEquals(null, new ArrayList<>(deviceAccidentRepository.findAll()).get(0).getStartDate());
        assertNotEquals(null, new ArrayList<>(deviceAccidentRepository.findAll()).get(0).getEndDate());
        assertNotEquals(null, new ArrayList<>(deviceAccidentRepository.findAll()).get(1).getStartDate());
        assertNotEquals(null, new ArrayList<>(deviceAccidentRepository.findAll()).get(1).getEndDate());
        assertNotEquals(null, new ArrayList<>(deviceAccidentRepository.findAll()).get(2).getStartDate());
        assertNotEquals(null, new ArrayList<>(deviceAccidentRepository.findAll()).get(2).getEndDate());
        assertNotEquals(null, new ArrayList<>(deviceAccidentRepository.findAll()).get(3).getStartDate());
        assertNotEquals(null, new ArrayList<>(deviceAccidentRepository.findAll()).get(3).getEndDate());

    }


}