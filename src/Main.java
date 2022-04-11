import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws IOException {

        // URL 수정해서, 데이터 보내보기
        URL url = new URL("");
        HttpURLConnection httpURLConnection = setHttpURLConnection(url);
        Data data = makeData();
        postData(httpURLConnection, data);
        StringBuilder response = getResponse(httpURLConnection);

        System.out.println(response);
    }

    private static void postData(HttpURLConnection httpURLConnection, Data data) throws IOException {
        OutputStream outputStream = httpURLConnection.getOutputStream();
        byte[] input = data.toString().getBytes("utf-8");
        outputStream.write(input, 0, input.length);
    }

    private static StringBuilder getResponse(HttpURLConnection httpURLConnection) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine;

        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }
        return response;
    }

    private static HttpURLConnection setHttpURLConnection(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type","applicaiton/json;utf-8");
        httpURLConnection.setRequestProperty("Accept","application/json");
        httpURLConnection.setDoOutput(true);

        return httpURLConnection;
    }

    // 이후 수정
    private static Data makeData() {
        Data data = new Data(1L);
        return data;
    }
}
