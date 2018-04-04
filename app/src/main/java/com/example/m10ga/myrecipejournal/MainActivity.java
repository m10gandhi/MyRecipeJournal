package com.example.m10ga.myrecipejournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.support.design.widget.FloatingActionButton;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;

import static android.bluetooth.BluetoothDevice.EXTRA_NAME;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity implements OnClickListener {
    TabLayout tablayout;
    ViewPager viewPager;
    private Boolean isFabOpen = false;
    private FloatingActionButton Fab,Fab1,Fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        tablayout= findViewById(R.id.mytab);
        viewPager= findViewById(R.id.view2);
        viewPager.setAdapter(new Myownpager(getSupportFragmentManager()));
        tablayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        final String cheeseName = intent.getStringExtra(EXTRA_NAME);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cheeseName);

        loadBackdrop();

        Fab = findViewById(R.id.Fab);
        Fab1 = findViewById(R.id.Fab1);
        Fab2 = findViewById(R.id.Fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        Fab.setOnClickListener(this);
        Fab1.setOnClickListener(this);
        Fab2.setOnClickListener(this);


        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.Fab:

                animateFAB();
                break;
            case R.id.Fab1:

                Log.d("User", "Fab 1");
                break;
            case R.id.Fab2:

                Log.d("User", "Fab 2");
                break;
        }
    }

    class Myownpager extends FragmentPagerAdapter {
        String data[]={"Recents","URL","MyReceipes"};


        public Myownpager(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position==0){
                return new Recent();

            }
            if(position==1){
                return new Popular();

            }
            if (position==2){
                return  new User();
            }
            return null;
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            return super.getPageTitle(position);
            return data[position];
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadBackdrop() {
        final ImageView imageView = findViewById(R.id.backdrop);
        Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);
    }



    public void animateFAB(){

        if(isFabOpen){

            Fab.startAnimation(rotate_backward);
            Fab1.startAnimation(fab_close);
            Fab2.startAnimation(fab_close);
            Fab1.setClickable(false);
            Fab2.setClickable(false);
            isFabOpen = false;
            Log.d("User", "close");

        } else {

            Fab.startAnimation(rotate_forward);
            Fab1.startAnimation(fab_open);
            Fab2.startAnimation(fab_open);
            Fab1.setClickable(true);
            Fab2.setClickable(true);
            isFabOpen = true;
            Log.d("User","open");

        }
    }
}