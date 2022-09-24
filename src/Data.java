import java.time.LocalDateTime;

public class Data {
    public Data(Long nodeId, LocalDateTime timeStamp, Long sequence, double temperature, double voltage, double electricCurrent) {
        this.nodePort = nodeId;
        this.timeStamp = timeStamp;
        this.sequence = sequence;

        this.temperature = temperature;
        this.voltage = voltage;
        this.electricCurrent = electricCurrent;
    }

    private Long nodePort;

    private LocalDateTime timeStamp;
    private Long sequence;

    private double temperature;
    private double voltage;
    private double electricCurrent;

    public Long getNodePort() {
        return this.nodePort;
    }

    public LocalDateTime getTimeStamp() {
        return this.timeStamp;
    }

    public Long getSequence() {
        return this.sequence;
    }

    public double getTemperaturee() {
        return this.temperature;
    }
    public double getVoltage() {
        return this.voltage;
    }
    public double getElectricCurrent() {
        return this.electricCurrent;
    }
    @Override
    public String toString() {

        return "{" +
                "\"port\" : " + nodePort + ", " +
                "\"timeStamp\" : " + "\"" + timeStamp.toString() + "\"" + ", " +
                "\"sequence\" : " + sequence + ", " +
                "\"temperature\" : " + String.format("%.2f", temperature) + ", " +
                "\"voltage\" : " + String.format("%.2f", voltage) + ", " +
                "\"electricCurrent\" : " + String.format("%.2f", electricCurrent) +
                "}";
    }
}
