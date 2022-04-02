package com.example.flightprep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperMain extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "flightprepdb";
    private static final int DATABASE_VERSION = 1;

    public DBHelperMain(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        {
            String SQL_CREATE_TABLE_AIRPORT = "CREATE TABLE " + Database.Airports.TABLE_NAME + "("
                    + Database.Airports.AIRPORT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Database.Airports.CITY + " TEXT, "
                    + Database.Airports.ICAO + " TEXT, "
                    + Database.Airports.IATA + " TEXT, "
                    + Database.Airports.NAME + " TEXT, "
                    + Database.Airports.LATITUDE + " REAL, "
                    + Database.Airports.LONGITUDE + " REAL)";

            db.execSQL(SQL_CREATE_TABLE_AIRPORT);

            db.execSQL("insert into " + Database.Airports.TABLE_NAME + " values(1,'Bratislava','LZIB','BTS','Letisko M. R. Stefanika',48.17,17.2125)");
            db.execSQL("insert into " + Database.Airports.TABLE_NAME + " values(2,'Kosice','LZKZ','KSC','Letisko Kosice',48.663056,21.241111)");
            db.execSQL("insert into " + Database.Airports.TABLE_NAME + " values(3,'Martin','LZMA','-','Letisko Martin',49.065278,18.950833)");
            db.execSQL("insert into " + Database.Airports.TABLE_NAME + " values(4,'Nitra','LZNI','-','Letisko Nitra',48.279444,18.132778)");
            db.execSQL("insert into " + Database.Airports.TABLE_NAME + " values(5,'Nova Zamky','LZNZ','-','Letisko Nove Zamky',47.960833,18.186111)");
            db.execSQL("insert into " + Database.Airports.TABLE_NAME + " values(6,'Piestany','LZPP','PZY','Letisko Piestany',48.625,17.828333)");
            db.execSQL("insert into " + Database.Airports.TABLE_NAME + " values(7,'Poprad','LZTT','TAT','Letisko Poprad-Tatry',49.073333,20.241111)");
            db.execSQL("insert into " + Database.Airports.TABLE_NAME + " values(8,'Prievidza','LZPE','-','Letisko Prievidza',48.766111,18.586667)");
            db.execSQL("insert into " + Database.Airports.TABLE_NAME + " values(9,'Partizanske','LZPT','-','Letisko Male Bielice',48.620278,18.333333)");
            db.execSQL("insert into " + Database.Airports.TABLE_NAME + " values(10,'Dubnica n/Vahom','LZDB','-','Letisko Slavnica',48.996944,18.192222)");
            db.execSQL("insert into " + Database.Airports.TABLE_NAME + " values(11,'Sliac','LZSL','SLD','Letisko Sliac',49.654444,19.133889)");
            db.execSQL("insert into " + Database.Airports.TABLE_NAME + " values(12,'Spisska Nova Ves','LZSV','-','Letisko Spisska Nova Ves',48.940833,20.533889)");
            db.execSQL("insert into " + Database.Airports.TABLE_NAME + " values(13,'Svidnik','LZSK','-','Letisko Svidnik',49.334167,21.570278)");
            db.execSQL("insert into " + Database.Airports.TABLE_NAME + " values(14,'Zilina','LZZI','ILZ','Letisko Zilina',49.233333,18.613611)");
        }

        {
            String SQL_CREATE_TABLE_ROUTE = "CREATE TABLE " + Database.Route.TABLE_NAME + "("
                    + Database.Route.ROUTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Database.Route.FROM + " TEXT, "
                    + Database.Route.TO + " TEXT, "
                    + Database.Route.STOP1 + " TEXT, "
                    + Database.Route.STOP2 + " TEXT, "
                    + Database.Route.STOP3 + " TEXT, "
                    + Database.Route.STOP4 + " TEXT, "
                    + Database.Route.STOP5 + " TEXT, "
                    + Database.Route.TIME + " TEXT, "
                    + Database.Route.SPEED + " TEXT, "
                    + Database.Route.DISTANCE + " TEXT)";

            db.execSQL(SQL_CREATE_TABLE_ROUTE);

        }

        {
            String SQL_CREATE_TABLE_SAVEDROUTES = "CREATE TABLE " + Database.SavedRoutes.TABLE_NAME + "("
                    + Database.SavedRoutes.SAVEDROUTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Database.SavedRoutes.FROM + " TEXT, "
                    + Database.SavedRoutes.FROMGPS + " REAL, "
                    + Database.SavedRoutes.TO + " TEXT, "
                    + Database.SavedRoutes.TOGPS + " REAL, "
                    + Database.SavedRoutes.STOP1 + " TEXT, "
                    + Database.SavedRoutes.STOP1GPS + " REAL, "
                    + Database.SavedRoutes.STOP2 + " TEXT, "
                    + Database.SavedRoutes.STOP2GPS + " REAL, "
                    + Database.SavedRoutes.STOP3 + " TEXT, "
                    + Database.SavedRoutes.STOP3GPS + " REAL, "
                    + Database.SavedRoutes.STOP4 + " TEXT, "
                    + Database.SavedRoutes.STOP4GPS + " REAL, "
                    + Database.SavedRoutes.STOP5 + " TEXT, "
                    + Database.SavedRoutes.STOP5GPS + " REAL, "
                    + Database.SavedRoutes.SPEED + " INTEGER, "
                    + Database.SavedRoutes.ALTITUDE + " INTEGER, "
                    + Database.SavedRoutes.DISTANCE + " REAL)";

            db.execSQL(SQL_CREATE_TABLE_SAVEDROUTES);

        }

        {
            String SQL_CREATE_TABLE_AIRCRAFT = "CREATE TABLE " + Database.Aircrafts.TABLE_NAME + "("
                    + Database.Aircrafts.AIRCRAFT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Database.Aircrafts.MANUFACTURER + " TEXT, "
                    + Database.Aircrafts.REG + " TEXT, "
                    + Database.Aircrafts.TYPE + " TEXT, "
                    + Database.Aircrafts.MAX_CRUISE_SPEED + " INTEGER, "
                    + Database.Aircrafts.MAX_ALTITUDE + " INTEGER, "
                    + Database.Aircrafts.MAX_RANGE + " INTEGER)";

            db.execSQL(SQL_CREATE_TABLE_AIRCRAFT);

            db.execSQL("insert into " + Database.Aircrafts.TABLE_NAME + " values(1,'Cessna 172','OM-ASDF2','single engine',120,10000,1185)");
        }

        {
            String SQL_CREATE_TABLE_MYROUTESLOCATIONS = "CREATE TABLE " + Database.MyRoutesLocations.TABLE_NAME + "("
                    + Database.MyRoutesLocations.MYROUTESLOCATIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Database.MyRoutesLocations.ROUTEID + " TEXT, "
                    + Database.MyRoutesLocations.LOCATION + " TEXT)";

            db.execSQL(SQL_CREATE_TABLE_MYROUTESLOCATIONS);
        }

        {
            String SQL_CREATE_TABLE_MYROUTESDATA = "CREATE TABLE " + Database.MyRoutesData.TABLE_NAME + "("
                    + Database.MyRoutesData.MYROUTESDATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Database.MyRoutesData.ROUTEID + " TEXT, "
                    + Database.MyRoutesData.FROM + " TEXT, "
                    + Database.MyRoutesData.TO + " TEXT, "
                    + Database.MyRoutesData.STOP1 + " TEXT, "
                    + Database.MyRoutesData.STOP2 + " TEXT, "
                    + Database.MyRoutesData.STOP3 + " TEXT, "
                    + Database.MyRoutesData.STOP4 + " TEXT, "
                    + Database.MyRoutesData.STOP5 + " TEXT, "
                    + Database.MyRoutesData.RAWTIME + " TEXT, "
                    + Database.MyRoutesData.RAWDISTANCE + " TEXT, "
                    + Database.MyRoutesData.RAWSPEED + " TEXT, "
                    + Database.MyRoutesData.TIME + " TEXT, "
                    + Database.MyRoutesData.DISTANCE + " TEXT, "
                    + Database.MyRoutesData.SPEED + " TEXT)";

            db.execSQL(SQL_CREATE_TABLE_MYROUTESDATA);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            String SQL_DROP_TABLE_AIRPORT = "DROP TABLE IF EXISTS " + Database.Airports.TABLE_NAME;
            String SQL_DROP_TABLE_ROUTE = "DROP TABLE IF EXISTS " + Database.Route.TABLE_NAME;
            String SQL_DROP_TABLE_SAVEDROUTES = "DROP TABLE IF EXISTS " + Database.SavedRoutes.TABLE_NAME;
            String SQL_DROP_TABLE_AIRCRAFTS = "DROP TABLE IF EXISTS " + Database.Aircrafts.TABLE_NAME;
            String SQL_DROP_TABLE_MYROUTESLOCATIONS = "DROP TABLE IF EXISTS " + Database.MyRoutesLocations.TABLE_NAME;
            String SQL_DROP_TABLE_MYROUTESDATA = "DROP TABLE IF EXISTS " + Database.MyRoutesData.TABLE_NAME;

            db.execSQL(SQL_DROP_TABLE_AIRPORT);
            db.execSQL(SQL_DROP_TABLE_ROUTE);
            db.execSQL(SQL_DROP_TABLE_SAVEDROUTES);
            db.execSQL(SQL_DROP_TABLE_AIRCRAFTS);
            db.execSQL(SQL_DROP_TABLE_MYROUTESLOCATIONS);
            db.execSQL(SQL_DROP_TABLE_MYROUTESDATA);

            onCreate(db);
        }
    }


