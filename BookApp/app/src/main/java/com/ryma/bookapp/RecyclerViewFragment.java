package com.ryma.bookapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import android.widget.Toast;

import domainModels.Book;

public class RecyclerViewFragment extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerViewAdapter mAdapter = null;
    RecyclerView.LayoutManager mLayoutManager;
    private Clicked clicked = null;

    private Book[] booksInAdapter;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int itemPosition = mRecyclerView.getChildLayoutPosition(v);
            clicked.onItemSelected(booksInAdapter[itemPosition]);
        }
    };


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        clicked = (Clicked) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_books_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerview_books);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        clicked = null;
    }

    public void setmAdapter(Book[] books) {
        booksInAdapter = books;
        try {
            mAdapter = new RecyclerViewAdapter(getContext(), books);
            mRecyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            Log.e("Error: ", e.getStackTrace().toString());
        }
    }

    public void setListener(Clicked listener) {
        clicked = listener;
    }

    public interface Clicked {
        public void onItemSelected(Book book);
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<BookViewHolder> {
        private Book[] books;
        private Context context;

        public RecyclerViewAdapter(Context context, Book[] allBooks) {
            this.context = context;
            books = allBooks;
        }

        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_books_list_item, viewGroup, false);
            v.setOnClickListener(mOnClickListener);
            return new BookViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int position) {
            Book book = books[position];
            bookViewHolder.setValues(context, book);
        }

        @Override
        public int getItemCount() {
            return books.length;
        }
    }

    private class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        TextView mTitleTextView;
        TextView mAuthorTextView;
        ImageView mBookImage;
        Book bookItem;

        BookViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.textView_title);
            mAuthorTextView = itemView.findViewById(R.id.textView_author);
            mBookImage = itemView.findViewById(R.id.imageView_cover);
        }

        public void setValues(Context context, Book book) {
            bookItem = book;
            try {
                mTitleTextView.setText(book.getTitle());
                mAuthorTextView.setText(book.getAuteur());
                if (book.getFront_cover() != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(book.getFront_cover(), 0, book.getFront_cover().length);
                    //bookViewHolder.mBookImage.setImageBitmap(bitmap);
                    mBookImage.setImageResource(R.mipmap.book_icon);
                } else {
                    mBookImage.setImageResource(R.mipmap.book_icon);
                }
            } catch (Exception ex) {
                Toast.makeText(context, "ImageConversion failed", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onClick(View v) {
           // clicked.onItemSelected();
        }
    }
}
