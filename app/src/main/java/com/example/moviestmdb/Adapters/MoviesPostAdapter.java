package com.example.moviestmdb.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.moviestmdb.Activities.MoviesDetailsActivity;
import com.example.moviestmdb.Models.PostModel;
import com.example.moviestmdb.R;

import java.util.ArrayList;

public class MoviesPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private ArrayList<PostModel> arrayList;

    public MoviesPostAdapter(Activity activity, ArrayList<PostModel> arrayList){
        this.activity = activity;
        this.arrayList = arrayList;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout lnItem;
        private ImageView imgItem;
        private TextView txtTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lnItem = itemView.findViewById(R.id.item_post);
            imgItem = itemView.findViewById(R.id.img_item);
            txtTitle = itemView.findViewById(R.id.txt_title);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(activity, R.layout.item_post, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PostModel postModel = arrayList.get(position);
        ((ViewHolder) holder).txtTitle.setText(postModel.getPostTitle());
        Glide.with(activity)
                .load("https://image.tmdb.org/t/p/w342" + postModel.getPosterPath())
                .thumbnail(0.25f)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_muvi_name))
                .error(activity.getResources().getDrawable(R.drawable.ic_muvi_name))
                .into(((ViewHolder) holder).imgItem);
        ((ViewHolder) holder).lnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MoviesDetailsActivity.class);
                intent.putExtra("postId", postModel.getPostId());
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
