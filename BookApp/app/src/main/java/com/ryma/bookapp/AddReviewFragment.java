package com.ryma.bookapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import data.Review;

public class AddReviewFragment extends Fragment {

    private RatingBar score;
    private EditText reviewText;
    private EditText name;
    private Review reviewToAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_review_fragment, container, false);

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


                final String URL = "http://81.240.220.38:8090/review/addToBook/12";
                new AddReviewTask().execute(URL);
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        score = view.findViewById(R.id.ratingBar);
        reviewText = view.findViewById(R.id.editText_review_text);
        name = view.findViewById(R.id.editText_review_name);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.activity_menu_drawer, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(getView().getContext(), MainActivity.class));
                return true;
            case R.id.nav_addBook:
                startActivity(new Intent(getView().getContext(), AddBookActivity.class));
                return true;
            case R.id.nav_AddReview:
                startActivity(new Intent(getView().getContext(), AddReviewActivity.class));
                return true;
            case R.id.nav_MyReviews:
                startActivity(new Intent(getView().getContext(), MyBooksActivity.class));
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    class AddReviewTask extends AsyncTask<String, Void, ResponseEntity<Review>> {

        //Implements method
        @Override
        protected ResponseEntity<Review> doInBackground(String... URL) {
            final String url = URL[0];
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();

                HttpEntity<Review> entity = new HttpEntity<>(reviewToAdd, headers);
                ResponseEntity<Review> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Review.class);
                return responseEntity;
            }
            catch (Exception ex) {
                return null;
            }
        }

        protected void onPostExecute(ResponseEntity<Review> result) {
            HttpStatus status = result.getStatusCode();
            Toast.makeText(getView().getContext(), "Review Added", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getView().getContext(), MainActivity.class));
        }

    }


}
