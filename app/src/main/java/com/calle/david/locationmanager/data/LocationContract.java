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
     * esto es como el dominio de una página web
     */
    public static final String CONTENT_AUTHORITY = "com.movil.sagrado.corazon";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_LOCATION = "location";

    public final static class LocationEntry implements BaseColumns{



        // la ubicación del recurso de ubicacion
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATION).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;



        public final static String TABLE_NAME = "location";
        public final static String COLUMN_LOCATION_NAME = "location_name";
        public final static String COLUMN_COORD_LAT = "coord_lat";
        public final static String COLUMN_COOD_LON = "coord:lon";

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
