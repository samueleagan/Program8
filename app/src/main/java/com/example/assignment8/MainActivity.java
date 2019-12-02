package com.example.assignment8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getBTN = findViewById(R.id.getBTN);
        getBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Random rand = new Random();
                            int randomInt = rand.nextInt(61);
                            randomInt++;
                            id = randomInt;

                            String urlString = "https://swapi.co/api/";
                            String params = "planets/" + randomInt;
                            URL url = new URL(urlString + params);
                            String json = fetchFromURL(url);
                            Log.d("JSON Fetch: ", json); //
                            processJSON(json);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }

    public static String fetchFromURL(URL url){

        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while((line = br.readLine()) != null)
                builder.append(line);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    public void processJSON(final String json) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView triviaTV = findViewById(R.id.triviaTV);

                String nameDisplay = triviaTV.getText().toString();
                String climateDisplay = triviaTV.getText().toString();
                String populationDisplay = triviaTV.getText().toString();

                try {
                    JSONObject response = new JSONObject(json);
                    nameDisplay = response.getString("name");
                    climateDisplay = response.getString("climate");
                    populationDisplay = response.getString("population");

                } catch (JSONException e) {
                    e.printStackTrace();

                }

                triviaTV.setText("ID: " + id + "\nName: " + nameDisplay + "\nClimate: " + climateDisplay +
                        "\nPopulation: " + populationDisplay);

            }
        });
    }
}
