import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JDBCController {
    private Long lastId;
    private Connection conn;

    private double temperature;
    private double humidity;
//    private double voltage;
//    private double electricCurrent;

    private int index;

    public JDBCController(Long lastId, Connection conn) {
        this.lastId = lastId;
        this.conn = conn;
    }

    public List<Data> loadDataList() {
        List<Data> dataList = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM dummyData WHERE id > " + this.lastId + ";");

//            +--------------+--------------------+------+-----+---------+----------------+
//            | Field        | Type               | Null | Key | Default | Extra          |
//            +--------------+--------------------+------+-----+---------+----------------+
//            | id           | bigint unsigned    | NO   | PRI | NULL    | auto_increment |
//            | mote         | bigint unsigned    | NO   |     | NULL    |                |
//            | time         | timestamp          | YES  |     | NULL    |                |
//            | time_usec    | mediumint unsigned | YES  |     | NULL    |                |
//            | accurateTime | tinyint(1)         | YES  |     | NULL    |                |
//            | seqNo        | int unsigned       | NO   |     | NULL    |                |
//            | port         | tinyint unsigned   | YES  |     | NULL    |                |
//            | data         | varchar(500)       | YES  |     | NULL    |                |
//            +--------------+--------------------+------+-----+---------+----------------+

            while(rs.next()) {
                //mote와 port를 조합하여, nodeId를 만들면 될 듯 -> 둘다 bigint Type
                Long nodePort = Long.valueOf(rs.getString("port"));

                LocalDateTime localDateTime = LocalDateTime.parse(rs.getString("time").replace(' ', 'T'));
                Long sequence = Long.valueOf(rs.getString("seqNo"));

                String stringData = rs.getString("data");
                transformStringData(stringData);

//                dataList.add(new Data(nodePort, localDateTime, sequence, temperature, voltage, electricCurrent));
                dataList.add(new Data(nodePort, localDateTime, sequence, temperature, humidity));
                lastId = Long.valueOf(rs.getString("id"));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    private void transformStringData(String stringData) throws SQLException {
        index = 0;
        temperature = makeStringDataToDouble(stringData);
        humidity = makeStringDataToDouble(stringData);
//        electricCurrent = makeStringDataToDouble(stringData);
    }

    private double makeStringDataToDouble(String stringData) {
        double data = 0;
        for (int i = index; i < stringData.length(); i ++) {
            if (stringData.charAt(i) == '2') {
                data += Integer.parseInt(String.valueOf(stringData.charAt(i+3))) * 0.1;
                data += Integer.parseInt(String.valueOf(stringData.charAt(i+5))) * 0.01;

                int temp = 0;
                for (int j = i; j > index; j -= 2) {
                    data += Integer.parseInt(String.valueOf(stringData.charAt(j - 1))) * Math.pow(10, temp);
                    temp++;
                }
                index = i + 6;
                return data;
            }
        }
        return -1;
    }
}
