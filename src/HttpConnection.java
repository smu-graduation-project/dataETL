import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HttpConnection {
    private JDBCController jdbcController;
    private URL url;

    public HttpConnection(JDBCController jdbcController, URL url) {
        this.jdbcController = jdbcController;
        this.url = url;
    }

    public String setConnection() throws IOException {

        HttpURLConnection httpURLConnection = setHttpURLConnection(url);

        List<Data> dataList = getDataList();
        postData(httpURLConnection, dataList);

        return getResponse(httpURLConnection);
    }

    private void postData(HttpURLConnection httpURLConnection, List<Data> dataList) throws IOException {
        OutputStream outputStream = httpURLConnection.getOutputStream();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (Data data : dataList) {
            stringBuilder.append(data.toString() + ", ");
        }
        stringBuilder.append("]");

        System.out.println(stringBuilder.toString());

        byte[] input = stringBuilder.toString().getBytes("utf-8");
        outputStream.write(input, 0, input.length);
    }

    private String getResponse(HttpURLConnection httpURLConnection) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine;

        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }
        return response.toString();
    }

    private HttpURLConnection setHttpURLConnection(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type","applicaiton/json;utf-8");
        httpURLConnection.setRequestProperty("Accept","application/json");
        httpURLConnection.setDoOutput(true);

        return httpURLConnection;
    }

    // 이후 수정
    private List<Data> getDataList() {
        return jdbcController.loadDataList();
    }
}
