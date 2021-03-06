package com.example.topcoder.pettingzoo.API;

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
 * Product has the format:
 * "name": "Level 45 Power Core",
 * "buildingNo": 6409,
 * "fixtures": [{
 * "id": 8847544,
 * "location": "698,901,45",
 * "status": "Disabled"
 * }]
 */

public class ProductService {
    public String ENDPOINT = "/products";

    private RequestQueue requestQueue;

    public ProductService(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void getProducts(final OnGetProducts callback) {
        final StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Convert the returned string to JSON
                        List<Product> posts =
                                Arrays.asList(new Gson().fromJson(response, Product[].class));

                        // Pass this back to fragment.
                        callback.productsLoaded(posts);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY ERROR", "Error getting data", error);
                        callback.productsLoaded(null);
                    }
                });
        this.requestQueue.add(request);
    }

    public interface OnGetProducts {
        void productsLoaded(List<Product> list);
    }

    public class Product {
        public String name;
        public String buildingNo;
        public List<Fixture> fixtures;
    }

    public class Fixture {
        public long id;
        public String location;
        public String status;
    }

}
