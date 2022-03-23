package com.example.flightprep;

public class Route {
    private long ID;
    private String route_from;
    private String route_to;
    private String route_stop1;
    private String route_stop2;
    private String route_stop3;
    private String route_stop4;
    private String route_stop5;
    private String route_time;
    private String route_speed;
    private String route_distance;

    public Route(long ID, String route_from, String route_to, String route_stop1, String route_stop2, String route_stop3, String route_stop4, String route_stop5, String route_time, String route_speed, String route_distance) {
        this.ID = ID;
        this.route_from = route_from;
        this.route_to = route_to;
        this.route_stop1 = route_stop1;
        this.route_stop2 = route_stop2;
        this.route_stop3 = route_stop3;
        this.route_stop4 = route_stop4;
        this.route_stop5 = route_stop5;
        this.route_speed = route_speed;
        this.route_time = route_time;
        this.route_distance = route_distance;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getRoute_from() {
        return route_from;
    }

    public void setRoute_from(String route_from) {
        this.route_from = route_from;
    }

    public String getRoute_to() {
        return route_to;
    }

    public void setRoute_to(String route_to) {
        this.route_to = route_to;
    }

    public String getRoute_stop1() {
        return route_stop1;
    }

    public void setRoute_stop1(String route_stop1) {
        this.route_stop1 = route_stop1;
    }

    public String getRoute_stop2() {
        return route_stop2;
    }

    public void setRoute_stop2(String route_stop2) {
        this.route_stop2 = route_stop2;
    }

    public String getRoute_stop3() {
        return route_stop3;
    }

    public void setRoute_stop3(String route_stop3) {
        this.route_stop3 = route_stop3;
    }

    public String getRoute_stop4() {
        return route_stop4;
    }

    public void setRoute_stop4(String route_stop4) {
        this.route_stop4 = route_stop4;
    }

    public String getRoute_stop5() {
        return route_stop5;
    }

    public void setRoute_stop5(String route_stop5) {
        this.route_stop5 = route_stop5;
    }

    public String getRoute_speed() {
        return route_speed;
    }

    public void setRoute_speed(String route_speed) {
        this.route_speed = route_speed;
    }

    public String getRoute_time() {
        return route_time;
    }

    public void setRoute_time(String route_time) {
        this.route_time = route_time;
    }

    public String getRoute_distance() {
        return route_distance;
    }

    public void setRoute_distance(String route_distance) {
        this.route_distance = route_distance;
    }
}
