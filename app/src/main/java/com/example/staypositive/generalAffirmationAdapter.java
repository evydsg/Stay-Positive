package com.example.staypositive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class generalAffirmationAdapter extends RecyclerView.Adapter <generalAffirmationAdapter.AffirmationViewHolder> {

    private List<String> generalAffirmations;

    public generalAffirmationAdapter(List<String> generalAffirmations)
    {
        this.generalAffirmations = generalAffirmations;
    }

    @NonNull
    @Override
    public generalAffirmationAdapter.AffirmationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.general_affirmations, parent, false);
        return new AffirmationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull generalAffirmationAdapter.AffirmationViewHolder holder, int position) {

        holder.affirmationTextView.setText(generalAffirmations.get(position));
    }

    @Override
    public int getItemCount() {
        return generalAffirmations.size();
    }

    static class AffirmationViewHolder extends RecyclerView.ViewHolder{
        TextView affirmationTextView;

        AffirmationViewHolder(@NonNull View itemView){
            super(itemView);
            affirmationTextView = itemView.findViewById(R.id.general_affirmations_textView);
        }
    }
}
