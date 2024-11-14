package com.example.yogaapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yogaapplication.views.EditClassActivity;
import com.example.yogaapplication.views.EditCourseActivity;
import com.example.yogaapplication.views.ViewClassActivity;
import com.example.yogaapplication.views.ViewCourseActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout frame_course = findViewById(R.id.image_frame_course);
        FrameLayout frame_class = findViewById(R.id.image_frame_class);
        button = findViewById(R.id.fab);


        frame_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewCourseActivity.class);
                startActivity(intent);
            }
        });

        frame_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewClassActivity.class);
                startActivity(intent);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            private Button addCourseBtn, addClassBtn;

            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
                View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.bottomsheet, null);
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();

                addCourseBtn = bottomSheetDialog.findViewById(R.id.add_course);
                addClassBtn = bottomSheetDialog.findViewById(R.id.add_class);

                addCourseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, EditCourseActivity.class);
                        startActivity(intent);
                    }
                });

                addClassBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, EditClassActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}