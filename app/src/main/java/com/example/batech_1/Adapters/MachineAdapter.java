package com.example.batech_1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.batech_1.ModelClasses.MachineModelClass;
import com.example.batech_1.R;

import java.util.ArrayList;

public class MachineAdapter extends RecyclerView.Adapter<MachineAdapter.ViewHolder> {

    ArrayList<MachineModelClass> modelClasses;

    Context context;
    public int counter = 0;
    String size;
    Animation card_animation_fade_in,recyc_anim;
    int last_itemcount = -1;

    public MachineAdapter(ArrayList<MachineModelClass> modelClasses, Context context) {
        this.modelClasses = modelClasses;
        this.context = context;
    }

    @NonNull
    @Override
    public MachineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.machineslayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MachineAdapter.ViewHolder holder, int position) {
        MachineModelClass machineModelClass = modelClasses.get(position);
        String name, model, desc;
            name = machineModelClass.getName();
            model = machineModelClass.getModel_number();
            desc = machineModelClass.getDesc();

        if(holder.getAdapterPosition()>last_itemcount){
            recyc_anim = AnimationUtils.loadAnimation(context,R.anim.recyc_animation);
            holder.cons.startAnimation(recyc_anim);
           holder.tv_name.setText(name);
           holder.tv_name.setText(name);

           holder.tv_model.setText(model);
            holder.tv_name.setText(name);

           holder.tv_desc.setText(desc);
            holder.tv_desc.getRootView();
//           last_itemcount = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return modelClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name, tv_model, tv_desc;
        ConstraintLayout cons;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_product_name);
            tv_model = itemView.findViewById(R.id.tv_product_model);
            tv_desc = itemView.findViewById(R.id.tv_product_desc);
            cons = itemView.findViewById(R.id.cons);
        }
    }
}
