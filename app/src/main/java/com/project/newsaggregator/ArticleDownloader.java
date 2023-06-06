package com.project.newsaggregator;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ArticleDownloader {
    private static final String DATE_FORMAT = "MMM dd, yyyy HH:mm";
    private static final String DATE_FORMAT_PARSE = "yyyy-MM-dd'T'HH:mm:ss";
    private static final SimpleDateFormat sdfFormat = new SimpleDateFormat(DATE_FORMAT);
    private static final SimpleDateFormat sdfParse = new SimpleDateFormat(DATE_FORMAT_PARSE);
    private static final String dataURL = "https://newsapi.org/v2/top-headlines";
    private static final String yourAPIKey = "e7f987bc26384ae2885a5cb396ad0a0f";
    private static MainActivity mainActivity;
    private static ArrayList<Article> articleList = new ArrayList<>();
    private static String source;
    private static RequestQueue queue;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Article> doDownload(MainActivity ma, String sourceq) {
        queue = Volley.newRequestQueue(ma);
        mainActivity = ma;
        source=sourceq;
        Uri.Builder buildURL = Uri.parse(dataURL).buildUpon();
        buildURL.appendQueryParameter("sources", sourceq);
        buildURL.appendQueryParameter("apiKey", yourAPIKey);
        String urlToUse = buildURL.build().toString();
        Response.Listener<JSONObject> listener =
                response -> {

                    parseJSON(response.toString());
                    mainActivity.updateArticleData(articleList);


                };

        Response.ErrorListener error =
                error1 -> {
                    Toast.makeText(mainActivity, "No network connection", Toast.LENGTH_SHORT).show();

                };

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, urlToUse,
                        null, listener, error) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("User-Agent", "News-App");
                        return headers;
                    }
                };


        queue.add(jsonObjectRequest);
        return articleList;

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void parseJSON(String s) {

        try {
            articleList.clear();
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jObjectJSONArray = jsonObject.getJSONArray("articles");
            for (int i = 0; i < jObjectJSONArray.length(); i++) {
                Article articleJson = new Article();
                articleJson.setArticleAuthor(jObjectJSONArray.getJSONObject(i).getString("author").replace("null", ""));
                articleJson.setArticleTitle(jObjectJSONArray.getJSONObject(i).getString("title").replace("null", ""));
                articleJson.setArticleUrl(jObjectJSONArray.getJSONObject(i).getString("url"));
                articleJson.setArticleUrltoimage(jObjectJSONArray.getJSONObject(i).getString("urlToImage"));
                Date parsedDate = sdfParse.parse(jObjectJSONArray.getJSONObject(i).getString("publishedAt"));
                articleJson.setArticlePublishedat(sdfFormat.format(parsedDate));
                articleJson.setArticleDescription(jObjectJSONArray.getJSONObject(i).getString("description"));
                articleList.add(articleJson);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }





}
