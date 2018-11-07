package com.ryma.bookapp.AddBook;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ryma.Controllers.BookController;
import com.ryma.bookapp.AddReview.AddReviewActivity;
import com.ryma.bookapp.MainActivity;
import com.ryma.bookapp.MyBooks.MyBooksActivity;
import com.ryma.bookapp.R;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import data.Book;
import data.Review;

public class AddBookActivity extends AppCompatActivity {
    private static final int CAMERA_PIC_REQUEST = 1111;

    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

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

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

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