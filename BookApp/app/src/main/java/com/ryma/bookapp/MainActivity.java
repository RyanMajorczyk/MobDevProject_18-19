package com.ryma.bookapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.ryma.Controllers.BookController;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import data.Book;

public class MainActivity extends AppCompatActivity {
    BookController controller = new BookController();

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter = null;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_books);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);



        final String URL = "http://81.240.220.38:8090/book";
        new GetAllBooksTask().execute(URL);


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

            setBookAdapter(result);

        }
    }

    private void setBookAdapter(ResponseEntity<Book[]> result) {
        // specify an adapter (see also next example)
        try {
            mAdapter = new RecyclerViewAdapter(MainActivity.this ,result.getBody());
            mRecyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}


