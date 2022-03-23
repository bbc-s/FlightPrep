package com.example.flightprep;

public class MyRoutesLocation {
    private long ID;
    private String myrouteslocations_routeid;
    private String myrouteslocations_location;

    public MyRoutesLocation(long ID, String myrouteslocations_routeid, String myrouteslocations_location) {
        this.ID = ID;
        this.myrouteslocations_routeid = myrouteslocations_routeid;
        this.myrouteslocations_location = myrouteslocations_location;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getMyrouteslocations_routeid() {
        return myrouteslocations_routeid;
    }

    public void setMyrouteslocations_routeid(String myrouteslocations_routeid) {
        this.myrouteslocations_routeid = myrouteslocations_routeid;
    }

    public String getMyrouteslocations_location() {
        return myrouteslocations_location;
    }

    public void setMyrouteslocations_location(String myrouteslocations_location) {
        this.myrouteslocations_location = myrouteslocations_location;
    }
}
