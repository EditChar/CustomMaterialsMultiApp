package com.ing.custommaterials;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UpdateDetailsAdapter extends RecyclerView.Adapter<UpdateDetailsAdapter.ViewHolder> {
    private final List<String> updateDetailsList;

    public UpdateDetailsAdapter(List<String> updateDetailsList) {
        this.updateDetailsList = updateDetailsList;
    }

    @NonNull
    @Override
    public UpdateDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.update_details_adapter_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateDetailsAdapter.ViewHolder holder, int position) {
        //set Details to the textview
        holder.updateDetailsTxt.setText(updateDetailsList.get(position));
    }

    @Override
    public int getItemCount() {
        return updateDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView updateDetailsTxt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            updateDetailsTxt = itemView.findViewById(R.id.updateDetailsTxt);
        }
    }
}
