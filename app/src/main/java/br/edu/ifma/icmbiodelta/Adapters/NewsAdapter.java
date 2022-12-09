package br.edu.ifma.icmbiodelta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.edu.ifma.icmbiodelta.Helpers.ImageTool;
import br.edu.ifma.icmbiodelta.Models.NewsData;
import br.edu.ifma.icmbiodelta.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private Context context;
    private List<NewsData> newsList;

    public NewsAdapter(List<NewsData> newslist) {
        this.newsList = newslist;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent
            , int viewType) {
        View newsItemList = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cardview
                        , parent
                        , false);
        return new NewsViewHolder(newsItemList);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder
            , int position) {
        ImageTool imageTool = new ImageTool();
        NewsData news = newsList.get(position);
        //holder.image.setImageResource(imageTool.getURIFromBytes news.getImage());
        //Glide.with(context).load(newsList.get(position)
        //        .getImage()).into(holder.image);
        holder.title
                .setText(news.getTitle());
        holder.description
                .setText(news.getDescription());
        holder.date
                .setText(news.getDate());

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        //ImageView image;
        TextView title;
        TextView description;
        TextView date;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            //image = itemView
            //        .findViewById(R.id.newsImage);
            title = itemView
                    .findViewById(R.id.newsTitle);
            description = itemView
                    .findViewById(R.id.newsDescription);
            date = itemView
                    .findViewById(R.id.newsDate);
        }
    }
}
