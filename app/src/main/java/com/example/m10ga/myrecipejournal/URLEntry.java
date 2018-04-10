package com.example.m10ga.myrecipejournal;

/**
 * Created by m10ga on 2018-04-04.
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedHashMap;
import java.util.Map;

public class URLEntry extends AppCompatActivity {
    EditText edt_url,edt_urlrecipe;
    Button btn_url;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("url");
    String url,url_recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlentry);

        edt_url=(EditText)findViewById(R.id.edt_url);
        edt_urlrecipe=(EditText)findViewById(R.id.edt_urlrecipe);
        btn_url=(Button)findViewById(R.id.btn_url);

        btn_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();

            }
        });
    }

    private void onSubmit()
    {
        url=edt_url.getText().toString();
        url_recipe=edt_urlrecipe.getText().toString();

        myRef.child(url_recipe).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    Map<String, String> map = new LinkedHashMap<>();
                    map.put("url_recipe:",url_recipe);
                    map.put("url", url);

//                    map.put("url",url);

                    myRef.child(url_recipe).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
//                                    p.dismiss();
                            Toast.makeText(getApplicationContext(), "Successfully saved URL", Toast.LENGTH_LONG).show();
//                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                            startActivity(i);
//                            p.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Toast.makeText(getApplicationContext(), "Invalid URL, Try Again!!!", Toast.LENGTH_LONG).show();
//
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
