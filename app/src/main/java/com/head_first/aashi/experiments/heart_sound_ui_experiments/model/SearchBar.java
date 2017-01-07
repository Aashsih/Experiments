package com.head_first.aashi.experiments.heart_sound_ui_experiments.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aashish Indorewala on 07-Jan-17.
 */

public class SearchBar {

    public static final <T> void onQueryTextChange(String query, Map<String, List<T>> allContentMap, Map<String, List<T>> searchContentMap){
        if(query == null || query == ""){
            if(searchContentMap != null){
                searchContentMap.putAll(allContentMap);
            }
            return;
        }
        searchContentMap.clear();
        for(String groupHeader : allContentMap.keySet()){
            List<T> filteredGroupItems = new ArrayList<>();
            searchContentMap.put(groupHeader, filteredGroupItems);
            List<T> allGroupItems = allContentMap.get(groupHeader);
            filterGroupItemsOnQueryChange(query, allGroupItems, filteredGroupItems);
        }
    }

    private static <T> void filterGroupItemsOnQueryChange(String query, List<T> allGroupItems, List<T> filteredGroupItems){
        for(int i = 0; i < allGroupItems.size(); i++){
            if(allGroupItems.get(i) instanceof String){
                String groupItem = (String) allGroupItems.get(i);
                if(groupItem.toLowerCase().contains(query.toLowerCase())){
                    filteredGroupItems.add(allGroupItems.get(i));
                }
            }
            else if(allGroupItems.get(i) instanceof Filter.GroupItem){
                Filter.GroupItem groupItem = (Filter.GroupItem) allGroupItems.get(i);
                if(groupItem.getItemName().toLowerCase().contains(query.toLowerCase())){
                    filteredGroupItems.add(allGroupItems.get(i));
                }
            }
        }
    }

    public static final <T> void onQueryTextSubmit(String query, Map<String, List<T>> allContentMap, Map<String, List<T>> searchContentMap){
        if(query == null || query == ""){
            if(searchContentMap != null){
                searchContentMap.putAll(allContentMap);
            }
            return;
        }
        searchContentMap.clear();
        for(String groupHeader : allContentMap.keySet()){
            List<T> filteredGroupItems = new ArrayList<>();
            searchContentMap.put(groupHeader, filteredGroupItems);
            List<T> allGroupItems = allContentMap.get(groupHeader);
            filterGroupItemsOnQuerySubmit(query, allGroupItems, filteredGroupItems);

        }
    }

    private static <T> void filterGroupItemsOnQuerySubmit(String query, List<T> allGroupItems, List<T> filteredGroupItems){
        for(int i = 0; i < allGroupItems.size(); i++){
            if(allGroupItems.get(i) instanceof String){
                String groupItem = (String) allGroupItems.get(i);
                if(groupItem.toLowerCase().startsWith(query.toLowerCase())){
                    filteredGroupItems.add(allGroupItems.get(i));
                }
            }
            else if(allGroupItems.get(i) instanceof Filter.GroupItem){
                Filter.GroupItem groupItem = (Filter.GroupItem) allGroupItems.get(i);
                if(groupItem.getItemName().toLowerCase().startsWith(query.toLowerCase())){
                    filteredGroupItems.add(allGroupItems.get(i));
                }
            }
        }
    }
}
