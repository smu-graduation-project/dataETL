import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private static final String apiUrl = "https://jsonplaceholder.typicode.com/posts";
    private static final String dbUrl = "jdbc:mysql://localhost/loraDummyData?useUnicode=true&"
            + "useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static void main(String[] args) throws IOException, InterruptedException {

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, "id", "pw");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        JDBCController jdbcController = new JDBCController(1L, conn);
        HttpConnection httpConnection = new HttpConnection(jdbcController, new URL(apiUrl));

        while (true) {
            System.out.println(httpConnection.setConnection());
            // api로 데이터를 보내야 한다.
            Thread.sleep(100);
        }

    }
}
