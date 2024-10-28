package com.example.fit2081_week4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.Manifest;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private String Book_ID_Saved;
    private String Book_Title_Saved;
    private String ISBN_Saved;
    private String Author_Saved;
    private String Description_Saved;
    private String Price_Saved;
    private String BookPosition;

    ArrayList<String> myList = new ArrayList<String>();
    ArrayAdapter myAdapter;
    int count = 1;

    ArrayList<Item> data = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager LayoutManager;
    MyRecyclerViewAdapter adapter;

    DrawerLayout drawer;
    View constraint_layout;
    GestureDetector gestureDetector;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);

        IntentFilter intentFilter = new IntentFilter("SMS_FILTER");
        registerReceiver(myReceiver, intentFilter);


        recyclerView = findViewById(R.id.rv);
        LayoutManager = new LinearLayoutManager(this);  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        recyclerView.setLayoutManager(LayoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager
        adapter = new MyRecyclerViewAdapter(data);
        recyclerView.setAdapter(adapter);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.NavigationView);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {

            EditText BookId = findViewById(R.id.ID_Input);
            EditText Title = findViewById(R.id.Title_input);
            EditText ISBN = findViewById(R.id.ISBN_Input);
            EditText Author = findViewById(R.id.Author_Input);
            EditText Description = findViewById(R.id.Description_Input);
            EditText Price = findViewById(R.id.Price_Input);

            SharedPreferences myData = getSharedPreferences("f1", 0);
            SharedPreferences.Editor myEditor = myData.edit();
            myEditor.putString("keyTitle", Title.getText().toString());
            myEditor.putString("keyISBN", ISBN.getText().toString());
            myEditor.putString("keyID", BookId.getText().toString());
            myEditor.putString("keyAuthor", Author.getText().toString());
            myEditor.putString("keyDescription", Description.getText().toString());
            myEditor.putString("keyPrice", Price.getText().toString());
            myEditor.apply();
            BookPosition = "Book" + count;
            count++;
            Item item = new Item(BookId.getText().toString(), Title.getText().toString(), ISBN.getText().toString()
                    , Author.getText().toString(), Description.getText().toString(), Price.getText().toString(), BookPosition);
            data.add(item);
            adapter.notifyDataSetChanged();
        });


        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        constraint_layout = findViewById(R.id.frameLayout);
        constraint_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            StringTokenizer message = new StringTokenizer(intent.getStringExtra("SMS_MSG_KEY"));
            String[] message = intent.getStringExtra("SMS_MSG_KEY").split("\\|");
            Toast ShowMessage = Toast.makeText(context , intent.getStringExtra("SMS_MSG_KEY") , Toast.LENGTH_SHORT);
            ShowMessage.show();

            EditText Book_ID = findViewById(R.id.ID_Input);
            EditText Title = findViewById(R.id.Title_input);
            EditText ISBN = findViewById(R.id.ISBN_Input);
            EditText Author = findViewById(R.id.Author_Input);
            EditText Description = findViewById(R.id.Description_Input);
            EditText Price = findViewById(R.id.Price_Input);
            boolean check_True = Boolean.parseBoolean(message[message.length-1]);

            Book_ID.setText(message[0]);
            Title.setText(message[1]);
            ISBN.setText(message[2]);
            Author.setText(message[3]);
            Description.setText(message[4]);

            if(check_True){
                Price.setText(String.valueOf(Integer.parseInt(message[5]) + 100));
            }else{
                Price.setText(String.valueOf(Integer.parseInt(message[5]) + 5));
            }
            
        }


    };

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener{
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public boolean onNavigationItemSelected(MenuItem item){
            int id = item.getItemId();
            if(id == R.id.add_book){
                EditText BookId = findViewById(R.id.ID_Input);
                EditText Title = findViewById(R.id.Title_input);
                EditText ISBN = findViewById(R.id.ISBN_Input);
                EditText Author = findViewById(R.id.Author_Input);
                EditText Description = findViewById(R.id.Description_Input);
                EditText Price = findViewById(R.id.Price_Input);

                SharedPreferences myData = getSharedPreferences("f1", 0);
                SharedPreferences.Editor myEditor = myData.edit();
                myEditor.putString("keyTitle", Title.getText().toString());
                myEditor.putString("keyISBN", ISBN.getText().toString());
                myEditor.putString("keyID", BookId.getText().toString());
                myEditor.putString("keyAuthor", Author.getText().toString());
                myEditor.putString("keyDescription", Description.getText().toString());
                myEditor.putString("keyPrice", Price.getText().toString());
                myEditor.apply();

//                myList.add(count +") "+ Title.getText().toString() + " | " + Price.getText().toString());
//                count++;
//                myAdapter.notifyDataSetChanged();

            }

            drawer.closeDrawers();
            return true;
        }
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            EditText BookId = findViewById(R.id.ID_Input);
            BookId.setText("");
            EditText Title = findViewById(R.id.Title_input);
            Title.setText("");
            EditText ISBN = findViewById(R.id.ISBN_Input);
            ISBN.setText("");
            EditText Author = findViewById(R.id.Author_Input);
            Author.setText("");
            EditText Description = findViewById(R.id.Description_Input);
            Description.setText("");
            EditText Price = findViewById(R.id.Price_Input);
            Price.setText("");
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            moveTaskToBack(true);
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            SharedPreferences myData = getSharedPreferences("f1", 0);
            String Saved_Title = myData.getString("keyTitle", "");
            String Saved_ID = myData.getString("keyID", "");
            String Saved_ISBN = myData.getString("keyISBN", "");
            String Saved_Author = myData.getString("keyAuthor", "");
            String Saved_Description = myData.getString("keyDescription", "");
            String Saved_Price = myData.getString("keyPrice", "");

            EditText BookId = findViewById(R.id.ID_Input);
            BookId.setText(Saved_ID);
            EditText Title = findViewById(R.id.Title_input);
            Title.setText(Saved_Title);
            EditText ISBN = findViewById(R.id.ISBN_Input);
            ISBN.setText(Saved_ISBN);
            EditText Author = findViewById(R.id.Author_Input);
            Author.setText(Saved_Author);
            EditText Description = findViewById(R.id.Description_Input);
            Description.setText(Saved_Description);
            EditText Price = findViewById(R.id.Price_Input);
            Price.setText(Saved_Price);
            super.onLongPress(e);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public boolean onScroll(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
            EditText Price = findViewById(R.id.Price_Input);
            EditText Title = findViewById(R.id.Title_input);
            int NewPrice = Integer.parseInt(Price.getText().toString());
            if(distanceX < 0){
                NewPrice += Math.abs(distanceX);
                Price.setText(String.valueOf(NewPrice));
            }
            if(distanceX > 0){
                NewPrice -= Math.abs(distanceX);
                Price.setText(String.valueOf(NewPrice));
            }
            if(Math.abs(distanceY) > 10){
                Title.setText("Untitled");
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onSingleTapUp(@NonNull MotionEvent e) {
            EditText ISBN = findViewById(R.id.ISBN_Input);
            String NewISBN = RandomString.generateNewRandomString(7);
            ISBN.setText(NewISBN);
            return super.onSingleTapUp(e);
        }
    }
}
















