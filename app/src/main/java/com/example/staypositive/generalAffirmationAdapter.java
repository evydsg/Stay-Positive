package com.example.staypositive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GeneralAffirmationAdapter extends RecyclerView.Adapter<GeneralAffirmationAdapter.ViewHolder> {

    private List<String> generalAffirmationsData;
    private Context context;

    public GeneralAffirmationAdapter(Context context, List<String> generalAffirmationsData) {
        this.context = context;
        this.generalAffirmationsData = generalAffirmationsData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.general_affirmations, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String affirmation = generalAffirmationsData.get(position);
        holder.tvAffirmation.setText(affirmation);
    }

    @Override
    public int getItemCount() {
        return generalAffirmationsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAffirmation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAffirmation = itemView.findViewById(R.id.tv_affirmation);
        }
    }
}
