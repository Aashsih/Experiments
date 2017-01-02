package com.head_first.aashi.experiments.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.head_first.aashi.experiments.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Aashish Indorewala on 02-Jan-17.
 */

public final class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listGroupHeader;
    private HashMap<String, List<String>> mapGroupItems; //maps the group headers to itemms

    public ExpandableListAdapter(Context context, List<String> listGroupHeader, HashMap<String, List<String>> mapGroupItems){
        this.context = context;
        this.listGroupHeader = listGroupHeader;
        this.mapGroupItems = mapGroupItems;
    }

    @Override
    public int getGroupCount() {
        return this.listGroupHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.mapGroupItems.get(this.listGroupHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listGroupHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.mapGroupItems.get(this.listGroupHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
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

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.heart_sound_ui_expandable_patient_list_group, null);
        }
        TextView patientListgroup = (TextView) convertView.findViewById(R.id.patientListGroup);
        patientListgroup.setText((getGroup(groupPosition)).toString());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.heart_sound_ui_experiments_patient_list_item, null);
        }
        TextView patientListItem = (TextView) convertView.findViewById(R.id.patientListItem);
        patientListItem.setText((getChild(groupPosition, childPosition)).toString());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
