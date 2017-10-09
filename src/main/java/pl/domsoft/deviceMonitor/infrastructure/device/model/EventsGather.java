package pl.domsoft.deviceMonitor.infrastructure.device.model;

import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceAccident;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceBreak;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceOverview;

import java.util.List;

/**
 * Created by szymo on 26.08.2017.
 * Obiekt agregujący wydarzenia urządzeń z podziałem na typy
 */
public class EventsGather {
    private final List<DeviceBreak> deviceBreaks;
    private final List<DeviceAccident> deviceAccidents;
    private final List<DeviceOverview> deviceOverviews;

    public EventsGather(List<DeviceBreak> deviceBreaks, List<DeviceAccident> deviceAccidents, List<DeviceOverview> deviceOverviews) {
        this.deviceBreaks = deviceBreaks;
        this.deviceAccidents = deviceAccidents;
        this.deviceOverviews = deviceOverviews;
    }


    public List<DeviceBreak> getDeviceBreaks() {
        return deviceBreaks;
    }

    public List<DeviceAccident> getDeviceAccidents() {
        return deviceAccidents;
    }

    public List<DeviceOverview> getDeviceOverviews() {
        return deviceOverviews;
    }
}
