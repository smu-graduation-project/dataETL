import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JDBCController {
    private Long lastId;
    private Connection conn;

    private double temperature;
    private double voltage;
    private double electricCurrent;

    public JDBCController(Long lastId, Connection conn) {
        this.lastId = lastId;
        this.conn = conn;
    }

    public Long getLastId() {
        return lastId;
    }

    public List<Data> loadDataList() {
        List<Data> dataList = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM appdata WHERE id > " + this.lastId + " and port > 30;");

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
                dataList.add(new Data(nodePort, localDateTime, sequence, temperature, voltage, electricCurrent));
                lastId = Long.valueOf(rs.getString("id"));
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    private void transformStringData(String stringData) throws SQLException {
        temperature = makeStringDataToDouble(stringData, 0);
        voltage = makeStringDataToDouble(stringData, 10);
        electricCurrent = temperature + voltage;
    }

    private double makeStringDataToDouble(String stringData,int start) {
        double data = 0;
        for (int i = start; i < stringData.length(); i ++) {

            if (stringData.charAt(i) == 'e') {
                data += Integer.parseInt(String.valueOf(stringData.charAt(i-2)));
                data += Integer.parseInt(String.valueOf(stringData.charAt(i-4))) * 10;
                data += Integer.parseInt(String.valueOf(stringData.charAt(i+1))) * 0.1;
                data += Integer.parseInt(String.valueOf(stringData.charAt(i+3))) * 0.01;

                return data;
            }
        }
        return -1;
    }
}
