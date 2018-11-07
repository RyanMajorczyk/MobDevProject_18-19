package com.ryma.bookapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import data.Book;

public class RecyclerViewFragment extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter = null;
    RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_books_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerview_books);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final String URL = "http://81.240.220.38:8090/book";
        new GetAllBooksTask().execute(URL);


        return view;
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
            setBookAdapter(result);
        }
    }

    private void setBookAdapter(ResponseEntity<Book[]> result) {
        try {
            mAdapter = new RecyclerViewAdapter(getActivity(), result.getBody());
            mRecyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
