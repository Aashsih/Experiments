package com.head_first.aashi.experiments.heart_sound_ui_experiments.model;

import android.view.View;
import android.widget.ExpandableListView;

import com.head_first.aashi.experiments.utils.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aashish Indorewala on 04-Jan-17.
 */

public class Filter implements Serializable{
    private static final String SEARCH_STRING_GROUP_HEADER_SEPARATOR = ";";
    private static final String SEARCH_STRING_GROUP_ITEM_SEPARATOR = ",";

    private String searchString;
    private LinkedHashMap<String, List<GroupItem>> filterContent;

    public Filter(String searchString){
        filterContent = new LinkedHashMap<>();
        this.setSearchString("");
    }

    //This method will take in all the models required to populate the information
    public final void populateFilterContent(){
        //remember to set the items that are mentioned in the searchString as true
        //ssly remember to set the items that are mentioned in the searchString as true
        //Dont forget!!!!! Set the items that are mentioned in the searchString as true
        List<String> groupHeaders = new ArrayList<>();
        groupHeaders.add("Header 1");
        groupHeaders.add("Header 2");
        groupHeaders.add("Header 3");
        String[] itemFilters = StringUtil.split(this.searchString, SEARCH_STRING_GROUP_HEADER_SEPARATOR.charAt(0));
        List<GroupItem> groupHeader1Items = new ArrayList<>();
        if(itemFilters[0].contains("Item1")){
            groupHeader1Items.add(new GroupItem("Item1", true));
        }
        else{
            groupHeader1Items.add(new GroupItem("Item1", false));
        }
        if(itemFilters[0].contains("Item2")){
            groupHeader1Items.add(new GroupItem("Item2", true));
        }
        else{
            groupHeader1Items.add(new GroupItem("Item2", false));
        }
        if(itemFilters[0].contains("Item3")){
            groupHeader1Items.add(new GroupItem("Item3", true));
        }
        else{
            groupHeader1Items.add(new GroupItem("Item3", false));
        }


        List<GroupItem> groupHeader2Items = new ArrayList<>();
        if(itemFilters[0].contains("Item1")){
            groupHeader2Items.add(new GroupItem("Item1", true));
        }
        else{
            groupHeader2Items.add(new GroupItem("Item1", false));
        }
        if(itemFilters[0].contains("Item2")){
            groupHeader2Items.add(new GroupItem("Item2", true));
        }
        else{
            groupHeader2Items.add(new GroupItem("Item2", false));
        }
        if(itemFilters[0].contains("Item3")){
            groupHeader2Items.add(new GroupItem("Item3", true));
        }
        else{
            groupHeader2Items.add(new GroupItem("Item3", false));
        }

        List<GroupItem> groupHeader3Items = new ArrayList<>();
        if(itemFilters[0].contains("Item1")){
            groupHeader3Items.add(new GroupItem("Item1", true));
        }
        else{
            groupHeader3Items.add(new GroupItem("Item1", false));
        }
        if(itemFilters[0].contains("Item2")){
            groupHeader3Items.add(new GroupItem("Item2", true));
        }
        else{
            groupHeader3Items.add(new GroupItem("Item2", false));
        }
        if(itemFilters[0].contains("Item3")){
            groupHeader3Items.add(new GroupItem("Item3", true));
        }
        else{
            groupHeader3Items.add(new GroupItem("Item3", false));
        }
        //This hashMap will actually be created by info that was retrieved from the database based on
        //The Study table and the DoctorsPatient table (this one will determine which Doctors shared)
        //a Patient with the current user.
        filterContent.put(groupHeaders.get(0), groupHeader1Items);
        filterContent.put(groupHeaders.get(1), groupHeader2Items);
        filterContent.put(groupHeaders.get(2), groupHeader3Items);
    }

    public String getSearchString(){
        return this.searchString;
    }

    public void setSearchString(String searchString){
        if(searchString == null){
            searchString = "";
        }
        else{
            this.searchString = searchString;
        }
    }

    public Map<String, List<GroupItem>> getFilterContent(){
        return this.filterContent;
    }

