package com.head_first.aashi.experiments.heart_sound_ui_experiments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.head_first.aashi.experiments.R;
import com.head_first.aashi.experiments.utils.ExpandableListAdapter;
import com.head_first.aashi.experiments.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FilterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends Fragment {
    private static final String SEARCH_STRING_GROUP_HEADER_SEPARATOR = ";";
    private static final String SEARCH_STRING_GROUP_ITEM_SEPARATOR = ",";
    private final String SEARCH_STRING_TAG = "SEARCH_STRING_TAG";
    private final String FILTER_CONTENT_MAP_TAG = "FILTER_CONTENT_MAP_TAG";

    private View mRootView;
    private TextView mSearchStringView;
    private Button mSearchButton;
    private ExpandableListView mExpandableListFilter;
    private ExpandableListAdapter expandableFilterAdapter;
    private HashMap<String, List<String>> filterContentMap;
    private String searchString;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FilterFragment() {
        //Required empty constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilterFragment newInstance(String param1, String param2) {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setRetainInstance(true);
        filterContentMap = new HashMap<>();
        if (savedInstanceState != null) {
            HashMap<String, List<String>>  savedHashMap;
            //Restore the fragment's state
            if((savedHashMap = (HashMap<String, List<String>>) savedInstanceState.getSerializable(this.FILTER_CONTENT_MAP_TAG)) == null){
                filterContentMap.clear();
                filterContentMap.putAll(savedHashMap);
                setupExpandableListViewData();
            }
            if((searchString = savedInstanceState.getString(this.SEARCH_STRING_TAG)) == null){
                searchString = "";
            }
        }
        else{
            setupExpandableListViewData();
            searchString = "";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(mRootView == null){
            // Inflate the layout for this fragment
            mRootView = inflater.inflate(R.layout.heart_sound_ui_experiments_fragment_filter_layout, container, false);
        }
        //Search bar
        mSearchStringView = (TextView) mRootView.findViewById(R.id.searchString);
        searchString = mSearchStringView.getText().toString();
        mSearchButton = (Button) mRootView.findViewById(R.id.searchButton);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fetch Data from the database or create a HashMap based on the filters applied
            }
        });

        //Expandable Filter
        mExpandableListFilter = (ExpandableListView) mRootView.findViewById(R.id.expandableFilterView);
        expandableFilterAdapter = new ExpandableListAdapter(getContext(), filterContentMap, R.id.filterListGroup, R.id.filterListItem, R.layout.heart_sound_ui_experiments_expandable_filter_list_group, R.layout.heart_sound_ui_experiments_expandable_filter_list_item);
        mExpandableListFilter.setAdapter(expandableFilterAdapter);
        mExpandableListFilter.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                FilterFragment.this.onFilterClick(parent, v, groupPosition, childPosition, id);
                return false;
            }
        });
        expandableFilterAdapter.notifyDataSetChanged();
        return mRootView;
    }
    @Override
    public void onStart(){
        super.onStart();
        restoreExpandableListViewState();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(this.FILTER_CONTENT_MAP_TAG, filterContentMap);
        outState.putString(this.SEARCH_STRING_TAG, searchString);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void restoreExpandableListViewState(){
        if(!searchString.isEmpty()){
            String[] filterGroup = StringUtil.split(searchString, SEARCH_STRING_GROUP_HEADER_SEPARATOR.charAt(0));
            List<View> groupHeaderView = expandableFilterAdapter.getListGroupHeaderView();
            for(int i = 0; i < groupHeaderView.size() && i < filterGroup.length; i++){
                List<View> groupItemsView = expandableFilterAdapter.getItems(groupHeaderView.get(i));
                String[] items = filterGroup[i].split(SEARCH_STRING_GROUP_ITEM_SEPARATOR);
                for(int j = 0; j < groupItemsView.size(); j++){
                    for(int k = 0; k < items.length; k++){
                        CheckBox anItem = (CheckBox) groupItemsView.get(j);
                        if(anItem.getText().toString().toLowerCase().equals(items[k].toLowerCase())){
                            anItem.setChecked(true);
                        }
                    }
                }
            }
        }
    }

    //Utility Methods for the fragment
    private void setupExpandableListViewData(){
        List<String> groupHeaders = new ArrayList<>();
        groupHeaders.add("Header 1");
        groupHeaders.add("Header 2");
        groupHeaders.add("Header 3");

        List<String> groupHeader1Items = new ArrayList<>();
        groupHeader1Items.add("Item1");
        groupHeader1Items.add("Item2");
        groupHeader1Items.add("Item3");

        List<String> groupHeader2Items = new ArrayList<>();
        groupHeader2Items.add("Item4");
        groupHeader2Items.add("Item5");
        groupHeader2Items.add("Item6");

        List<String> groupHeader3Items = new ArrayList<>();
        groupHeader3Items.add("Item7");
        groupHeader3Items.add("Item8");
        groupHeader3Items.add("Item9");

        //This hashMap will actually be created by info that was retrieved from the database based on
        //The Study table and the DoctorsPatient table (this one will determine which Doctors shared)
        //a Patient with the current user.
        filterContentMap.clear();
        filterContentMap.put(groupHeaders.get(0), groupHeader1Items);
        filterContentMap.put(groupHeaders.get(1), groupHeader2Items);
        filterContentMap.put(groupHeaders.get(2), groupHeader3Items);
        //set the content for searchContentMap here
    }

    private void onFilterClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id){
        CheckBox filter = (CheckBox) view.findViewById(R.id.filterListItem);
        String filterName = filter.getText().toString();
//        List<View> filterHeaders = expandableFilterAdapter.getListGroupHeaderView();
//        List<View> filterItems = expandableFilterAdapter.getItems(filterHeaders.get(groupPosition));
        if(filter.isChecked()){
            removeFilterFromSearchString(parent, view, groupPosition, childPosition, id, filterName);
            //((CheckBox)(filterItems.get(childPosition).findViewById(R.id.filterListItem))).setChecked(false);
            filter.setChecked(false);
        }
        else{
            addFilterToSearchString(parent, view, groupPosition, childPosition, id, filterName);
            //((CheckBox)(filterItems.get(childPosition).findViewById(R.id.filterListItem))).setChecked(true);
            filter.setChecked(true);
        }
        mSearchStringView.setText(searchString);
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
}
