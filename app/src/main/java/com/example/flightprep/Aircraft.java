package com.example.flightprep;

public class Aircraft {
    private long ID;
    private String aircraft_manufacturer;
    private String aircraft_reg;
    private String aircraft_type;
    private int aircraft_maxCruiseSpeed;
    private int aircraft_maxAltitude;
    private int aircraft_maxRange;

    public Aircraft(long ID, String aircraft_manufacturer, String aircraft_reg, String aircraft_type, int aircraft_maxCruiseSpeed, int aircraft_maxAltitude, int aircraft_maxRange) {
        this.ID = ID;
        this.aircraft_manufacturer = aircraft_manufacturer;
        this.aircraft_reg = aircraft_reg;
        this.aircraft_type = aircraft_type;
        this.aircraft_maxCruiseSpeed = aircraft_maxCruiseSpeed;
        this.aircraft_maxAltitude = aircraft_maxAltitude;
        this.aircraft_maxRange = aircraft_maxRange;
    }

    public long getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAircraft_manufacturer() {
        return aircraft_manufacturer;
    }

    public void setAircraft_manufacturer(String aircraft_manufacturer) {
        this.aircraft_manufacturer = aircraft_manufacturer;
    }

    public String getAircraft_reg() {
        return aircraft_reg;
    }

    public void setAircraft_reg(String aircraft_reg) {
        this.aircraft_reg = aircraft_reg;
    }

    public String getAircraft_type() {
        return aircraft_type;
    }

    public void setAircraft_type(String aircraft_type) {
        this.aircraft_type = aircraft_type;
    }

    public int getAircraft_maxCruiseSpeed() {
        return aircraft_maxCruiseSpeed;
    }

    public void setAircraft_maxCruiseSpeed(int aircraft_maxCruiseSpeed) {
        this.aircraft_maxCruiseSpeed = aircraft_maxCruiseSpeed;
    }

    public int getAircraft_maxAltitude() {
        return aircraft_maxAltitude;
    }

    public void setAircraft_maxAltitude(int aircraft_maxAltitude) {
        this.aircraft_maxAltitude = aircraft_maxAltitude;
    }

    public int getAircraft_maxRange() {
        return aircraft_maxRange;
    }

    public void setAircraft_maxRange(int aircraft_maxRange) {
        this.aircraft_maxRange = aircraft_maxRange;
    }

}

