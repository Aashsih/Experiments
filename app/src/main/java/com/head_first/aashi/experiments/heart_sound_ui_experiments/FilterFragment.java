package com.head_first.aashi.experiments.heart_sound_ui_experiments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.head_first.aashi.experiments.R;
import com.head_first.aashi.experiments.utils.ExpandableListAdapter;

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
        // Required empty public constructor
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.heart_sound_ui_experiments_fragment_filter_layout, container, false);
        //Search bar
        mSearchStringView = (TextView) mRootView.findViewById(R.id.searchString);
        mSearchButton = (Button) mRootView.findViewById(R.id.searchButton);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fetch Data from the database or create a HashMap based on the filters applied
            }
        });

        //Expandable Filter
        setupExpandableListView();

        searchString = "";
        return mRootView;
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

    //Utility Methods for the fragment
    private void setupExpandableListView(){
        List<String> groupHeaders = new ArrayList<>();
        groupHeaders.add("Header 1");
        groupHeaders.add("Header 2");
        groupHeaders.add("Header 3");

        List<String> groupHeader1Items = new ArrayList<>();
        groupHeader1Items.add("Item1");
        groupHeader1Items.add("Item2");
        groupHeader1Items.add("Item3");

        List<String> groupHeader2Items = new ArrayList<>();
        groupHeader2Items.add("Item1");
        groupHeader2Items.add("Item2");
        groupHeader2Items.add("Item3");

        List<String> groupHeader3Items = new ArrayList<>();
        groupHeader3Items.add("Item1");
        groupHeader3Items.add("Item2");
        groupHeader3Items.add("Item3");

        //This hashMap will actually be created by info that was retrieved from the database based on
        //The Study table and the DoctorsPatient table (this one will determine which Doctors shared)
        //a Patient with the current user.
        filterContentMap = new HashMap<>();
        filterContentMap.put(groupHeaders.get(0), groupHeader1Items);
        filterContentMap.put(groupHeaders.get(1), groupHeader2Items);
        filterContentMap.put(groupHeaders.get(2), groupHeader3Items);
        //set the content for searchContentMap here
        expandableFilterAdapter = new ExpandableListAdapter(getContext(), filterContentMap, R.id.filterListGroup, R.id.filterListItem, R.layout.heart_sound_ui_experiments_expandable_filter_list_group, R.layout.heart_sound_ui_experiments_expandable_filter_list_item);
        mExpandableListFilter = (ExpandableListView) mRootView.findViewById(R.id.expandableFilterView);
        mExpandableListFilter.setAdapter(expandableFilterAdapter);
        mExpandableListFilter.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                FilterFragment.this.onFilterClick(parent, v, groupPosition, childPosition, id);
                return false;
            }
        });
    }

    private void onFilterClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id){
        CheckBox filter = (CheckBox) view.findViewById(R.id.filterListItem);
        String filterName = filter.getText().toString();
        if(filter.isChecked()){
            boolean searchStringStartsWithFilterName = searchString.toLowerCase().startsWith(filterName.toLowerCase());
            if(searchStringStartsWithFilterName){
                if(searchString.contains(",")){
                    searchString = searchString.replace(filterName + ",","");
                }
                else{
                    searchString = searchString.replace(filterName,"");
                }

            }
            else{
                searchString = searchString.replace("," + filterName,"");
            }
            filter.setChecked(false);
        }
        else{
            if(searchString.isEmpty()){
                searchString += filterName;
            }
            else{
                searchString += "," + filterName;
            }
            filter.setChecked(true);
        }
        mSearchStringView.setText(searchString);
    }

}
