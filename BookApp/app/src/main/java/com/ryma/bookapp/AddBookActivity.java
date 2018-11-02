package com.ryma.bookapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ryma.Controllers.BookController;

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

    private EditText isbn;
    private EditText title;
    private EditText author;
    private EditText description;

    private byte[] image;

    private Book bookToAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        isbn = findViewById(R.id.editText_isbn);
        title = findViewById(R.id.editText_title);
        author = findViewById(R.id.editText_author);
        description = findViewById(R.id.editText_description);

        mImage = (ImageView) findViewById(R.id.imageView_cover);
    }

    public void CameraClick(View view) {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_PIC_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            try {
                mImage.setImageBitmap(bitmap);
            } catch (Exception ex) {
                Toast toast = Toast.makeText(this,"ERROR",Toast.LENGTH_LONG);
                toast.show();
                Log.e("FOUT",ex.getStackTrace() + "");
            }

            // mImage.setImageBitmap(bitmap);
        }

    }


    public void addBookClicked(View view) {

        mImage.setDrawingCacheEnabled(true);
        mImage.buildDrawingCache();
        Bitmap bmp = mImage.getDrawingCache();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 0, stream);
        image = stream.toByteArray();
        bmp.recycle();

        if (!isbn.getText().toString().equals("") && !title.getText().toString().equals("")
                && !author.getText().toString().equals("") && !description.getText().toString().equals("")  ) {
            bookToAdd = new Book(title.getText().toString()
                    , isbn.getText().toString()
                    , author.getText().toString()
                    , description.getText().toString()
                    );

            final String URL = "http://81.240.220.38:8090/book";
            new AddBookTask().execute(URL);

        } else {
            Toast toast = Toast.makeText(this,"Please fill in all fields",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    class AddBookTask extends AsyncTask<String, Void, ResponseEntity<Book>> {

        //Implements method
        @Override
        protected ResponseEntity<Book> doInBackground(String... URL) {
            final String url = URL[0];
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();

                HttpEntity<Book> entity = new HttpEntity<>(bookToAdd, headers);
                ResponseEntity<Book> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, Book.class);
                return responseEntity;
            }
            catch (Exception ex) {
                return null;
            }
        }

        protected void onPostExecute(ResponseEntity<Book> result) {
            HttpStatus status = result.getStatusCode();
            Toast.makeText(AddBookActivity.this, "Book Added!", Toast.LENGTH_LONG).show();

            final String URL = "http://81.240.220.38:8090/book/addCover/" + result.getBody().getId();
            new AddImageTask().execute(URL);

        }

    }


    class AddImageTask extends AsyncTask<String, Void, ResponseEntity<Book>> {

        //Implements method
        @Override
        protected ResponseEntity<Book> doInBackground(String... URL) {
            final String url = URL[0];
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();

                HttpEntity<byte[]> entity = new HttpEntity<>(image, headers);
                ResponseEntity<Book> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Book.class);
                return responseEntity;
            }
            catch (Exception ex) {
                return null;
            }
        }

        protected void onPostExecute(ResponseEntity<Book> result) {
            HttpStatus status = result.getStatusCode();
            Toast.makeText(AddBookActivity.this, "Image Added!", Toast.LENGTH_LONG).show();

            startActivity(new Intent(AddBookActivity.this, MenuActivity.class));
        }

    }
}