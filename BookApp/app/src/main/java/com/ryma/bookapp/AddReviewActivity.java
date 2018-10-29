package com.ryma.bookapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import data.Book;
import data.Review;

public class AddReviewActivity extends AppCompatActivity {

    private Spinner spinner;

    private RatingBar score;
    private EditText reviewText;
    private EditText name;
    private Review reviewToAdd;
    private List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        spinner = findViewById(R.id.spinner_books);

        score = findViewById(R.id.ratingBar);
        reviewText = findViewById(R.id.editText_review_text);
        name = findViewById(R.id.editText_review_name);

        getAllBooks();
    }

    private void getAllBooks() {
        final String URL = "http://81.240.220.38:8090/book";
        new GetAllBooksTask().execute(URL);
    }


    public void onAddReviewButtonClicked(View view) {
        if (score.getRating() == 0 || reviewText.getText() == null || name.getText() == null) {
            Toast.makeText(this, "Please fill in all fields!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        reviewToAdd = new Review(
                score.getRating(),reviewText.getText().toString(),name.getText().toString()
        );


        Book bookToAddReviewTo = (Book) spinner.getSelectedItem();

        final String URL = "http://81.240.220.38:8090/review/addToBook/" + bookToAddReviewTo.getId();
        new AddReviewTask().execute(URL);
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
            Toast.makeText(AddReviewActivity.this, "Review Added", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddReviewActivity.this, MenuActivity.class));
        }

    }

    class GetAllBooksTask extends AsyncTask<String, Void, ResponseEntity<Book[]>>{
        @Override
        protected ResponseEntity<Book[]> doInBackground(String... URL) {
            final String url = URL[0];
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();

                HttpEntity<Book> entity = new HttpEntity<>(headers);
                ResponseEntity<Book[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Book[].class);
                return responseEntity;
            }
            catch (Exception ex) {
                return null;
            }
        }

        protected void onPostExecute(ResponseEntity<Book[]> result) {
            books = new ArrayList<>(Arrays.asList(result.getBody()));
            HttpStatus status = result.getStatusCode();

            ArrayAdapter<Book> adapter =
                    new ArrayAdapter<Book>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, books);
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);
        }

    }
}
