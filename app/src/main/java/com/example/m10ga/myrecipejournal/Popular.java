package com.example.m10ga.myrecipejournal;

/**
 * Created by m10ga on 2018-04-04.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.m10ga.myrecipejournal.Model.ListItem;

import java.util.List;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.m10ga.myrecipejournal.Model.ListItem;
import com.google.firebase.database.ValueEventListener;


public class Popular extends Fragment
{
    private RecyclerView recyclerView;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference().child("url");

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Popular() {
        // Required empty public constructor
    }


    public static Popular newInstance(String param1, String param2) {
        Popular fragment = new Popular();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_popular, container, false);
        recyclerView=(RecyclerView)v.findViewById(R.id.recyclerview);
        myRef.keepSynced(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return  v;
    }

    @Override
    public void onStart() {
        super.onStart();

     final FirebaseRecyclerAdapter<ListItem,BlogViewHolder> firebaseRecyclerAdapter=new
             FirebaseRecyclerAdapter<ListItem, Popular.BlogViewHolder>(ListItem.class,R.layout.row_layout_url,BlogViewHolder.class,myRef)
             {
                 @Override
                 protected void populateViewHolder(final BlogViewHolder viewHolder, final ListItem model, int position) {
                     Log.e("url",""+myRef+""+model.getUrl()+"urlname"+model.getUrl_recipe());
                     viewHolder.setUrl(model.getUrl());
                    viewHolder.setUrl_recipe(model.getUrl_recipe());

                     viewHolder.setOnClickListener(new BlogViewHolder.ClickListener() {
                         @Override
                         public void onItemClick(View view, int position) {
                             String value = "Hello User";
                             Intent il=new Intent(getActivity(),DetailUrlActivity.class);
                             il.putExtra("url",model.getUrl());
                             il.putExtra("urlname",model.getUrl_recipe());
                             startActivity(il);
                         }

                         @Override
                         public void onItemLongClick(View view, int position) {
                             myRef.child(model.getUrl_recipe()).removeValue();
                         }

                     });


                 }
             };
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }


    public static class BlogViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        Context mContext;
        DatabaseReference myRef;
        ImageButton mImageButton;
        private BlogViewHolder.ClickListener mClickListener;


        //Interface to send callbacks...
        public interface ClickListener{
            public void onItemClick(View view, int position);
            public void onItemLongClick(View view, int position);
        }
        public void setOnClickListener(BlogViewHolder.ClickListener clickListener){
            mClickListener = clickListener;
        }

        public BlogViewHolder(View itemView)
        {
            super(itemView);
            mView=itemView;
            myRef=FirebaseDatabase.getInstance().getReference().child("url");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(v, getAdapterPosition());

                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mClickListener.onItemLongClick(v, getAdapterPosition());
                    return true;
                }
            });
        }

        public void setUrl(String url)
        {
            TextView tv_url=(TextView)mView.findViewById(R.id.tv_url);
            tv_url.setText(url);
        }
        public void setUrl_recipe(String url_name)
        {
            TextView tv_url_recipe=(TextView)mView.findViewById(R.id.tv_url_recipe);
            tv_url_recipe.setText(url_name);
        }



    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
