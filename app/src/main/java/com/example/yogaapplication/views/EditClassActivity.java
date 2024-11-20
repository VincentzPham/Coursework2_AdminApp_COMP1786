////code version 1.0
////package com.example.yogaapplication.views;
////
////import android.app.AlertDialog;
////import android.content.ContentValues;
////import android.content.DialogInterface;
////import android.content.Intent;
////import android.database.Cursor;
////import android.database.sqlite.SQLiteDatabase;
////import android.os.Bundle;
////import android.text.TextUtils;
////import android.util.Log;
////import android.view.View;
////import android.widget.AdapterView;
////import android.widget.ArrayAdapter;
////import android.widget.Button;
////import android.widget.EditText;
////import android.widget.Spinner;
////import android.widget.TextView;
////
////import androidx.activity.EdgeToEdge;
////import androidx.appcompat.app.AppCompatActivity;
////import androidx.core.graphics.Insets;
////import androidx.core.view.ViewCompat;
////import androidx.core.view.WindowInsetsCompat;
////
////import com.example.yogaapplication.DbHelper;
////import com.example.yogaapplication.MainActivity;
////import com.example.yogaapplication.R;
////import com.example.yogaapplication.entity.YogaClass;
////
////import java.util.ArrayList;
////
////public class EditClassActivity extends AppCompatActivity {
////
////    private Spinner spinnerCourses;
////    private EditText editName, editDate, editTeacher, editComment;
////    private TextView errorName, errorDate, errorTeacher;
////    private Button buttonSave, buttonDelete;
////    private String classId;
////    private String courseId;
////    private ArrayList<String> courseIds;
////    private DbHelper dbHelper;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_edit_class);
////
////        dbHelper = new DbHelper(this);
////        editName = findViewById(R.id.edit_name);
////        errorName = findViewById(R.id.error_name);
////        spinnerCourses = findViewById(R.id.spinner_course);
////        editDate = findViewById(R.id.edit_date);
////        editTeacher = findViewById(R.id.edit_teacher);
////        editComment = findViewById(R.id.edit_comment);
////        errorDate = findViewById(R.id.error_date);
////        errorTeacher = findViewById(R.id.error_teacher);
////        buttonSave = findViewById(R.id.button_save_class);
////
////        Intent intent = getIntent();
////        classId = intent.getStringExtra("CLASS_ID");
////
////        loadCourses();
////
////
////        spinnerCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////            @Override
////            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                courseId = courseIds.get(position).toString();
////            }
////
////            @Override
////            public void onNothingSelected(AdapterView<?> parent) {
////            }
////        });
////
////        if (classId != null) {
////            loadClassDetails(classId);
////            buttonDelete = findViewById(R.id.button_delete);
////            buttonDelete.setVisibility(View.VISIBLE);
////
////
////            buttonDelete.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    delete(classId);
////                }
////            });
////        }
////
////        buttonSave.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                saveClass();
////                Intent intent = new Intent(EditClassActivity.this, MainActivity.class);
////                startActivity(intent);
////            }
////        });
////    }
////
////
////    private void loadCourses() {
////        SQLiteDatabase db = dbHelper.getReadableDatabase();
////        Cursor cursor = db.query(DbHelper.TABLE_COURSE, null, null, null, null, null, null);
////
////        ArrayList<String> courseDescriptions = new ArrayList<>();
////        courseIds = new ArrayList<>();
////
////        while (cursor.moveToNext()) {
////            String id = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_COURSE_ID));
////            String courseName = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_COURSE_NAME));
////            Log.d("DBase", id);
////            courseIds.add(id);
////            courseDescriptions.add(courseName);
////        }
////        cursor.close();
////
////        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseDescriptions);
////        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        spinnerCourses.setAdapter(adapter);
////    }
////
////
////    private void loadClassDetails(String classId) {
////        YogaClass yogaClass = dbHelper.getClassById(classId);
////
////        editName.setText(yogaClass.getName());
////
////        Log.d("Class", yogaClass.getCourseId());
////
////        int coursePosition = courseIds.indexOf(yogaClass.getCourseId());
////
////        Log.d("Class", "" + coursePosition);
////
////        spinnerCourses.setSelection(coursePosition);
////        editDate.setText(yogaClass.getDate());
////        editTeacher.setText(yogaClass.getTeacher());
////        editComment.setText(yogaClass.getComment());
////
////
////    }
////
////
////    private void saveClass() {
////        String name = editName.getText().toString();
////        String date = editDate.getText().toString();
////        String teacher = editTeacher.getText().toString();
////        String comment = editComment.getText().toString();
////
////        boolean isValid = true;
////
////        if (name.isEmpty()) {
////            errorName.setVisibility(View.VISIBLE);
////            isValid = false;
////        } else {
////            errorName.setVisibility(View.GONE);
////        }
////
////        if (date.isEmpty()) {
////            errorDate.setVisibility(View.VISIBLE);
////            isValid = false;
////        } else {
////            errorDate.setVisibility(View.GONE);
////        }
////
////        if (teacher.isEmpty()) {
////            errorTeacher.setVisibility(View.VISIBLE);
////            isValid = false;
////        } else {
////            errorTeacher.setVisibility(View.GONE);
////        }
////
////        if (!isValid) {
////            return;
////        }
////
////        if (classId != null) {
//////            Update class
////            SQLiteDatabase db = dbHelper.getWritableDatabase();
////            ContentValues values = new ContentValues();
////            values.put(DbHelper.COLUMN_CLASS_ID, classId);
////            values.put(DbHelper.COLUMN_CLASS_NAME, name);
////            values.put(DbHelper.COLUMN_CLASS_COURSE_ID, courseId);
////            values.put(DbHelper.COLUMN_CLASS_DATE, date);
////            values.put(DbHelper.COLUMN_CLASS_TEACHER, teacher);
////            values.put(DbHelper.COLUMN_CLASS_COMMENT, comment);
////            YogaClass newClass = new YogaClass(name, courseId, date, teacher, comment);
////            newClass.setId(classId);
////            db.update(DbHelper.TABLE_CLASS, values, DbHelper.COLUMN_CLASS_ID + "=?", new String[]{String.valueOf(classId)});
////            dbHelper.saveClassFirebase(classId, newClass);
////            finish();
////        } else {
////            SQLiteDatabase db = dbHelper.getWritableDatabase();
////            ContentValues values = new ContentValues();
////            values.put(DbHelper.COLUMN_CLASS_COURSE_ID, courseId);
////            values.put(DbHelper.COLUMN_CLASS_DATE, date);
////            values.put(DbHelper.COLUMN_CLASS_NAME, name);
////            values.put(DbHelper.COLUMN_CLASS_TEACHER, teacher);
////            values.put(DbHelper.COLUMN_CLASS_COMMENT, comment);
////
////            YogaClass newClass = new YogaClass(name, courseId, date, teacher, comment);
////
////            String newId = dbHelper.saveClassFirebase(newClass);
////            values.put(DbHelper.COLUMN_CLASS_ID, newId);
////
////            Log.d("Class", values.toString());
////            // Insert new class
////            db.insert(DbHelper.TABLE_CLASS, null, values);
////            finish();
////        }
////    }
////
////    private void delete(String classId) {
////        SQLiteDatabase db = dbHelper.getWritableDatabase();
////
////        AlertDialog.Builder builder = new AlertDialog.Builder(EditClassActivity.this);
////        builder.setTitle("Confirm Delete");
////        builder.setMessage("Are you sure that you want to delete this class?");
////
////        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////                db.delete(DbHelper.TABLE_CLASS, DbHelper.COLUMN_CLASS_ID + "=?", new String[]{String.valueOf(classId)});
////                dbHelper.deleteClassFirebase(classId);
////                Intent intent = new Intent(EditClassActivity.this, MainActivity.class);
////                startActivity(intent);
////            }
////        });
////
////        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////                dialog.dismiss();  // Simply close the dialog
////            }
////        });
////
////        AlertDialog dialog = builder.create();
////        dialog.show();
////    }
////}
//


