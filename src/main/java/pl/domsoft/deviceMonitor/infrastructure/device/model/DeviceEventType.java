package pl.domsoft.deviceMonitor.infrastructure.device.model;

/**
 * Created by szymo on 14.05.2017.
 * Enum typów eventów urządzeń
 */
public enum DeviceEventType {
    deviceAccident(0l, "DEVICE_ACCIDENT"),
    deviceBreak(1l, "DEVICE_BREAK"),
    deviceOverview(2l, "DEVICE_OVERVIEW");

    private Long id;
    private String name;

    DeviceEventType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static DeviceEventType getByValue(Long id)
    {
        for (DeviceEventType type : DeviceEventType.values())
        {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }
}
