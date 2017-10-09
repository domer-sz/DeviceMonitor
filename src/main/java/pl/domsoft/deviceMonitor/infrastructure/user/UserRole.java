package pl.domsoft.deviceMonitor.infrastructure.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by szymo on 09.06.2017.
 * Enum zbierające możliwe role użytkowników
 * Spring Security automatycznie dodaje przedrostek ROLE_ więc aby
 * np dopuszczać do jakiegoś endpointa tylko administratora trzeba użyć @PreAuthorize("hasRole('ROLE_ADMIN')")
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum UserRole {
    USER(0L, "ROLE_USER"),
    SERVICEMAN(1L, "ROLE_SERVICEMAN"),
    ADMIN(2L, "ROLE_ADMIN");

    private Long value;
    private String label;

    UserRole(Long value, String label) {
        this.value = value;
        this.label = label;
    }
    public Long getValue() {
        return this.value;
    }
    public String getLabel(){
        return this.label;
    }

    @JsonCreator
    public static UserRole creator(long enumType) throws AppException {
        final Optional<UserRole> any = Arrays.stream(UserRole.values()).filter(en -> en.getValue().equals(enumType)).findAny();
        if(any.isPresent()){
            return any.get();
        }else{
            throw new AppException( UserRole.class + " błąd mapowania: " + enumType, UserRole.class + " błąd mapowania: " + enumType, null);
        }
    }

    /**
     * Remove "ROLE_" prefix to match some spring security matcher's
     * @return
     */
    public String labelWithoutPrefix(){
        return this.label.substring(5);
    }
    public String viewLabel(){
        if(this.equals(UserRole.SERVICEMAN)) return "Serwisant";
        if(this.equals(UserRole.ADMIN)) return "Administrator";
        return "Użytkownik";
    }
    @JsonCreator
    public static UserRole forValue(Long value) {
        switch (value.intValue()) {
            case 0:
                return UserRole.USER;
            case 1:
                return UserRole.SERVICEMAN;
            case 2:
                return UserRole.ADMIN;
            default:
                return UserRole.USER;
        }
    }

    @JsonValue
    public int toValue() {
        return ordinal();
    }

    public Set<String> getRoles(){
        if(this.equals(UserRole.SERVICEMAN)) return new HashSet<>(Arrays.asList(
                UserRole.USER.getLabel(),
                UserRole.SERVICEMAN.getLabel())
        );
        if(this.equals(UserRole.ADMIN)) return new HashSet<>(
                Arrays.asList(
                    UserRole.USER.getLabel(),
                    UserRole.SERVICEMAN.getLabel(),
                    UserRole.ADMIN.getLabel()
                )
        );
       return new HashSet<>(Arrays.asList(UserRole.USER.getLabel()));

    }

    @JsonIgnore
    public static List<Map.Entry<Long, String>> getAllEnumValues(){
       return Arrays.stream(UserRole.values())
                .map(enm -> new AbstractMap.SimpleImmutableEntry<Long, String>( enm.getValue(), enm.getLabel()))
                .collect(Collectors.toList());
    }
}
