package com.stayfit.app.stayfitBharat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mayank on 20/04/18.
 */

public class DataParser {

    //Method to get the Place
    private HashMap<String,String> getPlace(JSONObject googlePlaceJson) {

        HashMap<String, String> googlePlacesMap = new HashMap<>();
        String placename = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try {
            if (!googlePlaceJson.isNull("name")) {

                placename = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString(("vicinity"));
            }
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");

            reference = googlePlaceJson.getString("reference");

            googlePlacesMap.put("place_name", placename);
            googlePlacesMap.put("vicinity", vicinity);
            googlePlacesMap.put("lat", latitude);
            googlePlacesMap.put("lng", longitude);
            googlePlacesMap.put("reference", reference);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return googlePlacesMap;

    }

    private List<HashMap<String,String>> getPlaces(JSONArray jsonArray) {

        int count = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<>();
        HashMap<String, String> placeMap = null;

        for (int i = 0;i<count;i++) {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return placesList;

    }

    // When we will use DataParser it will call this method to parse the JSON data and then it will send it to getPlaces methods to get the places data
    public List<HashMap<String, String>> parse(String jsonData) {

        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getPlaces(jsonArray);
    }

}
