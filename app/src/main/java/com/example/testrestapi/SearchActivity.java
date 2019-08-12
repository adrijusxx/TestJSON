package com.example.testrestapi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final String JSON_URL = "http://tpbookserver.herokuapp.com/book/";
    final List<Book> list = new ArrayList<>();
    Button btSearch;
    EditText etId;
    TextView bookByID;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        bookByID = findViewById(R.id.tv_Bookbyid);
        btSearch = findViewById(R.id.bt_Searchbook);
        etId = findViewById(R.id.et_ID1);
        queue = Volley.newRequestQueue(this);

        btSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                list.clear();
                bookByID.setText("");
                loadBook();
            }
        });
    }


    private void loadBook() {
        queue = Volley.newRequestQueue(this);
        int id = Integer.parseInt(etId.getText().toString());
        StringRequest request = new StringRequest(Request.Method.GET, JSON_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    for (int i = 0; i < object.length(); i++) {
                        int bookid = object.getInt("id");
                        String title = object.getString("title");
                        String isbn = object.getString("isbn");
                        String description = object.getString("description");
                        int price = object.getInt("price");
                        String currencyCode = object.getString("currencyCode");
                        String author = object.getString("author");
                        list.add(new Book(bookid, title, isbn, description, price, currencyCode, author));
                    }
                    bookByID.setText("Id: " + list.get(0).id + " \nTitle: " + list.get(0).title + " \nIsbn: " + list.get(0).isbn + " \nDescription: " + list.get(0).description
                            + " \nPrice: " + list.get(0).price + " \nCurrency code: " + list.get(0).currencyCode + " \nAuthor: " + list.get(0).author
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }


}


