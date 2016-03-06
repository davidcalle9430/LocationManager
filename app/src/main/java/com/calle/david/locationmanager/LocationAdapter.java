package com.calle.david.locationmanager;
import com.calle.david.locationmanager.data.LocationContract.LocationEntry;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.calle.david.locationmanager.data.LocationContract;

import org.w3c.dom.Text;

/**
 * Created by david on 2/29/2016.
 */
public class LocationAdapter extends CursorAdapter {

    public static class ViewHolder{
        public final TextView locationName;
        public final TextView locationLocation;
        public final LinearLayout container;

        public ViewHolder(View v){
            locationLocation = (TextView) v.findViewById(R.id.location_location);
            locationName = (TextView) v.findViewById(R.id.location_name);
            container = (LinearLayout) v.findViewById(R.id.location_item);
        }
    }

    public LocationAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


    @Override
    public View newView(final Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.location_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent( context,  DetailView.class);
                i.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                // sacar el id del location y buscarlo en una query
                context.startActivity( i );
            }
        });
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        String locationName = cursor.getString( LocationEntry.COL_NAME );
        String positionLat = cursor.getString( LocationEntry.COL_LAT );
        String positionLon = cursor.getString( LocationEntry.COL_LON );
        String description = cursor.getString( LocationEntry.COL_DESC );
        String position = positionLat + "-" + positionLon;
        viewHolder.locationName.setText( locationName );
        viewHolder.locationLocation.setText( description );

    }
}
