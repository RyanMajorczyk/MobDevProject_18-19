package com.ryma.bookapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import domainModels.Review;

public class RecyclerViewReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder>{
    private Review[] reviews;
    private Context context;

    RecyclerViewReviewAdapter(Context context, Review[] reviews) {
        this.reviews = reviews;
        this.context = context;
    }


    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_reviews_list_item, viewGroup, false);
        return new ReviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder reviewViewHolder, int position) {
        Review review = reviews[position];

        reviewViewHolder.mReviewNameTextView.setText(review.getName());
        reviewViewHolder.mReviewTextTextView.setText(review.getReviewText());
        reviewViewHolder.mRatingBar.setRating((float) review.getScore());
    }

    @Override
    public int getItemCount() {
        return reviews.length;
    }
}

class ReviewViewHolder extends RecyclerView.ViewHolder {

    TextView mReviewNameTextView;
    TextView mReviewTextTextView;
    RatingBar mRatingBar;

    ReviewViewHolder(View itemView) {
        super(itemView);
        mReviewNameTextView = itemView.findViewById(R.id.textView_review_name);
        mReviewTextTextView = itemView.findViewById(R.id.textview_review_content);
        mRatingBar = itemView.findViewById(R.id.review_ratingbar);
    }
}