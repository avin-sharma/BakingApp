package com.example.bakingapp.ui.recipeDetails;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;

public class StepsListFragment extends Fragment {

    private RecyclerView mStepListRecyclerView;
    private StepsListFragmentInterface mStepsListFragmentInterface;

    public StepsListFragment() {
        // Required empty public constructor
    }

    interface StepsListFragmentInterface {
        String[] getStepsNames();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mStepsListFragmentInterface = (StepsListFragmentInterface) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_steps_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStepListRecyclerView = view.findViewById(R.id.rv_steps_list);
        StepsListAdapter adapter = new StepsListAdapter(mStepsListFragmentInterface.getStepsNames());
        mStepListRecyclerView.setAdapter(adapter);
    }

}
