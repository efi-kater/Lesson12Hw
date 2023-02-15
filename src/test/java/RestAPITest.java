import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Scanner;

public class RestAPITest {
    public static void main(String[] args) throws IOException, JSONException {
        System.out.println("Enter a Country Name to get info");
        Scanner scanner = new Scanner(System.in);
        String country = scanner.next();
        getCountryInfo(country);

    }

    private static void getCountryInfo(String countryName) throws IOException, JSONException {
        // use OKHttp client to create the connection and retrieve data
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://restcountries.com/v3.1/name/"+countryName)
                .addHeader("apikey", "rmXuebfSRwQqH6NntkOEkzmEnCQJcENl")
                .build();
        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();
        response.close();
        System.out.println(jsonData);
        // parse JSON
        JSONArray mainJsonObject = new JSONArray(jsonData);
        JSONObject mainJsonObject2 = mainJsonObject.getJSONObject(0);
        String region = mainJsonObject2.getString("region");
        System.out.println(countryName + "'s region: " +region);
        JSONArray borders = mainJsonObject2.getJSONArray("borders");
        for (int i =0; i<borders.length();i++){
            String border = borders.getString(i);
            System.out.println(countryName + "'s broder number "+ (i+1) + ": " + border );
        }
        JSONObject currencies = mainJsonObject2.getJSONObject("currencies");
        String subCurrencies="";
        for (Object key : currencies.keySet()) {
            subCurrencies = (key + "=" + currencies.get((String) key)).substring(0,3); // to get the value
        }
        JSONObject currency = currencies.getJSONObject(subCurrencies);
        String symbol = currency.getString("symbol");
        System.out.println(countryName + "'s currency symbol: "+ symbol);
    }
}