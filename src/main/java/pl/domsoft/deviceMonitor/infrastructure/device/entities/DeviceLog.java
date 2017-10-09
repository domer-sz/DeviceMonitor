package pl.domsoft.deviceMonitor.infrastructure.device.entities;

import pl.domsoft.deviceMonitor.infrastructure.base.model.entities.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by szymo on 20.04.2017.
 * Encja przechowująca informacje wysłane przez urzadzenie
 */
@Entity
@Table(name = "device_log")
@Cacheable(false)
public class DeviceLog extends BaseEntity {

    private final static String BASE = "DeviceLog.";
    public final static String Q_FIND_LAST_X_DEVICE_LOGS = BASE+"findLastXDeviceLogs";


    /**
     * Id urządzenia które wysłało log
     *
     */
    @Column(name = "device_id")
    private String deviceId;

    /**
     * timestamp w której przyszedł log
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "log_timestamp")
    private Date logTimestamp;

    /**
     * pole przechowujace zliczony czas dzialania urzadzenia
     */
    @Column(name = "time_on_count")
    private int timeOnCount;


    /**
     * wejscie cyfrowe nr 1
     */
    @Column(name = "digital_in1")
    private int digitalIn1;

    /**
     * wejscie cyfrowe nr 2
     */
    @Column(name = "digital_in2")
    private int digitalIn2;


    /**
     * wejscie cyfrowe nr 3
     */
    @Column(name = "digital_in3")
    private int digitalIn3;


    /**
     * wejscie cyfrowe nr 4
     */
    @Column(name = "digital_in4")
    private int digitalIn4;


    /**
     * wejscie cyfrowe nr 5
     */
    @Column(name = "digital_in5")
    private int digitalIn5;


    /**
     * wejscie cyfrowe nr 6
     */
    @Column(name = "digital_in6")
    private int digitalIn6;


    /**
     * wejscie cyfrowe nr 7
     */
    @Column(name = "digital_in7")
    private int digitalIn7;


    /**
     * wejscie cyfrowe nr 8
     */
    @Column(name = "digital_in8")
    private int digitalIn8;


    /**
     * wejscie cyfrowe nr 9
     */
    @Column(name = "digital_in9")
    private int digitalIn9;


    /**
     * wejscie cyfrowe nr 10
     */
    @Column(name = "digital_in10")
    private int digitalIn10;


    /**
     * wejscie cyfrowe nr 11
     */
    @Column(name = "digital_in11")
    private int digitalIn11;


    /**
     * wejscie cyfrowe nr 12
     */
    @Column(name = "digital_in12")
    private int digitalIn12;


    /**
     * wejscie cyfrowe nr 13
     */
    @Column(name = "digital_in13")
    private int digitalIn13;


    /**
     * wejscie cyfrowe nr 14
     */
    @Column(name = "digital_in14")
    private int digitalIn14;


    /**
     * wejscie cyfrowe nr 15
     */
    @Column(name = "digital_in15")
    private int digitalIn15;


    /**
     * wejscie cyfrowe nr 16
     */
    @Column(name = "digital_in16")
    private int digitalIn16;


    /**
     * wejscie cyfrowe nr 17
     */
    @Column(name = "digital_in17")
    private int digitalIn17;


    /**
     * wejscie cyfrowe nr 18
     */
    @Column(name = "digital_in18")
    private int digitalIn18;


    /**
     * wejscie cyfrowe nr 19
     */
    @Column(name = "digital_in19")
    private int digitalIn19;


    /**
     * wejscie cyfrowe nr 20
     */
    @Column(name = "digital_in20")
    private int digitalIn20;


    /**
     * wejscie cyfrowe nr 21
     */
    @Column(name = "digital_in21")
    private int digitalIn21;


    /**
     * wejscie cyfrowe nr 22
     */
    @Column(name = "digital_in22")
    private int digitalIn22;


    /**
     * wejscie cyfrowe nr 23
     */
    @Column(name = "digital_in23")
    private int digitalIn23;


    /**
     * wejscie cyfrowe nr 24
     */
    @Column(name = "digital_in24")
    private int digitalIn24;






    /**
     * wyjście cyfrowe nr 1
     */
    @Column(name = "digital_out1")
    private int digitalOut1;


