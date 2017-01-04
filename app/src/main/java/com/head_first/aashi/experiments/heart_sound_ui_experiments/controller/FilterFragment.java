package com.head_first.aashi.experiments.heart_sound_ui_experiments.controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.head_first.aashi.experiments.R;
import com.head_first.aashi.experiments.heart_sound_ui_experiments.model.Filter;
import com.head_first.aashi.experiments.utils.ExpandableFilterListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FilterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends Fragment {

    public static final String FILTER_CONTENT_MAP_TAG = "FILTER_CONTENT_MAP_TAG";

    //View related variables
    private View mRootView;
    private TextView mSearchStringView;
    private Button mSearchButton;
    private ExpandableListView mExpandableListFilter;
    private ExpandableFilterListAdapter expandableFilterAdapter;

    //Data for the Fragment
    private Filter filter;

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
        Bundle bundle = getArguments();
        if (bundle != null) {
            //Get the filter object from the Fragment that launched this
            if((filter =(Filter) bundle.getSerializable(FILTER_CONTENT_MAP_TAG)) != null){

            }
            else{
                //Throw an exception here
            }
        }
        else{
            //Throw an exception here
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
        mSearchStringView.setText(filter.getSearchString());
        mSearchButton = (Button) mRootView.findViewById(R.id.searchButton);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //use the information stored in the filter object to get all the related data from the database
                //then finish this fragment
            }
        });

        //Expandable Filter
        mExpandableListFilter = (ExpandableListView) mRootView.findViewById(R.id.expandableFilterView);
        expandableFilterAdapter = new ExpandableFilterListAdapter(getContext(), filter.getFilterContent());
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(this.FILTER_CONTENT_MAP_TAG, filter);
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

    private void onFilterClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id){
        filter.onFilterSelected(parent, view, groupPosition, childPosition, id);
        expandableFilterAdapter.notifyDataSetChanged();
        mSearchStringView.setText(filter.getSearchString());
    }


}

//    private void restoreExpandableListViewState(){
//        if(!searchString.isEmpty()){
//            String[] filterGroup = StringUtil.split(searchString, SEARCH_STRING_GROUP_HEADER_SEPARATOR.charAt(0));
//            List<View> groupHeaderView = expandableFilterAdapter.getListGroupHeaderView();
//            for(int i = 0; i < groupHeaderView.size() && i < filterGroup.length; i++){
//                List<View> groupItemsView = expandableFilterAdapter.getItems(groupHeaderView.get(i));
//                String[] items = filterGroup[i].split(SEARCH_STRING_GROUP_ITEM_SEPARATOR);
//                for(int j = 0; j < groupItemsView.size(); j++){
//                    for(int k = 0; k < items.length; k++){
//                        CheckBox anItem = (CheckBox) groupItemsView.get(j);
//                        if(anItem.getText().toString().toLowerCase().equals(items[k].toLowerCase())){
//                            anItem.setChecked(true);
//                        }
//                    }
//                }
//            }
//        }
//    }
