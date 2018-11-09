package com.ryma.bookapp.AddReview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.ryma.bookapp.R;


import domainModels.Review;

public class AddReviewFragment extends Fragment {

    private RatingBar score;
    private EditText reviewText;
    private EditText name;
    private Review reviewToAdd;


    private OnButtonClicked mCallBack;
    public interface OnButtonClicked {
        public void onButtonClicked(Review review);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_review_fragment, container, false);

        Button button = view.findViewById(R.id.add_review_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (score.getRating() == 0 || reviewText.getText() == null || name.getText() == null) {
                    Toast.makeText(getView().getContext(), "Please fill in all fields!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                reviewToAdd = new Review(
                        score.getRating(),reviewText.getText().toString(),name.getText().toString()
                );
                mCallBack.onButtonClicked(reviewToAdd);
            }
        });
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        score = view.findViewById(R.id.ratingBar);
        reviewText = view.findViewById(R.id.editText_review_text);
        name = view.findViewById(R.id.editText_review_name);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBack = (OnButtonClicked) context;
    }



}
