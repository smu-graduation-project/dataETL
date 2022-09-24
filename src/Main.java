
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private static final String apiUrl = "http://220.149.31.104:9090/api/realtime/rawData";
    private static final String dbUrl = "jdbc:mysql://169.254.101.71/lora_customer?useUnicode=true&"
            + "useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private static Long lastId = 1L;
    public static void main(String[] args) throws IOException, InterruptedException, ParseException {

        while (true) {
            Connection conn = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(dbUrl, "root", "root");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            JDBCController jdbcController = new JDBCController(lastId, conn);
            HttpConnection httpConnection = new HttpConnection(jdbcController, apiUrl);

            System.out.println(httpConnection.setConnection());
            // api로 데이터를 보내야 한다.
            lastId = jdbcController.getLastId();
            Thread.sleep(1000);
        }
    }
}
