package com.example.yogaapplication.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.yogaapplication.R;
import com.example.yogaapplication.entity.YogaClass;

import java.util.ArrayList;
import java.util.List;

public class ClassAdapter extends BaseAdapter implements Filterable {

    //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm

    private Activity activity;
    private ArrayList<YogaClass> listClass;
    private ArrayList<YogaClass> listClassOld;

    public ClassAdapter(Activity activity, ArrayList<YogaClass> listClass) {
        this.activity = activity;
        this.listClass = listClass;
        this.listClassOld = listClass;
    }

    @Override
    public int getCount() {
        //Trả về tổng số phần tử, nó được gọi bởi ListView
        return listClass.size();
    }

    @Override
    public Object getItem(int position) {
        //Trả về dữ liệu ở vị trí position của Adapter, tương ứng là phần tử
        //có chỉ số position trong listProduct
        return listClass.get(position);
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
        YogaClass yogaClass = (YogaClass) getItem(position);
        ((TextView) convertView.findViewById(R.id.name)).setText(yogaClass.getName());
        ((TextView) convertView.findViewById(R.id.description)).setText(yogaClass.getTeacher());
        ((TextView) convertView.findViewById(R.id.date)).setText(String.format("Day of week: %s", yogaClass.getDate()));

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()) {
                    listClass = listClassOld;
                } else {
                    ArrayList<YogaClass> classList = new ArrayList<>();

                    for (YogaClass yogaClass : listClassOld) {
                        if (yogaClass.getTeacher().toLowerCase().contains(strSearch.toLowerCase()) || yogaClass.getDate().contains(strSearch)) {
                            classList.add(yogaClass);
                        }
                    }

                    listClass = classList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listClass;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listClass = (ArrayList<YogaClass>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}

