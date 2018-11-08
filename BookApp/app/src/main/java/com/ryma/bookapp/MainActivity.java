package com.ryma.bookapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.ryma.bookapp.AddBook.AddBookActivity;
import com.ryma.bookapp.AddReview.AddReviewActivity;
import com.ryma.bookapp.MyBooks.MyBooksActivity;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import domainModels.Book;

public class MainActivity extends AppCompatActivity {
    Book[] books;
    private RecyclerViewFragment recyclerViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewFragment = new RecyclerViewFragment();
        final String URL = "http://81.240.220.38:8090/book";
        new GetAllBooksTask().execute(URL);



        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.master_frame_layout, recyclerViewFragment, "");
        transaction.commit();


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
            case R.id.nav_AddReview:
                startActivity(new Intent(this, AddReviewActivity.class));
                return true;
            case R.id.nav_MyBooks:
                startActivity(new Intent(this, MyBooksActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class GetAllBooksTask extends AsyncTask<String, Void, ResponseEntity<Book[]>> {
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
            } catch (Exception ex) {
                return null;
            }
        }

        protected void onPostExecute(ResponseEntity<Book[]> result) {
            books = result.getBody();

            try {
                recyclerViewFragment.setmAdapter(books);
            } catch (Exception e) {
                Log.e("Error: ", e.getStackTrace().toString());
            }
        }
    }
}