    /**
     * wyjście cyfrowe nr 2
     */
    @Column(name = "digital_out2")
    private int digitalOut2;


    /**
     * wyjście cyfrowe nr 3
     */
    @Column(name = "digital_out3")
    private int digitalOut3;


    /**
     * wyjście cyfrowe nr 4
     */
    @Column(name = "digital_out4")
    private int digitalOut4;


    /**
     * wyjście cyfrowe nr 5
     */
    @Column(name = "digital_out5")
    private int digitalOut5;


    /**
     * wyjście cyfrowe nr 6
     */
    @Column(name = "digital_out6")
    private int digitalOut6;


    /**
     * wyjście cyfrowe nr 7
     */
    @Column(name = "digital_out7")
    private int digitalOut7;


    /**
     * wyjście cyfrowe nr 8
     */
    @Column(name = "digital_out8")
    private int digitalOut8;


    /**
     * wyjście cyfrowe nr 9
     */
    @Column(name = "digital_out9")
    private int digitalOut9;


    /**
     * wyjście cyfrowe nr 10
     */
    @Column(name = "digital_out10")
    private int digitalOut10;


    /**
     * wyjście cyfrowe nr 11
     */
    @Column(name = "digital_out11")
    private int digitalOut11;


    /**
     * wyjście cyfrowe nr 12
     */
    @Column(name = "digital_out12")
    private int digitalOut12;


    /**
     * wyjście cyfrowe nr 13
     */
    @Column(name = "digital_out13")
    private int digitalOut13;


    /**
     * wyjście cyfrowe nr 14
     */
    @Column(name = "digital_out14")
    private int digitalOut14;


    /**
     * wyjście cyfrowe nr 15
     */
    @Column(name = "digital_out15")
    private int digitalOut15;


    /**
     * wyjście cyfrowe nr 16
     */
    @Column(name = "digital_out16")
    private int digitalOut16;


    /**
     * wyjście cyfrowe nr 17
     */
    @Column(name = "digital_out17")
    private int digitalOut17;


    /**
     * wyjście cyfrowe nr 18
     */
    @Column(name = "digital_out18")
    private int digitalOut18;


    /**
     * wyjście cyfrowe nr 19
     */
    @Column(name = "digital_out19")
    private int digitalOut19;


    /**
     * wyjście cyfrowe nr 20
     */
    @Column(name = "digital_out20")
    private int digitalOut20;


    /**
     * wyjście cyfrowe nr 21
     */
    @Column(name = "digital_out21")
    private int digitalOut21;


    /**
     * wyjście cyfrowe nr 22
     */
    @Column(name = "digital_out22")
    private int digitalOut22;


    /**
     * wyjście cyfrowe nr 23
     */
    @Column(name = "digital_out23")
    private int digitalOut23;


    /**
     * wyjście cyfrowe nr 24
     */
    @Column(name = "digital_out24")
    private int digitalOut24;


    /**
     * wejście analogowe nr 1
     */
    @Column(name = "analog_in1")
    private int analogIn1;

    /**
     * wejście analogowe nr 2
     */
    @Column(name = "analog_in2")
    private int analogIn2;


    /**
     * wejście analogowe nr 3
     */
    @Column(name = "analog_in3")
    private int analogIn3;


    /**
     * wejście analogowe nr 4
     */
    @Column(name = "analog_in4")
    private int analogIn4;


    /**
     * wejście analogowe nr 5
     */
    @Column(name = "analog_in5")
    private int analogIn5;


    /**
     * wejście analogowe nr 6
     */
    @Column(name = "analog_in6")
    private int analogIn6;


    /**
     * wejście analogowe nr 7
     */
    @Column(name = "analog_in7")
    private int analogIn7;


    /**
     * wejście analogowe nr 8
     */
    @Column(name = "analog_in8")
    private int analogIn8;


    /**
     * wyjście analogowe nr 1
     */
    @Column(name = "analog_out1")
    private int analogOutOut1;


    /**
     * wyjście analogowe nr 2
     */
    @Column(name = "analog_out2")
    private int analogOutOut2;


    /**
     * wyjście analogowe nr 3
     */
    @Column(name = "analog_out3")
    private int analogOutOut3;


