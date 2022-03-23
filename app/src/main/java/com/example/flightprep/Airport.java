package com.example.flightprep;

public class Airport {

    private long ID;
    private String airport_city;
    private String airport_icao;
    private String airport_iata;
    private String airport_name;
    private double airport_latitude;
    private double airport_longitude;

    public Airport(long ID, String airport_city, String airport_icao, String airport_iata, String airport_name, double airport_latitude, double airport_longitude) {
        this.ID = ID;
        this.airport_city = airport_city;
        this.airport_icao = airport_icao;
        this.airport_iata = airport_iata;
        this.airport_name = airport_name;
        this.airport_latitude = airport_latitude;
        this.airport_longitude = airport_longitude;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getAirport_city() {
        return airport_city;
    }

    public void setAirport_city(String airport_city) {
        this.airport_city = airport_city;
    }

    public String getAirport_icao() {
        return airport_icao;
    }

    public void setAirport_icao(String airport_icao) {
        this.airport_icao = airport_icao;
    }

    public String getAirport_iata() {
        return airport_iata;
    }

    public void setAirport_iata(String airport_iata) {
        this.airport_iata = airport_iata;
    }

    public String getAirport_name() {
        return airport_name;
    }

    public void setAirport_name(String airport_name) {
        this.airport_name = airport_name;
    }

    public double getAirport_latitude() {
        return airport_latitude;
    }

    public void setAirport_latitude(double airport_latitude) {
        this.airport_latitude = airport_latitude;
    }

    public double getAirport_longitude() {
        return airport_longitude;
    }

    public void setAirport_longitude(double airport_longitude) {
        this.airport_longitude = airport_longitude;
    }
}
