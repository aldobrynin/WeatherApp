package com.alexd.weather;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexd.weather.model.Weather;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {
    private TextView cityText;
    private TextView condDescription;
    private TextView temp;
    private TextView press;
    private TextView windSpeed;
    private TextView windDeg;

    private TextView hum;
    private TextView date;
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityText = (TextView) findViewById(R.id.cityText);
        condDescription = (TextView) findViewById(R.id.condDescr);
        temp = (TextView) findViewById(R.id.temp);
        hum = (TextView) findViewById(R.id.hum);
        press = (TextView) findViewById(R.id.press);
        windSpeed = (TextView) findViewById(R.id.windSpeed);
        windDeg = (TextView) findViewById(R.id.windDeg);
        imgView = (ImageView) findViewById(R.id.condIcon);
        date = (TextView) findViewById(R.id.updateTime);

        updateWeatherData();
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void refreshButtonClick(View view) {
        updateWeatherData();
    }

    private void updateWeatherData() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }

        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String longitude = "" + loc.getLongitude();
        String latitude = "" + loc.getLatitude();

        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(longitude, latitude);
    }
    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            WeatherHttpClient weatherHttpClient = new WeatherHttpClient();
            String data = weatherHttpClient.getWeatherData(params[0], params[1]);

            Weather weather = new Weather();
            try {
                weather = JSONWeatherParser.getWeather(data);
                weather.iconData = weatherHttpClient.getImage(weather.currentCondition.getIcon());


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            imgView.setImageBitmap(weather.iconData);
            cityText.setText(weather.location.getCity() + ", " + weather.location.getCountry());
            condDescription.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
            hum.setText("" + weather.currentCondition.getHumidity() + "%");
            press.setText("" + weather.currentCondition.getPressure() + " hPa");
            windSpeed.setText("" + weather.wind.getSpeed() + " m/s");
//            windDeg.setText("" + weather.wind.getDeg() + "°");
            date.setText(weather.currentCondition.getUpdateTime().toString());
            double tempValue = weather.temperature.getTemp();
            temp.setTextColor(getTextColor(tempValue));
            temp.setText("" + (tempValue > 0 ? ("+" + tempValue) : tempValue) + "°C");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

    private String getCityByLocation(Location loc){
        String cityName = null;
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            cityName = addresses.get(0).getAddressLine(1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return cityName;
    }
    public int getTextColor(double value){
        double in = value * 1.0 / 20;
        double v0 = Math.abs(in / Math.sqrt(1 + in*in));

        v0 = Math.max(0, Math.min(1, v0));
        int col = (int)(v0 * 255);
        return value > 0 ? Color.rgb(col, 0, 0) : Color.rgb(0, 0, col);
    }
}