//region <---Airport

    public Cursor getAirportCursor() {
        SQLiteDatabase db = getWritableDatabase();
        String myQuery = "SELECT " +
                Database.Airports.AIRPORT_ID + " , " +
                Database.Airports.CITY + " , " +
                Database.Airports.ICAO + " , " +
                Database.Airports.IATA + " , " +
                Database.Airports.NAME + " , " +
                Database.Airports.LATITUDE + " , " +
                Database.Airports.LONGITUDE +
                " FROM " + Database.Airports.TABLE_NAME;
        Cursor c = db.rawQuery(myQuery, null);
        c.moveToFirst();
        db.close();
        return c;
    }

//endregion <---Airport

//region <---Aircraft

    public void addAircraft(Aircraft a) {
        ContentValues values = new ContentValues();
        values.put(Database.Aircrafts.MANUFACTURER, a.getAircraft_manufacturer());
        values.put(Database.Aircrafts.REG, a.getAircraft_reg());
        values.put(Database.Aircrafts.TYPE, a.getAircraft_type());
        values.put(Database.Aircrafts.MAX_CRUISE_SPEED, a.getAircraft_maxCruiseSpeed());
        values.put(Database.Aircrafts.MAX_ALTITUDE, a.getAircraft_maxAltitude());
        values.put(Database.Aircrafts.MAX_RANGE, a.getAircraft_maxRange());

        SQLiteDatabase db = getWritableDatabase();
        long newID = db.insert(Database.Aircrafts.TABLE_NAME, null, values);
        db.close();
    }


    public void deleteAircraft(long ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Database.Aircrafts.TABLE_NAME, Database.Aircrafts.AIRCRAFT_ID + "=" + ID, null);
        db.close();
    }


    public Cursor getAircraftCursor() {
        SQLiteDatabase db = getWritableDatabase();
        String myQuery = "SELECT " +
                Database.Aircrafts.AIRCRAFT_ID + " , " +
                Database.Aircrafts.MANUFACTURER + " , " +
                Database.Aircrafts.REG + " , " +
                Database.Aircrafts.TYPE + " , " +
                Database.Aircrafts.MAX_CRUISE_SPEED + " , " +
                Database.Aircrafts.MAX_ALTITUDE + " , " +
                Database.Aircrafts.MAX_RANGE +
                " FROM " + Database.Aircrafts.TABLE_NAME;
        Cursor c = db.rawQuery(myQuery, null);
        c.moveToFirst();
        db.close();
        return c;
    }

    public Cursor getSingleAircraftCursor(long ID) {
        SQLiteDatabase db = getWritableDatabase();
        String myQuery = "SELECT " +
                Database.Aircrafts.AIRCRAFT_ID + " , " +
                Database.Aircrafts.MANUFACTURER + " , " +
                Database.Aircrafts.REG + " , " +
                Database.Aircrafts.TYPE + " , " +
                Database.Aircrafts.MAX_CRUISE_SPEED + " , " +
                Database.Aircrafts.MAX_ALTITUDE + " , " +
                Database.Aircrafts.MAX_RANGE +
                " FROM " + Database.Aircrafts.TABLE_NAME +
                " WHERE " + Database.Aircrafts.AIRCRAFT_ID + " = " + ID;;
        Cursor c = db.rawQuery(myQuery, null);
        c.moveToFirst();
        db.close();
        return c;
    }

    public void editAircraft(Aircraft a) {
        ContentValues values = new ContentValues();
        values.put(Database.Aircrafts.MANUFACTURER, a.getAircraft_manufacturer());
        values.put(Database.Aircrafts.REG, a.getAircraft_reg());
        values.put(Database.Aircrafts.TYPE, a.getAircraft_type());
        values.put(Database.Aircrafts.MAX_CRUISE_SPEED, a.getAircraft_maxCruiseSpeed());
        values.put(Database.Aircrafts.MAX_ALTITUDE, a.getAircraft_maxAltitude());
        values.put(Database.Aircrafts.MAX_RANGE, a.getAircraft_maxRange());

        SQLiteDatabase db = getWritableDatabase();
        db.update(Database.Aircrafts.TABLE_NAME, values, Database.Aircrafts.AIRCRAFT_ID + " = " + a.getID(), null);
        db.close();
    }

