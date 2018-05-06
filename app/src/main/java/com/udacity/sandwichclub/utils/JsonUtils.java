package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonObjectName = jsonObject.getJSONObject("name");
            String mainName = jsonObjectName.getString("mainName");
            JSONArray alsoKnownAsJSONArray = jsonObjectName.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = JSONArray2List(alsoKnownAsJSONArray);

            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            String description = jsonObject.getString("description");

            String image = jsonObject.getString("image");
            JSONArray ingredientsJSONArray = jsonObject.getJSONArray("ingredients");
            List<String> ingredients = JSONArray2List(ingredientsJSONArray);
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> JSONArray2List(JSONArray JSONarray) {
        List<String> list = new ArrayList<>();
        int length = JSONarray.length();
        for (int i = 0; i < length; i++) {
            try {
                list.add((String)JSONarray.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
