package com.example.group13zoosearch;public class DirectionStepItem {
//    public static double distance;
//    public static String road;
//    public static String count;
//    public static String nextNode;
//    public static DirectionStepItem previousNodeItem;

    public  double distance;
    public  String road;
    public  String count;
    public  String nextNode;
    public  DirectionStepItem previousNodeItem;

    DirectionStepItem(double distance, String road, String count, String nextNode, DirectionStepItem previousNodeItem){
        this.distance = distance;
        this.road = road;
        this.count = count;
        this.nextNode = nextNode;
        this.previousNodeItem = previousNodeItem;
    }

//    public static double getDistance() {
//        return distance;
//    }
//
//    public static String getRoad() {
//        return road;
//    }
//
//    public static String getCount() { return count;}
//
//    public static String getNextNode() { return nextNode; }
//
//    public static DirectionStepItem getPreviousNodeItem() {return previousNodeItem;}

    public double getDistance() {
        return distance;
    }

    public String getRoad() {
        return road;
    }

    public String getCount() { return count;}

    public String getNextNode() { return nextNode; }

    public DirectionStepItem getPreviousNodeItem() {return previousNodeItem;}

    @Override
    public String toString() {
        return this.count+ ". Head " + this.distance + "ft on "+ this.road + " towards " + this.nextNode + "\n";
    }
}
