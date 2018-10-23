package com.ryma.bookapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import data.Book;

public class AddBookActivity extends AppCompatActivity {
    private static final int CAMERA_PIC_REQUEST = 1111;

    private ImageView mImage;

    private EditText isbn;
    private EditText title;
    private EditText author;
    private EditText description;
    private BookController bookController;



    public void addBookClicked(View view) {

        if (isbn.getText() != null && title != null && author != null && description != null ) {
            Book bookToAdd = new Book(title.getText().toString()
                    , isbn.getText().toString()
                    , author.getText().toString()
                    , description.getText().toString());
            bookController.addBook(bookToAdd);
            startActivity(new Intent(AddBookActivity.this, MenuActivity.class));
        } else {
            Toast toast = Toast.makeText(this,"Please fill in all fields",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        isbn = findViewById(R.id.editText_isbn);
        title = findViewById(R.id.editText_title);
        author = findViewById(R.id.editText_author);
        description = findViewById(R.id.editText_description);
        bookController = new BookController();

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

}