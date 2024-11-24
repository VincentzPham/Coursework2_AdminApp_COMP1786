////code version 1.0
////package com.example.yogaapplication.views;
////
////import android.app.AlertDialog;
////import android.content.ContentValues;
////import android.content.DialogInterface;
////import android.content.Intent;
////import android.database.sqlite.SQLiteDatabase;
////import android.os.Bundle;
////import android.util.Log;
////import android.view.View;
////import android.widget.ArrayAdapter;
////import android.widget.Button;
////import android.widget.EditText;
////import android.widget.Spinner;
////import android.widget.TextView;
////import android.widget.Toast;
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
////import com.example.yogaapplication.entity.Course;
////
////public class EditCourseActivity extends AppCompatActivity {
////
////    private EditText editName, editTimeStart, editCapacity, editDuration, editPrice, editDescription;
////    private Spinner spinnerDayOfWeek, spinnerClassType;
////    private DbHelper dbHelper;
////    private String courseId;
////    Button buttonSave, buttonDelete;
////
////    private TextView errorName;
////    private TextView errorDayOfTheWeek;
////    private TextView errorTimeStart;
////    private TextView errorCapacity;
////    private TextView errorDuration;
////    private TextView errorPrice;
////    private TextView errorClassType;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_edit_course);
////
////
////        editName = findViewById(R.id.edit_name);
////        spinnerDayOfWeek = findViewById(R.id.spinner_day_of_the_week);
////        editTimeStart = findViewById(R.id.edit_time_start);
////        editCapacity = findViewById(R.id.edittext_capacity);
////        editDuration = findViewById(R.id.edittext_duration);
////        editPrice = findViewById(R.id.edittext_price);
////        spinnerClassType = findViewById(R.id.spinner_class_type);
////        editDescription = findViewById(R.id.edittext_description);
////        buttonSave = findViewById(R.id.button_save);
////
////
////        errorName = findViewById(R.id.error_name);
////        errorDayOfTheWeek = findViewById(R.id.error_day_of_the_week);
////        errorTimeStart = findViewById(R.id.error_time_start);
////        errorCapacity = findViewById(R.id.error_capacity);
////        errorDuration = findViewById(R.id.error_duration);
////        errorPrice = findViewById(R.id.error_price);
////        errorClassType = findViewById(R.id.error_class_type);
////
////
////        // Populate spinners
////        ArrayAdapter<CharSequence> dayOfWeekAdapter = ArrayAdapter.createFromResource(this,
////                R.array.days_of_week_array, android.R.layout.simple_spinner_item);
////        dayOfWeekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        spinnerDayOfWeek.setAdapter(dayOfWeekAdapter);
////
////        ArrayAdapter<CharSequence> classTypeAdapter = ArrayAdapter.createFromResource(this,
////                R.array.class_type_array, android.R.layout.simple_spinner_item);
////        classTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        spinnerClassType.setAdapter(classTypeAdapter);
////
////        dbHelper = new DbHelper(this);
////
////        // Get the data passed from the previous activity
////        courseId = getIntent().getStringExtra("COURSE_ID");
////
////        if (courseId != null) {
////            populateFields(courseId);
////            buttonDelete = findViewById(R.id.button_delete);
////            buttonDelete.setVisibility(View.VISIBLE);
////
////            buttonDelete.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    delete(courseId);
////                }
////            });
////        }
////
////
////        buttonSave.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                saveCourse();
////                Intent intent = new Intent(EditCourseActivity.this, MainActivity.class);
////                startActivity(intent);
////            }
////        });
////
////
////    }
////
////    private void populateFields(String courseId) {
////        // Simulated course loading; replace with actual data retrieval
////        Course updateCourse = dbHelper.getCourseById(courseId);
////        // Todo FETCH DETAIL COURSE AND UPDATE
////
////        editName.setText(updateCourse.getName());
////        spinnerDayOfWeek.setSelection(((ArrayAdapter) spinnerDayOfWeek.getAdapter()).getPosition(updateCourse.getDayOfWeek()));
////        editTimeStart.setText(updateCourse.getTimeStart());
////        editCapacity.setText(String.valueOf(updateCourse.getCapacity()));
////        editDuration.setText(String.valueOf(updateCourse.getDuration()));
////        editPrice.setText(String.valueOf(updateCourse.getPrice()));
////        spinnerClassType.setSelection(((ArrayAdapter) spinnerClassType.getAdapter()).getPosition(updateCourse.getClassType()));
////        editDescription.setText(updateCourse.getDescription());
////    }
////
////
////    private void saveCourse() {
////        String name = editName.getText().toString();
////        String dayOfWeek = spinnerDayOfWeek.getSelectedItem().toString();
////        String timeStart = editTimeStart.getText().toString();
////        int capacity = Integer.parseInt(editCapacity.getText().toString());
////        float duration = Float.parseFloat(editDuration.getText().toString());
////        float price = Float.parseFloat(editPrice.getText().toString());
////        String classType = spinnerClassType.getSelectedItem().toString();
////        String description = editDescription.getText().toString();
////
////        if (validate()) {
////            SQLiteDatabase db = dbHelper.getWritableDatabase();
////            ContentValues values = new ContentValues();
////            values.put(DbHelper.COLUMN_COURSE_ID, courseId);
////            values.put(DbHelper.COLUMN_COURSE_NAME, name);
////            values.put(DbHelper.COLUMN_COURSE_DAY_OF_WEEK, dayOfWeek);
////            values.put(DbHelper.COLUMN_COURSE_TIME_START, timeStart);
////            values.put(DbHelper.COLUMN_COURSE_CAPACITY, capacity);
////            values.put(DbHelper.COLUMN_COURSE_DURATION, duration);
////            values.put(DbHelper.COLUMN_COURSE_PRICE, price);
////            values.put(DbHelper.COLUMN_COURSE_CLASS_TYPE, classType);
////            values.put(DbHelper.COLUMN_COURSE_DESCRIPTION, description);
////
////            Course newCourse = new Course(name, dayOfWeek, timeStart, capacity, duration, price, classType, description);
////
////            //code cu
//////            if (courseId != null) {
//////                // Update existing course
//////                newCourse.setId(courseId);
//////                db.update(DbHelper.TABLE_COURSE, values,
//////                        DbHelper.COLUMN_COURSE_ID + "=?", new String[]{String.valueOf(courseId)});
//////
//////                dbHelper.saveCourseFirebase(courseId, newCourse);
//////                finish();
//////            }
////
////            //code moi
////            if (courseId != null) {
////                // Update existing course
////                newCourse.setId(courseId);
////                int rowsAffected = db.update(DbHelper.TABLE_COURSE, values,
////                        DbHelper.COLUMN_COURSE_ID + "=?", new String[]{String.valueOf(courseId)});
////
////                dbHelper.saveCourseFirebase(courseId, newCourse);
////
////                if (rowsAffected > 0) {
////                    Toast.makeText(this, "Course updated successfully!", Toast.LENGTH_SHORT).show();
////                } else {
////                    Toast.makeText(this, "Error updating course.", Toast.LENGTH_SHORT).show();
////                }
////
////                finish();
////            }
////
////            else {
////                String newId = dbHelper.saveCourseFirebase(newCourse);
////                values.put(DbHelper.COLUMN_COURSE_ID, newId);
////
////                // Insert the new row, returning the primary key value of the new row
////                long newRowId = db.insert(DbHelper.TABLE_COURSE, null, values);
////
////                if (newRowId != -1) {
////                    Toast.makeText(this, "Yoga course saved!", Toast.LENGTH_SHORT).show();
////                } else {
////                    Toast.makeText(this, "Error saving yoga course", Toast.LENGTH_SHORT).show();
////                }
////            }
////        }
////
////    }
////
////
////    private boolean validate() {
////        boolean isValid = true;
////
////        // Validate inputs
////        if (editName.getText().toString().equals("")) {
////            errorName.setText("Please enter the course name.");
////            errorName.setVisibility(View.VISIBLE);
////            isValid = false;
////        } else {
////            errorName.setVisibility(View.GONE);
////        }
////
////        if (spinnerDayOfWeek.getSelectedItem().toString().equals("")) {
////            errorDayOfTheWeek.setText("Please select a day of the week.");
////            errorDayOfTheWeek.setVisibility(View.VISIBLE);
////            isValid = false;
////        } else {
////            errorDayOfTheWeek.setVisibility(View.GONE);
////        }
////
////        if (editTimeStart.getText().toString().isEmpty()) {
////            errorTimeStart.setText("Please enter the start time.");
////            errorTimeStart.setVisibility(View.VISIBLE);
////            isValid = false;
////        } else {
////            errorTimeStart.setVisibility(View.GONE);
////        }
////
////        if (editCapacity.getText().toString().isEmpty()) {
////            errorCapacity.setText("Please enter the capacity.");
////            errorCapacity.setVisibility(View.VISIBLE);
////            isValid = false;
////        } else {
////            errorCapacity.setVisibility(View.GONE);
////        }
////
////        if (editDuration.getText().toString().isEmpty()) {
////            errorDuration.setText("Please enter the duration.");
////            errorDuration.setVisibility(View.VISIBLE);
////            isValid = false;
////        } else {
////            errorDuration.setVisibility(View.GONE);
////        }
////
////        if (editPrice.getText().toString().isEmpty()) {
////            errorPrice.setText("Please enter the price.");
////            errorPrice.setVisibility(View.VISIBLE);
////            isValid = false;
////        } else {
////            errorPrice.setVisibility(View.GONE);
////        }
////
////        if (spinnerClassType.getSelectedItem().toString().equals("")) {
////            errorClassType.setText("Please select a class type.");
////            errorClassType.setVisibility(View.VISIBLE);
////            isValid = false;
////        } else {
////            errorClassType.setVisibility(View.GONE);
////        }
////        return isValid;
////    }
////
//////    private void delete(String courseId) {
//////        SQLiteDatabase db = dbHelper.getWritableDatabase();
//////
//////        AlertDialog.Builder builder = new AlertDialog.Builder(EditCourseActivity.this);
//////        builder.setTitle("Confirm Delete");
//////        builder.setMessage("Are you sure you want to delete this course?");
//////
//////        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//////            @Override
//////            public void onClick(DialogInterface dialog, int which) {
//////                db.delete(DbHelper.TABLE_COURSE, DbHelper.COLUMN_COURSE_ID + "=?", new String[]{String.valueOf(courseId)});
//////                dbHelper.deleteCourseFirebase(courseId);
//////                Intent intent = new Intent(EditCourseActivity.this, MainActivity.class);
//////                startActivity(intent);
//////            }
//////        });
//////
//////        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//////            @Override
//////            public void onClick(DialogInterface dialog, int which) {
//////                dialog.dismiss();  // Simply close the dialog
//////            }
//////        });
//////
//////        AlertDialog dialog = builder.create();
//////        dialog.show();
//////    }
////
////
////    private void delete(String courseId) {
////        // Sử dụng DbHelper để mở cơ sở dữ liệu
////        SQLiteDatabase db = dbHelper.getWritableDatabase();
////
////        // Kiểm tra nếu Course có chứa YogaClass
////        if (dbHelper.hasYogaClasses(courseId)) {
////            // Nếu Course có YogaClass, hiển thị thông báo cho người dùng
////            AlertDialog.Builder builder = new AlertDialog.Builder(EditCourseActivity.this);
////            builder.setTitle("You can't delete");
////            builder.setMessage("This course that contains YogaClass insides, please delete YogaClass before deleting this course");
////            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialog, int which) {
////                    dialog.dismiss();  // Đóng thông báo
////                }
////            });
////            AlertDialog dialog = builder.create();
////            dialog.show();
////        } else {
////            // Nếu không có lớp học nhỏ, tiến hành xóa Course
////            AlertDialog.Builder builder = new AlertDialog.Builder(EditCourseActivity.this);
////            builder.setTitle("Delete Approvement");
////            builder.setMessage("Are you sure to delete this course?");
////            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialog, int which) {
////                    // Thực hiện việc xóa khỏi SQLite và Firebase
////                    db.delete(DbHelper.TABLE_COURSE, DbHelper.COLUMN_COURSE_ID + "=?", new String[]{String.valueOf(courseId)});
////                    dbHelper.deleteCourseFirebase(courseId);
////                    // Quay lại MainActivity sau khi xóa thành công
////                    Intent intent = new Intent(EditCourseActivity.this, MainActivity.class);
////                    startActivity(intent);
////                }
////            });
////            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialog, int which) {
////                    dialog.dismiss();  // Đóng thông báo
////                }
////            });
////
////            AlertDialog dialog = builder.create();
////            dialog.show();
////        }
////    }
////
////}
//


