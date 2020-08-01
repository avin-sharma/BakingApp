/**
 * Recycler View Adapter for showing List of Steps.
 * Used in StepsListFragment
 */

package com.example.bakingapp.ui.recipeDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.StepViewHolder> {
    private String[] mStepsNames;
    final private ListItemClickListener mOnClickListener;
    private int selectedPos = RecyclerView.NO_POSITION;

    public StepsListAdapter(String[] mStepsNames, ListItemClickListener listener) {
        this.mStepsNames = mStepsNames;
        mOnClickListener = listener;
    }

    interface ListItemClickListener{
        void onListItemClick(int position);
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView stepName;
        TextView stepNumber;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            stepName = itemView.findViewById(R.id.tv_step_name);
            stepNumber = itemView.findViewById(R.id.tv_step_number);
        }

        private void bind(String stepName, int position) {
            this.stepName.setText(stepName);

            // If we are showing ingredients hide step number
            if (position == 0){
                stepNumber.setVisibility(View.GONE);
            }else {
                stepNumber.setVisibility(View.VISIBLE);
                stepNumber.setText("Step " + (position - 1));
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnClickListener.onListItemClick(getAdapterPosition());
            notifyItemChanged(selectedPos);
            selectedPos = getAdapterPosition();
            notifyItemChanged(selectedPos);
        }
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.step_list_item, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bind(mStepsNames[position], position);
        holder.itemView.setSelected(position == selectedPos);
    }

    @Override
    public int getItemCount() {
        return mStepsNames.length;
    }

}
