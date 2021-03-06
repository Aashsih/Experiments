package com.head_first.aashi.experiments.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.head_first.aashi.experiments.R;
import com.head_first.aashi.experiments.heart_sound_ui_experiments.model.Filter;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aashish Indorewala on 04-Jan-17.
 */

public class ExpandableFilterListAdapter extends BaseExpandableListAdapter {
    //Views
    private Context context;
    private static final int GROUP_VIEW_ID = R.id.filterListGroup;
    private static final int ITEM_VIEW_ID = R.id.filterListItem;
    private static final int GROUP_LAYOUT_ID = R.layout.heart_sound_ui_experiments_expandable_filter_list_group;
    private static final int ITEM_LAYOUT_ID = R.layout.heart_sound_ui_experiments_expandable_filter_list_item;

    //Datatype
    private List<String> listGroupHeaderData;
    private Map<String, List<Filter.GroupItem>> mapGroupItemsData; //maps the group headers to itemms

    public ExpandableFilterListAdapter(Context context, Map<String, List<Filter.GroupItem>> mapGroupItemsData){
        this.context = context;
        if(mapGroupItemsData == null || mapGroupItemsData.isEmpty()){
            mapGroupItemsData = new LinkedHashMap<>();
            mapGroupItemsData.put("NO CONTENT FOUND", new ArrayList<Filter.GroupItem>());
        }
        this.listGroupHeaderData = Collections.unmodifiableList(new ArrayList<String>(mapGroupItemsData.keySet()));
        this.mapGroupItemsData = mapGroupItemsData;
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
        }
        convertView.setClickable(false);
        customLayoutSetup(groupPosition, childPosition, isLastChild, convertView, parent);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void customLayoutSetup(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent){
        CheckBox filterListItem = (CheckBox) convertView.findViewById(this.ITEM_VIEW_ID);
        filterListItem.setText(((Filter.GroupItem)(getChild(groupPosition, childPosition))).getItemName());
        filterListItem.setChecked(mapGroupItemsData.get(listGroupHeaderData.get(groupPosition)).get(childPosition).isItemSelectecAsFilter());
    }


}
