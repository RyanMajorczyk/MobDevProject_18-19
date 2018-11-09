package com.ryma.bookapp.AddBook;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.ryma.bookapp.AddReview.AddReviewActivity;
import com.ryma.bookapp.BookDetail.BookDetailFragment;
import com.ryma.bookapp.MainActivity;
import com.ryma.bookapp.MyBooks.MyBooksActivity;
import com.ryma.bookapp.R;
import com.ryma.database.DatabaseHandler;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import domainModels.Book;

public class AddBookActivity extends AppCompatActivity implements AddBookButtonsFragment.AddBookButtonClicked {
    private EditText isbn;
    private EditText title;
    private EditText author;
    private EditText description;

    private Book bookToAdd;

    private DatabaseHandler myDatabase;

    private byte[] image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_book);
        initializeVariables();
    }

    private void SendNotification(String title, String bookName) {
        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this.getApplicationContext(), "notify_001");
        Intent ii = new Intent(this.getApplicationContext(), MyBooksActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(title);
        bigText.setBigContentTitle("Successfully added "+ bookName);
        bigText.setSummaryText("You successfully added the book with name: " + bookName);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText("Successfully added "+ bookName);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(0, mBuilder.build());
    }


    @Override
    public void addBookButtonClicked(byte[] image) {
        this.image = image;

        if (!isbn.getText().toString().equals("") && !title.getText().toString().equals("")
                && !author.getText().toString().equals("") && !description.getText().toString().equals("")) {
            bookToAdd = new Book(title.getText().toString()
                    , isbn.getText().toString()
                    , author.getText().toString()
                    , description.getText().toString()
            );

            //bookToAdd = new Book("Boek titel", "ISBN-1234","Author Name","Some Description");
            final String URL = "http://81.240.220.38:8090/book";
            new AddBookTask().execute(URL);

        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void initializeVariables() {
        // Initialization
        myDatabase = new DatabaseHandler(getApplicationContext());
        isbn = findViewById(R.id.editText_isbn);
        title = findViewById(R.id.editText_title);
        author = findViewById(R.id.editText_author);
        description = findViewById(R.id.editText_description);
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
            } catch (Exception ex) {
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
                Toast.makeText(getApplicationContext(), "Book Added!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Locale book save failed!", Toast.LENGTH_LONG).show();
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
            } catch (Exception ex) {
                return null;
            }
        }

        protected void onPostExecute(ResponseEntity<Book> result) {
            HttpStatus status = result.getStatusCode();
            Toast.makeText(getApplicationContext(), "Image Added!", Toast.LENGTH_LONG).show();
            SendNotification("Book added", bookToAdd.getTitle());
            startActivity(new Intent(getApplicationContext(), MainActivity.class));


        }

    }
}