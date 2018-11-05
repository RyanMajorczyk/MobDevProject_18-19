package com.ryma.bookapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ryma.data.DatabaseHandler;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import data.Book;

public class MyBooksActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter = null;
    RecyclerView.LayoutManager mLayoutManager;

    DatabaseHandler database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);
        database = new DatabaseHandler(this);

        try {
            Cursor result = database.getAllBooks();

            if (result.getCount() == 0) {
                Toast.makeText(this, "No books to show", Toast.LENGTH_SHORT).show();
            }

            List<Book> booksList = new ArrayList<>();
            while(result.moveToNext()) {
                booksList.add(new Book(
                        Long.parseLong(result.getString((0))),
                        result.getString((1)),
                        result.getString((2)),
                        result.getString((3)),
                        result.getString((4))
                ));
            }


            Book[] booksToShow = booksList.toArray(new Book[booksList.size()]);

            setBookAdapter(booksToShow);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    private void setBookAdapter(Book[] result) {
        // specify an adapter (see also next example)
        try {
            mAdapter = new RecyclerViewAdapter(MyBooksActivity.this ,result);
            mRecyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
