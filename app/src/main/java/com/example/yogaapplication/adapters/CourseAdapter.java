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

    //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm
    private Activity activity;
    final ArrayList<Course> listCourse;

    public CourseAdapter(Activity activity, ArrayList<Course> listCourse) {
        this.activity = activity;
        this.listCourse = listCourse;
    }

    @Override
    public int getCount() {
        //Trả về tổng số phần tử, nó được gọi bởi ListView
        return listCourse.size();
    }

    @Override
    public Object getItem(int position) {
        //Trả về dữ liệu ở vị trí position của Adapter, tương ứng là phần tử
        //có chỉ số position trong listProduct
        return listCourse.get(position);
    }

    @Override
    public long getItemId(int position) {
        //Trả về một ID của phần
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();

        // Đổ dữ liệu vào biến View, view này chính là những gì nằm trong item_name.xml
        convertView = inflater.inflate(R.layout.item_view, null);

        //Bind sữ liệu phần tử vào View
        Course course = (Course) getItem(position);

        ((TextView) convertView.findViewById(R.id.name)).setText(course.getName());
        ((TextView) convertView.findViewById(R.id.description)).setText(course.getTimeStart());
        ((TextView) convertView.findViewById(R.id.date)).setText(String.format("Day of week: %s", course.getDayOfWeek()));

        return convertView;
    }
}

