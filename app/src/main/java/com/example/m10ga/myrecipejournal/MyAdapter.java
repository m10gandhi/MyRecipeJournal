package com.example.m10ga.myrecipejournal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.m10ga.myrecipejournal.Model.ListItem;

import java.util.List;

/**
 * Created by m10ga on 2018-04-08.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    private List<ListItem> listItems;

    private Context ctx;

    public MyAdapter(List<ListItem> listItem, Context ctx)
    {
        this.listItems = listItem;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout,parent,false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tv_recipe;
        TextView tv_people;
        TextView tv_rating;




        public ViewHolder(final View itemView)
        {
            super(itemView);

            tv_recipe=(TextView)itemView.findViewById(R.id.tv_recipe);
            tv_people=(TextView)itemView.findViewById(R.id.tv_people);
            tv_rating=(TextView)itemView.findViewById(R.id.tv_rating);

        }


        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        final ListItem listItem=listItems.get(position);

        holder.tv_recipe.setText(listItem.getRecipe_name());
        holder.tv_people.setText(listItem.getPeople());
        holder.tv_rating.setText(listItem.getRating());


    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
//[1][2][5]