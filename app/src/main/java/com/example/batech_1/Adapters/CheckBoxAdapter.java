package com.example.batech_1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.batech_1.R;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.List;

public class CheckBoxAdapter extends RecyclerView.Adapter<CheckBoxAdapter.ViewHolder> {
    Context context;
    List<String> cb_text;
    List<String> selectedValues;


    public CheckBoxAdapter(Context requireContext, List<String> cb_text1) {
        context = requireContext;
        cb_text = cb_text1;
    }

    @NonNull
    @Override
    public CheckBoxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CheckBoxAdapter.ViewHolder holder, int position) {
        String all = cb_text.get(position);

        holder.cb.setText(cb_text.get(position));

        holder.cb.setOnClickListener(v->{
            if(holder.cb.isChecked()){
                selectedValues.add(all);
            }else{
                selectedValues.remove(all);
            }
        });
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
            selectedValues = new ArrayList<>();
        }
    }

    public List<String> getSelectedValues(){return selectedValues;}
}