    /**
     * wyjście analogowe nr 4
     */
    @Column(name = "analog_out4")
    private int analogOutOut4;


    /**
     * wyjście analogowe nr 5
     */
    @Column(name = "analog_out5")
    private int analogOutOut5;


    /**
     * wyjście analogowe nr 6
     */
    @Column(name = "analog_out6")
    private int analogOutOut6;


    /**
     * wyjście analogowe nr 7
     */
    @Column(name = "analog_out7")
    private int analogOutOut7;


    /**
     * wyjście analogowe nr 8
     */
    @Column(name = "analog_out8")
    private int analogOutOut8;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getLogTimestamp() {
        return logTimestamp;
    }

    public void setLogTimestamp(Date logTimestamp) {
        this.logTimestamp = logTimestamp;
    }

    public int getTimeOnCount() {
        return timeOnCount;
    }

    public void setTimeOnCount(int timeOnCount) {
        this.timeOnCount = timeOnCount;
    }

    public int getDigitalIn1() {
        return digitalIn1;
    }

    public void setDigitalIn1(int digitalIn1) {
        this.digitalIn1 = digitalIn1;
    }

    public int getDigitalIn2() {
        return digitalIn2;
    }

    public void setDigitalIn2(int digitalIn2) {
        this.digitalIn2 = digitalIn2;
    }

    public int getDigitalIn3() {
        return digitalIn3;
    }

    public void setDigitalIn3(int digitalIn3) {
        this.digitalIn3 = digitalIn3;
    }

    public int getDigitalIn4() {
        return digitalIn4;
    }

    public void setDigitalIn4(int digitalIn4) {
        this.digitalIn4 = digitalIn4;
    }

    public int getDigitalIn5() {
        return digitalIn5;
    }

    public void setDigitalIn5(int digitalIn5) {
        this.digitalIn5 = digitalIn5;
    }

    public int getDigitalIn6() {
        return digitalIn6;
    }

    public void setDigitalIn6(int digitalIn6) {
        this.digitalIn6 = digitalIn6;
    }

    public int getDigitalIn7() {
        return digitalIn7;
    }

    public void setDigitalIn7(int digitalIn7) {
        this.digitalIn7 = digitalIn7;
    }

    public int getDigitalIn8() {
        return digitalIn8;
    }

    public void setDigitalIn8(int digitalIn8) {
        this.digitalIn8 = digitalIn8;
    }

    public int getDigitalIn9() {
        return digitalIn9;
    }

    public void setDigitalIn9(int digitalIn9) {
        this.digitalIn9 = digitalIn9;
    }

    public int getDigitalIn10() {
        return digitalIn10;
    }

    public void setDigitalIn10(int digitalIn10) {
        this.digitalIn10 = digitalIn10;
    }

    public int getDigitalIn11() {
        return digitalIn11;
    }

    public void setDigitalIn11(int digitalIn11) {
        this.digitalIn11 = digitalIn11;
    }

    public int getDigitalIn12() {
        return digitalIn12;
    }

    public void setDigitalIn12(int digitalIn12) {
        this.digitalIn12 = digitalIn12;
    }

    public int getDigitalIn13() {
        return digitalIn13;
    }

    public void setDigitalIn13(int digitalIn13) {
        this.digitalIn13 = digitalIn13;
    }

    public int getDigitalIn14() {
        return digitalIn14;
    }

    public void setDigitalIn14(int digitalIn14) {
        this.digitalIn14 = digitalIn14;
    }

    public int getDigitalIn15() {
        return digitalIn15;
    }

    public void setDigitalIn15(int digitalIn15) {
        this.digitalIn15 = digitalIn15;
    }

    public int getDigitalIn16() {
        return digitalIn16;
    }

    public void setDigitalIn16(int digitalIn16) {
        this.digitalIn16 = digitalIn16;
    }

    public int getDigitalIn17() {
        return digitalIn17;
    }

    public void setDigitalIn17(int digitalIn17) {
        this.digitalIn17 = digitalIn17;
    }

    public int getDigitalIn18() {
        return digitalIn18;
    }

    public void setDigitalIn18(int digitalIn18) {
        this.digitalIn18 = digitalIn18;
    }

