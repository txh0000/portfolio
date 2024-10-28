package com.example.content_resolver;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Uri uri;
    TextView tV = findViewById(R.id.textView_id);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uri = Uri.parse("content://fit2081.app.XiHeng/items");

        String[] projection = {"Book_ID", "Book_Title", "Price"};
        String selection = null;
        String[] selectionArgs = null;

        Cursor result = getContentResolver().query(uri, projection, selection,selectionArgs,null);
        tV.setText(result.getCount()+"");

    }

    public void add(View v){
        ContentValues values = new ContentValues();
        values.put("Book_Title","harry");
        values.put("Price","45");
        Uri uri2 = getContentResolver().insert(uri,values);
    }

    public void show(View v){
        TextView tV = findViewById(R.id.textView3);
        String[] projection = {"Book_ID", "Book_Title","ISBN", "Author", "Description", "Price"};
        String selection = "Price>?";
        String[] selectionArgs = {"50"};
        Cursor result = getContentResolver().query(uri, projection, selection,selectionArgs,null);
        tV.setText(result.getCount()+"");
    }
}