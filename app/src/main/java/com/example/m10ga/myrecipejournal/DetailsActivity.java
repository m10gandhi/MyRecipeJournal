package com.example.m10ga.myrecipejournal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    TextView tv_cooking;
    TextView tv_recipe;
    TextView tv_person;
    TextView tv_ingredients;
    TextView tv_prepartion;
    TextView tv_preparationtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tv_cooking=(TextView)findViewById(R.id.tv_cooking);
        tv_recipe=(TextView)findViewById(R.id.tv_recipe);
        tv_person=(TextView)findViewById(R.id.tv_person);
        tv_ingredients=(TextView)findViewById(R.id.tv_ingredients);
        tv_prepartion=(TextView)findViewById(R.id.tv_preparation);
        tv_preparationtime=(TextView)findViewById(R.id.tv_preparationtime);

        String cookingtime=getIntent().getStringExtra("key");
        String recipe_name=getIntent().getStringExtra("recipename");
        String person=getIntent().getStringExtra("person");
        String ingredients=getIntent().getStringExtra("ingredients");
        String preparation=getIntent().getStringExtra("preparation");
        String preparationtime=getIntent().getStringExtra("preparationtime");

        tv_cooking.setText(cookingtime+" "+"minutes");
        tv_recipe.setText(recipe_name);
        tv_person.setText(person);
        tv_ingredients.setText(ingredients);
        tv_prepartion.setText(preparation);
        tv_preparationtime.setText(preparationtime+" "+"minutes");


    }
}
