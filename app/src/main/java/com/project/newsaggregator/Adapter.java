package com.project.newsaggregator;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;
import com.squareup.picasso.Picasso;

public class Adapter extends
        RecyclerView.Adapter<ViewHolder> {

    private final MainActivity mainActivity;
    private final ArrayList<Article> countryList;

    public Adapter(MainActivity mainActivity, ArrayList<Article> countryList) {
        this.mainActivity = mainActivity;
        this.countryList = countryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.viewpager, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Article c = countryList.get(position);
        holder.articleText.setText(c.getArticleDescription());
        holder.author.setText(c.getArticleAuthor());
        holder.date.setText(c.getArticlePublishedat());
        holder.headline.setText(c.getArticleTitle());
        if (c.getArticleUrltoimage() != null)
            Picasso.get().load(c.getArticleUrltoimage()).error(R.drawable.brokenimage)
                    .placeholder(R.drawable.loading).into(holder.image);
        else
            Picasso.get().load(c.getArticleUrltoimage()).error(R.drawable.noimage)
                    .placeholder(R.drawable.loading).into(holder.image);
        holder.image.setOnClickListener(v -> clickFlag(c.getArticleUrl()));
        holder.articleText.setOnClickListener(v -> clickFlag(c.getArticleUrl()));
        holder.headline.setOnClickListener(v -> clickFlag(c.getArticleUrl()));
        if (countryList.size() >= 10) {
            if(position<10)
                holder.pagenum.setText(String.format(
                        Locale.getDefault(), "%d of %d", (position + 1), 10));
            else{

            }
        }
        else
            holder.pagenum.setText(String.format(
                    Locale.getDefault(), "%d of %d", (position + 1), countryList.size()));
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }
    private void clickFlag(String name) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(name));
        mainActivity.startActivity(intent);
    }
}