    public void onFilterSelected(ExpandableListView parent, View view, int groupPosition, int childPosition, long id){
        List<String> listGroupHeaderData = Collections.unmodifiableList(new ArrayList<String>(this.getFilterContent().keySet()));
        Filter.GroupItem groupItem = this.getFilterContent().get(listGroupHeaderData.get(groupPosition)).get(childPosition);
        String filterName = groupItem.getItemName();
//        List<View> filterHeaders = expandableFilterAdapter.getListGroupHeaderView();
//        List<View> filterItems = expandableFilterAdapter.getItems(filterHeaders.get(groupPosition));
        if(groupItem.isItemSelectecAsFilter()){
            removeFilterFromSearchString(parent, view, groupPosition, childPosition, id, filterName);
            //((CheckBox)(filterItems.get(childPosition).findViewById(R.id.filterListItem))).setChecked(false);
            groupItem.setItemSelectedAsFilter(false);
        }
        else{
            addFilterToSearchString(parent, view, groupPosition, childPosition, id, filterName);
            //((CheckBox)(filterItems.get(childPosition).findViewById(R.id.filterListItem))).setChecked(true);
            groupItem.setItemSelectedAsFilter(true);
        }
    }

    private void removeFilterFromSearchString(ExpandableListView parent, View view, int groupPosition, int childPosition, long id, String filterName){
        String[] groupHeaderFilter = StringUtil.split(searchString, SEARCH_STRING_GROUP_HEADER_SEPARATOR.charAt(0));
        searchString = "";
        //This for loop rebuilds the searchString by appending the new filter in the specific group
        for(int i = 0 ; i < groupHeaderFilter.length; i++){
            if(i == groupPosition){
                boolean gruopHeaderFilterStartsWithFilterName = groupHeaderFilter[i].toLowerCase().startsWith(filterName.toLowerCase());
                if(gruopHeaderFilterStartsWithFilterName){
                    if(groupHeaderFilter[i].contains(SEARCH_STRING_GROUP_ITEM_SEPARATOR)){
                        searchString += groupHeaderFilter[i].replace(filterName + SEARCH_STRING_GROUP_ITEM_SEPARATOR,"");
                    }
                    else{
                        searchString += groupHeaderFilter[i].replace(filterName,"");
                    }

                }
                else{
                    searchString += groupHeaderFilter[i].replace(SEARCH_STRING_GROUP_ITEM_SEPARATOR + filterName,"");
                }
                if(i != groupHeaderFilter.length - 1){
                    searchString += ";";
                }
            }
            else{
                if(i == groupHeaderFilter.length - 1){
                    searchString += groupHeaderFilter[i];
                }
                else{
                    searchString += groupHeaderFilter[i] + ";";
                }
            }

        }
        if(searchString.split(";").length == 0){
            searchString = "";
        }
    }

    private void addFilterToSearchString(ExpandableListView parent, View view, int groupPosition, int childPosition, long id, String filterName){
        if(searchString.isEmpty()){
            for(int i = 0; i < parent.getExpandableListAdapter().getGroupCount(); i++){
                if(i == groupPosition){
                    searchString += filterName;
                }
                if(i != parent.getExpandableListAdapter().getGroupCount() - 1){
                    searchString += SEARCH_STRING_GROUP_HEADER_SEPARATOR;
                }

            }


        }
        else{
            String[] groupHeaderFilter = StringUtil.split(searchString, SEARCH_STRING_GROUP_HEADER_SEPARATOR.charAt(0));
            searchString = "";
            //This for loop rebuilds the searchString by appending the new filter in the specific group
            for(int i = 0 ; i < groupHeaderFilter.length; i++){
                if(i == groupPosition){
                    //append the new filter name to the current group header filters
                    if(groupHeaderFilter[i].isEmpty()){
                        searchString += filterName;
                    }
                    else{
                        searchString += groupHeaderFilter[i] + SEARCH_STRING_GROUP_ITEM_SEPARATOR + filterName;
                    }
                    if(i != groupHeaderFilter.length - 1){
                        searchString += ";";
                    }
                }
                else{
                    if(i == groupHeaderFilter.length - 1){
                        searchString += groupHeaderFilter[i];
                    }
                    else{
                        searchString += groupHeaderFilter[i] + ";";
                    }
                }

            }
        }
    }

    public class GroupItem implements Serializable{
        private String itemName;
        private boolean itemSelectedAsFilter;

        public GroupItem(String itemName, boolean itemSelectedAsFilter){
            this.itemName = itemName;
            this.itemSelectedAsFilter = itemSelectedAsFilter;
        }

        public String getItemName(){
            return this.itemName;
        }

        public boolean isItemSelectecAsFilter(){
            return this.itemSelectedAsFilter;
        }

        public void setItemSelectedAsFilter(boolean itemSelectedAsFilter){
            this.itemSelectedAsFilter = itemSelectedAsFilter;
        }
    }
}