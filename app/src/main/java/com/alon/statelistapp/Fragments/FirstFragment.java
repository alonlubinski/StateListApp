package com.alon.statelistapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.alon.statelistapp.Controllers.StateListController;
import com.alon.statelistapp.Activities.MainActivity;
import com.alon.statelistapp.Models.State;
import com.alon.statelistapp.R;
import com.alon.statelistapp.Adapters.StateAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText first_EDT_input;
    private RecyclerView first_RCV;
    private RecyclerView.LayoutManager layoutManager;
    private StateAdapter stateAdapter;
    private ArrayList<State> allStates = new ArrayList<>();
    private ProgressBar first_PGB;
    private StateAdapter.OnItemClickListener clickListener;
    private boolean firstLoadDone = false;

    private OnFirstFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
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
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        findAll(view);
        first_EDT_input.setEnabled(false);
        if(firstLoadDone){
            first_PGB.setVisibility(View.GONE);
            first_EDT_input.setEnabled(true);
        }
        clickListener = new StateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(State state) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.LoadSecFragment(state);
            }
        };
        StateListController stateListController = new StateListController();
        if(!firstLoadDone) {
            stateListController.start(new StateListController.Callback_StateList() {
                @Override
                public void onResult(ArrayList<State> stateList) {
                    allStates = stateList;
                    stateAdapter = new StateAdapter(getActivity(), allStates, clickListener);
                    initRecyclerView();
                    first_PGB.setVisibility(View.GONE);
                    first_EDT_input.setEnabled(true);
                    firstLoadDone = true;
                }
            });
        }

        first_EDT_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                stateAdapter = new StateAdapter(getActivity(), filterStateArray(allStates, s), clickListener);
                initRecyclerView();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void initRecyclerView() {
        first_RCV.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        first_RCV.setLayoutManager(layoutManager);
        first_RCV.setAdapter(stateAdapter);
    }


    private void findAll(View view) {
        first_EDT_input = view.findViewById(R.id.first_EDT_input);
        first_RCV = view.findViewById(R.id.first_RCV);
        first_PGB = view.findViewById(R.id.first_PGB);
    }

    private ArrayList<State> filterStateArray(ArrayList<State> arr, CharSequence charSequence) {
        ArrayList<State> filteredArr = new ArrayList<>();
        String str = "";
        if(charSequence.length() != 0)
            str = charSequence.subSequence(0, 1).toString().toUpperCase() + charSequence.subSequence(1, charSequence.length());
        for(State state : arr){
            if(state.getName().startsWith(str)){
                filteredArr.add(state);
            }
        }
        return filteredArr;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFirstFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFirstFragmentInteractionListener) {
            mListener = (OnFirstFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
    public interface OnFirstFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFirstFragmentInteraction(Uri uri);
    }
}