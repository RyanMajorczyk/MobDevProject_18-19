package com.ryma.bookapp.AddReview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ryma.bookapp.AddBook.AddBookActivity;
import com.ryma.bookapp.MainActivity;
import com.ryma.bookapp.MyBooks.MyBooksActivity;
import com.ryma.bookapp.R;

public class AddReviewActivity extends AppCompatActivity {
    private String bookId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        bookId = getIntent().getStringExtra("bookId");


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
            case R.id.nav_MyBooks:
                startActivity(new Intent(this, MyBooksActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
}
