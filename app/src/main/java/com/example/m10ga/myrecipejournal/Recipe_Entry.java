package com.example.m10ga.myrecipejournal;

/**
 * Created by m10ga on 2018-04-04.
 */

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

public class Recipe_Entry extends AppCompatActivity {
    SeekBar seekbar, seekbar1;
    TextView tv_seek, tv_diff, tv_prepTime, tv_cookTime;
    EditText edt_recipe, edt_prepTime, edt_cookTime;
    Button btn_photo,btn_submit;
    CheckBox chk_appetizer, chk_mex, chk_main, chk_side, chk_veg, chk_dessert;
    private int hr;
    private int min;
    static final int TIME_DIALOG_ID = 1111;
    ProgressDialog p;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("recipe");
    String category, recipe, people, preparation, cooking;
    ImageView img;

    StorageReference mStorageRef;
    private Uri filePath;
    private static final int PICK_IMAGE_REQUEST = 1234;
    public static final String STORAGE_PATH = "image/";
    public static final String DATABASE_PATH = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mStorageRef= FirebaseStorage.getInstance().getReference();

        edt_recipe = (EditText) findViewById(R.id.edt_recipe);
        edt_cookTime = (EditText) findViewById(R.id.edt_cookTime);
        edt_prepTime = (EditText) findViewById(R.id.edt_prepTime);
        tv_prepTime = (TextView) findViewById(R.id.tv_prepTime);
        tv_cookTime = (TextView) findViewById(R.id.tv_cookTime);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        tv_seek = (TextView) findViewById(R.id.tv_seek);
        seekbar1 = (SeekBar) findViewById(R.id.seekbar1);
        tv_diff = (TextView) findViewById(R.id.tv_diff);
        btn_photo=(Button)findViewById(R.id.btn_photo);
        btn_submit=(Button)findViewById(R.id.btn_submit);
        img=(ImageView)findViewById(R.id.img);
        chk_appetizer = (CheckBox) findViewById(R.id.chk_appetizer);
        chk_mex = (CheckBox) findViewById(R.id.chk_mex);
        chk_main = (CheckBox) findViewById(R.id.chk_main);
        chk_side = (CheckBox) findViewById(R.id.chk_side);
        chk_veg = (CheckBox) findViewById(R.id.chk_veg);
        chk_dessert = (CheckBox) findViewById(R.id.chk_dessert);

        final Calendar c = Calendar.getInstance();
        hr = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);

        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showFileChooser();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                uploadFile();
            }


        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_seek.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        edt_prepTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Recipe_Entry.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        edt_prepTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        edt_cookTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Recipe_Entry.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        edt_prepTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }

    private void uploadFile() {

        if (filePath != null) {
            StorageReference riverRef = mStorageRef.child("images").child(recipe + "" + "pic.jpg");
            riverRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener <UploadTask.TaskSnapshot>( ) {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri url = taskSnapshot.getMetadata().getDownloadUrl();
                    onSubmit();

                }
            }).addOnFailureListener(new OnFailureListener( ) {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            }).addOnProgressListener(new OnProgressListener <UploadTask.TaskSnapshot>( ) {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                }
            });
        }
        else {}
    }

    private void onSubmit()
    {
        recipe=edt_recipe.getText().toString();
        people=tv_seek.getText().toString();

        myRef.child(recipe).addValueEventListener(new ValueEventListener( ) {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue()==null)
                {
                    Map<String,String> map=new LinkedHashMap <>();
                    map.put("recipe_name",recipe);
                    map.put("people",people);


                    myRef.child(recipe).setValue(map).addOnCompleteListener(new OnCompleteListener <Void>( ) {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Successfully stored recipe", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener( ) {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "add recipe again", Toast.LENGTH_LONG).show();

                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                img.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}


