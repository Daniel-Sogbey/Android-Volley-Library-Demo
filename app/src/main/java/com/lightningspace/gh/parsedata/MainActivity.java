package com.lightningspace.gh.parsedata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue;
    String url = "https://www.google.com";
    String apiUrl = "https://jsonplaceholder.typicode.com/todos/1";
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);

        queue = Volley.newRequestQueue(this);

        getJsonObjectRequest(queue);


        getJsonArrayRequest(queue);

        getStringRequest(queue);

    }

    private void getJsonObjectRequest(RequestQueue queue) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    try {
                        Log.d("JSON", "onResponse: " + response.getString("title"));
                        text.setText(response.getString("title"));
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON", "onErrorResponse: Failed " + error.getMessage());
            }
        });

        ;queue.add(jsonObjectRequest);
    }

    private void getStringRequest(RequestQueue queue) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            //display content of our url
                Log.d("Main", "onResponse: " + response.substring(0,500));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //display error message from request if any
                Log.d("Main", "Failed to get info!" + error.getMessage());
            }
        });

        queue.add(stringRequest);
    }

    private void getJsonArrayRequest(RequestQueue queue) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i = 0; i < response.length(); i ++){
                    try{
                        JSONObject jsonObject = response.getJSONObject(i);
                        Log.d("JSON", "onResponse: " + jsonObject.getString("userId"));
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON", "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);
    }


}