////code version 2.0
//package com.example.yogaapplication.views;
//
//import android.app.AlertDialog;
//import android.content.ContentValues;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.yogaapplication.DbHelper;
//import com.example.yogaapplication.MainActivity;
//import com.example.yogaapplication.R;
//import com.example.yogaapplication.entity.YogaClass;
//
//import java.util.ArrayList;
//
//public class EditClassActivity extends AppCompatActivity {
//
//    private Spinner spinnerCourses;
//    private EditText editName, editDate, editTeacher, editComment;
//    private TextView errorName, errorDate, errorTeacher;
//    private Button buttonSave, buttonDelete;
//    private String classId;
//    private String courseId;
//    private ArrayList<String> courseIds;
//    private DbHelper dbHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_class);
//
//        // Initialize views
//        dbHelper = new DbHelper(this);
//        editName = findViewById(R.id.edit_name);
//        errorName = findViewById(R.id.error_name);
//        spinnerCourses = findViewById(R.id.spinner_course);
//        editDate = findViewById(R.id.edit_date);
//        editTeacher = findViewById(R.id.edit_teacher);
//        editComment = findViewById(R.id.edit_comment);
//        errorDate = findViewById(R.id.error_date);
//        errorTeacher = findViewById(R.id.error_teacher);
//        buttonSave = findViewById(R.id.button_save_class);
//
//        Intent intent = getIntent();
//        classId = intent.getStringExtra("CLASS_ID");
//
//        loadCourses();
//
//        spinnerCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                courseId = courseIds.get(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//        if (classId != null) {
//            loadClassDetails(classId);
//            buttonDelete = findViewById(R.id.button_delete);
//            buttonDelete.setVisibility(View.VISIBLE);
//
//            buttonDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    delete(classId);
//                }
//            });
//        }
//
//        buttonSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveClass();
//            }
//        });
//    }
//
//    private void loadCourses() {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.query(DbHelper.TABLE_COURSE, null, null, null, null, null, null);
//
//        ArrayList<String> courseDescriptions = new ArrayList<>();
//        courseIds = new ArrayList<>();
//
//        while (cursor.moveToNext()) {
//            String id = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_COURSE_ID));
//            String courseName = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_COURSE_NAME));
//            courseIds.add(id);
//            courseDescriptions.add(courseName);
//        }
//        cursor.close();
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseDescriptions);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCourses.setAdapter(adapter);
//    }
//
//    private void loadClassDetails(String classId) {
//        YogaClass yogaClass = dbHelper.getClassById(classId);
//
//        editName.setText(yogaClass.getName());
//
//        int coursePosition = courseIds.indexOf(yogaClass.getCourseId());
//
//        spinnerCourses.setSelection(coursePosition);
//        editDate.setText(yogaClass.getDate());
//        editTeacher.setText(yogaClass.getTeacher());
//        editComment.setText(yogaClass.getComment());
//    }
//
//    private void saveClass() {
//        String name = editName.getText().toString().trim();
//        String date = editDate.getText().toString().trim();
//        String teacher = editTeacher.getText().toString().trim();
//        String comment = editComment.getText().toString().trim();
//
//        if (validate()) {
//            if (classId != null) {
//                // Update class
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put(DbHelper.COLUMN_CLASS_ID, classId);
//                values.put(DbHelper.COLUMN_CLASS_NAME, name);
//                values.put(DbHelper.COLUMN_CLASS_COURSE_ID, courseId);
//                values.put(DbHelper.COLUMN_CLASS_DATE, date);
//                values.put(DbHelper.COLUMN_CLASS_TEACHER, teacher);
//                values.put(DbHelper.COLUMN_CLASS_COMMENT, comment);
//
//                YogaClass newClass = new YogaClass(name, courseId, date, teacher, comment);
//                newClass.setId(classId);
//                int rowsAffected = db.update(DbHelper.TABLE_CLASS, values, DbHelper.COLUMN_CLASS_ID + "=?", new String[]{String.valueOf(classId)});
//                dbHelper.saveClassFirebase(classId, newClass);
//
//                if (rowsAffected > 0) {
//                    Toast.makeText(this, "Class updated successfully!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "Error updating class.", Toast.LENGTH_SHORT).show();
//                }
//
//                finish();
//            } else {
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put(DbHelper.COLUMN_CLASS_COURSE_ID, courseId);
//                values.put(DbHelper.COLUMN_CLASS_DATE, date);
//                values.put(DbHelper.COLUMN_CLASS_NAME, name);
//                values.put(DbHelper.COLUMN_CLASS_TEACHER, teacher);
//                values.put(DbHelper.COLUMN_CLASS_COMMENT, comment);
//
//                YogaClass newClass = new YogaClass(name, courseId, date, teacher, comment);
//
//                String newId = dbHelper.saveClassFirebase(newClass);
//                newClass.setId(newId);
//                values.put(DbHelper.COLUMN_CLASS_ID, newId);
//
//                // Insert new class
//                long newRowId = db.insert(DbHelper.TABLE_CLASS, null, values);
//
//                if (newRowId != -1) {
//                    Toast.makeText(this, "Class added successfully!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "Error adding class.", Toast.LENGTH_SHORT).show();
//                }
//
//                finish();
//            }
//        }
//    }
//
//    private boolean validate() {
//        boolean isValid = true;
//
//        if (editName.getText().toString().trim().isEmpty()) {
//            errorName.setVisibility(View.VISIBLE);
//            errorName.setText("Please enter the class name.");
//            isValid = false;
//        } else {
//            errorName.setVisibility(View.GONE);
//        }
//
//        if (editDate.getText().toString().trim().isEmpty()) {
//            errorDate.setVisibility(View.VISIBLE);
//            errorDate.setText("Please enter the date.");
//            isValid = false;
//        } else {
//            errorDate.setVisibility(View.GONE);
//        }
//
//        if (editTeacher.getText().toString().trim().isEmpty()) {
//            errorTeacher.setVisibility(View.VISIBLE);
//            errorTeacher.setText("Please enter the teacher's name.");
//            isValid = false;
//        } else {
//            errorTeacher.setVisibility(View.GONE);
//        }
//
//        return isValid;
//    }
//
//    private void delete(String classId) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(EditClassActivity.this);
//        builder.setTitle("Confirm Delete");
//        builder.setMessage("Are you sure that you want to delete this class?");
//
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                int rowsDeleted = db.delete(DbHelper.TABLE_CLASS, DbHelper.COLUMN_CLASS_ID + "=?", new String[]{String.valueOf(classId)});
//                dbHelper.deleteClassFirebase(classId);
//
//                if (rowsDeleted > 0) {
//                    Toast.makeText(EditClassActivity.this, "Class deleted successfully!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(EditClassActivity.this, "Error deleting class.", Toast.LENGTH_SHORT).show();
//                }
//
//                Intent intent = new Intent(EditClassActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//}

