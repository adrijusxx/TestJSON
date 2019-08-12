package com.example.testrestapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //the URL having the json data
    private static final String JSON_URL = "http://tpbookserver.herokuapp.com/books";

    //listview object
    ListView listView;

    //Button object
    Button btSearch;
    Button btPost;

    //the list where I store all the book objects after parsing json
    List<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing listview and list
        btSearch = (Button) findViewById(R.id.btSearchbook);
        btPost = (Button) findViewById(R.id.btPostbook);
        listView = (ListView) findViewById(R.id.listView);
        bookList = new ArrayList<>();

        loadBookList();
        btSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentDetails = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intentDetails);
            }
        });
/*        btPost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentDetails = new Intent(MainActivity.this, PostActivity.class);
                startActivity(intentDetails);
            }
        });*/
    }

    //this method will fetch and parse the data
    private void loadBookList() {

        final ProgressBar loadingbar = (ProgressBar) findViewById(R.id.progressBar);


        loadingbar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        loadingbar.setVisibility(View.INVISIBLE);
                        try {
                            //getting the whole json object from the response
                            JSONArray obj = new JSONArray(response);

                            //looping through all the elements of the json array
                            for (int i = 0; i < obj.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject bookObject = obj.getJSONObject(i);

                                //creating a book object and giving them the values from json object
                                Book book = new Book(
                                        bookObject.getInt("id"),
                                        bookObject.getString("title"),
                                        bookObject.getString("isbn"),
                                        bookObject.getInt("price"),
                                        bookObject.getString("currencyCode"),
                                        bookObject.getString("author"));

                                bookList.add(book);
                            }
                            //creating custom adapter object
                            ListViewAdapter adapter = new ListViewAdapter(bookList, getApplicationContext());
                            //adding the adapter to listview
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //adding the string request to request queue
        requestQueue.add(stringRequest);
        //I had a problem with request on slow network so did this
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}