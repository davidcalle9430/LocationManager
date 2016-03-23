package com.calle.david.locationmanager.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by david on 2/28/2016.
 */
public class LocationContract {

    /**
     * esto es como el dominio de una página web, la idea es usarlo para identificar la app de las demas
     * fue el unico nombre que se me ocurrio
     */
    public static final String CONTENT_AUTHORITY = "com.movil.sagrado.corazon";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    //este es el id para identificar los recursos que sean de ubicacion
    public static final String PATH_LOCATION = "location";
    public static final String PATH_INFORMATION = "information";
    public static final String PATH_SITUATION = "situation";
    public static final String PATH_SITUATION_LOCATION = "situation_location";



    public final static class LocationEntry implements BaseColumns{
        // la ubicación del recurso de ubicacion
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATION).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
        /**
         * nombres de la tabla como de las columnas
         */
        public final static String TABLE_NAME = "location";

        //nombre de los atributos de la tabla
        public final static String COLUMN_LOCATION_NAME = "location_name";
        public final static String COLUMN_COORD_LAT = "coord_lat";
        public final static String COLUMN_COOD_LON = "coord_lon";
        public final static String COLUMN_DESCRIPTION = "description";
        public final static String COLUMN_RADIO = "radio" ;

        /*
        * indices de las columnas
         */
        public final static Integer COL_ID = 0;
        public final static Integer COL_NAME = 1;
        public final static Integer COL_LAT = 2;
        public final static Integer COL_LON = 3;
        public final static Integer COL_DESC = 4;
        public final static Integer COL_RADIO = 5;


        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
    public final static class InformationEntry implements  BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INFORMATION).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INFORMATION;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INFORMATION;


        public final static String COLUMN_LOC_KEY = "location_id";
        public final static String TABLE_NAME = "information";
        public final static String COLUMN_KEY = "key";
        public final static String COLUMN_VALUE = "value";

        public final static Integer COL_ID = 0;
        public final static Integer COL_KEY = 1;
        public final static Integer COL_VALUE = 2;

        public static Uri buildInformationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildInformationLocation(String locationSetting) {
            return CONTENT_URI.buildUpon().appendPath(locationSetting).build();
        }

        public static Integer getLocationFromURI( Uri uri){
            return Integer.parseInt(uri.getPathSegments().get(1)); // ejemplo de URI  /information/1 obteine la informacion de la ubicacion 1
        }

    }


    public final static class SituationEntry {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SITUATION).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SITUATION;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SITUATION;

        public final static String TABLE_NAME = "situation";

        public final static String COLUMN_SITUATION_NAME = "situation_name";

        public final static Integer COL_NAME = 0;

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public final static class SituationXLocationEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath( PATH_SITUATION_LOCATION ).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SITUATION_LOCATION;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SITUATION_LOCATION ;

        public final static String TABLE_NAME = "situationXlocation";

        public final static String COLUMN_SITUATION_NAME = "situation_pk";
        public final static String COLUMN_LOCATION_KEY = "location_pk";


        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
