package com.ryma.bookapp.BookDetail;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryma.bookapp.MainActivity;
import com.ryma.bookapp.R;
import com.ryma.bookapp.RecyclerViewAdapter;
import com.ryma.bookapp.RecyclerViewFragment;
import com.ryma.bookapp.RecyclerViewReviewAdapter;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import domainModels.Book;
import domainModels.Review;

public class BookDetailFragment extends Fragment {
    RecyclerView mRecyclerView;
    RecyclerViewAdapter mAdapter = null;
    RecyclerView.LayoutManager mLayoutManager;
    ImageView mimageView;
    TextView titleContentTextView;
    TextView authorContentTextView;
    TextView descriptionContentTextView;
    Book book;

    public static BookDetailFragment newFragment(String bookId){
        Bundle bundle = new Bundle();
        bundle.putString("book_id" ,bookId);
        BookDetailFragment bookDetailFragment = new BookDetailFragment();
        bookDetailFragment.setArguments(bundle);
        return bookDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String id = getArguments().getString("id");
            final String URL = "http://81.240.220.38:8090/book" + id;
            new GetBookByIdTask().execute(URL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_detail_fragment, container, false);

        mimageView = view.findViewById(R.id.imageView_detail);
        mimageView.setImageResource(R.mipmap.book_icon);

        titleContentTextView = view.findViewById(R.id.textView_title_content);
        titleContentTextView.setText(book.getTitle());

        authorContentTextView = view.findViewById(R.id.textView_author_content);
        authorContentTextView.setText(book.getAuteur());

        descriptionContentTextView = view.findViewById(R.id.textView_description_content);
        descriptionContentTextView.setText(book.getDescription());

        mRecyclerView = view.findViewById(R.id.recyclerview_reviews);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        List<Review> reviewList = book.getReviews();
        if (reviewList.size() != 0) {
            Review[] reviews = new Review[reviewList.size()];
            reviews = reviewList.toArray(reviews);
            RecyclerViewReviewAdapter reviewAdapter = new RecyclerViewReviewAdapter(getContext(), reviews);
            mRecyclerView.setAdapter(reviewAdapter);
        }

        return view;
    }

    class GetBookByIdTask extends AsyncTask<String, Void, ResponseEntity<Book>> {
        @Override
        protected ResponseEntity<Book> doInBackground(String... URL) {
            final String url = URL[0];
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();

                HttpEntity<Book> entity = new HttpEntity<>(headers);
                ResponseEntity<Book> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Book.class);
                return responseEntity;
            } catch (Exception ex) {
                return null;
            }
        }

        protected void onPostExecute(ResponseEntity<Book> result) {
            book = result.getBody();
        }
    }
}
