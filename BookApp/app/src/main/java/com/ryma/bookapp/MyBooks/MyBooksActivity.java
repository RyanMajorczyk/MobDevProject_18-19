package com.ryma.bookapp.MyBooks;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.ryma.bookapp.AddBook.AddBookActivity;
import com.ryma.bookapp.AddReview.AddReviewActivity;
import com.ryma.bookapp.MainActivity;
import com.ryma.bookapp.R;
import com.ryma.bookapp.RecyclerViewAdapter;
import com.ryma.data.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

import data.Book;

public class MyBooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.activity_menu_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.nav_addBook:
                startActivity(new Intent(this, AddBookActivity.class));
                return true;
            case R.id.nav_AddReview:
                startActivity(new Intent(this, AddReviewActivity.class));
                return true;
            case R.id.nav_MyBooks:
                startActivity(new Intent(this, MyBooksActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
