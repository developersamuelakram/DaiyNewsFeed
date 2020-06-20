package com.example.newsfeed.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.newsfeed.R;
import com.example.newsfeed.Splash.Utils;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> implements Filterable {

    Context context;
    List<Model> newsList;
    List<Model> newsListfiltered;


    public Adapter(Context context, List<Model> newsList) {
        this.context = context;
        this.newsList = newsList;
        this.newsListfiltered = newsList;
    }



    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int position) {

            View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        String name  = newsListfiltered.get(position).getTitle();
        String source = newsListfiltered.get(position).getSource();
        String time = newsList.get(position).getTime();
        String image = newsList.get(position).getUrlToImage();
        String date = newsList.get(position).getTime();
        String description = newsListfiltered.get(position).getDescription();

        RequestOptions requestOptions = new RequestOptions();


        holder.title.setText(name);
        holder.source.setText(source);
        holder.description.setText(description);
        holder.time.setText("\u2022 " +Utils.DateToTimeFormat(time));
        holder.published_At.setText(Utils.DateFormat(date));



        Glide.with(context).load(image).apply(requestOptions).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;            }
        }).transition(DrawableTransitionOptions.withCrossFade()).into(holder.imageView);




    }



    @Override
    public int getItemCount() {
        return newsListfiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charcater = constraint.toString();
                if (charcater.isEmpty()){
                    newsListfiltered = newsList ;
                }else {
                    List<Model> filterList = new ArrayList<>();
                    for (Model row: newsList){
                        if (row.getTitle().toLowerCase().contains(charcater.toLowerCase())){
                            filterList.add(row);
                        }
                    }

                    newsListfiltered = filterList ;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = newsListfiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                newsListfiltered = (ArrayList<Model>) results.values ;
                notifyDataSetChanged();
            }
        };
    }



    class MyViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        TextView title, description, source, time, published_At;
        ImageView imageView;
        ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);


            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.desc);
            source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.img);
            published_At = itemView.findViewById(R.id.publishedAt);
            progressBar = itemView.findViewById(R.id.prograss_load_photo);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Model model = newsList.get(position);

            Uri uri = Uri.parse(model.getUrl());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }


}
