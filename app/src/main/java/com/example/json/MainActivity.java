package com.example.json;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadTask task = new downloadTask();
        task.execute("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");
    }

    public class downloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader inReader = new InputStreamReader(in);

                int data = inReader.read();

                while (data!=-1){
                    char current = (char) data;

                    result += current;

                    data = inReader.read();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                JSONObject jsonObject = new JSONObject(result);
                String weatherInfo = jsonObject.getString("weather");
                //System.out.println("Website Content" + weatherInfo);

                JSONArray arr = new JSONArray(weatherInfo);

                for(int i=0; i<arr.length();i++){
                    JSONObject jsonPart = arr.getJSONObject(i);
                    System.out.println("Main " + jsonPart.getString("main"));
                    System.out.println("Description " + jsonPart.getString("description"));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //System.out.println("Website Content " + result);
        }
    }
}
