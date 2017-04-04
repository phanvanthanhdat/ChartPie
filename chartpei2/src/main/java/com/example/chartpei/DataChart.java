package com.example.chartpei;

/**
 * Created by baohp on 23/03/2017.
 */

public class DataChart {
    private double point;
    private String disription;
    private int color;
    private double percenter;

    public DataChart() {
    }

    public DataChart(double point, String disription, int color, double percenter) {
        this.point = point;
        this.disription = disription;
        this.color = color;
        this.percenter = percenter;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public String getDisription() {
        return disription;
    }

    public void setDisription(String disription) {
        this.disription = disription;
    }

    public int getColor() {
        return color;
    }

    public double getPercenter() {

        return percenter;
    }

    public void setPercenter(double percenter) {
        this.percenter = percenter;
    }


    public void setColor(int color) {
        this.color = color;
    }

    public double getPercent360() {
        return percenter * 360 / 100;
    }
}
