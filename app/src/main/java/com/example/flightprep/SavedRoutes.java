package com.example.flightprep;

public class SavedRoutes {
    private long ID;
    private String savedroutes_from;
    private String savedroutes_fromgps;
    private String savedroutes_to;
    private String savedroutes_togps;
    private String savedroutes_stop1;
    private String savedroutes_stop1gps;
    private String savedroutes_stop2;
    private String savedroutes_stop2gps;
    private String savedroutes_stop3;
    private String savedroutes_stop3gps;
    private String savedroutes_stop4;
    private String savedroutes_stop4gps;
    private String savedroutes_stop5;
    private String savedroutes_stop5gps;
    private String savedroutes_speed;
    private String savedroutes_altitude;
    private String savedroutes_distance;

    public SavedRoutes(long ID, String savedroutes_from, String savedroutes_fromgps, String savedroutes_to, String savedroutes_togps, String savedroutes_stop1, String savedroutes_stop1gps, String savedroutes_stop2, String savedroutes_stop2gps, String savedroutes_stop3, String savedroutes_stop3gps, String savedroutes_stop4, String savedroutes_stop4gps, String savedroutes_stop5, String savedroutes_stop5gps, String savedroutes_speed, String savedroutes_altitude, String savedroutes_distance) {
        this.ID = ID;
        this.savedroutes_from = savedroutes_from;
        this.savedroutes_fromgps = savedroutes_fromgps;
        this.savedroutes_to = savedroutes_to;
        this.savedroutes_togps = savedroutes_togps;
        this.savedroutes_stop1 = savedroutes_stop1;
        this.savedroutes_stop1gps = savedroutes_stop1gps;
        this.savedroutes_stop2 = savedroutes_stop2;
        this.savedroutes_stop2gps = savedroutes_stop2gps;
        this.savedroutes_stop3 = savedroutes_stop3;
        this.savedroutes_stop3gps = savedroutes_stop3gps;
        this.savedroutes_stop4 = savedroutes_stop4;
        this.savedroutes_stop4gps = savedroutes_stop4gps;
        this.savedroutes_stop5 = savedroutes_stop5;
        this.savedroutes_stop5gps = savedroutes_stop5gps;
        this.savedroutes_speed = savedroutes_speed;
        this.savedroutes_altitude = savedroutes_altitude;
        this.savedroutes_distance = savedroutes_distance;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getSavedroutes_from() {
        return savedroutes_from;
    }

    public void setSavedroutes_from(String savedroutes_from) {
        this.savedroutes_from = savedroutes_from;
    }

    public String getSavedroutes_fromgps() {
        return savedroutes_fromgps;
    }

    public void setSavedroutes_fromgps(String savedroutes_fromgps) {
        this.savedroutes_fromgps = savedroutes_fromgps;
    }

    public String getSavedroutes_to() {
        return savedroutes_to;
    }

    public void setSavedroutes_to(String savedroutes_to) {
        this.savedroutes_to = savedroutes_to;
    }

    public String getSavedroutes_togps() {
        return savedroutes_togps;
    }

    public void setSavedroutes_togps(String savedroutes_togps) {
        this.savedroutes_togps = savedroutes_togps;
    }

    public String getSavedroutes_stop1() {
        return savedroutes_stop1;
    }

    public void setSavedroutes_stop1(String savedroutes_stop1) {
        this.savedroutes_stop1 = savedroutes_stop1;
    }

    public String getSavedroutes_stop1gps() {
        return savedroutes_stop1gps;
    }

    public void setSavedroutes_stop1gps(String savedroutes_stop1gps) {
        this.savedroutes_stop1gps = savedroutes_stop1gps;
    }

    public String getSavedroutes_stop2() {
        return savedroutes_stop2;
    }

    public void setSavedroutes_stop2(String savedroutesstop2) {
        this.savedroutes_stop2 = savedroutesstop2;
    }

    public String getSavedroutes_stop2gps() {
        return savedroutes_stop2gps;
    }

    public void setSavedroutesstop2gps(String savedroutesstop2gps) {
        this.savedroutes_stop2gps = savedroutesstop2gps;
    }

    public String getSavedroutes_stop3() {
        return savedroutes_stop3;
    }

    public void setSavedroutes_stop3(String savedroutes_stop3) {
        this.savedroutes_stop3 = savedroutes_stop3;
    }

    public String getSavedroutes_stop3gps() {
        return savedroutes_stop3gps;
    }

    public void setSavedroutes_stop3gps(String savedroutes_stop3gps) {
        this.savedroutes_stop3gps = savedroutes_stop3gps;
    }

    public String getSavedroutes_stop4() {
        return savedroutes_stop4;
    }

    public void setSavedroutes_stop4(String savedroutes_stop4) {
        this.savedroutes_stop4 = savedroutes_stop4;
    }

    public String getSavedroutes_stop4gps() {
        return savedroutes_stop4gps;
    }

    public void setSavedroutes_stop4gps(String savedroutes_stop4gps) {
        this.savedroutes_stop4gps = savedroutes_stop4gps;
    }

    public String getSavedroutes_stop5() {
        return savedroutes_stop5;
    }

    public void setSavedroutes_stop5(String savedroutes_stop5) {
        this.savedroutes_stop5 = savedroutes_stop5;
    }

    public String getSavedroutes_stop5gps() {
        return savedroutes_stop5gps;
    }

    public void setSavedroutes_stop5gps(String savedroutes_stop5gps) {
        this.savedroutes_stop5gps = savedroutes_stop5gps;
    }

    public String getSavedroutes_speed() {
        return savedroutes_speed;
    }

    public void setSavedroutes_speed(String savedroutes_speed) {
        this.savedroutes_speed = savedroutes_speed;
    }

    public String getSavedroutes_altitude() {
        return savedroutes_altitude;
    }

    public void setSavedroutes_altitude(String savedroutes_altitude) {
        this.savedroutes_altitude = savedroutes_altitude;
    }

    public String getSavedroutes_distance() {
        return savedroutes_distance;
    }

    public void setSavedroutes_distance(String savedroutesdistance) {
        this.savedroutes_distance = savedroutesdistance;
    }
}
