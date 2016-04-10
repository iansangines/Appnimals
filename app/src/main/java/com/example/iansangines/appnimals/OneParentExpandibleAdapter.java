package com.example.iansangines.appnimals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ian on 10/04/2016.
 */
public class OneParentExpandibleAdapter extends BaseExpandableListAdapter {
    private String selectType;
    private ArrayList<String> typesList;
    private Context context;

    public OneParentExpandibleAdapter(Context context, String selectType, ArrayList<String> typesList){
        this.context = context;
        this.selectType = selectType;
        this.typesList = typesList;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return typesList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return selectType;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return typesList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null) convertView = LayoutInflater.from(context).inflate(R.layout.expandible_parent_layout, parent, false);

        TextView tv = (TextView) convertView.findViewById(R.id.parenttextview);
        tv.setText(selectType);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String selectedType = (String) typesList.get(childPosition);

        if(convertView == null) convertView = LayoutInflater.from(context).inflate(R.layout.expandible_child_layout, parent, false);

        TextView tv = (TextView) convertView.findViewById(R.id.childtextview);
        ImageView imgview = (ImageView) convertView.findViewById(R.id.childimage);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
