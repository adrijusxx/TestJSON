package com.example.testrestapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Book> {

    //the list that will be displayed
    private List<Book> bookList;

    //the context object
    private Context mCtx;

    //getting the list and context
    public ListViewAdapter(List<Book> bookList, Context mCtx) {
        super(mCtx, R.layout.list_items, bookList);
        this.bookList = bookList;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.list_items, null, true);

        //getting text views
        TextView textViewId = listViewItem.findViewById(R.id.textViewId);
        TextView textViewAuthor = listViewItem.findViewById(R.id.textViewAuthor);
        TextView textViewCurrencyCode = listViewItem.findViewById(R.id.textViewCurrencyCode);
        TextView textViewIsbn = listViewItem.findViewById(R.id.textViewIsbn);
        TextView textViewPrice = listViewItem.findViewById(R.id.textViewPrice);
        TextView textViewTitle = listViewItem.findViewById(R.id.textViewTitle);
        //getting the book for the specified position
        Book book = bookList.get(position);

        //setting book values to textviews
        textViewId.setText("ID: " + book.getId());
        textViewAuthor.setText("Author: " + book.getAuthor());
        textViewCurrencyCode.setText("Currency Code: " + book.getCurrencyCode());
        textViewIsbn.setText("Isbn: " + book.getIsbn());
        textViewPrice.setText("Price: " + book.getPrice());
        textViewTitle.setText("Title: " + book.getTitle());

        //returning the listitem
        return listViewItem;
    }
}