package com.example.group13zoosearch;public class DirectionStepItem {
    public static double distance;
    public static String road;
    public static String count;

    DirectionStepItem(double distance, String road, String count){
        this.distance = distance;
        this.road = road;
        this.count = count;
    }

    public static double getDistance() {
        return distance;
    }

    public static String getRoad() {
        return road;
    }

    public static String getCount() { return count;}

    @Override
    public String toString() {
        return this.count+ ". Head " + this.distance + "ft on "+ this.road + "\n";
    }
}
