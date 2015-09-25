package com.alexd.weather.model;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by alexd on 2015-09-18.
 */
public class Weather {

    public Location location;
    public CurrentCondition currentCondition = new CurrentCondition();
    public Temperature temperature = new Temperature();
    public Wind wind = new Wind();
    public Rain rain = new Rain();
    public Snow snow = new Snow()	;
    public Clouds clouds = new Clouds();
    public Bitmap iconData;

    public  class CurrentCondition {
        private int weatherId;
        private String condition;
        private String descr;
        private String icon;
        private Date updateTime;


        private double pressure;
        private double humidity;

        public int getWeatherId() {
            return weatherId;
        }
        public void setWeatherId(int weatherId) {
            this.weatherId = weatherId;
        }
        public String getCondition() {
            return condition;
        }
        public void setCondition(String condition) {
            this.condition = condition;
        }
        public String getDescr() {
            return descr;
        }
        public void setDescr(String descr) {
            this.descr = descr;
        }
        public String getIcon() {
            return icon;
        }
        public void setIcon(String icon) {
            this.icon = icon;
        }
        public double getPressure() {
            return pressure;
        }
        public void setPressure(double pressure) {
            this.pressure = pressure;
        }
        public double getHumidity() {
            return humidity;
        }
        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }


        public Date getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }
    }

    public  class Temperature {
        private double temp;
        private double minTemp;
        private double maxTemp;

        public double getTemp() {
            return temp;
        }
        public void setTemp(double temp) {
            this.temp = temp;
        }
        public double getMinTemp() {
            return minTemp;
        }
        public void setMinTemp(double minTemp) {
            this.minTemp = minTemp;
        }
        public double getMaxTemp() {
            return maxTemp;
        }
        public void setMaxTemp(double maxTemp) {
            this.maxTemp = maxTemp;
        }

    }

    public  class Wind {
        private double speed;
        private double deg;
        public double getSpeed() {
            return speed;
        }
        public void setSpeed(double speed) {
            this.speed = speed;
        }
        public double getDeg() {
            return deg;
        }
        public void setDeg(double deg) {
            this.deg = deg;
        }


    }

    public  class Rain {
        private String time;
        private double ammount;
        public String getTime() {
            return time;
        }
        public void setTime(String time) {
            this.time = time;
        }
        public double getAmmount() {
            return ammount;
        }
        public void setAmmount(double ammount) {
            this.ammount = ammount;
        }



    }

    public  class Snow {
        private String time;
        private double ammount;

        public String getTime() {
            return time;
        }
        public void setTime(String time) {
            this.time = time;
        }
        public double getAmmount() {
            return ammount;
        }
        public void setAmmount(double ammount) {
            this.ammount = ammount;
        }


    }

    public  class Clouds {
        private int perc;

        public int getPerc() {
            return perc;
        }

        public void setPerc(int perc) {
            this.perc = perc;
        }


    }

}