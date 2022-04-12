import java.time.LocalDateTime;

public class Data {
    public Data(Long nodeId, LocalDateTime timeStamp, Long sequence, double temperature, double voltage, double electricCurrent) {
        this.nodeId = nodeId;
        this.timeStamp = timeStamp;
        this.sequence = sequence;

        this.temperature = temperature;
        this.voltage = voltage;
        this.electricCurrent = electricCurrent;
    }

    private Long nodeId;

    private LocalDateTime timeStamp;
    private Long sequence;

    private double temperature;
    private double voltage;
    private double electricCurrent;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{");
        stringBuilder.append("node_id : " + nodeId + ", ");
        stringBuilder.append("timeStamp : " + "\"" + timeStamp.toString() + "\"" + ", ");
        stringBuilder.append("sequence : " + sequence + ", ");

        stringBuilder.append("temperature : " + temperature + ", ");
        stringBuilder.append("voltage : " + voltage + ", ");
        stringBuilder.append("electricCurrent : " + electricCurrent);

        stringBuilder.append("}");

        return stringBuilder.toString();
    }
}
