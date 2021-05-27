package com.akash.cp.vtu.vtuparta_6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("all")
public class MainActivity extends AppCompatActivity implements Base{
    Button parsejson,parsexml;
    TextView mTextViewCity,mTextViewLatitude,mTextViewLongitude,mTextViewTemperature,mTextViewHumidity,mTextViewJsonTitile;
    String  mCityName,mLatitude,mLongitude,mTemperature,mHumidity;
    TextView xml;
    InputStream stream;
    String tag = "", text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        listener();
    }

    @Override
    public void init() {
        parsejson=(Button)findViewById(R.id.parse_json);
        parsexml=(Button)findViewById(R.id.parse_xml);
        mTextViewCity=(TextView)findViewById(R.id.cityname);
        mTextViewLatitude=(TextView)findViewById(R.id.Latitude);
        mTextViewLongitude=(TextView)findViewById(R.id.Longitude);
        mTextViewTemperature=(TextView)findViewById(R.id.Temperature);
        mTextViewHumidity=(TextView)findViewById(R.id.Humidity);
        mTextViewJsonTitile=(TextView)findViewById(R.id.json_title);
        xml=(TextView)findViewById(R.id.xml_tv);
    }

    @Override
    public void listener() {
        stream = this.getResources().openRawResource(R.raw.city);
        parsexml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = xmlPullParserFactory.newPullParser();

                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(stream, null);
                    xml.append("XML Data\n\n");
                    int event = parser.getEventType();

                    while (event != XmlPullParser.END_DOCUMENT) {
                        tag = parser.getName();

                        switch (event) {
                            case XmlPullParser.START_TAG:
                                break;
                            case XmlPullParser.TEXT:
                                text = parser.getText();
                                break;
                            case XmlPullParser.END_TAG:
                                switch (tag) {
                                    case "City_Name":
                                        xml.append("City_Name : " + text + "\n\n");
                                        break;
                                    case "Latitude":
                                        xml.append("Latitude : " + text + "\n\n");
                                        break;
                                    case "Longitude":
                                        xml.append("Longitude : " + text + "\n\n");
                                        break;
                                    case "Temperature":
                                        xml.append("Temperature : " + text + "\n\n");
                                        break;
                                    case "Humidity":
                                        xml.append("Humidity : " + text + "\n\n");
                                        break;
                                }
                                break;
                        }
                        event = parser.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        //JSON Parse
        parsejson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mTextViewJsonTitile.setText("Json Data");
                    JSONObject obj = new JSONObject(loadJSONFromAsset());
                    JSONArray m_jArry = obj.getJSONArray("city");
                    for (int i = 0; i < m_jArry.length(); i++) {
                        JSONObject insideobject = m_jArry.getJSONObject(i);
                        mCityName  = insideobject.getString("Cityname");
                        mLatitude = insideobject.getString("Latitude");
                        mLongitude = insideobject.getString("Longitude");
                        mTemperature = insideobject.getString("Temperature");
                        mHumidity = insideobject.getString("Humidity");
                        mTextViewCity.setText("City Name: "+mCityName+"\n");
                        mTextViewLatitude.setText("Latitude: "+mLatitude+"\n");
                        mTextViewLongitude.setText("Longitude: "+mLongitude+"\n");
                        mTextViewHumidity.setText("Humidity: "+mHumidity+"\n");
                        mTextViewTemperature.setText("Temperature: "+mTemperature+"\n");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    public String loadJSONFromAsset() {
        String json_data = null;
        try {
            InputStream in = MainActivity.this.getAssets().open("sample.json");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            json_data = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json_data;
    }
}
