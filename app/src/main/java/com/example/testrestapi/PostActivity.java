package com.example.testrestapi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostActivity extends AppCompatActivity {
    List<Book> bookList;
    private RequestQueue requestQueue;
    private EditText etID1, etTitle, etIsbn, etDescription, etPrice, etCurrencyCode, etAuthor;
    private Button btPostbook;
    private String URLline = "http://tpbookserver.herokuapp.com/book/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        bookList = new ArrayList<>();
        etID1 = findViewById(R.id.etID1);
        etTitle = findViewById(R.id.etTitle);
        etIsbn = findViewById(R.id.etIsbn);
        etDescription = findViewById(R.id.etDescription);
        etPrice = findViewById(R.id.etPrice);
        etCurrencyCode = findViewById(R.id.etCurrencyCode);
        etAuthor = findViewById(R.id.etAuthor);

        btPostbook = findViewById(R.id.btPostbook);

        btPostbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit();
            }
        });
    }

    private void Submit() {
        final String id = etID1.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PostActivity.this, response, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(PostActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    int id = Integer.parseInt(etID1.getText().toString().trim());
                    String title = etTitle.getText().toString().trim();
                    String isbn = etIsbn.getText().toString().trim();
                    String description = etDescription.getText().toString().trim();
                    int price = Integer.parseInt(etPrice.getText().toString().trim());
                    String currencycode = etCurrencyCode.getText().toString().trim();
                    String author = etAuthor.getText().toString().trim();
                    params.put("id", String.valueOf(id));
                    params.put("title", title);
                    params.put("isbn", isbn);
                    params.put("description", description);
                    params.put("price", String.valueOf(price));
                    params.put("currencyCode", currencycode);
                    params.put("author", author);


                } catch (
                        NumberFormatException e) {

                    e.printStackTrace();
                }
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