//endregion <---Aircraft

//region <---Route

    public void addRoute(Route c) {
        ContentValues values = new ContentValues();
        values.put(Database.Route.FROM, c.getRoute_from());
        values.put(Database.Route.TO, c.getRoute_to());
        values.put(Database.Route.STOP1, c.getRoute_stop1());
        values.put(Database.Route.STOP2, c.getRoute_stop2());
        values.put(Database.Route.STOP3, c.getRoute_stop3());
        values.put(Database.Route.STOP4, c.getRoute_stop4());
        values.put(Database.Route.STOP5, c.getRoute_stop5());
        values.put(Database.Route.TIME, c.getRoute_time());
        values.put(Database.Route.SPEED, c.getRoute_speed());
        values.put(Database.Route.DISTANCE, c.getRoute_distance());

        SQLiteDatabase db = getWritableDatabase();
        long newID = db.insert(Database.Route.TABLE_NAME, null, values);
        db.close();
    }

    public Cursor lastRouteID() {
        SQLiteDatabase db = getWritableDatabase();
        String myQuery = "SELECT " +
                Database.Route.ROUTE_ID +
                " FROM " + Database.Route.TABLE_NAME +
                " ORDER BY " + Database.Route.ROUTE_ID + " DESC " + "limit 1";
        Cursor c = db.rawQuery(myQuery, null);
        c.moveToFirst();
        db.close();
        return c;
    }


    public void updateRoute(Route c) {
        ContentValues values = new ContentValues();
        values.put(Database.Route.FROM, c.getRoute_from());
        values.put(Database.Route.TO, c.getRoute_to());
        values.put(Database.Route.STOP1, c.getRoute_stop1());
        values.put(Database.Route.STOP2, c.getRoute_stop2());
        values.put(Database.Route.STOP3, c.getRoute_stop3());
        values.put(Database.Route.STOP4, c.getRoute_stop4());
        values.put(Database.Route.STOP5, c.getRoute_stop5());
        values.put(Database.Route.TIME, c.getRoute_time());
        values.put(Database.Route.SPEED, c.getRoute_speed());
        values.put(Database.Route.DISTANCE, c.getRoute_distance());

        SQLiteDatabase db = getWritableDatabase();

        db.update(Database.Route.TABLE_NAME, values, Database.Route.ROUTE_ID + " = " + c.getID(), null);
        db.close();
    }

