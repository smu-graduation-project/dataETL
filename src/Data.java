import java.time.LocalDateTime;

public class Data {
    public Data(Long nodeId, LocalDateTime timeStamp, Long sequence, double temperature, double humidity) {
        this.nodeId = nodeId;
        this.timeStamp = timeStamp;
        this.sequence = sequence;

        this.temperature = temperature;
        this.humidity = humidity;
    }

    private Long nodeId;

    private LocalDateTime timeStamp;
    private Long sequence;

    private double temperature;
    private double humidity;;

    @Override
    public String toString() {

        return "{" +
                "\"node_id\" : " + nodeId + ", " +
                "\"timeStamp\" : " + "\"" + timeStamp.toString() + "\"" + ", " +
                "\"sequence\" : " + sequence + ", " +
                "\"temperature\" : " + temperature + ", " +
                "\"voltage\" : " + humidity + ", " +
                "}";
    }
}
