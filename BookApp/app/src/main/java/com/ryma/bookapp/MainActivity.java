package com.ryma.bookapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import data.Book;

public class MainActivity extends AppCompatActivity {


    public void sendMessage(View view) {
        final String URL = "http://81.240.220.38:8090/book/2";
        new RESTTask().execute(URL);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        }

    class RESTTask extends AsyncTask<String, Void, ResponseEntity<Book>> {

        //Implements method
        protected ResponseEntity<Book> doInBackground(String... URL) {
            final String url = URL[0];
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                HttpHeaders headers = new HttpHeaders();

                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<Book> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Book.class);

                return responseEntity;
            }
            catch (Exception ex) {
                return null;
            }
        }

        protected void onPostExecute(ResponseEntity<Book> result) {
            HttpStatus status = result.getStatusCode();
            Book book = result.getBody();
            TextView t = (TextView) findViewById(R.id.textView);
            t.setText(book.toString());
        }

    }
}
