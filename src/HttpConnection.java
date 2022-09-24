import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HttpConnection {
    private JDBCController jdbcController;
    private URL url;

    public HttpConnection(JDBCController jdbcController, String url) throws MalformedURLException {
        this.jdbcController = jdbcController;
        this.url = new URL(url);
    }

    public String setConnection() throws IOException, ParseException {
        List<Data> dataList = getDataList();
        for(Data data : dataList) {
            HttpURLConnection httpURLConnection = setHttpURLConnection(url);
            postData(httpURLConnection, data);
            System.out.println(getResponse(httpURLConnection));
        }
        return "success";
    }

    private void postData(HttpURLConnection httpURLConnection, Data data) throws IOException {
        System.out.println(data.toString());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), StandardCharsets.UTF_8));
        bw.write( createRequestBody(data).toString());
        System.out.println(createRequestBody(data).toString());
        bw.flush();
        bw.close();
    }

    private String getResponse(HttpURLConnection httpURLConnection) throws IOException, ParseException {
        System.out.println(httpURLConnection.getResponseCode());
        BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
        JSONObject response = parseStringToJson(br.readLine());
        System.out.println(response.toString());
        readResponseCode(httpURLConnection);

        return response.toString();
    }

    private HttpURLConnection setHttpURLConnection(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("Accept", " application/json");
        httpURLConnection.setDoOutput(true);
        return httpURLConnection;
    }

    // 이후 수정
    private List<Data> getDataList() {
        return jdbcController.loadDataList();
    }

    public JSONObject createRequestBody(Data data){
        JSONObject body = new JSONObject();

        body.put("nodePort", data.getNodePort());
        body.put("timeStamp", data.getTimeStamp().toString());
        body.put("sequence", data.getSequence());
        body.put("temperature", Double.valueOf(String.format("%.2f", data.getTemperaturee())));
        body.put("voltage", Double.valueOf(String.format("%.2f", data.getVoltage())));
        body.put("electricCurrent", Double.valueOf(String.format("%.2f", data.getElectricCurrent())));

        return body;
    }

    private JSONObject parseStringToJson(String stringJson) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj  = parser.parse(stringJson);
        JSONObject json = (JSONObject) obj;
        return json;
    }

    private void readResponseCode(HttpURLConnection connection){

        try{
            int responseCode = connection.getResponseCode();
            if(responseCode == 400){
                System.out.println("400 : command error");
            } else if (responseCode == 500){
                System.out.println("500 : Server error");
            } else {
                System.out.println("response code : " + responseCode);
            }
        } catch (IOException e){
            System.out.println("IOException : read response code error");
        }
    }
}
