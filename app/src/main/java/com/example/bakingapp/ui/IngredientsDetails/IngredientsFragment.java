package com.example.bakingapp.ui.IngredientsDetails;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingapp.R;
import com.example.bakingapp.models.Ingredient;
import com.example.bakingapp.models.Step;
import com.example.bakingapp.ui.stepDetails.StepDetailsFragment;

public class IngredientsFragment extends Fragment {

    public static final String INGREDIENTS_KEY = "ingredients-key";
    public static final String TAG = IngredientsFragment.class.getSimpleName();

    private RecyclerView rvIngredientsList;
    private FragmentLoadStatus mLoadStatus;

    public interface FragmentLoadStatus {
        void isNowIdle();
    }

    public IngredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mLoadStatus = (FragmentLoadStatus) context;
    }

    public static IngredientsFragment newInstance(Ingredient[] ingredients){
        Bundle bundle = new Bundle();
        bundle.putSerializable(INGREDIENTS_KEY, ingredients);
        IngredientsFragment fragment = new IngredientsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredients, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Ingredient[] ingredients = (Ingredient[]) getArguments().getSerializable(INGREDIENTS_KEY);
        for (int i = 0; i < ingredients.length; i++) {
            Log.d(TAG, ingredients[i].getIngredients());
        }
        rvIngredientsList = view.findViewById(R.id.rv_ingredients_list);
        rvIngredientsList.setAdapter(new IngredientsListAdapter(ingredients));
        mLoadStatus.isNowIdle();
    }
}