//endregion <---Route

//region <---SavedRoutes

    public void saveRoute(SavedRoutes c) {
        ContentValues values = new ContentValues();
        values.put(Database.SavedRoutes.FROM, c.getSavedroutes_from());
        values.put(Database.SavedRoutes.FROMGPS, c.getSavedroutes_fromgps());
        values.put(Database.SavedRoutes.TO, c.getSavedroutes_to());
        values.put(Database.SavedRoutes.TOGPS, c.getSavedroutes_togps());
        values.put(Database.SavedRoutes.STOP1, c.getSavedroutes_stop1());
        values.put(Database.SavedRoutes.STOP1GPS, c.getSavedroutes_stop1gps());
        values.put(Database.SavedRoutes.STOP2, c.getSavedroutes_stop2());
        values.put(Database.SavedRoutes.STOP2GPS, c.getSavedroutes_stop2gps());
        values.put(Database.SavedRoutes.STOP3, c.getSavedroutes_stop3());
        values.put(Database.SavedRoutes.STOP3GPS, c.getSavedroutes_stop3gps());
        values.put(Database.SavedRoutes.STOP4, c.getSavedroutes_stop4());
        values.put(Database.SavedRoutes.STOP4GPS, c.getSavedroutes_stop4gps());
        values.put(Database.SavedRoutes.STOP5, c.getSavedroutes_stop5());
        values.put(Database.SavedRoutes.STOP5GPS, c.getSavedroutes_stop5gps());
        values.put(Database.SavedRoutes.SPEED, c.getSavedroutes_speed());
        values.put(Database.SavedRoutes.ALTITUDE, c.getSavedroutes_altitude());
        values.put(Database.SavedRoutes.DISTANCE, c.getSavedroutes_distance());

        SQLiteDatabase db = getWritableDatabase();
        long newID = db.insert(Database.SavedRoutes.TABLE_NAME, null, values);
        db.close();
    }


    public Cursor getSavedRoutesNameCursor() {
        SQLiteDatabase db = getWritableDatabase();
        String myQuery = "SELECT " +
                Database.SavedRoutes.SAVEDROUTE_ID + " , " +
                Database.SavedRoutes.FROM + " , " +
                Database.SavedRoutes.TO + " , " +
                Database.SavedRoutes.STOP1 + " , " +
                Database.SavedRoutes.STOP2 + " , " +
                Database.SavedRoutes.STOP3 + " , " +
                Database.SavedRoutes.STOP4 + " , " +
                Database.SavedRoutes.STOP5 + " , " +
                Database.SavedRoutes.SPEED + " , " +
                Database.SavedRoutes.ALTITUDE + " , " +
                Database.SavedRoutes.DISTANCE + " , " +
                Database.SavedRoutes.FROMGPS + " , " +
                Database.SavedRoutes.TOGPS + " , " +
                Database.SavedRoutes.STOP1GPS + " , " +
                Database.SavedRoutes.STOP2GPS + " , " +
                Database.SavedRoutes.STOP3GPS + " , " +
                Database.SavedRoutes.STOP4GPS + " , " +
                Database.SavedRoutes.STOP5GPS +
                " FROM " + Database.SavedRoutes.TABLE_NAME;
        Cursor c = db.rawQuery(myQuery, null);
        c.moveToFirst();
        db.close();
        return c;
    }

    public void deleteSavedRoute(long ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Database.SavedRoutes.TABLE_NAME, Database.SavedRoutes.SAVEDROUTE_ID + "=" + ID, null);
        db.close();
    }


