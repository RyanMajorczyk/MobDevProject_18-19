package com.ryma.bookapp.AddBook;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.ryma.bookapp.AddReview.AddReviewActivity;
import com.ryma.bookapp.MainActivity;
import com.ryma.bookapp.MyBooks.MyBooksActivity;
import com.ryma.bookapp.R;

public class AddBookActivity extends AppCompatActivity {
    private static final int CAMERA_PIC_REQUEST = 1111;

    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        // Code voor pushnotifictations
        try {
            NotificationManager notifi = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Book added")
                    .setContentText("BOOK DATA")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .build();
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
            notifi.notify(0, notify);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == RESULT_OK) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("domainModels");

            try {
                mImage.setImageBitmap(bitmap);
            } catch (Exception ex) {
                Toast toast = Toast.makeText(this, "ERROR", Toast.LENGTH_LONG);
                toast.show();
                Log.e("FOUT", ex.getStackTrace() + "");
            }

        }

    }


}