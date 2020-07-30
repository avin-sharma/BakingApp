package com.example.bakingapp.ui.stepDetails;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.models.Step;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailsFragment extends Fragment {

    private Step  mStep;
    public static final String TAG = StepDetailsFragment.class.getSimpleName();
    public static final String STEP_KEY = "step-key";
    private TextView mTvDescription;

    public static StepDetailsFragment newInstance(Step step){
        Bundle bundle = new Bundle();
        bundle.putSerializable(STEP_KEY, step);
        StepDetailsFragment fragment = new StepDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mStep = (Step)getArguments().getSerializable(STEP_KEY);
        Log.d(TAG, mStep.getDescription());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvDescription = view.findViewById(R.id.tv_step_description);
        mTvDescription.setText(mStep.getDescription());
    }
}