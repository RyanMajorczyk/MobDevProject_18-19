package com.ryma.bookapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import domainModels.Book;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyBookViewHolder> {
    private Book[] books;

    private Context context;

    public RecyclerViewAdapter(Context context, Book[] allBooks){
        this.context = context;
        books = allBooks;
    }


    @NonNull
    @Override
    public MyBookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_books_list_item, viewGroup, false);
        return new MyBookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookViewHolder myBookViewHolder, int position) {
        Book book = books[position];

        try {
            if (book.getFront_cover() != null) {
                //Bitmap bitmap = BitmapFactory.decodeByteArray(book.getFront_cover(), 0, book.getFront_cover().length);
                //bookViewHolder.mBookImage.setImageBitmap(bitmap);
                myBookViewHolder.mBookImage.setImageResource(R.mipmap.book_icon);
            } else {
                myBookViewHolder.mBookImage.setImageResource(R.mipmap.book_icon);
            }
        } catch (Exception ex) {
            Toast.makeText(context, "ImageConversion failed", Toast.LENGTH_SHORT).show();
        }

        myBookViewHolder.mTitleTextView.setText(book.getTitle());
        myBookViewHolder.mAuthorTextView.setText(book.getAuteur());
    }

    @Override
    public int getItemCount() {
        return books.length;
    }

    public class MyBookViewHolder extends RecyclerView.ViewHolder{
        TextView mTitleTextView;
        TextView mAuthorTextView;
        ImageView mBookImage;

        public MyBookViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.textView_title);
            mAuthorTextView = itemView.findViewById(R.id.textView_author);
            mBookImage = itemView.findViewById(R.id.imageView_cover);
        }
    }
}



