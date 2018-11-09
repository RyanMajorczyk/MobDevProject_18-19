package com.ryma.bookapp.AddReview;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.ryma.bookapp.AddBook.AddBookActivity;
import com.ryma.bookapp.MainActivity;
import com.ryma.bookapp.MyBooks.MyBooksActivity;
import com.ryma.bookapp.R;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import domainModels.Review;

public class AddReviewActivity extends AppCompatActivity implements AddReviewFragment.OnButtonClicked{
    private String bookId;
    private Review reviewToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        bookId = getIntent().getStringExtra("bookId");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.activity_menu_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.nav_addBook:
                startActivity(new Intent(this, AddBookActivity.class));
                return true;
            case R.id.nav_MyBooks:
                startActivity(new Intent(this, MyBooksActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onButtonClicked(Review review) {
        reviewToAdd = review;
        final String URL = "http://81.240.220.38:8090/review/addToBook/" + bookId;
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
            startActivity(new Intent(AddReviewActivity.this, MainActivity.class));
        }

    }

}
