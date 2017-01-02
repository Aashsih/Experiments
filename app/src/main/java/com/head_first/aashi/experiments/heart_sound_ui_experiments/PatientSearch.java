package com.head_first.aashi.experiments.heart_sound_ui_experiments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Aashish Indorewala on 02-Jan-17.
 *
 * This class contains the Searching logic used by the SearchView in the
 * Patient related Fragments
 */

public final class PatientSearch {

    public static final void onQueryTextChange(String query, HashMap<String, List<String>> allContentMap, HashMap<String, List<String>> searchContentMap){
        if(query == null || query == ""){
            searchContentMap.putAll(allContentMap);
            return;
        }
        searchContentMap.clear();
        for(String groupHeader : allContentMap.keySet()){
            List<String> groupItems = new ArrayList<>();
            searchContentMap.put(groupHeader, groupItems);
            for(String groupItem : allContentMap.get(groupHeader)){
                if(groupItem.toLowerCase().contains(query.toLowerCase())){
                    groupItems.add(groupItem);
                }
            }
        }
    }

    public static final void onQueryTextSubmit(String query, HashMap<String, List<String>> allContentMap, HashMap<String, List<String>> searchContentMap){
        if(query == null || query == ""){
            searchContentMap.putAll(allContentMap);
            return;
        }
        searchContentMap.clear();
        for(String groupHeader : allContentMap.keySet()){
            List<String> groupItems = new ArrayList<>();
            searchContentMap.put(groupHeader, groupItems);
            for(String groupItem : allContentMap.get(groupHeader)){
                if(groupItem.toLowerCase().startsWith(query.toLowerCase())){
                    groupItems.add(groupItem);
                }
            }
        }
    }

}
