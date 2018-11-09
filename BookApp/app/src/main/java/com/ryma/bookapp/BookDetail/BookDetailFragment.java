package com.ryma.bookapp.BookDetail;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryma.bookapp.AddReview.AddReviewActivity;
import com.ryma.bookapp.R;
import com.ryma.bookapp.RecyclerViewAdapter;
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

    private static String bookToUseId;

    public static BookDetailFragment newFragment(String bookId){
        Bundle bundle = new Bundle();
        bundle.putString("book_id" ,bookId);
        bookToUseId = bookId;
        BookDetailFragment bookDetailFragment = new BookDetailFragment();
        bookDetailFragment.setArguments(bundle);
        return bookDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String id = getArguments().getString("id");
            if (id != null) {
                final String URL = "http://81.240.220.38:8090/book/" + id;
                new GetBookByIdTask().execute(URL);
            } else {
                final String URL = "http://81.240.220.38:8090/book/" + bookToUseId;
                new GetBookByIdTask().execute(URL);
            }
        }
    }
    public void SetValues(final Book book) {
        Button addReviewButton = getView().findViewById(R.id.add_review_button);
        addReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddReviewActivity.class);
                intent.putExtra("bookId", book.getId().toString());
                startActivity(intent);
            }
        });
        mimageView = getView().findViewById(R.id.imageView_detail);
        titleContentTextView = getView().findViewById(R.id.textView_title_content);
        authorContentTextView = getView().findViewById(R.id.textView_author_content);
        descriptionContentTextView = getView().findViewById(R.id.textView_description_content);

        mimageView.setImageResource(R.mipmap.book_icon);
        titleContentTextView.setText(book.getTitle());
        authorContentTextView.setText(book.getAuteur());
        descriptionContentTextView.setText(book.getDescription());

        mRecyclerView = getView().findViewById(R.id.recyclerview_reviews);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        List<Review> reviewList = book.getReviews();
        if (reviewList.size() != 0) {
            Review[] reviews = new Review[reviewList.size()];
            reviews = reviewList.toArray(reviews);
            RecyclerViewReviewAdapter reviewAdapter = new RecyclerViewReviewAdapter(getContext(), reviews);
            mRecyclerView.setAdapter(reviewAdapter);
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_detail_fragment, container, false);
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
            try {
                book = result.getBody();
                SetValues(result.getBody());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
