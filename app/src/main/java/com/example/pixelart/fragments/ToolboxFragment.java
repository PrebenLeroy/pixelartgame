package com.example.pixelart.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.pixelart.R;
import com.example.pixelart.interfaces.MyListener;

/**
 * Created by prebe on 21/01/2018.
 */

public class ToolboxFragment extends Fragment {

    ImageButton btnColor, btnCam;
    Button btnCircle, btnTriangle, btnSquare;
    MyListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = (MyListener) getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.toolbox, container, false);
        btnColor = (ImageButton) v.findViewById(R.id.colorpicker);
        btnColor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.chooseColor();
            }
        });
        btnCam = (ImageButton) v.findViewById(R.id.camera);
        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.takePicture();
            }
        });
        btnCircle = (Button) v.findViewById(R.id.circle);
        btnCircle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.drawCircle();
            }
        });
        btnTriangle = (Button) v.findViewById(R.id.triangle);
        btnTriangle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.drawTriangle();
            }
        });
        btnSquare = (Button) v.findViewById(R.id.square);
        btnSquare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.drawSquare();
            }
        });
        return v;

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
    }
}