    public int getDigitalIn19() {
        return digitalIn19;
    }

    public void setDigitalIn19(int digitalIn19) {
        this.digitalIn19 = digitalIn19;
    }

    public int getDigitalIn20() {
        return digitalIn20;
    }

    public void setDigitalIn20(int digitalIn20) {
        this.digitalIn20 = digitalIn20;
    }

    public int getDigitalIn21() {
        return digitalIn21;
    }

    public void setDigitalIn21(int digitalIn21) {
        this.digitalIn21 = digitalIn21;
    }

    public int getDigitalIn22() {
        return digitalIn22;
    }

    public void setDigitalIn22(int digitalIn22) {
        this.digitalIn22 = digitalIn22;
    }

    public int getDigitalIn23() {
        return digitalIn23;
    }

    public void setDigitalIn23(int digitalIn23) {
        this.digitalIn23 = digitalIn23;
    }

    public int getDigitalIn24() {
        return digitalIn24;
    }

    public void setDigitalIn24(int digitalIn24) {
        this.digitalIn24 = digitalIn24;
    }

    public int getDigitalOut1() {
        return digitalOut1;
    }

    public void setDigitalOut1(int digitalOut1) {
        this.digitalOut1 = digitalOut1;
    }

    public int getDigitalOut2() {
        return digitalOut2;
    }

    public void setDigitalOut2(int digitalOut2) {
        this.digitalOut2 = digitalOut2;
    }

    public int getDigitalOut3() {
        return digitalOut3;
    }

    public void setDigitalOut3(int digitalOut3) {
        this.digitalOut3 = digitalOut3;
    }

    public int getDigitalOut4() {
        return digitalOut4;
    }

    public void setDigitalOut4(int digitalOut4) {
        this.digitalOut4 = digitalOut4;
    }

    public int getDigitalOut5() {
        return digitalOut5;
    }

    public void setDigitalOut5(int digitalOut5) {
        this.digitalOut5 = digitalOut5;
    }

    public int getDigitalOut6() {
        return digitalOut6;
    }

    public void setDigitalOut6(int digitalOut6) {
        this.digitalOut6 = digitalOut6;
    }

    public int getDigitalOut7() {
        return digitalOut7;
    }

    public void setDigitalOut7(int digitalOut7) {
        this.digitalOut7 = digitalOut7;
    }

    public int getDigitalOut8() {
        return digitalOut8;
    }

    public void setDigitalOut8(int digitalOut8) {
        this.digitalOut8 = digitalOut8;
    }

    public int getDigitalOut9() {
        return digitalOut9;
    }

    public void setDigitalOut9(int digitalOut9) {
        this.digitalOut9 = digitalOut9;
    }

    public int getDigitalOut10() {
        return digitalOut10;
    }

    public void setDigitalOut10(int digitalOut10) {
        this.digitalOut10 = digitalOut10;
    }

    public int getDigitalOut11() {
        return digitalOut11;
    }

    public void setDigitalOut11(int digitalOut11) {
        this.digitalOut11 = digitalOut11;
    }

    public int getDigitalOut12() {
        return digitalOut12;
    }

    public void setDigitalOut12(int digitalOut12) {
        this.digitalOut12 = digitalOut12;
    }

    public int getDigitalOut13() {
        return digitalOut13;
    }

    public void setDigitalOut13(int digitalOut13) {
        this.digitalOut13 = digitalOut13;
    }

    public int getDigitalOut14() {
        return digitalOut14;
    }

    public void setDigitalOut14(int digitalOut14) {
        this.digitalOut14 = digitalOut14;
    }

    public int getDigitalOut15() {
        return digitalOut15;
    }

    public void setDigitalOut15(int digitalOut15) {
        this.digitalOut15 = digitalOut15;
    }

    public int getDigitalOut16() {
        return digitalOut16;
    }

    public void setDigitalOut16(int digitalOut16) {
        this.digitalOut16 = digitalOut16;
    }

    public int getDigitalOut17() {
        return digitalOut17;
    }

    public void setDigitalOut17(int digitalOut17) {
        this.digitalOut17 = digitalOut17;
    }

    public int getDigitalOut18() {
        return digitalOut18;
    }

