package com.example.m10ga.myrecipejournal;

/**
 * Created by m10ga on 2018-04-04.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.LinkedHashMap;
import java.util.Map;

public class URLEntry extends AppCompatActivity {
    EditText edt_url;
    Button btn_url;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("url");
    SeekBar seekbar_url, seekbar1_url;
    TextView tv_seek_url, tv_rating_url;
    EditText edt_recipe_url;
    private int hr;
    private int min;
    static final int TIME_DIALOG_ID = 1111;
    ProgressDialog p;
    String category, url_recipe, people_url,rating_url, url;
    private Uri filePath;
    private static final int PICK_IMAGE_REQUEST = 1234;
    StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlentry);

        edt_url=(EditText)findViewById(R.id.edt_url);
        edt_recipe_url=(EditText)findViewById(R.id.edt_recipe_url);
        btn_url=(Button)findViewById(R.id.btn_url);
        seekbar_url = (SeekBar) findViewById(R.id.seekbar_url);
        tv_seek_url = (TextView) findViewById(R.id.tv_seek_url);
        seekbar1_url = (SeekBar) findViewById(R.id.seekbar1_url);
        tv_rating_url = (TextView) findViewById(R.id.tv_rating_url);

        btn_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();

            }
        });

        seekbar_url.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_seek_url.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbar1_url.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_rating_url.setText(String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void onSubmit()
    {
        url=edt_url.getText().toString();
        url_recipe=edt_recipe_url.getText().toString();
        people_url = tv_seek_url.getText().toString();
        rating_url=tv_rating_url.getText().toString();
        Log.e("name", "onSubmit: "+url_recipe+"people" +people_url);

        myRef.child(url_recipe).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    Map<String, String> map = new LinkedHashMap<>();
                    map.put("url_recipe",url_recipe);
                    map.put("url", url);
                    map.put("people_url", people_url);
                    map.put("rating_url",rating_url);
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
        Intent in=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(in);
    }

}
//[1][2][5][10]