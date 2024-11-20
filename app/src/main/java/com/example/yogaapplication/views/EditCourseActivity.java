//code cu
//package com.example.yogaapplication.views;
//
//import android.app.AlertDialog;
//import android.content.ContentValues;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.yogaapplication.DbHelper;
//import com.example.yogaapplication.MainActivity;
//import com.example.yogaapplication.R;
//import com.example.yogaapplication.entity.Course;
//
//public class EditCourseActivity extends AppCompatActivity {
//
//    private EditText editName, editTimeStart, editCapacity, editDuration, editPrice, editDescription;
//    private Spinner spinnerDayOfWeek, spinnerClassType;
//    private DbHelper dbHelper;
//    private String courseId;
//    Button buttonSave, buttonDelete;
//
//    private TextView errorName;
//    private TextView errorDayOfTheWeek;
//    private TextView errorTimeStart;
//    private TextView errorCapacity;
//    private TextView errorDuration;
//    private TextView errorPrice;
//    private TextView errorClassType;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_course);
//
//
//        editName = findViewById(R.id.edit_name);
//        spinnerDayOfWeek = findViewById(R.id.spinner_day_of_the_week);
//        editTimeStart = findViewById(R.id.edit_time_start);
//        editCapacity = findViewById(R.id.edittext_capacity);
//        editDuration = findViewById(R.id.edittext_duration);
//        editPrice = findViewById(R.id.edittext_price);
//        spinnerClassType = findViewById(R.id.spinner_class_type);
//        editDescription = findViewById(R.id.edittext_description);
//        buttonSave = findViewById(R.id.button_save);
//
//
//        errorName = findViewById(R.id.error_name);
//        errorDayOfTheWeek = findViewById(R.id.error_day_of_the_week);
//        errorTimeStart = findViewById(R.id.error_time_start);
//        errorCapacity = findViewById(R.id.error_capacity);
//        errorDuration = findViewById(R.id.error_duration);
//        errorPrice = findViewById(R.id.error_price);
//        errorClassType = findViewById(R.id.error_class_type);
//
//
//        // Populate spinners
//        ArrayAdapter<CharSequence> dayOfWeekAdapter = ArrayAdapter.createFromResource(this,
//                R.array.days_of_week_array, android.R.layout.simple_spinner_item);
//        dayOfWeekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerDayOfWeek.setAdapter(dayOfWeekAdapter);
//
//        ArrayAdapter<CharSequence> classTypeAdapter = ArrayAdapter.createFromResource(this,
//                R.array.class_type_array, android.R.layout.simple_spinner_item);
//        classTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerClassType.setAdapter(classTypeAdapter);
//
//        dbHelper = new DbHelper(this);
//
//        // Get the data passed from the previous activity
//        courseId = getIntent().getStringExtra("COURSE_ID");
//
//        if (courseId != null) {
//            populateFields(courseId);
//            buttonDelete = findViewById(R.id.button_delete);
//            buttonDelete.setVisibility(View.VISIBLE);
//
//            buttonDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    delete(courseId);
//                }
//            });
//        }
//
//
//        buttonSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveCourse();
//                Intent intent = new Intent(EditCourseActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//    }
//
//    private void populateFields(String courseId) {
//        // Simulated course loading; replace with actual data retrieval
//        Course updateCourse = dbHelper.getCourseById(courseId);
//        // Todo FETCH DETAIL COURSE AND UPDATE
//
//        editName.setText(updateCourse.getName());
//        spinnerDayOfWeek.setSelection(((ArrayAdapter) spinnerDayOfWeek.getAdapter()).getPosition(updateCourse.getDayOfWeek()));
//        editTimeStart.setText(updateCourse.getTimeStart());
//        editCapacity.setText(String.valueOf(updateCourse.getCapacity()));
//        editDuration.setText(String.valueOf(updateCourse.getDuration()));
//        editPrice.setText(String.valueOf(updateCourse.getPrice()));
//        spinnerClassType.setSelection(((ArrayAdapter) spinnerClassType.getAdapter()).getPosition(updateCourse.getClassType()));
//        editDescription.setText(updateCourse.getDescription());
//    }
//
//
//    private void saveCourse() {
//        String name = editName.getText().toString();
//        String dayOfWeek = spinnerDayOfWeek.getSelectedItem().toString();
//        String timeStart = editTimeStart.getText().toString();
//        int capacity = Integer.parseInt(editCapacity.getText().toString());
//        float duration = Float.parseFloat(editDuration.getText().toString());
//        float price = Float.parseFloat(editPrice.getText().toString());
//        String classType = spinnerClassType.getSelectedItem().toString();
//        String description = editDescription.getText().toString();
//
//        if (validate()) {
//            SQLiteDatabase db = dbHelper.getWritableDatabase();
//            ContentValues values = new ContentValues();
//            values.put(DbHelper.COLUMN_COURSE_ID, courseId);
//            values.put(DbHelper.COLUMN_COURSE_NAME, name);
//            values.put(DbHelper.COLUMN_COURSE_DAY_OF_WEEK, dayOfWeek);
//            values.put(DbHelper.COLUMN_COURSE_TIME_START, timeStart);
//            values.put(DbHelper.COLUMN_COURSE_CAPACITY, capacity);
//            values.put(DbHelper.COLUMN_COURSE_DURATION, duration);
//            values.put(DbHelper.COLUMN_COURSE_PRICE, price);
//            values.put(DbHelper.COLUMN_COURSE_CLASS_TYPE, classType);
//            values.put(DbHelper.COLUMN_COURSE_DESCRIPTION, description);
//
//            Course newCourse = new Course(name, dayOfWeek, timeStart, capacity, duration, price, classType, description);
//
//            //code cu
////            if (courseId != null) {
////                // Update existing course
////                newCourse.setId(courseId);
////                db.update(DbHelper.TABLE_COURSE, values,
////                        DbHelper.COLUMN_COURSE_ID + "=?", new String[]{String.valueOf(courseId)});
////
////                dbHelper.saveCourseFirebase(courseId, newCourse);
////                finish();
////            }
//
//            //code moi
//            if (courseId != null) {
//                // Update existing course
//                newCourse.setId(courseId);
//                int rowsAffected = db.update(DbHelper.TABLE_COURSE, values,
//                        DbHelper.COLUMN_COURSE_ID + "=?", new String[]{String.valueOf(courseId)});
//
//                dbHelper.saveCourseFirebase(courseId, newCourse);
//
//                if (rowsAffected > 0) {
//                    Toast.makeText(this, "Course updated successfully!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "Error updating course.", Toast.LENGTH_SHORT).show();
//                }
//
//                finish();
//            }
//
//            else {
//                String newId = dbHelper.saveCourseFirebase(newCourse);
//                values.put(DbHelper.COLUMN_COURSE_ID, newId);
//
//                // Insert the new row, returning the primary key value of the new row
//                long newRowId = db.insert(DbHelper.TABLE_COURSE, null, values);
//
//                if (newRowId != -1) {
//                    Toast.makeText(this, "Yoga course saved!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "Error saving yoga course", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//
//    }
//
//
//    private boolean validate() {
//        boolean isValid = true;
//
//        // Validate inputs
//        if (editName.getText().toString().equals("")) {
//            errorName.setText("Please enter the course name.");
//            errorName.setVisibility(View.VISIBLE);
//            isValid = false;
//        } else {
//            errorName.setVisibility(View.GONE);
//        }
//
//        if (spinnerDayOfWeek.getSelectedItem().toString().equals("")) {
//            errorDayOfTheWeek.setText("Please select a day of the week.");
//            errorDayOfTheWeek.setVisibility(View.VISIBLE);
//            isValid = false;
//        } else {
//            errorDayOfTheWeek.setVisibility(View.GONE);
//        }
//
//        if (editTimeStart.getText().toString().isEmpty()) {
//            errorTimeStart.setText("Please enter the start time.");
//            errorTimeStart.setVisibility(View.VISIBLE);
//            isValid = false;
//        } else {
//            errorTimeStart.setVisibility(View.GONE);
//        }
//
//        if (editCapacity.getText().toString().isEmpty()) {
//            errorCapacity.setText("Please enter the capacity.");
//            errorCapacity.setVisibility(View.VISIBLE);
//            isValid = false;
//        } else {
//            errorCapacity.setVisibility(View.GONE);
//        }
//
//        if (editDuration.getText().toString().isEmpty()) {
//            errorDuration.setText("Please enter the duration.");
//            errorDuration.setVisibility(View.VISIBLE);
//            isValid = false;
//        } else {
//            errorDuration.setVisibility(View.GONE);
//        }
//
//        if (editPrice.getText().toString().isEmpty()) {
//            errorPrice.setText("Please enter the price.");
//            errorPrice.setVisibility(View.VISIBLE);
//            isValid = false;
//        } else {
//            errorPrice.setVisibility(View.GONE);
//        }
//
//        if (spinnerClassType.getSelectedItem().toString().equals("")) {
//            errorClassType.setText("Please select a class type.");
//            errorClassType.setVisibility(View.VISIBLE);
//            isValid = false;
//        } else {
//            errorClassType.setVisibility(View.GONE);
//        }
//        return isValid;
//    }
//
////    private void delete(String courseId) {
////        SQLiteDatabase db = dbHelper.getWritableDatabase();
////
////        AlertDialog.Builder builder = new AlertDialog.Builder(EditCourseActivity.this);
////        builder.setTitle("Confirm Delete");
////        builder.setMessage("Are you sure you want to delete this course?");
////
////        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////                db.delete(DbHelper.TABLE_COURSE, DbHelper.COLUMN_COURSE_ID + "=?", new String[]{String.valueOf(courseId)});
////                dbHelper.deleteCourseFirebase(courseId);
////                Intent intent = new Intent(EditCourseActivity.this, MainActivity.class);
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
//
//
//    private void delete(String courseId) {
//        // Sử dụng DbHelper để mở cơ sở dữ liệu
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // Kiểm tra nếu Course có chứa YogaClass
//        if (dbHelper.hasYogaClasses(courseId)) {
//            // Nếu Course có YogaClass, hiển thị thông báo cho người dùng
//            AlertDialog.Builder builder = new AlertDialog.Builder(EditCourseActivity.this);
//            builder.setTitle("You can't delete");
//            builder.setMessage("This course that contains YogaClass insides, please delete YogaClass before deleting this course");
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();  // Đóng thông báo
//                }
//            });
//            AlertDialog dialog = builder.create();
//            dialog.show();
//        } else {
//            // Nếu không có lớp học nhỏ, tiến hành xóa Course
//            AlertDialog.Builder builder = new AlertDialog.Builder(EditCourseActivity.this);
//            builder.setTitle("Delete Approvement");
//            builder.setMessage("Are you sure to delete this course?");
//            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    // Thực hiện việc xóa khỏi SQLite và Firebase
//                    db.delete(DbHelper.TABLE_COURSE, DbHelper.COLUMN_COURSE_ID + "=?", new String[]{String.valueOf(courseId)});
//                    dbHelper.deleteCourseFirebase(courseId);
//                    // Quay lại MainActivity sau khi xóa thành công
//                    Intent intent = new Intent(EditCourseActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }
//            });
//            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();  // Đóng thông báo
//                }
//            });
//
//            AlertDialog dialog = builder.create();
//            dialog.show();
//        }
//    }
//
//}

