package com.ryma.bookapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ryma.data.DatabaseHandler;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;

import data.Book;

public class AddBookFragment extends Fragment {

    private DatabaseHandler myDatabase;

    private static final int CAMERA_PIC_REQUEST = 1111;

    private ImageView mImage;

    private EditText isbn;
    private EditText title;
    private EditText author;
    private EditText description;

    private byte[] image;

    private Book bookToAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_book_fragment, container, false);

        ImageView imageView = view.findViewById(R.id.imageView_cover);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_PIC_REQUEST);
            }
        });

        Button button = view.findViewById(R.id.button_add_book);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mImage.setDrawingCacheEnabled(true);
                    mImage.buildDrawingCache();
                    Bitmap bmp = mImage.getDrawingCache();

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 50, stream);
                    image = stream.toByteArray();
                    bmp.recycle();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

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
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),"Please fill in all fields",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        isbn = view.findViewById(R.id.editText_isbn);
        title = view.findViewById(R.id.editText_title);
        author = view.findViewById(R.id.editText_author);
        description = view.findViewById(R.id.editText_description);

        mImage = (ImageView) view.findViewById(R.id.imageView_cover);

        myDatabase = new DatabaseHandler(getActivity().getApplicationContext());

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
            boolean isInserted = false;
            try {
                isInserted = myDatabase.insertData(result.getBody().getId().intValue(), bookToAdd.getTitle(), bookToAdd.getIsbn(), bookToAdd.getAuteur(), bookToAdd.getDescription());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            // Can do some checks
            if (isInserted) {
                Toast.makeText(getActivity().getApplicationContext(), "Book Added!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Locale book save failed!", Toast.LENGTH_LONG).show();
            }
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
            Toast.makeText(getActivity().getApplicationContext(), "Image Added!", Toast.LENGTH_LONG).show();

            startActivity(new Intent(getActivity().getApplicationContext(), MenuActivity.class));

        }

    }
}
