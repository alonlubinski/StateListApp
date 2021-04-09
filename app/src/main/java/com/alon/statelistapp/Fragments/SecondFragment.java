package com.alon.statelistapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alon.statelistapp.Controllers.StateController;
import com.alon.statelistapp.Models.State;
import com.alon.statelistapp.R;
import com.alon.statelistapp.Adapters.StateAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView second_LBL_borders;
    private RecyclerView second_RCV;
    private RecyclerView.LayoutManager layoutManager;
    private StateAdapter stateAdapter;
    private ArrayList<State> borderStates;
    private ProgressBar second_PGB;
    private int arrSize;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
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
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        findAll(view);
        Intent intent = getActivity().getIntent();
        State state = (State) intent.getSerializableExtra("StateOb");
        second_LBL_borders.setText(state.getName() + " has borders with:");
        borderStates = new ArrayList<>();
        StateController stateController = new StateController();
        arrSize = state.getBorders().size();
        for (String borderState : state.getBorders()) {
            stateController.start(borderState, new StateController.Callback_State() {
                @Override
                public void onResult(State state) {
                    borderStates.add(state);
                    if(borderStates.size() == arrSize) {
                        stateAdapter = new StateAdapter(getActivity(), borderStates, null);
                        initRecyclerView();
                        second_PGB.setVisibility(View.GONE);
                    }
                }
            });
        }

        if (state.getBorders().size() == 0) {
            Toast.makeText(getActivity(), "No borders", Toast.LENGTH_LONG).show();
            second_LBL_borders.setVisibility(View.GONE);
            second_PGB.setVisibility(View.GONE);
        }


        return view;
    }

    private void initRecyclerView() {
        second_RCV.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        second_RCV.setLayoutManager(layoutManager);
        second_RCV.setAdapter(stateAdapter);
    }

    private void findAll(View view) {
        second_LBL_borders = view.findViewById(R.id.second_LBL_borders);
        second_RCV = view.findViewById(R.id.second_RCV);
        second_PGB = view.findViewById(R.id.second_PGB);
    }
}