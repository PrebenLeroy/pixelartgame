package com.example.pixelart.fragments;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.pixelart.R;
import com.example.pixelart.Views.Board;
import com.example.pixelart.Views.PixelView;
import com.example.pixelart.persistency.PixelArtContract;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by prebe on 21/01/2018.
 */

public class BoardFragment extends Fragment {

    Board board;
    private int currentColor = Color.BLACK;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap imageBit, imageBitmapNew;
    private static final String TAG = "PixelArt";
    private EditText boardName;
    private int[] colors;
    private String colorArray;
    private List<String> list = new ArrayList<String>();

    private String selectedBoard = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            this.currentColor = (int) savedInstanceState.getInt("color");
            this.selectedBoard = (String) savedInstanceState.getString("name");
        }

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.board, container, false);
        board = (Board) v.findViewById(R.id.board);

        this.colors = new int[this.board.getChildCount()];

        board.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                checkTouch(event);
                return true;
            }
        });

        return v;
    }

    private void checkTouch(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        for (int i = 0; i < board.getChildCount(); i++) {
            PixelView pixelView = (PixelView) board.getChildAt(i);
            if (x > pixelView.getLeft() && x < pixelView.getRight() && y > pixelView.getTop() && y < pixelView.getBottom()) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    pixelView.setBackgroundColor(currentColor);
                    pixelView.getTextView().setBackgroundColor(currentColor);
                    pixelView.setPixelColor(currentColor);
                }
            }
        }
    }

    public void drawCircle() {
        int[] x = {109, 110, 111, 127, 128, 132, 133, 146, 154, 165, 175,
                185, 195, 204, 216, 224, 236, 244, 256, 265, 275, 285, 295, 306, 314,
                327, 328, 332, 333, 349, 350, 351};
        for (int i : x) {
            PixelView pixelView = (PixelView) board.getChildAt(i);
            pixelView.setBackgroundColor(currentColor);
            pixelView.getTextView().setBackgroundColor(currentColor);
            pixelView.setPixelColor(currentColor);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("color", currentColor);
        outState.putString("name", selectedBoard);
    }

    public void drawTriangle() {
        int[] x = {110, 129, 131, 148, 152, 167, 173, 186, 194, 205, 215, 224, 236, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256};
        for (int i : x) {
            PixelView pixelView = (PixelView) board.getChildAt(i);
            pixelView.setBackgroundColor(currentColor);
            pixelView.getTextView().setBackgroundColor(currentColor);
            pixelView.setPixelColor(currentColor);
        }
    }

    public void drawSquare() {
        int[] x = {104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116,
                124, 136, 144, 156, 164, 176, 184, 196, 204, 216, 224, 236, 244,
                256, 264, 276, 284, 296, 304, 316, 324, 336, 344, 345, 346, 347,
                348, 349, 350, 351, 352, 353, 354, 355, 356};
        for (int i : x) {
            PixelView pixelView = (PixelView) board.getChildAt(i);
            pixelView.setBackgroundColor(currentColor);
            pixelView.getTextView().setBackgroundColor(currentColor);
            pixelView.setPixelColor(currentColor);
        }
    }

    public void setCurrentColor(int color){
        this.currentColor = color;
        Log.i("colorBlack", String.format("%d", Color.BLACK));
        Log.i("colorWhite", String.format("%d", Color.WHITE));
        Log.i("currentColor", String.format("%d", currentColor));
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public Bitmap getResizedBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();

        Bitmap res = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        return res;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBit = (Bitmap) extras.get("data");
            imageBit = Bitmap.createScaledBitmap(imageBit, 20, 20, false);
            imageBitmapNew = getResizedBitmap(imageBit, 25, 25);

            int width = imageBitmapNew.getWidth();
            int height = imageBitmapNew.getHeight();
            int[] store = new int[width * height];
            imageBitmapNew.getPixels(store, 0, width, 0, 0, width, height);

            for (int i = 0; i < board.getChildCount(); i++) {
                Log.i("color", String.format("%d", store[i]));
                PixelView pixelView = (PixelView) board.getChildAt(i);
                pixelView.setBackgroundColor(store[i]);
                pixelView.getTextView().setBackgroundColor(store[i]);
                pixelView.setPixelColor(store[i]);
            }
        }
    }

    public void deleteBoard() {
        if(this.selectedBoard == ""){
            new AlertDialog.Builder(getActivity())
                    .setView(R.layout.error)
                    .setNegativeButton(android.R.string.cancel, null).show();
        } else {
            new AlertDialog.Builder(getActivity())
                    .setView(R.layout.confirmdelete)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            String selection = PixelArtContract.PixelArtEntry.COLUMN_BOARDNAME + " = ?";
                            String[] selectionArgs = {selectedBoard};

                            Uri uri = Uri.withAppendedPath(PixelArtContract.PixelArtEntry.CONTENT_URI, selectedBoard);
                            int rowsUpdated = getActivity().getContentResolver().delete(uri, selection, selectionArgs);
                            Log.i(TAG, "Number of rows updated: " + rowsUpdated);

                            newBoard();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null).show();
        }
    }

    public void updateBoard() {

        if(this.selectedBoard == ""){
            insertBoard();
        } else {

            this.colorArray = "";

            for (int i = 0; i < board.getChildCount(); i++) {
                PixelView pixelView = (PixelView) board.getChildAt(i);
                this.colors[i] = pixelView.getPixelColor();
            }

            for (int j = 0; j < this.colors.length; j++) {
                colorArray = colorArray + String.format("%d,", this.colors[j]);
            }

            new AlertDialog.Builder(getActivity())
                    .setView(R.layout.confirmation)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            String selection = PixelArtContract.PixelArtEntry.COLUMN_BOARDNAME + " = ?";
                            String[] selectionArgs = {selectedBoard};

                            ContentValues contentValues = new ContentValues();
                            contentValues.put(PixelArtContract.PixelArtEntry.COLUMN_COLORS, colorArray);

                            Uri uri = PixelArtContract.PixelArtEntry.CONTENT_URI;
                            int rowsUpdated = getActivity().getContentResolver().update(uri, contentValues, selection, selectionArgs);
                            Log.i(TAG, "Number of rows updated: " + rowsUpdated);

                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null).show();
        }
    }

    public void showAll() {
        this.list = new ArrayList<>();
        String[] projection = {
                PixelArtContract.PixelArtEntry._ID,
                PixelArtContract.PixelArtEntry.COLUMN_BOARDNAME,
                PixelArtContract.PixelArtEntry.COLUMN_COLORS
        };

        String selection = null;
        String[] selectionArgs = null;

        String sortOrder = null;

        Uri uri = PixelArtContract.PixelArtEntry.CONTENT_URI;

        Cursor cursor = getActivity().getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);

        if (cursor != null) {
            try {
                while(cursor.moveToNext()) {
                    list.add(cursor.getString(cursor.getColumnIndex(PixelArtContract.PixelArtEntry.COLUMN_BOARDNAME)));
                }
            } finally {
                cursor.close();
            }
        }

        View view = View.inflate(getActivity(), R.layout.boardlist, null);
        final Spinner spinner = view.findViewById(R.id.spinner);

        if(this.list.isEmpty())
            spinner.setEnabled(false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, this.list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        if(!this.list.isEmpty()) {
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = spinner.getSelectedItem().toString();
                    Log.i("name", name);
                    openBoard(name);
                }
            });
        }

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void openBoard(String selectedBoard) {
        this.selectedBoard = selectedBoard;

        String[] projection = {
                PixelArtContract.PixelArtEntry._ID,
                PixelArtContract.PixelArtEntry.COLUMN_BOARDNAME,
                PixelArtContract.PixelArtEntry.COLUMN_COLORS
        };

        String selection = PixelArtContract.PixelArtEntry.COLUMN_BOARDNAME + " = ? ";
        String[] selectionArgs = {this.selectedBoard};

        String sortOrder = null;

        Uri uri = PixelArtContract.PixelArtEntry.CONTENT_URI;
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);

        if (cursor != null && cursor.moveToNext()) {

            String str = "";

            String[] columns = cursor.getColumnNames();
            for (String column : columns) {
                str += "\t" + cursor.getString(cursor.getColumnIndex(column));
            }
            str += "\n";

            cursor.close();

            String[] split;
            split = str.split("\t", 4);

            loadBoard(split[3]);
        }

    }

    public void loadBoard(String s) {
        String[] split = s.split(",");

        for (int i = 0; i < board.getChildCount(); i++) {
            PixelView pixelView = (PixelView) board.getChildAt(i);
            pixelView.setBackgroundColor(Integer.parseInt(split[i]));
            pixelView.getTextView().setBackgroundColor(Integer.parseInt(split[i]));
            pixelView.setPixelColor(Integer.parseInt(split[i]));
        }
    }

    public void insertBoard() {

        this.list = new ArrayList<>();
        String[] projection = {
                PixelArtContract.PixelArtEntry._ID,
                PixelArtContract.PixelArtEntry.COLUMN_BOARDNAME,
                PixelArtContract.PixelArtEntry.COLUMN_COLORS
        };

        String selection = null;
        String[] selectionArgs = null;

        String sortOrder = null;

        Uri uri = PixelArtContract.PixelArtEntry.CONTENT_URI;

        Cursor cursor = getActivity().getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);

        if (cursor != null) {
            try {
                while(cursor.moveToNext()) {
                    list.add(cursor.getString(cursor.getColumnIndex(PixelArtContract.PixelArtEntry.COLUMN_BOARDNAME)));
                }
            } finally {
                cursor.close();
            }
        }

        Log.i("list", list.toString());

        this.colorArray = "";

        for (int i = 0; i < board.getChildCount(); i++) {
            PixelView pixelView = (PixelView) board.getChildAt(i);
            this.colors[i] = pixelView.getPixelColor();
        }

        for(int j = 0; j < this.colors.length; j++){
            colorArray = colorArray + String.format("%d,", this.colors[j]);
        }

        View view = View.inflate(getActivity(), R.layout.save, null);
        boardName = view.findViewById(R.id.board_name);
        this.selectedBoard = boardName.getText().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!BoardFragment.this.list.contains(BoardFragment.this.boardName.getText().toString())) {
                    new AlertDialog.Builder(getActivity())
                            .setView(R.layout.confirmation)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {

                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put(PixelArtContract.PixelArtEntry.COLUMN_BOARDNAME, BoardFragment.this.boardName.getText().toString());
                                    contentValues.put(PixelArtContract.PixelArtEntry.COLUMN_COLORS, BoardFragment.this.colorArray);

                                    Uri uri = PixelArtContract.PixelArtEntry.CONTENT_URI;
                                    Uri uriRowInserted = getActivity().getContentResolver().insert(uri, contentValues);
                                    Log.i(TAG, "Item inserted at: " + uriRowInserted);

                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                } else {
                    BoardFragment.this.insertBoard();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void newBoard() {
        this.selectedBoard = "";
        for (int i = 0; i < board.getChildCount(); i++) {
            PixelView pixelView = (PixelView) board.getChildAt(i);
            pixelView.setBackgroundColor(Color.parseColor("#d3d3d3"));
            pixelView.getTextView().setBackgroundColor(Color.parseColor("#d3d3d3"));
            pixelView.setPixelColor(Color.parseColor("#d3d3d3"));
        }
    }
}
