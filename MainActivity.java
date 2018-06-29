package com.example.vturb.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private double temperature;
    private boolean buttonState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String lightBulbURL = "http://192.168.4.1/lightBulb/0";
        final String temperatureURL = "http://192.168.4.1/temperature/0";
        new GetState().execute(lightBulbURL);
        new GetState().execute(temperatureURL);
        RelativeLayout button = findViewById(R.id.device);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    new SetState().execute(lightBulbURL);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        LinearLayout main = findViewById(R.id.background);
        main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new GetState().execute(temperatureURL);
            }
        });
    }

    private class SetState extends AsyncTask<String, Void, Void> {
        RelativeLayout layout = findViewById(R.id.device);

        @Override
        protected void onPostExecute(Void aVoid) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateButton(buttonState);
                }
            });
        }

        @Override
        protected Void doInBackground(String... strings) {
            HttpHandler sh = new HttpHandler();
            String lightBulbURL = strings[0];
            if (buttonState) {
                buttonState = false;
                sh.postService(lightBulbURL,"0", String.valueOf(buttonState));

            } else {
                buttonState = true;
                sh.postService(lightBulbURL,"0", String.valueOf(buttonState));
            }
            return null;
        }
    }
    private class GetState extends AsyncTask<String, Void, TextView> {
       // RelativeLayout button = findViewById(R.id.device);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(TextView text) {
            if( text.equals(findViewById(R.id.ONOFF))) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateButton(buttonState);
                    }
                });
            } else {
                text.setText(temperature + "°C");
            }
        }

        @Override
        protected TextView doInBackground(String... args) {
            TextView textView = null;
            String url = args[0];
            //Pass URL
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.getServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    if (jsonObj.has("status"))
                    if (jsonObj.get("status").getClass() == Boolean.class){
                        buttonState =  jsonObj.getBoolean("status");
                        textView = findViewById(R.id.ONOFF);
                    } else {
                        temperature = jsonObj.getDouble("status");
                        textView = findViewById(R.id.temperature);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return textView;
        }
        }

        private void updateButton(boolean button) {
            RelativeLayout layout = findViewById(R.id.device);
            TextView textView = findViewById(R.id.ONOFF);
            if (button) {
                layout.setBackgroundResource(R.drawable.round_rect_shape_on);
                ImageView viewLamp = findViewById(R.id.lamp);
                viewLamp.setImageResource(R.drawable.lightbulb_on);
                textView.setText("ON");
            } else {
                layout.setBackgroundResource(R.drawable.round_rect_shape_off);
                ImageView viewLamp = findViewById(R.id.lamp);
                viewLamp.setImageResource(R.drawable.lightbulb_off);
                textView.setText("OFF");
            }
        }
    }
