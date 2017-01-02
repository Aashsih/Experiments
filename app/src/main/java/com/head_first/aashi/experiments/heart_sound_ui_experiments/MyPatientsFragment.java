package com.head_first.aashi.experiments.heart_sound_ui_experiments;

import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.head_first.aashi.experiments.R;
import com.head_first.aashi.experiments.utils.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyPatientsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyPatientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPatientsFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    private View mRootView;
    private SearchView mSearchView;
    private ExpandableListView mExpandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private HashMap<String, List<String>> filterContentMap;
    private HashMap<String, List<String>> searchContentMap;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyPatientsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPatientsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPatientsFragment newInstance(String param1, String param2) {
        MyPatientsFragment fragment = new MyPatientsFragment();
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
        mRootView = inflater.inflate(R.layout.heart_sound_ui_experiments_fragment_my_patients, container, false);
        setHasOptionsMenu(true);

        //SearchView Setup
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) mRootView.findViewById(R.id.patientSearchView);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);

        //Populate Data in the ExpandableListView
        setupExpandableListView();

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

        //This hashMap will actually be created by info that was retrieved from the database based on the filters selected by the user
        searchContentMap = new HashMap<>();
        filterContentMap = new HashMap<>();
        filterContentMap.put(groupHeaders.get(0), groupHeader1Items);
        filterContentMap.put(groupHeaders.get(1), groupHeader2Items);
        filterContentMap.put(groupHeaders.get(2), groupHeader3Items);

        //set the content for searchContentMap here
        PatientSearch.onQueryTextSubmit("", filterContentMap, searchContentMap);
        expandableListAdapter = new ExpandableListAdapter(getContext(), groupHeaders, searchContentMap);
        mExpandableListView = (ExpandableListView) mRootView.findViewById(R.id.expandableListView);
        mExpandableListView.setAdapter(expandableListAdapter);
    }

    //SearchView onCreateOptionsMenu implementation
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.heart_sound_ui_experiments_search_view_menu, menu);
    }

    //SearchView.OnQueryTextListener Implementation
    @Override
    public boolean onQueryTextSubmit(String query) {
        PatientSearch.onQueryTextSubmit(query, filterContentMap, searchContentMap);
        expandableListAdapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        PatientSearch.onQueryTextChange(newText , filterContentMap, searchContentMap);
        expandableListAdapter.notifyDataSetChanged();
        return false;
    }

    //SearchView.OnCloseListener Implementation
    @Override
    public boolean onClose() {
        return false;
    }
}