//endregion <---SavedRoutes

//region <---Navigation

    public void addMyrouteLocations(MyRoutesLocation d) {
        ContentValues values = new ContentValues();
        values.put(Database.MyRoutesLocations.ROUTEID, d.getMyrouteslocations_routeid());
        values.put(Database.MyRoutesLocations.LOCATION, d.getMyrouteslocations_location());

        SQLiteDatabase db = getWritableDatabase();
        long newID = db.insert(Database.MyRoutesLocations.TABLE_NAME, null, values);
    }

    public void addMyRouteData(MyRoutesData g) {
        ContentValues values = new ContentValues();
        values.put(Database.MyRoutesData.ROUTEID, g.getMyroutesdata_routeid());
        values.put(Database.MyRoutesData.FROM, g.getMyroutesdata_from());
        values.put(Database.MyRoutesData.TO, g.getMyroutesdata_to());
        values.put(Database.MyRoutesData.STOP1, g.getMyroutesdata_stop1());
        values.put(Database.MyRoutesData.STOP2, g.getMyroutesdata_stop2());
        values.put(Database.MyRoutesData.STOP3, g.getMyroutesdata_stop3());
        values.put(Database.MyRoutesData.STOP4, g.getMyroutesdata_stop4());
        values.put(Database.MyRoutesData.STOP5, g.getMyroutesdata_stop5());
        values.put(Database.MyRoutesData.RAWTIME, g.getMyroutesdata_rawtime());
        values.put(Database.MyRoutesData.RAWDISTANCE, g.getMyroutesdata_rawdistance());
        values.put(Database.MyRoutesData.RAWSPEED, g.getMyroutesdata_rawspeed());
        values.put(Database.MyRoutesData.TIME, g.getMyroutesdata_time());
        values.put(Database.MyRoutesData.DISTANCE, g.getMyroutesdata_distance());
        values.put(Database.MyRoutesData.SPEED, g.getMyroutesdata_speed());

        SQLiteDatabase db = getWritableDatabase();
        long newID = db.insert(Database.MyRoutesData.TABLE_NAME, null, values);
        db.close();
    }

    public void deleteMyRoutesData(long ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Database.MyRoutesData.TABLE_NAME, Database.MyRoutesData.ROUTEID + "=" + ID, null);
        db.close();
    }

    public void deleteMyRoutesLocations(long ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Database.MyRoutesLocations.TABLE_NAME, Database.MyRoutesLocations.ROUTEID + "=" + ID, null);
        db.close();
    }


    public Cursor getMyRoutesDataCursor() {
        SQLiteDatabase db = getWritableDatabase();
        String myQuery = "SELECT " +
                Database.MyRoutesData.MYROUTESDATA_ID + " , " +
                Database.MyRoutesData.ROUTEID + " , " +
                Database.MyRoutesData.FROM + " , " +
                Database.MyRoutesData.TO + " , " +
                Database.MyRoutesData.STOP1 + " , " +
                Database.MyRoutesData.STOP2 + " , " +
                Database.MyRoutesData.STOP3 + " , " +
                Database.MyRoutesData.STOP4 + " , " +
                Database.MyRoutesData.STOP5 + " , " +
                Database.MyRoutesData.TIME + " , " +
                Database.MyRoutesData.DISTANCE + " , " +
                Database.MyRoutesData.SPEED +
                " FROM " + Database.MyRoutesData.TABLE_NAME;
        Cursor c = db.rawQuery(myQuery, null);
        c.moveToFirst();
        db.close();
        return c;
    }

    public Cursor getMyRoutesLocationsCursor(long ID) {
        SQLiteDatabase db = getWritableDatabase();
        String myQuery = "SELECT " +
                Database.MyRoutesLocations.LOCATION +
                " FROM " + Database.MyRoutesLocations.TABLE_NAME +
                " WHERE " + Database.MyRoutesLocations.ROUTEID + " = " + ID;
        Cursor c = db.rawQuery(myQuery, null);
        c.moveToFirst();
        db.close();
        return c;
    }

//endregion <---Navigation

//region  <---Stats

    public Cursor getStats() {
        SQLiteDatabase db = getWritableDatabase();
        String myQuery = "SELECT " +
                "min(" + Database.Route.TIME + "), " +
                "max(" + Database.Route.TIME + "), " +
                "cast(round(avg(" + Database.Route.TIME + ")) as int), " +
                "sum(" + Database.Route.TIME + "), " +

                "min(" + Database.Route.DISTANCE + "), " +
                "max(" + Database.Route.DISTANCE + "), " +
                "avg(" + Database.Route.DISTANCE + "), " +
                "sum(" + Database.Route.DISTANCE + "), " +

                "min(" + Database.Route.SPEED + "), " +
                "max(" + Database.Route.SPEED + "), " +
                "avg(" + Database.Route.SPEED + ")" +

                " FROM " + Database.Route.TABLE_NAME;
        Cursor c = db.rawQuery(myQuery, null);
        c.moveToFirst();
        db.close();
        return c;
    }


//endregion <---Stats
}
