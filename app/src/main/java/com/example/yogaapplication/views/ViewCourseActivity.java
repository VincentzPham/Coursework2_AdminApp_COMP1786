package com.example.yogaapplication.views;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yogaapplication.DbHelper;
import com.example.yogaapplication.MainActivity;
import com.example.yogaapplication.adapters.CourseAdapter;

import com.example.yogaapplication.R;
import com.example.yogaapplication.entity.Course;

import java.util.ArrayList;
import java.util.List;

public class ViewCourseActivity extends AppCompatActivity {

    private DbHelper dbHelper;
    ArrayList<Course> courses;
    CourseAdapter courseViewAdapter;
    ListView listViewCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);

        dbHelper = new DbHelper(this); // Khởi tạo DbHelper
        dbHelper.syncCoursesWithFirebase(); // Đồng bộ dữ liệu khóa học từ Firebase xuống local

        courses = getCourses(); // Lấy danh sách các khóa học

        listViewCourse = findViewById(R.id.list_courses);
        courseViewAdapter = new CourseAdapter(this, courses);
        listViewCourse.setAdapter(courseViewAdapter);

        listViewCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Khi click vào một khóa học, chuyển sang màn hình chỉnh sửa khóa học đó
                Course course = (Course) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(ViewCourseActivity.this, EditCourseActivity.class);
                intent.putExtra("COURSE_ID", course.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Cập nhật lại danh sách khóa học khi quay lại màn hình
        courses = getCourses();
        courseViewAdapter.notifyDataSetChanged();
    }

    public ArrayList<Course> getCourses() {
        ArrayList<Course> courseList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DbHelper.TABLE_COURSE, null);

        if (cursor.moveToFirst()) {
            do {
                // Tạo một đối tượng Course và gán dữ liệu từ cơ sở dữ liệu
                Course course = new Course();
                course.setId(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_COURSE_ID)));
                course.setName(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_COURSE_NAME)));
                course.setDayOfWeek(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_COURSE_DAY_OF_WEEK)));
                course.setTimeStart(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_COURSE_TIME_START)));
                course.setCapacity(cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_COURSE_CAPACITY)));
                course.setDuration(cursor.getFloat(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_COURSE_DURATION)));
                course.setPrice(cursor.getFloat(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_COURSE_PRICE)));
                course.setClassType(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_COURSE_CLASS_TYPE)));
                course.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_COURSE_DESCRIPTION)));
                courseList.add(course);
            } while (cursor.moveToNext());
        }

        cursor.close(); // Đóng con trỏ để giải phóng tài nguyên
        return courseList;
    }
}