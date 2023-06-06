package com.project.newsaggregator;
import android.nfc.Tag;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.newsaggregator.databinding.ViewpagerBinding;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView headline;
    TextView date;
    TextView author;
    TextView articleText;
    TextView pagenum;
    ImageView image;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        headline = itemView.findViewById(R.id.headline);
        date = itemView.findViewById(R.id.date);
        author = itemView.findViewById(R.id.author);
        articleText = itemView.findViewById(R.id.articleText);
        image = itemView.findViewById(R.id.image);
        pagenum = itemView.findViewById(R.id.pagenum);
    }
}