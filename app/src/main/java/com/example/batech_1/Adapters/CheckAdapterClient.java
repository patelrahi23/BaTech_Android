package com.example.batech_1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.batech_1.R;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.List;

public class CheckAdapterClient  extends RecyclerView.Adapter<CheckAdapterClient.ViewHolder>{

    Context context;
    List<String> cb_text;
    List<String> selectedValues;

    public CheckAdapterClient(Context requireContext, List<String> cb_text1) {
        context = requireContext;
        cb_text = cb_text1;
    }

    @NonNull
    @Override
    public CheckAdapterClient.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckAdapterClient.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CheckAdapterClient.ViewHolder holder, int position) {
        holder.cb.setClickable(false);
        holder.cb.setText(cb_text.get(position));
        holder.cb.setChecked(true);
    }

    @Override
    public int getItemCount() {
        return cb_text.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCheckBox cb;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.cb);
        }
    }
}
