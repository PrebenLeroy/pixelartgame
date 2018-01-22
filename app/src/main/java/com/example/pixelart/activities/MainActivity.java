package com.example.pixelart.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pixelart.R;
import com.example.pixelart.fragments.BoardFragment;
import com.example.pixelart.fragments.ToolboxFragment;
import com.example.pixelart.interfaces.MyListener;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity implements MyListener {

    BoardFragment firstFragment;
    private int currentColor = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        firstFragment = new BoardFragment();

        if (findViewById(R.id.fragment_container) != null) {
            if (manager.findFragmentById(R.id.fragment_container) == null) {

                // Add the fragment to the 'fragment_container' FrameLayout

                transaction.add(R.id.fragment_container, firstFragment);
            }
        }

        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newBoard:
                firstFragment.newBoard();
                return true;
            case R.id.saveBoard:
                firstFragment.insertBoard();
                return true;
            case R.id.openBoard:
                firstFragment.showAll();
                return true;
            case R.id.updateBoard:
                firstFragment.updateBoard();
                return true;
            case R.id.deleteBoard:
                firstFragment.deleteBoard();
                return true;
            case R.id.btnToolbox:
                showToolbox();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showToolbox() {
        ToolboxFragment newFragment = new ToolboxFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(findViewById(R.id.toolbox_container) == null) {
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
        } else {
            if (getSupportFragmentManager().findFragmentById(R.id.toolbox_container) == null) {

                // Add the fragment to the 'fragment_container' FrameLayout

                transaction.add(R.id.toolbox_container, newFragment);
            }
        }

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void drawCircle() {
        firstFragment.drawCircle();
    }

    @Override
    public void drawTriangle() {
        firstFragment.drawTriangle();
    }

    @Override
    public void drawSquare() {
        firstFragment.drawSquare();
    }

    @Override
    public void chooseColor() {
        openDialog(false);
    }

    @Override
    public void takePicture() {
        firstFragment.dispatchTakePictureIntent();
    }

    public void openDialog(boolean supportsAlpha) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, currentColor, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                currentColor = color;
                MainActivity.this.firstFragment.setCurrentColor(currentColor);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(getApplicationContext(), "Action canceled!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}