    public void setDigitalOut18(int digitalOut18) {
        this.digitalOut18 = digitalOut18;
    }

    public int getDigitalOut19() {
        return digitalOut19;
    }

    public void setDigitalOut19(int digitalOut19) {
        this.digitalOut19 = digitalOut19;
    }

    public int getDigitalOut20() {
        return digitalOut20;
    }

    public void setDigitalOut20(int digitalOut20) {
        this.digitalOut20 = digitalOut20;
    }

    public int getDigitalOut21() {
        return digitalOut21;
    }

    public void setDigitalOut21(int digitalOut21) {
        this.digitalOut21 = digitalOut21;
    }

    public int getDigitalOut22() {
        return digitalOut22;
    }

    public void setDigitalOut22(int digitalOut22) {
        this.digitalOut22 = digitalOut22;
    }

    public int getDigitalOut23() {
        return digitalOut23;
    }

    public void setDigitalOut23(int digitalOut23) {
        this.digitalOut23 = digitalOut23;
    }

    public int getDigitalOut24() {
        return digitalOut24;
    }

    public void setDigitalOut24(int digitalOut24) {
        this.digitalOut24 = digitalOut24;
    }

    public int getAnalogIn1() {
        return analogIn1;
    }

    public void setAnalogIn1(int analogIn1) {
        this.analogIn1 = analogIn1;
    }

    public int getAnalogIn2() {
        return analogIn2;
    }

    public void setAnalogIn2(int analogIn2) {
        this.analogIn2 = analogIn2;
    }

    public int getAnalogIn3() {
        return analogIn3;
    }

    public void setAnalogIn3(int analogIn3) {
        this.analogIn3 = analogIn3;
    }

    public int getAnalogIn4() {
        return analogIn4;
    }

    public void setAnalogIn4(int analogIn4) {
        this.analogIn4 = analogIn4;
    }

    public int getAnalogIn5() {
        return analogIn5;
    }

    public void setAnalogIn5(int analogIn5) {
        this.analogIn5 = analogIn5;
    }

    public int getAnalogIn6() {
        return analogIn6;
    }

    public void setAnalogIn6(int analogIn6) {
        this.analogIn6 = analogIn6;
    }

    public int getAnalogIn7() {
        return analogIn7;
    }

    public void setAnalogIn7(int analogIn7) {
        this.analogIn7 = analogIn7;
    }

    public int getAnalogIn8() {
        return analogIn8;
    }

    public void setAnalogIn8(int analogIn8) {
        this.analogIn8 = analogIn8;
    }

    public int getAnalogOutOut1() {
        return analogOutOut1;
    }

    public void setAnalogOutOut1(int analogOutOut1) {
        this.analogOutOut1 = analogOutOut1;
    }

    public int getAnalogOutOut2() {
        return analogOutOut2;
    }

    public void setAnalogOutOut2(int analogOutOut2) {
        this.analogOutOut2 = analogOutOut2;
    }

    public int getAnalogOutOut3() {
        return analogOutOut3;
    }

    public void setAnalogOutOut3(int analogOutOut3) {
        this.analogOutOut3 = analogOutOut3;
    }

    public int getAnalogOutOut4() {
        return analogOutOut4;
    }

    public void setAnalogOutOut4(int analogOutOut4) {
        this.analogOutOut4 = analogOutOut4;
    }

    public int getAnalogOutOut5() {
        return analogOutOut5;
    }

    public void setAnalogOutOut5(int analogOutOut5) {
        this.analogOutOut5 = analogOutOut5;
    }

    public int getAnalogOutOut6() {
        return analogOutOut6;
    }

    public void setAnalogOutOut6(int analogOutOut6) {
        this.analogOutOut6 = analogOutOut6;
    }

    public int getAnalogOutOut7() {
        return analogOutOut7;
    }

    public void setAnalogOutOut7(int analogOutOut7) {
        this.analogOutOut7 = analogOutOut7;
    }

    public int getAnalogOutOut8() {
        return analogOutOut8;
    }

    public void setAnalogOutOut8(int analogOutOut8) {
        this.analogOutOut8 = analogOutOut8;
    }


