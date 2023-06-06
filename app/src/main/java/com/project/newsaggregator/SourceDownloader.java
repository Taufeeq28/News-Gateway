package com.project.newsaggregator;

import android.net.Uri;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class SourceDownloader {

    private static final String dataURL = "https://newsapi.org/v2/sources?apiKey=01a85ff77e064b7c9a4db13df6fcfab2";
    private static final String yourAPIKey = "e7f987bc26384ae2885a5cb396ad0a0f";
    private static MainActivity mainActivity;
    private static ArrayList<Source> sourceList = new ArrayList<>();
    private static ArrayList<String> categoryList = new ArrayList<>();
    private static String category;
    private static int[] topicColors;
    private static Map<String, Integer> topicIntMap;
    private static RequestQueue queue;
    public static void download(MainActivity ma, String categorya) {
        mainActivity = ma;
        queue = Volley.newRequestQueue(mainActivity);
        if (categorya.isEmpty() || categorya.equalsIgnoreCase("all")) {
            category = "";
        } else {
            category = categorya;
        }
        Response.Listener<JSONObject> listener =
                response -> {

                    parseJSON(response.toString());
                    mainActivity.updateData(sourceList,categoryList);


                };

        Response.ErrorListener error =
                error1 -> {
                    Toast.makeText(mainActivity, "No network connection", Toast.LENGTH_SHORT).show();

                };

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, dataURL,
                        null, listener, error) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("User-Agent", "News-App");
                        return headers;
                    }
                };


        queue.add(jsonObjectRequest);


    }
    private static void parseJSON(String s) {

        try {
            topicIntMap = new HashMap<>();
            sourceList.clear();
            categoryList.clear();
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonObjectJSONArray = jsonObject.getJSONArray("sources");
            for (int i = 0; i < jsonObjectJSONArray.length(); i++) {
                Source setSource = new Source();
                setSource.setId(jsonObjectJSONArray.getJSONObject(i).getString("id"));
                setSource.setName(jsonObjectJSONArray.getJSONObject(i).getString("name"));
                setSource.setUrl(jsonObjectJSONArray.getJSONObject(i).getString("url"));
                setSource.setCategory(jsonObjectJSONArray.getJSONObject(i).getString("category"));
                sourceList.add(setSource);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < sourceList.size(); i++) {
            if (!categoryList.contains(sourceList.get(i).getCategory())) {
                categoryList.add(sourceList.get(i).getCategory());
            }
        }
    }

}
