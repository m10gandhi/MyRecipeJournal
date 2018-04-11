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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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
    TextView tv_seek, tv_rating, tv_prepTime, tv_cookTime;
    EditText edt_recipe, edt_prepTime, edt_cookTime,edt_preparation,edt_ingredients;
    Button  btn_submit,btn_photo;
    CheckBox chk_appetizer, chk_mex, chk_main, chk_side, chk_veg, chk_dessert;
    private int hr;
    private int min;
    static final int TIME_DIALOG_ID = 1111;
    ProgressDialog p;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("recipe");
  //  myRef.keepSynced(true);

    String category, recipe_name, people, preparation, cooking,ingredients,preparation_steps,rating;
    ImageView img;
    private Uri filePath;
    private static final int PICK_IMAGE_REQUEST = 1234;
    StorageReference mStorageRef;


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
        edt_ingredients=(EditText)findViewById(R.id.edt_ingredients);
        edt_preparation=(EditText)findViewById(R.id.edt_preparation);
        tv_prepTime = (TextView) findViewById(R.id.tv_prepTime);
        tv_cookTime = (TextView) findViewById(R.id.tv_cookTime);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        tv_seek = (TextView) findViewById(R.id.tv_seek);
        seekbar1 = (SeekBar) findViewById(R.id.seekbar1);
        tv_rating = (TextView) findViewById(R.id.tv_rating);
//        btn_photo = (Button) findViewById(R.id.btn_photo);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_photo=(Button)findViewById(R.id.btn_photo);
//        chk_appetizer = (CheckBox) findViewById(R.id.chk_appetizer);
//        chk_mex = (CheckBox) findViewById(R.id.chk_mex);
//        chk_main = (CheckBox) findViewById(R.id.chk_main);
//        chk_side = (CheckBox) findViewById(R.id.chk_side);
//        chk_veg = (CheckBox) findViewById(R.id.chk_veg);
//        chk_dessert = (CheckBox) findViewById(R.id.chk_dessert);
        img = (ImageView) findViewById(R.id.img);


        final Calendar c = Calendar.getInstance();
        hr = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
                onSubmit();
                Log.e("name", "onClick: uploadcall" );
            }
        });

        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

//        btn_photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                uploadFile();
//            }
//        });

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
                tv_rating.setText(String.valueOf(progress));

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
                        edt_prepTime.setText(selectedHour + ":" + selectedMinute);
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
                        edt_cookTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

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




    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
//            final ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setTitle("Uploading");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//            Random r = new Random();


            StorageReference riversRef = mStorageRef.child("images").child(recipe_name + "" + "pic.jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            // progressDialog.dismiss();

//                           Toast.makeText(getApplicationContext(),"Done Uploading",Toast.LENGTH_LONG).show();
                            //and displaying a success toast
                            Uri url = taskSnapshot.getMetadata().getDownloadUrl();
                            onSubmit();

                            Log.e("name", "onClick: storage call" );


//                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
//                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
//                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }

    }
    private void onSubmit() {
        recipe_name = edt_recipe.getText().toString();
        people = tv_seek.getText().toString();
        preparation = edt_prepTime.getText().toString();
        cooking = edt_cookTime.getText().toString();
        ingredients=edt_ingredients.getText().toString();
        preparation_steps=edt_preparation.getText().toString();
        rating=tv_rating.getText().toString();
        Log.e("name", "onSubmit: "+recipe_name+"people" +people);


        myRef.child(recipe_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    Map<String, String> map = new LinkedHashMap<>();
                    map.put("recipe_name", recipe_name);
                    map.put("people", people);
                    map.put("preparation_time", preparation);
                    map.put("cooking_time", cooking);
                    map.put("ingredients",ingredients);
                    map.put("preparation_steps",preparation_steps);
                    map.put("rating",rating);

//                    map.put("url",url);

                    myRef.child(recipe_name).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
//                                    p.dismiss();
                            Toast.makeText(getApplicationContext(), "Successfully stored recipe", Toast.LENGTH_LONG).show();

// Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                            startActivity(i);


//                            p.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            p.dismiss();
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