////code version 2.0
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
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.yogaapplication.DbHelper;
//import com.example.yogaapplication.MainActivity;
//import com.example.yogaapplication.R;
//import com.example.yogaapplication.entity.Course;
//
//
//import android.app.TimePickerDialog;
//
//import java.util.Locale;
//public class EditCourseActivity extends AppCompatActivity {
//
//    private EditText editName, editTimeStart, editCapacity, editDuration, editPrice, editDescription;
//    private Spinner spinnerDayOfWeek, spinnerClassType;
//    private DbHelper dbHelper;
//    private String courseId;
//    private Button buttonSave, buttonDelete;
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
//        // Initialize views
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
//        errorName = findViewById(R.id.error_name);
//        errorDayOfTheWeek = findViewById(R.id.error_day_of_the_week);
//        errorTimeStart = findViewById(R.id.error_time_start);
//        errorCapacity = findViewById(R.id.error_capacity);
//        errorDuration = findViewById(R.id.error_duration);
//        errorPrice = findViewById(R.id.error_price);
//        errorClassType = findViewById(R.id.error_class_type);
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
//        // Set OnClickListener to show the TimePickerDialog
//        editTimeStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showTimePickerDialog();
//            }
//        });
//
////        // Optionally, prevent the EditText from being editable
////        editTimeStart.setFocusable(false);
////        editTimeStart.setClickable(true);
//
//        buttonSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveCourse();
//            }
//        });
//    }
//
//    private void populateFields(String courseId) {
//        // Fetch course details and update fields
//        Course updateCourse = dbHelper.getCourseById(courseId);
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
//    //code version 1.0
////    private void saveCourse() {
////        String name = editName.getText().toString().trim();
////        String dayOfWeek = spinnerDayOfWeek.getSelectedItem().toString();
////        String timeStart = editTimeStart.getText().toString().trim();
////        String capacityStr = editCapacity.getText().toString().trim();
////        String durationStr = editDuration.getText().toString().trim();
////        String priceStr = editPrice.getText().toString().trim();
////        String classType = spinnerClassType.getSelectedItem().toString();
////        String description = editDescription.getText().toString().trim();
////
////        if (validate()) {
////            int capacity = Integer.parseInt(capacityStr);
////            float duration = Float.parseFloat(durationStr);
////            float price = Float.parseFloat(priceStr);
////
////            SQLiteDatabase db = dbHelper.getWritableDatabase();
////            ContentValues values = new ContentValues();
////            values.put(DbHelper.COLUMN_COURSE_NAME, name);
////            values.put(DbHelper.COLUMN_COURSE_DAY_OF_WEEK, dayOfWeek);
////            values.put(DbHelper.COLUMN_COURSE_TIME_START, timeStart);
////            values.put(DbHelper.COLUMN_COURSE_CAPACITY, capacity);
////            values.put(DbHelper.COLUMN_COURSE_DURATION, duration);
////            values.put(DbHelper.COLUMN_COURSE_PRICE, price);
////            values.put(DbHelper.COLUMN_COURSE_CLASS_TYPE, classType);
////            values.put(DbHelper.COLUMN_COURSE_DESCRIPTION, description);
////
////            Course newCourse = new Course(name, dayOfWeek, timeStart, capacity, duration, price, classType, description);
////
////            if (courseId != null) {
////                // Update existing course
////                newCourse.setId(courseId);
////                int rowsAffected = db.update(DbHelper.TABLE_COURSE, values,
////                        DbHelper.COLUMN_COURSE_ID + "=?", new String[]{String.valueOf(courseId)});
////
////                dbHelper.saveCourseFirebase(courseId, newCourse);
////
////                if (rowsAffected > 0) {
////                    Toast.makeText(this, "Course updated successfully!", Toast.LENGTH_SHORT).show();
////                } else {
////                    Toast.makeText(this, "Error updating course", Toast.LENGTH_SHORT).show();
////                }
////
////                finish();
////            } else {
////                String newId = dbHelper.saveCourseFirebase(newCourse);
////                newCourse.setId(newId);
////                values.put(DbHelper.COLUMN_COURSE_ID, newId);
////
////                long newRowId = db.insert(DbHelper.TABLE_COURSE, null, values);
////
////                if (newRowId != -1) {
////                    Toast.makeText(this, "Yoga course saved!", Toast.LENGTH_SHORT).show();
////                } else {
////                    Toast.makeText(this, "Error saving yoga course", Toast.LENGTH_SHORT).show();
////                }
////
////                finish();
////            }
////        }
////    }
//
//    private void showTimePickerDialog() {
//        // Show a TimePickerDialog to select the start time
//        String currentTimeStr = editTimeStart.getText().toString().trim();
//        int hour = 0;
//        int minute = 0;
//
//        if (!currentTimeStr.isEmpty()) {
//            String[] timeParts = currentTimeStr.split(":");
//            if (timeParts.length == 2) {
//                hour = Integer.parseInt(timeParts[0]);
//                minute = Integer.parseInt(timeParts[1]);
//            }
//        }
//
//        TimePickerDialog timePickerDialog = new TimePickerDialog(EditCourseActivity.this,
//                (view, selectedHour, selectedMinute) -> {
//                    String timeStr = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
//                    editTimeStart.setText(timeStr);
//                }, hour, minute, true);
//        timePickerDialog.show();
//    }
//
//
//    //code version 2.0
//    private void saveCourse() {
//        String name = editName.getText().toString().trim();
//        String dayOfWeek = spinnerDayOfWeek.getSelectedItem().toString();
//        String timeStart = editTimeStart.getText().toString().trim();
//        String capacityStr = editCapacity.getText().toString().trim();
//        String durationStr = editDuration.getText().toString().trim();
//        String priceStr = editPrice.getText().toString().trim();
//        String classType = spinnerClassType.getSelectedItem().toString();
//        String description = editDescription.getText().toString().trim();
//
//        if (validate()) {
//            int capacity = Integer.parseInt(capacityStr);
//            float duration = Float.parseFloat(durationStr);
//            float price = Float.parseFloat(priceStr);
//
//            SQLiteDatabase db = dbHelper.getWritableDatabase();
//            ContentValues values = new ContentValues();
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
//                    Toast.makeText(this, "Error updating course", Toast.LENGTH_SHORT).show();
//                }
//
//                // Remove finish(); here
//            } else {
//                // Insert new course
//                String newId = dbHelper.saveCourseFirebase(newCourse);
//                newCourse.setId(newId);
//                values.put(DbHelper.COLUMN_COURSE_ID, newId);
//
//                long newRowId = db.insert(DbHelper.TABLE_COURSE, null, values);
//
//                if (newRowId != -1) {
//                    Toast.makeText(this, "Yoga course saved!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "Error saving yoga course", Toast.LENGTH_SHORT).show();
//                }
//
//                // Remove finish(); here
//            }
//
//            // Navigate to MainActivity
//            Intent intent = new Intent(EditCourseActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish(); // Close current activity
//        }
//    }
//
//
//    private boolean validate() {
//        boolean isValid = true;
//
//        // Validate name
//        if (editName.getText().toString().trim().isEmpty()) {
//            errorName.setText("Please enter the course name.");
//            errorName.setVisibility(View.VISIBLE);
//            isValid = false;
//        } else {
//            errorName.setVisibility(View.GONE);
//        }
//
//        // Validate day of the week
//        if (spinnerDayOfWeek.getSelectedItem().toString().trim().isEmpty()) {
//            errorDayOfTheWeek.setText("Please select a day of the week.");
//            errorDayOfTheWeek.setVisibility(View.VISIBLE);
//            isValid = false;
//        } else {
//            errorDayOfTheWeek.setVisibility(View.GONE);
//        }
//
//        // Validate time start
//        if (editTimeStart.getText().toString().trim().isEmpty()) {
//            errorTimeStart.setText("Please enter the start time.");
//            errorTimeStart.setVisibility(View.VISIBLE);
//            isValid = false;
//        } else {
//            errorTimeStart.setVisibility(View.GONE);
//        }
//
//        // Validate capacity
//        String capacityStr = editCapacity.getText().toString().trim();
//        if (capacityStr.isEmpty()) {
//            errorCapacity.setText("Please enter the capacity.");
//            errorCapacity.setVisibility(View.VISIBLE);
//            isValid = false;
//        } else if (!capacityStr.matches("\\d+")) {
//            errorCapacity.setText("Capacity must be a valid number.");
//            errorCapacity.setVisibility(View.VISIBLE);
//            isValid = false;
//        } else {
//            errorCapacity.setVisibility(View.GONE);
//        }
//
//        // Validate duration
//        String durationStr = editDuration.getText().toString().trim();
//        if (durationStr.isEmpty()) {
//            errorDuration.setText("Please enter the duration.");
//            errorDuration.setVisibility(View.VISIBLE);
//            isValid = false;
//        } else if (!durationStr.matches("\\d+(\\.\\d+)?")) {
//            errorDuration.setText("Duration must be a valid number.");
//            errorDuration.setVisibility(View.VISIBLE);
//            isValid = false;
//        } else {
//            errorDuration.setVisibility(View.GONE);
//        }
//
//        // Validate price
//        String priceStr = editPrice.getText().toString().trim();
//        if (priceStr.isEmpty()) {
//            errorPrice.setText("Please enter the price.");
//            errorPrice.setVisibility(View.VISIBLE);
//            isValid = false;
//        } else if (!priceStr.matches("\\d+(\\.\\d+)?")) {
//            errorPrice.setText("Price must be a valid number.");
//            errorPrice.setVisibility(View.VISIBLE);
//            isValid = false;
//        } else {
//            errorPrice.setVisibility(View.GONE);
//        }
//
//        // Validate class type
//        if (spinnerClassType.getSelectedItem().toString().trim().isEmpty()) {
//            errorClassType.setText("Please select a class type.");
//            errorClassType.setVisibility(View.VISIBLE);
//            isValid = false;
//        } else {
//            errorClassType.setVisibility(View.GONE);
//        }
//
//        return isValid;
//    }
//
//    private void delete(String courseId) {
//        // Use DbHelper to open database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // Check if Course has YogaClass
//        if (dbHelper.hasYogaClasses(courseId)) {
//            // If Course has YogaClass, show message to user
//            AlertDialog.Builder builder = new AlertDialog.Builder(EditCourseActivity.this);
//            builder.setTitle("Cannot Delete");
//            builder.setMessage("This course contains Yoga Classes inside. Please delete Yoga Classes before deleting this course.");
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            AlertDialog dialog = builder.create();
//            dialog.show();
//        } else {
//            // If no classes, proceed to delete Course
//            AlertDialog.Builder builder = new AlertDialog.Builder(EditCourseActivity.this);
//            builder.setTitle("Delete Confirmation");
//            builder.setMessage("Are you sure you want to delete this course?");
//            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    int rowsDeleted = db.delete(DbHelper.TABLE_COURSE, DbHelper.COLUMN_COURSE_ID + "=?", new String[]{String.valueOf(courseId)});
//                    dbHelper.deleteCourseFirebase(courseId);
//
//                    if (rowsDeleted > 0) {
//                        Toast.makeText(EditCourseActivity.this, "Course deleted successfully!", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(EditCourseActivity.this, "Error deleting course.", Toast.LENGTH_SHORT).show();
//                    }
//
//                    Intent intent = new Intent(EditCourseActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }
//            });
//            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//
//            AlertDialog dialog = builder.create();
//            dialog.show();
//        }
//    }
//}


