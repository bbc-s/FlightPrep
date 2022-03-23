package com.example.flightprep;

public final class Database {

    public static class Aircrafts {
        public static final String TABLE_NAME = "aircrafts";
        public static final String AIRCRAFT_ID = "_id";
        public static final String MANUFACTURER = "aircraft_manufacturer";
        public static final String REG = "aircraft_reg";
        public static final String TYPE = "aircraft_type";
        public static final String MAX_CRUISE_SPEED = "aircraft_maxCruiseSpeed";
        public static final String MAX_ALTITUDE = "aircraft_maxAltitude";
        public static final String MAX_RANGE = "aircraft_maxRange";

    }

    public static class Airports {
        public static final String TABLE_NAME = "airports";
        public static final String AIRPORT_ID = "_id";
        public static final String CITY = "airport_city";
        public static final String ICAO = "airport_icao";
        public static final String IATA = "airport_iata";
        public static final String NAME = "airport_name";
        public static final String LATITUDE = "airport_latitude";
        public static final String LONGITUDE = "airport_longitude";

    }

    public static class Route {
        public static final String TABLE_NAME = "routes";
        public static final String ROUTE_ID = "_id";
        public static final String FROM = "route_from";
        public static final String TO = "route_to";
        public static final String STOP1 = "route_stop1";
        public static final String STOP2 = "route_stop2";
        public static final String STOP3 = "route_stop3";
        public static final String STOP4 = "route_stop4";
        public static final String STOP5 = "route_stop5";
        public static final String TIME = "route_time";
        public static final String SPEED = "route_speed";
        public static final String DISTANCE = "route_distance";

    }

    public static class SavedRoutes {
        public static final String TABLE_NAME = "savedroutes";
        public static final String SAVEDROUTE_ID = "_id";
        public static final String FROM = "savedroutes_from";
        public static final String FROMGPS = "savedroutes_fromgps";
        public static final String TO = "savedroutes_to";
        public static final String TOGPS = "savedroutes_togps";
        public static final String STOP1 = "savedroutes_stop1";
        public static final String STOP1GPS = "savedroutes_stop1gps";
        public static final String STOP2 = "savedroutes_stop2";
        public static final String STOP2GPS = "savedroutes_stop2gps";
        public static final String STOP3 = "savedroutes_stop3";
        public static final String STOP3GPS = "savedroutes_stop3gps";
        public static final String STOP4 = "savedroutes_stop4";
        public static final String STOP4GPS = "savedroutes_stop4gps";
        public static final String STOP5 = "savedroutes_stop5";
        public static final String STOP5GPS = "savedroutes_stop5gps";
        public static final String SPEED = "savedroutes_speed";
        public static final String ALTITUDE = "savedroutes_altitude";
        public static final String DISTANCE = "savedroutes_distance";
    }

    public static class MyRoutesLocations {
        public static final String TABLE_NAME = "myrouteslocations";
        public static final String MYROUTESLOCATIONS_ID = "_id";
        public static final String ROUTEID = "myrouteslocations_routeid";
        public static final String LOCATION = "myrouteslocations_location";
    }

    public static class MyRoutesData {
        public static final String TABLE_NAME = "myroutesdata";
        public static final String MYROUTESDATA_ID = "_id";
        public static final String ROUTEID = "myroutesdata_routeid";
        public static final String FROM = "myroutesdata_from";
        public static final String TO = "myroutesdata_to";
        public static final String STOP1 = "myroutesdata_stop1";
        public static final String STOP2 = "myroutesdata_stop2";
        public static final String STOP3 = "myroutesdata_stop3";
        public static final String STOP4 = "myroutesdata_stop4";
        public static final String STOP5 = "myroutesdata_stop5";
        public static final String RAWTIME = "myroutesdata_rawtime";
        public static final String RAWDISTANCE = "myroutesdata_rawdistance";
        public static final String RAWSPEED = "myroutesdata_rawspeed";
        public static final String TIME = "myroutesdata_time";
        public static final String DISTANCE = "myroutesdata_distance";
        public static final String SPEED = "myroutesdata_speed";
    }


}
