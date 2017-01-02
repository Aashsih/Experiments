package com.head_first.aashi.experiments.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.head_first.aashi.experiments.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Aashish Indorewala on 02-Jan-17.
 */

public final class ExpandableListAdapter extends BaseExpandableListAdapter {

    //Views
    private Context context;
    private final int GROUP_VIEW_ID;
    private final int ITEM_VIEW_ID;
    private final int GROUP_LAYOUT_ID;
    private final int ITEM_LAYOUT_ID;


    //Datatype
    private List<String> listGroupHeader;
    private HashMap<String, List<String>> mapGroupItems; //maps the group headers to itemms

    public ExpandableListAdapter(Context context, HashMap<String, List<String>> mapGroupItems, final int GROUP_VIEW_ID,
                                 final int ITEM_VIEW_ID, final int GROUP_LAYOUT_ID, final int ITEM_LAYOUT_ID){
        this.context = context;
        this.GROUP_VIEW_ID = GROUP_VIEW_ID;
        this.ITEM_VIEW_ID = ITEM_VIEW_ID;
        this.GROUP_LAYOUT_ID = GROUP_LAYOUT_ID;
        this.ITEM_LAYOUT_ID = ITEM_LAYOUT_ID;
        if(mapGroupItems == null || mapGroupItems.isEmpty()){
            mapGroupItems = new HashMap<>();
            mapGroupItems.put("NO CONTENT FOUND", null);
        }
        this.listGroupHeader = Collections.unmodifiableList(new ArrayList<String>(mapGroupItems.keySet()));
        this.mapGroupItems = mapGroupItems;
    }

    @Override
    public int getGroupCount() {
        if(this.listGroupHeader != null){
            return this.listGroupHeader.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(groupPosition >= 0 && groupPosition < this.listGroupHeader.size()){
            return this.mapGroupItems.get(this.listGroupHeader.get(groupPosition)).size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if(groupPosition >= 0 && groupPosition < this.listGroupHeader.size()) {
            return this.listGroupHeader.get(groupPosition);
        }
        return 0;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if(groupPosition >= 0 && groupPosition < this.listGroupHeader.size()
                && childPosition >= 0 && childPosition < this.mapGroupItems.get(this.listGroupHeader.get(groupPosition)).size()) {
            return this.mapGroupItems.get(this.listGroupHeader.get(groupPosition)).get(childPosition);
        }
        return null;
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
            convertView = layoutInflater.inflate(GROUP_LAYOUT_ID, null);
        }
        convertView.setClickable(false);
        TextView patientListgroup = (TextView) convertView.findViewById(this.GROUP_VIEW_ID);
        patientListgroup.setText((getGroup(groupPosition)).toString());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(ITEM_LAYOUT_ID, null);
        }
        convertView.setClickable(false);
        customItemLayoutSetup(groupPosition, childPosition, isLastChild, convertView, parent);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void customItemLayoutSetup(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent){
        switch(ITEM_LAYOUT_ID){
            case R.layout.heart_sound_ui_experiments_expandable_patient_list_item:
                expandablePatientListItem(groupPosition, childPosition, isLastChild, convertView, parent);
                break;
            case R.layout.heart_sound_ui_experiments_expandable_filter_list_item:
                expandableFilteListItem(groupPosition, childPosition, isLastChild, convertView, parent);
                break;
        }
    }

    private void expandablePatientListItem(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent){
        TextView patientListItem = (TextView) convertView.findViewById(this.ITEM_VIEW_ID);
        patientListItem.setText((getChild(groupPosition, childPosition)).toString());
    }

    private void expandableFilteListItem(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent){
        CheckBox filterListItem = (CheckBox) convertView.findViewById(this.ITEM_VIEW_ID);
        filterListItem.setText((getChild(groupPosition, childPosition)).toString());
    }

}
