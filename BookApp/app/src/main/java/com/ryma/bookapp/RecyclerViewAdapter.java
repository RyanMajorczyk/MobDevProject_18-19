package com.ryma.bookapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import domainModels.Book;

public class RecyclerViewAdapter extends RecyclerView.Adapter<BookViewHolder> {
    private Book[] books;
    private Context context;

    public RecyclerViewAdapter(Context context, Book[] allBooks){
        this.context = context;
        books = allBooks;
    }


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_books_list_item, viewGroup, false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int position) {
        Book book = books[position];

        try {
            if (book.getFront_cover() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(book.getFront_cover(), 0, book.getFront_cover().length);
                //bookViewHolder.mBookImage.setImageBitmap(bitmap);
                bookViewHolder.mBookImage.setImageResource(R.mipmap.book_icon);
            } else {
                bookViewHolder.mBookImage.setImageResource(R.mipmap.book_icon);
            }
        } catch (Exception ex) {
            Toast.makeText(context, "ImageConversion failed", Toast.LENGTH_SHORT).show();
        }


        bookViewHolder.mTitleTextView.setText(book.getTitle());
        bookViewHolder.mAuthorTextView.setText(book.getAuteur());

    }

    @Override
    public int getItemCount() {
        return books.length;
    }
}

class BookViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    TextView mTitleTextView;
    TextView mAuthorTextView;
    ImageView mBookImage;

    BookViewHolder(View itemView) {
        super(itemView);
        mTitleTextView = itemView.findViewById(R.id.textView_title);
        mAuthorTextView = itemView.findViewById(R.id.textView_author);
        mBookImage = itemView.findViewById(R.id.imageView_cover);
    }
}

