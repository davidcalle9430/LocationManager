package com.calle.david.locationmanager.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.content.UriMatcher.*;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by david on 2/28/2016.
 */
public class LocationProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final int LOCATION = 300;
    private LocationDBHelper mOpenHelper;

    private static final SQLiteQueryBuilder sLocationSettingQueryBuilder;
    static{
        sLocationSettingQueryBuilder = new SQLiteQueryBuilder();
        sLocationSettingQueryBuilder.setTables( LocationContract.LocationEntry.TABLE_NAME );
    }


    static UriMatcher buildUriMatcher() {
        // 1) The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case. Add the constructor below.
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // 2) Use the addURI function to match each of the types.  Use the constants from
        // WeatherContract to help define the types to the UriMatcher.
        uriMatcher.addURI(LocationContract.CONTENT_AUTHORITY, LocationContract.PATH_LOCATION, LOCATION);
        // 3) Return the new matcher!
        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new LocationDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor = null ;
        switch (sUriMatcher.match(uri)){
            case LOCATION:{
                retCursor = null;
                retCursor = sLocationSettingQueryBuilder.query( mOpenHelper.getReadableDatabase(),
                  projection, null, null, null, null, sortOrder
                );
            }
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case LOCATION:
                return LocationContract.LocationEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown URI: "+ uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.v("Insercion", uri.toString());
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match){
            case LOCATION:{
                long _id = db.insert(LocationContract.LocationEntry.TABLE_NAME, null, values);
                if( _id > 0){
                    returnUri = LocationContract.LocationEntry.buildLocationUri(_id);
                }else {
                    throw new SQLException("Error al insertar la fila");
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown URI "+uri);
        }
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }


}
