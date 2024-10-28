package edu.monash.fit2081.countryinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebWiki extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_wiki);

        getSupportActionBar().setTitle("WIKI");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String selectedCountry = getIntent().getStringExtra("country");

        WebView webview = findViewById(R.id.webView);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("https://en.wikipedia.org/wiki/" + selectedCountry);
    }

    public void ToHome(View v){
        final String selectedCountry = getIntent().getStringExtra("country");
        Intent intent = new Intent(this, CountryDetails.class);
        intent.putExtra("country", selectedCountry);
        startActivity(intent);
    }
}
