package com.ryma.bookapp.AddBook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ryma.bookapp.R;

import java.io.ByteArrayOutputStream;


import static android.app.Activity.RESULT_OK;

public class AddBookButtonsFragment extends Fragment {
    private static final int CAMERA_PIC_REQUEST = 1111;

    private ImageView mImage;

    private Bitmap bookCoverBitmap;
    private byte[] image;



    AddBookButtonClicked mCallback;
    public interface AddBookButtonClicked {
        public void addBookButtonClicked(byte[] image);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_book_buttons_fragment, container, false);

        ImageView imageView = view.findViewById(R.id.imageView_cover);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_PIC_REQUEST);
                } catch (Exception ex) {
                    Log.e("Camera failure: ", ex.getStackTrace().toString());
                }
            }
        });

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (AddBookButtonClicked) context;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
              try {
                  bookCoverBitmap = (Bitmap) data.getExtras().get("data");
                  mImage.setImageBitmap(bookCoverBitmap);
            } catch (Exception ex) {
                Toast toast = Toast.makeText(getContext(), "ERROR", Toast.LENGTH_LONG);
                toast.show();
                Log.e("Error: ", ex.getStackTrace().toString());
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mImage = (ImageView) view.findViewById(R.id.imageView_cover);
        Button button = view.findViewById(R.id.button_add_book);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bookCoverBitmap != null) {
                    try {
                        Bitmap bmp = bookCoverBitmap;

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        image = stream.toByteArray();
                        bmp.recycle();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    image = new byte[]{0};
                }
                mCallback.addBookButtonClicked(image);
            }
        });
    }
}
