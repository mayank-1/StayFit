package com.stayfit.app.stayfitBharat;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by bruker on 22.06.2017.
 */

public class FoodCursorAdapter extends CursorAdapter {
    public FoodCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you dont bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.fragment_food_list_item, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView textViewListName = (TextView) view.findViewById(R.id.textViewListName);
        TextView textViewListNumber = (TextView) view.findViewById(R.id.textViewListNumber);
        TextView textViewListSubName = (TextView) view.findViewById(R.id.textViewListSubName);

        // Extract properties from cursor
        int getID = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        String getName = cursor.getString(cursor.getColumnIndexOrThrow("food_name"));

        String getManufactorName = cursor.getString(cursor.getColumnIndexOrThrow("food_manufactor_name"));
        String getDescription = cursor.getString(cursor.getColumnIndexOrThrow("food_description"));
        String getServingSize = cursor.getString(cursor.getColumnIndexOrThrow("food_serving_size_gram"));
        String getServingMesurment = cursor.getString(cursor.getColumnIndexOrThrow("food_serving_size_gram_mesurment"));
        String getServingNameNumber = cursor.getString(cursor.getColumnIndexOrThrow("food_serving_size_pcs"));
        String getServingNameWord = cursor.getString(cursor.getColumnIndexOrThrow("food_serving_size_pcs_mesurment"));
        int getEnergyCalculated = cursor.getInt(cursor.getColumnIndexOrThrow("food_energy_calculated"));

        String subLine = getManufactorName + ", " +
                getServingSize + " " +
                getServingMesurment + ", " +
                getServingNameNumber + " " +
                getServingNameWord;

        // Populate fields with extracted properties
        textViewListName.setText(getName);
        textViewListNumber.setText(String.valueOf(getEnergyCalculated));
        textViewListSubName.setText(subLine);

    }
}