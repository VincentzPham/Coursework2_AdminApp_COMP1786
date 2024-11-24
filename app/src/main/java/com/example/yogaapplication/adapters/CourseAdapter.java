package com.example.yogaapplication.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yogaapplication.R;
import com.example.yogaapplication.entity.Course;

import java.util.ArrayList;

public class CourseAdapter extends BaseAdapter {

    //Dữ liệu liên kết bởi Adapter là một mảng các khóa học.
    //Adapter liên kết dữ liệu là danh sách các khóa học và hiển thị chúng trên ListView
    private Activity activity;
    final ArrayList<Course> listCourse;

    public CourseAdapter(Activity activity, ArrayList<Course> listCourse) {
        this.activity = activity;
        this.listCourse = listCourse;
    }

    @Override
    public int getCount() {
        //Hệ thống trả về tổng số khóa học, nó được gọi bởi ListView
        // Trả về tổng số khóa học trong danh sách
        return listCourse.size();
    }

    @Override
    public Object getItem(int position) {
        //Trả về dữ liệu ở vị trí position của Adapter, tương ứng là khóa học
        //có chỉ số position trong listProduct
        return listCourse.get(position);
    }

    @Override
    public long getItemId(int position) {
        //Trả về một ID của phần, tức là trả về vị trí của phần tử trong danh sách
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();

        // Hệ thống đổ dữ liệu vào biến View, view này chính là những gì nằm trong item_view.xml
        // Nó tạo hoặc tái sử dụng View để hiển thị phần tử, sử dụng layout từ item_view.xml
        convertView = inflater.inflate(R.layout.item_view, null);

        //Bind sữ liệu phần tử vào View.
        //Gán dữ liệu của khóa học hiện tại (ở vị trí position) vào View
        Course course = (Course) getItem(position);

        ((TextView) convertView.findViewById(R.id.name)).setText(course.getName());
        ((TextView) convertView.findViewById(R.id.description)).setText(course.getTimeStart());
        ((TextView) convertView.findViewById(R.id.date)).setText(String.format("Day of week: %s", course.getDayOfWeek()));

        return convertView;
    }
}

