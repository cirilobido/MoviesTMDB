package com.example.moviestmdb.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.moviestmdb.R;
import com.example.moviestmdb.Models.PostModel;
import com.opensooq.pluto.base.PlutoAdapter;
import com.opensooq.pluto.base.PlutoViewHolder;

import java.util.List;

import io.reactivex.rxjava3.annotations.Nullable;

public class SliderAdapter extends PlutoAdapter<PostModel, SliderAdapter.ViewHolder> {
    private Activity activity;
    private List<PostModel> postModelList;

    @Nullable
    public SliderAdapter(Activity activity, List<PostModel> postModels){
        super(postModels);
        this.activity = activity;
        this.postModelList = postModels;
    }

    @Override
    public SliderAdapter.ViewHolder getViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(viewGroup, R.layout.item_slider);
    }
    public class ViewHolder extends PlutoViewHolder<PostModel>{
        LinearLayout item_post;
        ImageView imgPoster;
        TextView txtTitle, txtYear, txtGenres, txtAverage;

        public ViewHolder(ViewGroup parent, int itemLayoutId){
            super(parent, itemLayoutId);
            imgPoster = getView(R.id.img_poster);
            txtTitle = getView(R.id.txt_title);
            txtYear = getView(R.id.txt_year);
            txtGenres = getView(R.id.txt_genres);
            txtAverage = getView(R.id.txt_average);
            item_post = getView(R.id.item_post);
        }

        @Override
        public void set(PostModel postModel, int i) {
            Glide.with(activity)
                    .load("https://image.tmdb.org/t/p/w342" + postModel.getPosterPath())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(activity.getResources().getDrawable(R.drawable.ic_muvi_name))
                    .error(activity.getResources().getDrawable(R.drawable.ic_muvi_name))
                    .into(imgPoster);
            txtTitle.setText(postModel.getPostTitle());
            txtAverage.setText(String.valueOf(postModel.getVoteAverage()));
            String[] date = postModel.getReleaseDate().split("-");
            txtYear.setText(date[0]);
            //TODO: RXJava for the genders from the Array
            for (int genderId : postModel.getGenreIdsList()){
                Log.d("Genre", String.valueOf(genderId));
            }
            txtGenres.setText("None");
        }
    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }
}
