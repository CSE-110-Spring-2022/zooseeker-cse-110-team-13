package com.example.group13zoosearch;public class DirectionStepItem {
    public static double distance;
    public static String road;
    public static String count;
    public static String nextNode;

    DirectionStepItem(double distance, String road, String count, String nextNode){
        this.distance = distance;
        this.road = road;
        this.count = count;
        this.nextNode = nextNode;
    }

    public static double getDistance() {
        return distance;
    }

    public static String getRoad() {
        return road;
    }

    public static String getCount() { return count;}

    public static String getNextNode() { return nextNode; }

    @Override
    public String toString() {
        return this.count+ ". Head " + this.distance + "ft on "+ this.road + " towards " + this.nextNode + "\n";
    }
}
