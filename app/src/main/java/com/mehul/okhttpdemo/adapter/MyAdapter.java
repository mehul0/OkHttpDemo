package com.mehul.okhttpdemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mehul.okhttpdemo.R;
import com.mehul.okhttpdemo.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Micky on 11/12/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecyclerViewHolder> {

    List<Movie> movieList = new ArrayList<>();
    MyAdapterEvent myAdapterEvent;
    Context context;

    public interface MyAdapterEvent {
        void onItemClicked(int position);
    }


    public MyAdapter(){}

    public MyAdapter(Context context, MyAdapterEvent myAdapterEvent, List<Movie> movieList) {
        this.context = context;
        this.myAdapterEvent = myAdapterEvent;
        this.movieList = movieList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder,final int position) {
        //holder.imgView.setImageURI(Uri.parse(movieList.get(position).getImage()));
        String type = "",movie = "<b>" + "Movie   &nbsp&nbsp: &nbsp&nbsp" + "</b> " + movieList.get(position).getTitle()
                ,types = "<b>" + "Type &nbsp&nbsp&nbsp  : &nbsp&nbsp&nbsp" + "</b>"
                ,rating = "<b>" + "Rating &nbsp: &nbsp&nbsp" + "</b> " + movieList.get(position).getRating()
                ,year = "<b>" + "Year &nbsp&nbsp&nbsp&nbsp&nbsp: &nbsp&nbsp" + "</b> " + movieList.get(position).getReleaseYear();

        holder.tvName.setText(Html.fromHtml(movie));
        holder.tvRating.setText(Html.fromHtml(rating));
        holder.tvYear.setText(Html.fromHtml(year));
        //holder.tvName.setText("Movie : " + movieList.get(position).getTitle());
        //holder.tvRating.setText(String.valueOf("Rating : " + movieList.get(position).getRating()));
        //holder.tvYear.setText(String.valueOf("Year     : " + movieList.get(position).getReleaseYear()));
        //holder.tvType.setText(String.valueOf("Type    : " + movieList.get(position).getGenre().get(0)));
        Picasso.with(context).load(movieList.get(position).getImage()).into(holder.imgView);

        for (int i = 0; i < movieList.get(position).getGenre().size(); i++) {
            type = i == 0 ? (type = movieList.get(position).getGenre().get(i)) : (type = type + " , " + movieList.get(position).getGenre().get(i));
        }
        holder.tvType.setText(Html.fromHtml(types + type));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgView;
        TextView tvName,tvRating,tvType,tvYear;
        public RecyclerViewHolder(View itemView) {
            super(itemView);

            imgView = itemView.findViewById(R.id.img_recycler_view);
            tvName = itemView.findViewById(R.id.tv_name_recycler_view);
            tvRating = itemView.findViewById(R.id.tv_rating_recycler_view);
            tvType = itemView.findViewById(R.id.tv_type_recycler_view);
            tvYear = itemView.findViewById(R.id.tv_year_recycler_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (view.getId() == itemView.getId()){
                if (myAdapterEvent != null){
                    myAdapterEvent.onItemClicked(getAdapterPosition());
                }
            }

        }
    }
}