        public static DeviceLog buildDeviceLogWithRandomData(String deviceId, Date logDate){
        DeviceLog deviceLog = new DeviceLog();
        deviceLog.setDeviceId(deviceId);
        deviceLog.setLogTimestamp(logDate);

        int analogInMin = -65000;
        int analogInMax = 65000;

        int analogOutMin = -65000;
        int analogOutMax = 65000;

        deviceLog.setAnalogIn1(ThreadLocalRandom.current().nextInt(analogInMin, analogInMax+ 1));
        deviceLog.setAnalogIn2(ThreadLocalRandom.current().nextInt(analogInMin, analogInMax+ 1));
        deviceLog.setAnalogIn3(ThreadLocalRandom.current().nextInt(analogInMin, analogInMax+ 1));
        deviceLog.setAnalogIn4(ThreadLocalRandom.current().nextInt(analogInMin, analogInMax+ 1));
        deviceLog.setAnalogIn5(ThreadLocalRandom.current().nextInt(analogInMin, analogInMax+ 1));
        deviceLog.setAnalogIn6(ThreadLocalRandom.current().nextInt(analogInMin, analogInMax+ 1));
        deviceLog.setAnalogIn7(ThreadLocalRandom.current().nextInt(analogInMin, analogInMax+ 1));
        deviceLog.setAnalogIn8(ThreadLocalRandom.current().nextInt(analogInMin, analogInMax+ 1));
//        deviceLog.setAnalogIn9(ThreadLocalRandom.current().nextInt(analogInMin, analogInMax+ 1));
//        deviceLog.setAnalogIn10(ThreadLocalRandom.current().nextInt(analogInMin, analogInMax+ 1));
//
//        deviceLog.setAnalogOut1(ThreadLocalRandom.current().nextInt(analogOutMin, analogOutMax+ 1));
//        deviceLog.setAnalogOut2(ThreadLocalRandom.current().nextInt(analogOutMin, analogOutMax+ 1));
//        deviceLog.setAnalogOut3(ThreadLocalRandom.current().nextInt(analogOutMin, analogOutMax+ 1));
//        deviceLog.setAnalogOut4(ThreadLocalRandom.current().nextInt(analogOutMin, analogOutMax+ 1));
//        deviceLog.setAnalogOut5(ThreadLocalRandom.current().nextInt(analogOutMin, analogOutMax+ 1));
//        deviceLog.setAnalogOut6(ThreadLocalRandom.current().nextInt(analogOutMin, analogOutMax+ 1));
//        deviceLog.setAnalogOut7(ThreadLocalRandom.current().nextInt(analogOutMin, analogOutMax+ 1));
//        deviceLog.setAnalogOut8(ThreadLocalRandom.current().nextInt(analogOutMin, analogOutMax+ 1));
//        deviceLog.setAnalogOut9(ThreadLocalRandom.current().nextInt(analogOutMin, analogOutMax+ 1));
//        deviceLog.setAnalogOut10(ThreadLocalRandom.current().nextInt(analogOutMin, analogOutMax+ 1));


        deviceLog.setDigitalIn1(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn2(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn3(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn4(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn5(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn6(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn7(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn8(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn9(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn10(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn11(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn12(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn13(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn14(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn15(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn16(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn17(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn18(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn19(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn20(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn21(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn22(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn23(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalIn24(ThreadLocalRandom.current().nextInt(0, 1 + 1));
//        deviceLog.setDigitalIn25(ThreadLocalRandom.current().nextInt(0, 1 + 1));

        deviceLog.setDigitalOut1(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut2(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut3(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut4(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut5(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut6(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut7(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut8(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut9(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut10(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut11(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut12(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut13(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut14(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut15(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut16(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut17(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut18(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut19(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut20(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut21(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut22(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut23(ThreadLocalRandom.current().nextInt(0, 1 + 1));
        deviceLog.setDigitalOut24(ThreadLocalRandom.current().nextInt(0, 1 + 1));
//        deviceLog.setDigitalOut25(ThreadLocalRandom.current().nextInt(0, 1 + 1));

        deviceLog.setTimeOnCount(ThreadLocalRandom.current().nextInt(900000, 1900000 + 1));

        return deviceLog;
    }
}
