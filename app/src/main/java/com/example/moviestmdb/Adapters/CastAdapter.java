package com.example.moviestmdb.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviestmdb.Models.CastModel;
import com.example.moviestmdb.Models.PostModel;
import com.example.moviestmdb.R;
import com.orhanobut.logger.Logger;

import org.w3c.dom.Text;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class CastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private ArrayList<CastModel> arrayList;

    public CastAdapter(Activity activity, ArrayList<CastModel> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout lnItem;
        private ImageView imgCast;
        private TextView txtName, txtCharacter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lnItem = itemView.findViewById(R.id.item_post);
            imgCast = itemView.findViewById(R.id.img_item);
            txtName = itemView.findViewById(R.id.txt_name);
            txtCharacter = itemView.findViewById(R.id.txt_character);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(activity, R.layout.item_cast, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CastModel castModel = arrayList.get(position);
        Logger.i(castModel.getName());
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.txtName.setText(castModel.getName());
        viewHolder.txtCharacter.setText(castModel.getCharacterName());
        Glide.with(activity)
                .load("https://image.tmdb.org/t/p/w342" + castModel.getProfilePath())
                .thumbnail(0.25f)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .apply(RequestOptions.bitmapTransform(new CropCircleWithBorderTransformation(4, activity.getResources().getColor(R.color.colorAccent))))
                .error(activity.getResources().getDrawable(R.drawable.ic_muvi_name))
                .into(viewHolder.imgCast);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
