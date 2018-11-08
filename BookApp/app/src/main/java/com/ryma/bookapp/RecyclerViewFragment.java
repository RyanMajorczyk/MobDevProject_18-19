package com.ryma.bookapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import domainModels.Book;

public class RecyclerViewFragment extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter = null;
    RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_books_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerview_books);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }


    public void setBookAdapter(Book[] books) {

        mAdapter = new RecyclerViewAdapter(getActivity(), books);
        mRecyclerView.setAdapter(mAdapter);
    }

    public interface onFragmentClick{
        public void onItemSelected();
    }
}