//code version 3.0 optimized
package com.example.yogaapplication.views;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log; // Remember to remove or disable in production
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

import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
        dbHelper = new DbHelper(this);
        editName = findViewById(R.id.edit_name);
        spinnerDayOfWeek = findViewById(R.id.spinner_day_of_the_week);
        editTimeStart = findViewById(R.id.edit_time_start);
        editCapacity = findViewById(R.id.edittext_capacity);
        editDuration = findViewById(R.id.edittext_duration);
        editPrice = findViewById(R.id.edittext_price);
        spinnerClassType = findViewById(R.id.spinner_class_type);
        editDescription = findViewById(R.id.edittext_description);
        buttonSave = findViewById(R.id.button_save);
        buttonDelete = findViewById(R.id.button_delete);

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

        // Get the data passed from the previous activity
        courseId = getIntent().getStringExtra("COURSE_ID");

        if (courseId != null) {
            populateFields(courseId);
            buttonDelete.setVisibility(View.VISIBLE);

            buttonDelete.setOnClickListener(view -> deleteCourse());
        } else {
            buttonDelete.setVisibility(View.GONE);
        }

        // Initially disable the save button
        buttonSave.setEnabled(false);

        // Add TextWatchers to required fields
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                checkFieldsForEmptyValues();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed
            }
        };

        editName.addTextChangedListener(textWatcher);
        editTimeStart.addTextChangedListener(textWatcher);
        editCapacity.addTextChangedListener(textWatcher);
        editDuration.addTextChangedListener(textWatcher);
        editPrice.addTextChangedListener(textWatcher);

        // Set up click listener for save button
        buttonSave.setOnClickListener(view -> saveCourse());

        // Set up TimePickerDialog for editTimeStart
        editTimeStart.setOnClickListener(view -> showTimePickerDialog());
    }

    private void checkFieldsForEmptyValues() {
        String name = editName.getText().toString().trim();
        String timeStart = editTimeStart.getText().toString().trim();
        String capacity = editCapacity.getText().toString().trim();
        String duration = editDuration.getText().toString().trim();
        String price = editPrice.getText().toString().trim();

        buttonSave.setEnabled(!name.isEmpty() && !timeStart.isEmpty() && !capacity.isEmpty()
                && !duration.isEmpty() && !price.isEmpty());
    }

    private void populateFields(String courseId) {
        Course updateCourse = dbHelper.getCourseById(courseId);

        if (updateCourse != null) {
            editName.setText(updateCourse.getName());
            spinnerDayOfWeek.setSelection(((ArrayAdapter) spinnerDayOfWeek.getAdapter())
                    .getPosition(updateCourse.getDayOfWeek()));
            editTimeStart.setText(updateCourse.getTimeStart());
            editCapacity.setText(String.valueOf(updateCourse.getCapacity()));
            editDuration.setText(String.valueOf(updateCourse.getDuration()));
            editPrice.setText(String.valueOf(updateCourse.getPrice()));
            spinnerClassType.setSelection(((ArrayAdapter) spinnerClassType.getAdapter())
                    .getPosition(updateCourse.getClassType()));
            editDescription.setText(updateCourse.getDescription());
        } else {
            showToast("Course details not found.");
            finish();
        }
    }

    private void showTimePickerDialog() {
        // Show a TimePickerDialog to select the start time
        String currentTimeStr = editTimeStart.getText().toString().trim();
        int hour = 0;
        int minute = 0;

        if (!currentTimeStr.isEmpty()) {
            String[] timeParts = currentTimeStr.split(":");
            if (timeParts.length == 2) {
                hour = Integer.parseInt(timeParts[0]);
                minute = Integer.parseInt(timeParts[1]);
            }
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(EditCourseActivity.this,
                (view, selectedHour, selectedMinute) -> {
                    String timeStr = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
                    editTimeStart.setText(timeStr);
                }, hour, minute, true);
        timePickerDialog.show();
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

        if (!validate()) {
            return;
        }

        int capacity = Integer.parseInt(capacityStr);
        float duration = Float.parseFloat(durationStr);
        float price = Float.parseFloat(priceStr);

        Course newCourse = new Course(name, dayOfWeek, timeStart, capacity, duration, price, classType, description);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            SQLiteDatabase db = null;
            try {
                db = dbHelper.getWritableDatabase();
                ContentValues values = getCourseContentValues(newCourse);

                if (courseId != null) {
                    // Update existing course
                    newCourse.setId(courseId);
                    values.put(DbHelper.COLUMN_COURSE_ID, courseId);

                    int rowsAffected = db.update(DbHelper.TABLE_COURSE, values,
                            DbHelper.COLUMN_COURSE_ID + "=?", new String[]{courseId});
                    dbHelper.saveCourseFirebase(courseId, newCourse);

                    if (rowsAffected > 0) {
                        runOnUiThread(() -> showToast("Course updated successfully!"));
                    } else {
                        runOnUiThread(() -> showToast("Error updating course."));
                    }
                } else {
                    // Insert new course
                    String newId = dbHelper.saveCourseFirebase(newCourse);
                    newCourse.setId(newId);
                    values.put(DbHelper.COLUMN_COURSE_ID, newId);

                    long newRowId = db.insert(DbHelper.TABLE_COURSE, null, values);

                    if (newRowId != -1) {
                        runOnUiThread(() -> showToast("Yoga course saved!"));
                    } else {
                        runOnUiThread(() -> showToast("Error saving yoga course."));
                    }
                }

                // Navigate to MainActivity
                runOnUiThread(() -> {
                    Intent intent = new Intent(EditCourseActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); // Close current activity
                });

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> showToast("An error occurred while saving the course."));
            } finally {
                if (db != null) {
                    db.close();
                }
            }
        });
    }

    private ContentValues getCourseContentValues(Course course) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_COURSE_NAME, course.getName());
        values.put(DbHelper.COLUMN_COURSE_DAY_OF_WEEK, course.getDayOfWeek());
        values.put(DbHelper.COLUMN_COURSE_TIME_START, course.getTimeStart());
        values.put(DbHelper.COLUMN_COURSE_CAPACITY, course.getCapacity());
        values.put(DbHelper.COLUMN_COURSE_DURATION, course.getDuration());
        values.put(DbHelper.COLUMN_COURSE_PRICE, course.getPrice());
        values.put(DbHelper.COLUMN_COURSE_CLASS_TYPE, course.getClassType());
        values.put(DbHelper.COLUMN_COURSE_DESCRIPTION, course.getDescription());
        return values;
    }

    private boolean validate() {
        boolean isValid = true;

        // Validate name
        if (editName.getText().toString().trim().isEmpty()) {
            errorName.setText(getString(R.string.error_enter_course_name));
            errorName.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorName.setVisibility(View.GONE);
        }

        // Validate day of the week
        if (spinnerDayOfWeek.getSelectedItem() == null || spinnerDayOfWeek.getSelectedItem().toString().trim().isEmpty()) {
            errorDayOfTheWeek.setText(getString(R.string.error_select_day_of_week));
            errorDayOfTheWeek.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorDayOfTheWeek.setVisibility(View.GONE);
        }

        // Validate time start
        if (editTimeStart.getText().toString().trim().isEmpty()) {
            errorTimeStart.setText(getString(R.string.error_enter_start_time));
            errorTimeStart.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorTimeStart.setVisibility(View.GONE);
        }

        // Validate capacity
        String capacityStr = editCapacity.getText().toString().trim();
        if (capacityStr.isEmpty()) {
            errorCapacity.setText(getString(R.string.error_enter_capacity));
            errorCapacity.setVisibility(View.VISIBLE);
            isValid = false;
        } else if (!capacityStr.matches("\\d+")) {
            errorCapacity.setText(getString(R.string.error_invalid_capacity));
            errorCapacity.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorCapacity.setVisibility(View.GONE);
        }

        // Validate duration
        String durationStr = editDuration.getText().toString().trim();
        if (durationStr.isEmpty()) {
            errorDuration.setText(getString(R.string.error_enter_duration));
            errorDuration.setVisibility(View.VISIBLE);
            isValid = false;
        } else if (!durationStr.matches("\\d+(\\.\\d+)?")) {
            errorDuration.setText(getString(R.string.error_invalid_duration));
            errorDuration.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorDuration.setVisibility(View.GONE);
        }

        // Validate price
        String priceStr = editPrice.getText().toString().trim();
        if (priceStr.isEmpty()) {
            errorPrice.setText(getString(R.string.error_enter_price));
            errorPrice.setVisibility(View.VISIBLE);
            isValid = false;
        } else if (!priceStr.matches("\\d+(\\.\\d+)?")) {
            errorPrice.setText(getString(R.string.error_invalid_price));
            errorPrice.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorPrice.setVisibility(View.GONE);
        }

        // Validate class type
        if (spinnerClassType.getSelectedItem() == null || spinnerClassType.getSelectedItem().toString().trim().isEmpty()) {
            errorClassType.setText(getString(R.string.error_select_class_type));
            errorClassType.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorClassType.setVisibility(View.GONE);
        }

        return isValid;
    }

    private void deleteCourse() {
        // Check if Course has Yoga Classes
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            boolean hasClasses = dbHelper.hasYogaClasses(courseId);
            runOnUiThread(() -> {
                if (hasClasses) {
                    // Show message if course has classes
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditCourseActivity.this);
                    builder.setTitle("Cannot Delete");
                    builder.setMessage("This course contains Yoga Classes. Please delete the classes before deleting this course.");
                    builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                    builder.create().show();
                } else {
                    // Confirm deletion
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditCourseActivity.this);
                    builder.setTitle("Delete Confirmation");
                    builder.setMessage("Are you sure you want to delete this course?");
                    builder.setPositiveButton("Yes", (dialog, which) -> {
                        executor.execute(() -> {
                            SQLiteDatabase db = null;
                            try {
                                db = dbHelper.getWritableDatabase();
                                int rowsDeleted = db.delete(DbHelper.TABLE_COURSE, DbHelper.COLUMN_COURSE_ID + "=?", new String[]{courseId});
                                dbHelper.deleteCourseFirebase(courseId);

                                if (rowsDeleted > 0) {
                                    runOnUiThread(() -> showToast("Course deleted successfully!"));
                                } else {
                                    runOnUiThread(() -> showToast("Error deleting course."));
                                }

                                // Navigate to MainActivity
                                runOnUiThread(() -> {
                                    Intent intent = new Intent(EditCourseActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                runOnUiThread(() -> showToast("An error occurred while deleting the course."));
                            } finally {
                                if (db != null) {
                                    db.close();
                                }
                            }
                        });
                    });
                    builder.setNegativeButton("No", (dialog1, which1) -> dialog1.dismiss());
                    builder.create().show();
                }
            });
        });
    }

    private void showToast(String message) {
        Toast.makeText(EditCourseActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}

