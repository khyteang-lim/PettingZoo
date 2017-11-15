package com.example.limsanity.firstapp.API;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * ALERT has the format:
 * "name": "Level 45 Power Core",
 * "status": "Active",
 * "selected": false,
 * "description": "Second label",
 * "date": "2017-05-08, 07:05:35 PM"
 */

public class AlertService {
    private static final String ENDPOINT = "http://:3000/alerts";

    private RequestQueue requestQueue;

    public AlertService(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public class Alert {
        public String name;
        public String status;
        boolean selected;
        public String description;
        public String date;
    }

    public void getAlerts(final OnGetAlerts callback, final Date[] dateGroups) {
        final StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Convert the returned string to JSON
                        List<Alert> posts = Arrays.asList(new Gson().fromJson(response, Alert[].class));

                        List<List<Alert>> grouped = new ArrayList<>(dateGroups.length);
                        for(Date date : dateGroups) {
                            // Get the first group of each subgroup and add to our return list.
                            grouped.add(groupByDate(posts, date).get(0));
                        }
                        // Pass this back to fragment.
                        callback.postsLoaded(grouped);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY ERROR", "Error getting data", error);
                        callback.postsLoaded(null);
                    }
                });
        this.requestQueue.add(request);
    }

    private List<List<Alert>> groupByDate(List<Alert> posts, final Date groupTime) {
        // Hold a list of groups
        List<List<Alert>> groups = new ArrayList<>();
        // Hold the current group of alerts
        List<Alert> currGroup = new ArrayList<>();

        // Loop through and group by groupTime
        Date startTime = parseDate(posts.get(0).date);
        for (Alert alert : posts) {
            assert startTime != null;
            Date alertTime = parseDate(alert.date);
            assert alertTime != null;
            // If alert + groupTime is past our range, add a new group
            if (alertTime.before(new Date(startTime.getTime() +
                    groupTime.getTime()))) {
                currGroup.add(alert);
            } else {
                startTime = parseDate(alert.date);
                groups.add(currGroup);
                currGroup = new ArrayList<>();
            }
        }
        // Add the last group into our final list
        groups.add(currGroup);
        return groups;
    }

    public static Date parseDate(String date) {
        SimpleDateFormat parser =
                new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss a", Locale.ENGLISH);
        try {
            return parser.parse(date);
        } catch (ParseException e) {
            Log.e("Error", "Invalid date", e);
        }
        return null;
    }

    public interface OnGetAlerts {
        void postsLoaded(List<List<Alert>> list);
    }

}
