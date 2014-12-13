package gcu.ac.uk.gamenews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapDataDBMgr extends SQLiteOpenHelper {

    // Declare and initialise variables
    private static final int DB_VER = 1;
    private static final String DB_PATH = "/data/data/gcu.ac.uk.gamenews/databases/";
    private static final String DB_NAME = "GameStudios.s3db";
    private static final String TBL_MAPSTUDIOS = "GameStudios";

    public static final String COL_ENTRYID = "entryID";
    public static final String COL_NAME = "Name";
    public static final String COL_WEBSITE = "Website";
    public static final String COL_LATITUDE = "Latitude";
    public static final String COL_LONGITUDE = "Longitude";

    private final Context appContext;

    public MapDataDBMgr(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        //create activity
        super(context, name, factory, version);
        this.appContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the table if it does not exist yrt
        String CREATE_MAPEKFAME_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TBL_MAPSTUDIOS + "("
                + COL_ENTRYID + " INTEGER PRIMARY KEY," + COL_NAME
                + " TEXT," + " TEXT," + COL_WEBSITE + " TEXT,"
                + " TEXT" + COL_LATITUDE + " FLOAT" + COL_LONGITUDE + " FLOAT" +")";
        db.execSQL(CREATE_MAPEKFAME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //check database version
        if(newVersion > oldVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TBL_MAPSTUDIOS);
            onCreate(db);
        }
    }

    // Creates a empty database on the system and rewrites it with your own database.
    public void dbCreate() throws IOException {
        boolean dbExist = dbCheck();
        if(!dbExist){
            //By calling this method an empty database will be created into the default system path
            //of your application so we can overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDBFromAssets();
            } catch (IOException e) {//catch any errors
                throw new Error("Error copying database");
            }
        }
    }

    private boolean dbCheck(){
        SQLiteDatabase db = null;
        // Check if the database already exist to avoid re-copying the file each time you open the application.
        try{
            String dbPath = DB_PATH + DB_NAME;
            db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
            db.setLocale(Locale.getDefault());
            db.setVersion(1);

        }catch(SQLiteException e){//catch any errors
            Log.e("SQLHelper","Database not Found!");
        }
        if(db != null){
            db.close();
        }
        //return true if it exists, false if it doesn't
        return db != null ? true : false;
    }

    // Copies your database from your local assets-folder to the just created empty database in the
    // system folder, from where it can be accessed and handled.
    // This is done by transfering bytestream.
    private void copyDBFromAssets() throws IOException{
        InputStream dbInput = null;
        OutputStream dbOutput = null;
        String dbFileName = DB_PATH + DB_NAME;
        try {
            dbInput = appContext.getAssets().open(DB_NAME);
            dbOutput = new FileOutputStream(dbFileName);
            //transfer bytes from the dbInput to the dbOutput
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dbInput.read(buffer)) > 0) {
                dbOutput.write(buffer, 0, length);
            }
            //Close the streams
            dbOutput.flush();
            dbOutput.close();
            dbInput.close();
        } catch (IOException e)//catch any errors
        {
            throw new Error("Problems copying DB!");
        }
    }

    public void addaMapStudioEntry(MapData aMapStudio) {

        //add all values to internal database
        ContentValues values = new ContentValues();
        values.put(COL_NAME, aMapStudio.getName());
        values.put(COL_WEBSITE, aMapStudio.getWebsite());
        values.put(COL_LATITUDE, aMapStudio.getLatitude());
        values.put(COL_LONGITUDE, aMapStudio.getLongitude());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TBL_MAPSTUDIOS, null, values);
        db.close();
    }

    public MapData getMapStudioEntry(String aMapStudioEntry) {
        //get data from database
        String query = "Select * FROM " + TBL_MAPSTUDIOS + " WHERE " + COL_NAME + " =  \"" + aMapStudioEntry + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        MapData MapDataEntry = new MapData();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            MapDataEntry.setEntryID(Integer.parseInt(cursor.getString(0)));
            MapDataEntry.setName(cursor.getString(1));
            MapDataEntry.setWebsite(cursor.getString(2));
            MapDataEntry.setLatitude(Float.parseFloat(cursor.getString(3)));
            MapDataEntry.setLatitude(Float.parseFloat(cursor.getString(4)));
            cursor.close();
        } else {
            MapDataEntry = null;
        }
        db.close();
        return MapDataEntry;
    }

    public List<MapData> allMapData()
    {
        //get all data from database
        String query = "Select * FROM " + TBL_MAPSTUDIOS;
        //create list
        List<MapData> mcMapDataList = new ArrayList<MapData>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        //add each piece of data to the list
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast()==false) {
                MapData MapDataEntry = new MapData();
                MapDataEntry.setEntryID(Integer.parseInt(cursor.getString(0)));
                MapDataEntry.setName(cursor.getString(1));
                MapDataEntry.setWebsite(cursor.getString(2));
                MapDataEntry.setLatitude(Float.parseFloat(cursor.getString(3)));
                MapDataEntry.setLongitude(Float.parseFloat(cursor.getString(4)));
                mcMapDataList.add(MapDataEntry);
                cursor.moveToNext();
            }
        } else {//if no data set to null
            mcMapDataList.add(null);
        }
        //close the database
        db.close();
        //return the list
        return mcMapDataList;
    }
}