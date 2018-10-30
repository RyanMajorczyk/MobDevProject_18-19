package com.ryma.bookapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;


import com.ryma.Controllers.BookController;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

import data.Book;

public class MainActivity extends AppCompatActivity {
    BookController controller = new BookController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RecyclerView mRecyclerView;
        RecyclerView.Adapter mAdapter = null;
        RecyclerView.LayoutManager mLayoutManager;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_books);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        try {
            mAdapter = new BookAdapter(this ,getAllBooks());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        mRecyclerView.setAdapter(mAdapter);
    }

    private ResponseEntity<Book[]> getAllBooks() throws ExecutionException, InterruptedException {
        final String URL = "http://81.240.220.38:8090/book";
        return new GetAllBooksTask().execute(URL).get();
    }

    @SuppressLint("StaticFieldLeak")
    class GetAllBooksTask extends AsyncTask<String, Void, ResponseEntity<Book[]>> {
        @Override
        protected ResponseEntity<Book[]> doInBackground(String... URL) {
            final String url = URL[0];
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();

                HttpEntity<Book> entity = new HttpEntity<>(headers);
                return restTemplate.exchange(url, HttpMethod.GET, entity, Book[].class);
            } catch (Exception ex) {
                return null;
            }
        }
    }

}


