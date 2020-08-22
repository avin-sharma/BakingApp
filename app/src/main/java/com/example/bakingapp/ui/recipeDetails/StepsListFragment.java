package com.example.bakingapp.ui.recipeDetails;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;

import java.util.Objects;

public class StepsListFragment extends Fragment {

    private RecyclerView mStepListRecyclerView;
    private StepsListFragmentInterface mStepsListFragmentInterface;
    private Context mContext;
    private static final String LAST_CLICK_POSITION = "lastClickPosition";

    public StepsListFragment() {
        // Required empty public constructor
    }

    interface StepsListFragmentInterface {
        String[] getStepsNames();
        int getLastClickedPosition();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mStepsListFragmentInterface = (StepsListFragmentInterface) context;
        mContext = context;
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
        int lastClickPosition = RecyclerView.NO_POSITION;
        if (savedInstanceState != null) {
            lastClickPosition = savedInstanceState.getInt(LAST_CLICK_POSITION);
        }
        mStepListRecyclerView = view.findViewById(R.id.rv_steps_list);
        // Item click listener is implemented in the activity since
        // all the work is done there including communication with
        // the step details fragment/activity.
        StepsListAdapter adapter = new StepsListAdapter(mStepsListFragmentInterface.getStepsNames(),
                (StepsListAdapter.ListItemClickListener) mContext, lastClickPosition);
        mStepListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(LAST_CLICK_POSITION, mStepsListFragmentInterface.getLastClickedPosition());
    }
}
