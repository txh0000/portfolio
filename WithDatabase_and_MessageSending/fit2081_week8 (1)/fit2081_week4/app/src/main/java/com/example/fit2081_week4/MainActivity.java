package com.example.fit2081_week4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fit2081_week4.provider.Item;
import com.example.fit2081_week4.provider.ItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ItemViewModel mItemViewModel;
    DatabaseReference myRef;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager LayoutManager;
    MyRecyclerViewAdapter adapter;

    DrawerLayout drawer;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBook();
            }
        });

        adapter = new MyRecyclerViewAdapter();
        mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        mItemViewModel.getAllItems().observe(this, newData -> {
            adapter.setData(newData);
            adapter.notifyDataSetChanged();

        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Item/book");
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.add_book) {
                addBook();

            }

            if (id == R.id.remove_lastbook) {
                deleteSpecified();

            }

            if (id == R.id.remove_allbook) {
                deleteAll();
            }

            if (id == R.id.close) {
                finish();
            }

            if (id == R.id.showlist) {
                showList();
            }

            drawer.closeDrawers();
            return true;
        }
    }
    public void addBook () {
        EditText BookId = findViewById(R.id.ID_Input);
        EditText Title = findViewById(R.id.Title_input);
        EditText ISBN = findViewById(R.id.ISBN_Input);
        EditText Author = findViewById(R.id.Author_Input);
        EditText Description = findViewById(R.id.Description_Input);
        EditText Price = findViewById(R.id.Price_Input);

        Item item = new Item(BookId.getText().toString(), Title.getText().toString(), ISBN.getText().toString(), Author.getText().toString(),
                Description.getText().toString(), Price.getText().toString());
        mItemViewModel.insert(item);
        myRef.push().setValue(item);
    }

    public void showList() {
        Intent i = new Intent(this, MainActivity2.class);
        startActivity(i);
    }

    public void deleteSpecified() {
        mItemViewModel.deleteSpecified();
    }

    public void deleteAll() {
        mItemViewModel.deleteAll();
        myRef.removeValue();
    }
}
















