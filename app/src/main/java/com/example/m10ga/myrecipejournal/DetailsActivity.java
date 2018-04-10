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

public class DetailsActivity extends AppCompatActivity {

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference().child("recipe");
    ListItem model;
    TextView tv_cooking;
    TextView tv_recipe;
    TextView tv_person;
    TextView tv_ingredients;
    TextView tv_prepartion;
    TextView tv_preparationtime;
   // ImageButton ib;
    //EditText ed;

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
     //   ib = (ImageButton)findViewById(R.id.imageButton);
      //  ed = (EditText)findViewById(R.id.update1);

//        ed.setVisibility(View.GONE);

  //      ib.setOnClickListener(new View.OnClickListener( ) {
    //       @Override
      //      public void onClick(View view) {
        //        ed.setVisibility(View.VISIBLE);
      //      }
       // });
    }


    /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater me = getMenuInflater();
        me.inflate(R.menu.menu_delete,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int i= item.getItemId();
        if (i == R.id.Delete) {
            myRef.child(model.getRecipe_name()).removeValue();

            Intent in=new Intent(getApplicationContext(),Recent.class);
            startActivity(in);
        }
        return super.onOptionsItemSelected(item);
    }*/
}
