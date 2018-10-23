package com.ryma.Controllers;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ryma.bookapp.MainActivity;
import com.ryma.bookapp.R;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import data.Book;

public class BookController {
    private Book bookToGiveBack = new Book();
    private Activity activity;
    private Book bookToAdd;

    public Book getBookById(int id, MainActivity mainActivity) {
        final String URL = "http://81.240.220.38:8090/book/" + id;
        new BookController.RESTTask().execute(URL);

        activity = mainActivity;

        return bookToGiveBack;
    }

    public void addBook(Book book) {
        final String URL = "http://81.240.220.38:8090/book";
        bookToAdd = book;
        new BookController.AddTask().execute(URL);
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
            bookToGiveBack = result.getBody();
          //  TextView t = (TextView) activity.findViewById(R.id.textView);
          //  t.setText(bookToGiveBack.toString());
        }

    }

    class AddTask extends AsyncTask<String, Void, ResponseEntity<Book>> {
        @Override
        protected ResponseEntity<Book> doInBackground(String... URL) {
            final String url = URL[0];
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();

                HttpEntity<Book> entity = new HttpEntity<>(bookToAdd, headers);
                ResponseEntity<Book> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, Book.class);
                return responseEntity;
            }
            catch (Exception ex) {
                return null;
            }
        }
    }
}