//code moi
package com.example.yogaapplication.views;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class EditCourseActivity extends AppCompatActivity {

    private EditText editName, editTimeStart, editCapacity, editDuration, editPrice, editDescription;
    private Spinner spinnerDayOfWeek, spinnerClassType;
    private DbHelper dbHelper;
    private String courseId;
    private Button buttonSave, buttonDelete;

    private TextView errorName;
    private TextView errorDayOfTheWeek;
    private TextView errorTimeStart;
    private TextView errorCapacity;
    private TextView errorDuration;
    private TextView errorPrice;
    private TextView errorClassType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        // Initialize views
        editName = findViewById(R.id.edit_name);
        spinnerDayOfWeek = findViewById(R.id.spinner_day_of_the_week);
        editTimeStart = findViewById(R.id.edit_time_start);
        editCapacity = findViewById(R.id.edittext_capacity);
        editDuration = findViewById(R.id.edittext_duration);
        editPrice = findViewById(R.id.edittext_price);
        spinnerClassType = findViewById(R.id.spinner_class_type);
        editDescription = findViewById(R.id.edittext_description);
        buttonSave = findViewById(R.id.button_save);

        errorName = findViewById(R.id.error_name);
        errorDayOfTheWeek = findViewById(R.id.error_day_of_the_week);
        errorTimeStart = findViewById(R.id.error_time_start);
        errorCapacity = findViewById(R.id.error_capacity);
        errorDuration = findViewById(R.id.error_duration);
        errorPrice = findViewById(R.id.error_price);
        errorClassType = findViewById(R.id.error_class_type);

        // Populate spinners
        ArrayAdapter<CharSequence> dayOfWeekAdapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_week_array, android.R.layout.simple_spinner_item);
        dayOfWeekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDayOfWeek.setAdapter(dayOfWeekAdapter);

        ArrayAdapter<CharSequence> classTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.class_type_array, android.R.layout.simple_spinner_item);
        classTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClassType.setAdapter(classTypeAdapter);

        dbHelper = new DbHelper(this);

        // Get the data passed from the previous activity
        courseId = getIntent().getStringExtra("COURSE_ID");

        if (courseId != null) {
            populateFields(courseId);
            buttonDelete = findViewById(R.id.button_delete);
            buttonDelete.setVisibility(View.VISIBLE);

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete(courseId);
                }
            });
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCourse();
            }
        });
    }

    private void populateFields(String courseId) {
        // Fetch course details and update fields
        Course updateCourse = dbHelper.getCourseById(courseId);

        editName.setText(updateCourse.getName());
        spinnerDayOfWeek.setSelection(((ArrayAdapter) spinnerDayOfWeek.getAdapter()).getPosition(updateCourse.getDayOfWeek()));
        editTimeStart.setText(updateCourse.getTimeStart());
        editCapacity.setText(String.valueOf(updateCourse.getCapacity()));
        editDuration.setText(String.valueOf(updateCourse.getDuration()));
        editPrice.setText(String.valueOf(updateCourse.getPrice()));
        spinnerClassType.setSelection(((ArrayAdapter) spinnerClassType.getAdapter()).getPosition(updateCourse.getClassType()));
        editDescription.setText(updateCourse.getDescription());
    }

    private void saveCourse() {
        String name = editName.getText().toString().trim();
        String dayOfWeek = spinnerDayOfWeek.getSelectedItem().toString();
        String timeStart = editTimeStart.getText().toString().trim();
        String capacityStr = editCapacity.getText().toString().trim();
        String durationStr = editDuration.getText().toString().trim();
        String priceStr = editPrice.getText().toString().trim();
        String classType = spinnerClassType.getSelectedItem().toString();
        String description = editDescription.getText().toString().trim();

        if (validate()) {
            int capacity = Integer.parseInt(capacityStr);
            float duration = Float.parseFloat(durationStr);
            float price = Float.parseFloat(priceStr);

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DbHelper.COLUMN_COURSE_NAME, name);
            values.put(DbHelper.COLUMN_COURSE_DAY_OF_WEEK, dayOfWeek);
            values.put(DbHelper.COLUMN_COURSE_TIME_START, timeStart);
            values.put(DbHelper.COLUMN_COURSE_CAPACITY, capacity);
            values.put(DbHelper.COLUMN_COURSE_DURATION, duration);
            values.put(DbHelper.COLUMN_COURSE_PRICE, price);
            values.put(DbHelper.COLUMN_COURSE_CLASS_TYPE, classType);
            values.put(DbHelper.COLUMN_COURSE_DESCRIPTION, description);

            Course newCourse = new Course(name, dayOfWeek, timeStart, capacity, duration, price, classType, description);

            if (courseId != null) {
                // Update existing course
                newCourse.setId(courseId);
                int rowsAffected = db.update(DbHelper.TABLE_COURSE, values,
                        DbHelper.COLUMN_COURSE_ID + "=?", new String[]{String.valueOf(courseId)});

                dbHelper.saveCourseFirebase(courseId, newCourse);

                if (rowsAffected > 0) {
                    Toast.makeText(this, "Course updated successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error updating course", Toast.LENGTH_SHORT).show();
                }

                finish();
            } else {
                String newId = dbHelper.saveCourseFirebase(newCourse);
                newCourse.setId(newId);
                values.put(DbHelper.COLUMN_COURSE_ID, newId);

                long newRowId = db.insert(DbHelper.TABLE_COURSE, null, values);

                if (newRowId != -1) {
                    Toast.makeText(this, "Yoga course saved!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error saving yoga course", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        }
    }

    private boolean validate() {
        boolean isValid = true;

        // Validate name
        if (editName.getText().toString().trim().isEmpty()) {
            errorName.setText("Please enter the course name.");
            errorName.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorName.setVisibility(View.GONE);
        }

        // Validate day of the week
        if (spinnerDayOfWeek.getSelectedItem().toString().trim().isEmpty()) {
            errorDayOfTheWeek.setText("Please select a day of the week.");
            errorDayOfTheWeek.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorDayOfTheWeek.setVisibility(View.GONE);
        }

        // Validate time start
        if (editTimeStart.getText().toString().trim().isEmpty()) {
            errorTimeStart.setText("Please enter the start time.");
            errorTimeStart.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorTimeStart.setVisibility(View.GONE);
        }

        // Validate capacity
        String capacityStr = editCapacity.getText().toString().trim();
        if (capacityStr.isEmpty()) {
            errorCapacity.setText("Please enter the capacity.");
            errorCapacity.setVisibility(View.VISIBLE);
            isValid = false;
        } else if (!capacityStr.matches("\\d+")) {
            errorCapacity.setText("Capacity must be a valid number.");
            errorCapacity.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorCapacity.setVisibility(View.GONE);
        }

        // Validate duration
        String durationStr = editDuration.getText().toString().trim();
        if (durationStr.isEmpty()) {
            errorDuration.setText("Please enter the duration.");
            errorDuration.setVisibility(View.VISIBLE);
            isValid = false;
        } else if (!durationStr.matches("\\d+(\\.\\d+)?")) {
            errorDuration.setText("Duration must be a valid number.");
            errorDuration.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorDuration.setVisibility(View.GONE);
        }

        // Validate price
        String priceStr = editPrice.getText().toString().trim();
        if (priceStr.isEmpty()) {
            errorPrice.setText("Please enter the price.");
            errorPrice.setVisibility(View.VISIBLE);
            isValid = false;
        } else if (!priceStr.matches("\\d+(\\.\\d+)?")) {
            errorPrice.setText("Price must be a valid number.");
            errorPrice.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorPrice.setVisibility(View.GONE);
        }

        // Validate class type
        if (spinnerClassType.getSelectedItem().toString().trim().isEmpty()) {
            errorClassType.setText("Please select a class type.");
            errorClassType.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorClassType.setVisibility(View.GONE);
        }

        return isValid;
    }

    private void delete(String courseId) {
        // Use DbHelper to open database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Check if Course has YogaClass
        if (dbHelper.hasYogaClasses(courseId)) {
            // If Course has YogaClass, show message to user
            AlertDialog.Builder builder = new AlertDialog.Builder(EditCourseActivity.this);
            builder.setTitle("Cannot Delete");
            builder.setMessage("This course contains Yoga Classes inside. Please delete Yoga Classes before deleting this course.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            // If no classes, proceed to delete Course
            AlertDialog.Builder builder = new AlertDialog.Builder(EditCourseActivity.this);
            builder.setTitle("Delete Confirmation");
            builder.setMessage("Are you sure you want to delete this course?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int rowsDeleted = db.delete(DbHelper.TABLE_COURSE, DbHelper.COLUMN_COURSE_ID + "=?", new String[]{String.valueOf(courseId)});
                    dbHelper.deleteCourseFirebase(courseId);

                    if (rowsDeleted > 0) {
                        Toast.makeText(EditCourseActivity.this, "Course deleted successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditCourseActivity.this, "Error deleting course.", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(EditCourseActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
