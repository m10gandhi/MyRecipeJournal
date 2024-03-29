package com.example.m10ga.myrecipejournal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.m10ga.myrecipejournal.Model.ListItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailUrlActivity extends AppCompatActivity {

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference().child("url");
    TextView tv_url_recipe;
    TextView tv_url;
    TextView tv_person_url;
    TextView tv_rating_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_url);

        tv_url_recipe=(TextView)findViewById(R.id.tv_url_recipe);
        tv_url=(TextView)findViewById(R.id.tv_url);
        tv_person_url=(TextView)findViewById(R.id.tv_person_url);
        tv_rating_url=(TextView)findViewById(R.id.tv_rating_url);

        String url_recipe=getIntent().getStringExtra("url_recipe");
        String Link=getIntent().getStringExtra("url");
        String person_url=getIntent().getStringExtra("people_url");
        String rating_url=getIntent().getStringExtra("rating_url");

        tv_rating_url.setText(rating_url);
        tv_url_recipe.setText(url_recipe);
        tv_url.setText(Link);
        tv_person_url.setText(person_url);

    }



}
//[1][2][5]