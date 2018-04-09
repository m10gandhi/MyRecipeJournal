package com.example.m10ga.myrecipejournal;

/**
 * Created by m10ga on 2018-04-04.
 */

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
                FirebaseRecyclerAdapter<ListItem, BlogViewHolder>(ListItem.class,R.layout.row_layout,BlogViewHolder.class,myRef) {

                    @Override
                    protected void populateViewHolder(BlogViewHolder viewHolder, ListItem model, int position) {
                        Log.e("data",""+myRef+""+model.getRecipename());
                        viewHolder.setRecipe_name(model.getRecipename());
                        viewHolder.setPeople(model.getPeople_served());
                    }


                };



        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        public BlogViewHolder(View itemView)
        {
            super(itemView);
            mView=itemView;
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
