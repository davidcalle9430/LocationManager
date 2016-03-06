package com.calle.david.locationmanager.data;

import com.calle.david.locationmanager.data.LocationContract.LocationEntry;
import com.calle.david.locationmanager.data.LocationContract.InformationEntry;
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

    private static final int LOCATION = 300;
    private static final int INFORMATION = 101;
    private static final int INFORMATION_WITH_LOCATION = 102;

    private LocationDBHelper mOpenHelper;

    private static final SQLiteQueryBuilder sLocationSettingQueryBuilder;
    static{
        sLocationSettingQueryBuilder = new SQLiteQueryBuilder();
        sLocationSettingQueryBuilder.setTables( LocationContract.LocationEntry.TABLE_NAME );
    }

    private static final SQLiteQueryBuilder sLocationInformationQueryBuilder;
    static{
        sLocationInformationQueryBuilder = new SQLiteQueryBuilder();
        sLocationInformationQueryBuilder.setTables(
                LocationEntry.TABLE_NAME + " INNER JOIN " + InformationEntry.TABLE_NAME
                + " ON " + LocationEntry.TABLE_NAME + "." + LocationEntry._ID
                + " = " + InformationEntry.TABLE_NAME + "." + InformationEntry.COLUMN_LOC_KEY
        );
    }



    private static final String sLocationInformationSelection =
            InformationEntry.TABLE_NAME+
                    "." + InformationEntry.COLUMN_LOC_KEY + " = ? ";
    
    private static final String sLocationSelection = LocationEntry._ID + " = ? ";

    static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher( UriMatcher.NO_MATCH );

        uriMatcher.addURI(LocationContract.CONTENT_AUTHORITY, LocationContract.PATH_LOCATION, LOCATION);
         // falta aniadir las URIs Faltantes
        uriMatcher.addURI(LocationContract.CONTENT_AUTHORITY, LocationContract.PATH_INFORMATION, INFORMATION );
        uriMatcher.addURI(LocationContract.CONTENT_AUTHORITY, LocationContract.PATH_INFORMATION + "/*", INFORMATION_WITH_LOCATION);
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
                  projection, selection, selectionArgs, null, null, sortOrder
                );
                break;
            }case INFORMATION_WITH_LOCATION: {
                Log.wtf("El id es ", uri.toString() );
                Integer locationId = InformationEntry.getLocationFromURI(uri); // ya con la ubicaion solo es meter esto en el where
                String[] join = { locationId + "" };
                retCursor = sLocationInformationQueryBuilder.query( mOpenHelper.getReadableDatabase(),
                        projection,
                        sLocationInformationSelection , // con esto ohago el join entre ambas tablas
                        join,
                        null, // group by
                        null, // having
                        sortOrder
                );
                break;
            }case INFORMATION:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        InformationEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
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
            case INFORMATION:
                return InformationEntry.CONTENT_TYPE;
            case INFORMATION_WITH_LOCATION:
                return InformationEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown URI: "+ uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match){
            case LOCATION:{
                long _id = db.insert(LocationContract.LocationEntry.TABLE_NAME, null, values);
                if( _id > 0){
                    returnUri = LocationContract.LocationEntry.buildLocationUri(_id);
                }else {
                    throw new SQLException("Error al insertar la fila de ubicacion");
                }
                break;
            }case INFORMATION:{
                long _id = db.insert( InformationEntry.TABLE_NAME , null, values);
                if( _id > 0){
                    returnUri = LocationContract.LocationEntry.buildLocationUri(_id);
                }else {
                    throw new SQLException("Error al insertar la fila de informacion");
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown URI "+uri);
        }
        return returnUri;
    }

    /**
     * Por ahora para el alcance del proyecto no se puede ni editar ni eliminar
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }





}
