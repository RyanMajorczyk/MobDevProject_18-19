package com.ryma.bookapp.BookDetail;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ryma.bookapp.AddBook.AddBookActivity;
import com.ryma.bookapp.AddReview.AddReviewActivity;
import com.ryma.bookapp.MainActivity;
import com.ryma.bookapp.MyBooks.MyBooksActivity;
import com.ryma.bookapp.R;
import com.ryma.bookapp.RecyclerViewReviewFragment;

public class BookDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        BookDetailImageFragment imageFragment = new BookDetailImageFragment();
        BookDetailLabelsFragment labelsFragment = new BookDetailLabelsFragment();
        //RecyclerViewReviewFragment reviewsFragment = new RecyclerViewReviewFragment();

        FragmentManager fragmentManageranager=getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManageranager.beginTransaction();

        transaction.add(R.id.book_detail_image, imageFragment, "Top");
        transaction.add(R.id.book_detail_labels, labelsFragment,"Middle");
        //transaction.add(R.id.book_detail_reviews, reviewsFragment,"Bottom");

        transaction.commit();
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
