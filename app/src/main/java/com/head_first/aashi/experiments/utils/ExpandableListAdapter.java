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
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    //Layouts and Views
    private List<View> listGroupHeaderView;
    private Map<View, List<View>> mapGroupItemsView;
    //private Map<View, List<View>> prevViewMap;

    //Datatype
    private List<String> listGroupHeaderData;
    private Map<String, List<String>> mapGroupItemsData; //maps the group headers to itemms

    public ExpandableListAdapter(Context context, Map<String, List<String>> mapGroupItemsData, final int GROUP_VIEW_ID,
                                 final int ITEM_VIEW_ID, final int GROUP_LAYOUT_ID, final int ITEM_LAYOUT_ID){
        this.context = context;
        this.GROUP_VIEW_ID = GROUP_VIEW_ID;
        this.ITEM_VIEW_ID = ITEM_VIEW_ID;
        this.GROUP_LAYOUT_ID = GROUP_LAYOUT_ID;
        this.ITEM_LAYOUT_ID = ITEM_LAYOUT_ID;
        if(mapGroupItemsData == null || mapGroupItemsData.isEmpty()){
            mapGroupItemsData = new HashMap<>();
            mapGroupItemsData.put("NO CONTENT FOUND", new ArrayList<String>());
        }
        List<String> unsortedList = new ArrayList<String>(mapGroupItemsData.keySet());
        Collections.sort(unsortedList);
        this.listGroupHeaderData = Collections.unmodifiableList(unsortedList);
        this.mapGroupItemsData = mapGroupItemsData;
        mapGroupItemsView = new HashMap<>();
        listGroupHeaderView = new ArrayList<>(unsortedList.size());
    }

    @Override
    public int getGroupCount() {
        if(this.listGroupHeaderData != null){
            return this.listGroupHeaderData.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(groupPosition >= 0 && groupPosition < this.listGroupHeaderData.size()){
            return this.mapGroupItemsData.get(this.listGroupHeaderData.get(groupPosition)).size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if(groupPosition >= 0 && groupPosition < this.listGroupHeaderData.size()) {
            return this.listGroupHeaderData.get(groupPosition);
        }
        return 0;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if(groupPosition >= 0 && groupPosition < this.listGroupHeaderData.size()
                && childPosition >= 0 && childPosition < this.mapGroupItemsData.get(this.listGroupHeaderData.get(groupPosition)).size()) {
            return this.mapGroupItemsData.get(this.listGroupHeaderData.get(groupPosition)).get(childPosition);
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
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(GROUP_LAYOUT_ID, null);
            if (listGroupHeaderView.size() > groupPosition && listGroupHeaderView.get(groupPosition) != null) {
//                prevViewMap = new HashMap<>();
//                prevViewMap.putAll(mapGroupItemsView);
                listGroupHeaderView.set(groupPosition, convertView);
                mapGroupItemsView.remove(listGroupHeaderView.get(groupPosition));
                mapGroupItemsView.put(listGroupHeaderView.get(groupPosition), new ArrayList<View>());
            } else {
//                prevViewMap = new HashMap<>();
//                prevViewMap.putAll(mapGroupItemsView);
                listGroupHeaderView.add(groupPosition, convertView);
                mapGroupItemsView.put(convertView, new ArrayList<View>());
            }
        }
        convertView.setClickable(false);
        TextView listGroupHeader = (TextView) convertView.findViewById(this.GROUP_VIEW_ID);
        listGroupHeader.setText((getGroup(groupPosition)).toString());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(ITEM_LAYOUT_ID, null);
            mapGroupItemsView.get(listGroupHeaderView.get(groupPosition)).add(convertView);
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
//        if(((CheckBox)(prevViewMap.get(listGroupHeaderView.get(groupPosition)).get(childPosition).findViewById(ITEM_VIEW_ID))).isChecked()){
//            ((CheckBox)(mapGroupItemsView.get(listGroupHeaderView.get(groupPosition)).get(childPosition).findViewById(ITEM_VIEW_ID))).setChecked(true);
//        }

    }

    public List<View> getListGroupHeaderView(){
        return Collections.unmodifiableList(listGroupHeaderView);
    }

    public List<View> getItems(View groupHeaderView){
        return Collections.unmodifiableList(mapGroupItemsView.get(groupHeaderView));
    }
}
