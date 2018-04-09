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

import com.example.m10ga.myrecipejournal.Model.ListItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Recent extends Fragment {
    private RecyclerView recyclerView;
    //    private RecyclerView.Adapter adapter;
//    private List<ListItem>listItems;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference().child("recipe");

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Recent() {
        // Required empty public constructor
    }


    public static Recent newInstance(String param1, String param2) {
        Recent fragment = new Recent();
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
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_recent, container, false);
        recyclerView=(RecyclerView)v.findViewById(R.id.recyclerview);
        myRef.keepSynced(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        listItems=new ArrayList<>();
//
//        for(int i=0;i<=10;i++)
//        {
//            ListItem listItem=new ListItem("pizza ","4");
//            listItems.add(listItem);
//        }
//
//        adapter=new MyAdapter(listItems,getActivity());
//        recyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        FirebaseRecyclerAdapter<ListItem,BlogViewHolder> firebaseRecyclerAdapter=new
                FirebaseRecyclerAdapter<ListItem, BlogViewHolder>(ListItem.class,R.layout.row_layout,BlogViewHolder.class,myRef)
                {

                    @Override
                    protected void populateViewHolder(BlogViewHolder viewHolder, final ListItem model, int position)
                    {
                        Log.e("data",""+myRef+""+model.getRecipe_name()+"cooking time"+model.getCooking_time()+""+" prepareation_time"+model.getPreparation_time());
                        viewHolder.setRecipe_name(model.getRecipe_name());
                        viewHolder.setPeople(model.getPeople());

                        viewHolder.setOnClickListener(new BlogViewHolder.ClickListener()
                        {
                            @Override
                            public void onItemClick(View view, int position)
                            {
                                String value="Hello world";
                                Intent i = new Intent(getActivity(),DetailsActivity.class);
                                i.putExtra("key",model.getCooking_time());
                                i.putExtra("recipename",model.getRecipe_name());
                                i.putExtra("person",model.getPeople());
                                i.putExtra("ingredients",model.getIngredients());
                                i.putExtra("preparation",model.getPreparation_steps());
                                i.putExtra("preparationtime",model.getPreparation_time());
                                startActivity(i);

                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                myRef.child(model.getRecipe_name()).removeValue();

                            }
                        });



                    }


                };



        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
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

        public void setRecipe_name(String recipe_name)
        {
            TextView tv_recipe=(TextView)mView.findViewById(R.id.tv_recipe);
            tv_recipe.setText(recipe_name);
        }
        public void setPeople(String people)
        {
            TextView tv_people=(TextView)mView.findViewById(R.id.tv_people);
            tv_people.setText(people);
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