//code version 3.0
package com.example.yogaapplication.views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yogaapplication.DbHelper;
import com.example.yogaapplication.MainActivity;
import com.example.yogaapplication.R;
import com.example.yogaapplication.entity.Course;
import com.example.yogaapplication.entity.YogaClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;

public class EditClassActivity extends AppCompatActivity {

    private Spinner spinnerCourses;
    private EditText editName, editDate, editTeacher, editComment;
    private TextView errorName, errorDate, errorTeacher;
    private Button buttonSave, buttonDelete;
    private String classId;
    private String courseId;
    private ArrayList<String> courseIds;
    private DbHelper dbHelper;

    // Date format used throughout the app
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class);

        // Initialize views
        dbHelper = new DbHelper(this);
        editName = findViewById(R.id.edit_name);
        errorName = findViewById(R.id.error_name);
        spinnerCourses = findViewById(R.id.spinner_course);
        editDate = findViewById(R.id.edit_date);
        editTeacher = findViewById(R.id.edit_teacher);
        editComment = findViewById(R.id.edit_comment);
        errorDate = findViewById(R.id.error_date);
        errorTeacher = findViewById(R.id.error_teacher);
        buttonSave = findViewById(R.id.button_save_class);

        Intent intent = getIntent();
        classId = intent.getStringExtra("CLASS_ID");

        loadCourses();

        spinnerCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                courseId = courseIds.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        if (classId != null) {
            loadClassDetails(classId);
            buttonDelete = findViewById(R.id.button_delete);
            buttonDelete.setVisibility(View.VISIBLE);

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete(classId);
                }
            });
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveClass();
            }
        });

        // Set up DatePickerDialog for editDate
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    private void loadCourses() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DbHelper.TABLE_COURSE, null, null, null, null, null, null);

        ArrayList<String> courseDescriptions = new ArrayList<>();
        courseIds = new ArrayList<>();

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_COURSE_ID));
            String courseName = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_COURSE_NAME));
            courseIds.add(id);
            courseDescriptions.add(courseName);
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseDescriptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourses.setAdapter(adapter);
    }

    private void loadClassDetails(String classId) {
        YogaClass yogaClass = dbHelper.getClassById(classId);

        editName.setText(yogaClass.getName());

        int coursePosition = courseIds.indexOf(yogaClass.getCourseId());
        spinnerCourses.setSelection(coursePosition);

        editDate.setText(yogaClass.getDate());
        editTeacher.setText(yogaClass.getTeacher());
        editComment.setText(yogaClass.getComment());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();

        // If a date is already selected, show that date in the picker
        String currentDateStr = editDate.getText().toString().trim();
        if (!currentDateStr.isEmpty()) {
            try {
                Date currentDate = dateFormat.parse(currentDateStr);
                calendar.setTime(currentDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(EditClassActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format the date and set it to the editDate EditText
                    String dateStr = String.format(Locale.getDefault(), "%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                    editDate.setText(dateStr);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void saveClass() {
        String name = editName.getText().toString().trim();
        String dateStr = editDate.getText().toString().trim();
        String teacher = editTeacher.getText().toString().trim();
        String comment = editComment.getText().toString().trim();

        if (!validate()) {
            return;
        }

        // Retrieve the day of the week for the selected course
        Course selectedCourse = dbHelper.getCourseById(courseId);
        if (selectedCourse == null) {
            Toast.makeText(this, "Selected course not found.", Toast.LENGTH_SHORT).show();
            return;
        }
        String expectedDayOfWeek = selectedCourse.getDayOfWeek();

        // Validate that the entered date matches the day of the week
        if (!validateDate(dateStr, expectedDayOfWeek)) {
            errorDate.setVisibility(View.VISIBLE);
            errorDate.setText("The date must match the course's day of the week: " + expectedDayOfWeek + ".");
            return;
        } else {
            errorDate.setVisibility(View.GONE);
        }

        // Proceed to save the class if validation passes
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_CLASS_NAME, name);
        values.put(DbHelper.COLUMN_CLASS_COURSE_ID, courseId);
        values.put(DbHelper.COLUMN_CLASS_DATE, dateStr);
        values.put(DbHelper.COLUMN_CLASS_TEACHER, teacher);
        values.put(DbHelper.COLUMN_CLASS_COMMENT, comment);

        YogaClass newClass = new YogaClass(name, courseId, dateStr, teacher, comment);

        if (classId != null) {
            // Update class
            newClass.setId(classId);
            values.put(DbHelper.COLUMN_CLASS_ID, classId);

            int rowsAffected = db.update(DbHelper.TABLE_CLASS, values, DbHelper.COLUMN_CLASS_ID + "=?", new String[]{classId});
            dbHelper.saveClassFirebase(classId, newClass);

            if (rowsAffected > 0) {
                Toast.makeText(this, "Class updated successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error updating class.", Toast.LENGTH_SHORT).show();
            }

        } else {
            // Add new class
            String newId = dbHelper.saveClassFirebase(newClass);
            newClass.setId(newId);
            values.put(DbHelper.COLUMN_CLASS_ID, newId);

            long newRowId = db.insert(DbHelper.TABLE_CLASS, null, values);

            if (newRowId != -1) {
                Toast.makeText(this, "Class added successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error adding class.", Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }

    private boolean validate() {
        boolean isValid = true;

        if (editName.getText().toString().trim().isEmpty()) {
            errorName.setVisibility(View.VISIBLE);
            errorName.setText("Please enter the class name.");
            isValid = false;
        } else {
            errorName.setVisibility(View.GONE);
        }

        if (editDate.getText().toString().trim().isEmpty()) {
            errorDate.setVisibility(View.VISIBLE);
            errorDate.setText("Please enter the date.");
            isValid = false;
        } else {
            errorDate.setVisibility(View.GONE);
        }

        if (editTeacher.getText().toString().trim().isEmpty()) {
            errorTeacher.setVisibility(View.VISIBLE);
            errorTeacher.setText("Please enter the teacher's name.");
            isValid = false;
        } else {
            errorTeacher.setVisibility(View.GONE);
        }

        return isValid;
    }

    private boolean validateDate(String dateInput, String expectedDayOfWeek) {
        try {
            // Parse the date
            Date date = dateFormat.parse(dateInput);

            // Get the actual day of the week for the entered date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            // Map Calendar.DAY_OF_WEEK to a day name
            String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
            String actualDayOfWeek = days[dayOfWeek - 1];

            // Compare with the expected day of the week
            return actualDayOfWeek.equalsIgnoreCase(expectedDayOfWeek);

        } catch (ParseException e) {
            // If the date format is invalid, return false
            e.printStackTrace();
            errorDate.setVisibility(View.VISIBLE);
            errorDate.setText("Invalid date format. Please use dd/MM/yyyy.");
            return false;
        }
    }

    private void delete(String classId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        AlertDialog.Builder builder = new AlertDialog.Builder(EditClassActivity.this);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure that you want to delete this class?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int rowsDeleted = db.delete(DbHelper.TABLE_CLASS, DbHelper.COLUMN_CLASS_ID + "=?", new String[]{classId});
                dbHelper.deleteClassFirebase(classId);

                if (rowsDeleted > 0) {
                    Toast.makeText(EditClassActivity.this, "Class deleted successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditClassActivity.this, "Error deleting class.", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(EditClassActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

