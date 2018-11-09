package com.ryma.bookapp.MyBooks;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ryma.bookapp.R;
import com.ryma.bookapp.RecyclerViewAdapter;
import com.ryma.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

import domainModels.Book;

public class RecyclerViewMyBooksFragment extends Fragment {
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter = null;
    RecyclerView.LayoutManager mLayoutManager;

    DatabaseHandler database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_books_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerview_books);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        database = new DatabaseHandler(getContext());

        database.insertData(111,"De jaren van verachting","1-4564-448-451", "Andrej Sapkowski", "De hekser Geralt gaat met Ciri naar Kaer Mohen en traint haar in de vechtkunst van de herksers ...,");
        database.insertData(112,"Vrouwe van het meer","1-564-448-451", "Andrej Sapkowski", "De hekser Geralt is nog steeds op zoek naar zijn Ciri ...");

        try {
            Cursor result = database.getAllBooks();

            if (result.getCount() == 0) {
                Toast.makeText(getActivity(), "No books to show", Toast.LENGTH_SHORT).show();
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

        return view;
    }

    private void setBookAdapter(Book[] result) {
        try {
            //mAdapter = new RecyclerViewAdapter(getActivity() ,result);
            //mRecyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
