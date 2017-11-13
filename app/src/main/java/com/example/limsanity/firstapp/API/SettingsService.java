package com.example.limsanity.firstapp.API;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

/**
 * Setting has the format:
     {
         "propertyGroup": "Connectivity",
         "properties": [
             {
                 "name": "Bluetooth",
                 "description": "Not visible to other devices",
                 "type": "toggle",
                 "value": false
             }, {
                 "name": "NFC",
                 "description": "Allow data exchange when the device touches another device",
                 "type": "toggle",
                 "value": true
             }]
    }
 */

public class SettingsService {
    private static final String ENDPOINT = "http://192.168.1.7:3000/settings";

    private RequestQueue requestQueue;

    public SettingsService(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public class Setting {
        public String name;
        public String description;
        public String type;
        public String value;
        public String min;
        public String max;
    }

    public void getSettings(final OnGetSettings callback) {
        final StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Convert the returned string to JSON
                        List<Setting> posts =
                                Arrays.asList(new Gson().fromJson(response, Setting[].class));

                        // Pass this back to fragment.
                        callback.settingsLoaded(posts);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY ERROR", "Error getting data", error);
                        callback.settingsLoaded(null);
                    }
                });
        this.requestQueue.add(request);
    }

    public interface OnGetSettings {
        void settingsLoaded(List<Setting> list);
    }

}
