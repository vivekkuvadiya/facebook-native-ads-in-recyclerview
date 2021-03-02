package com.example.myapplication.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class DataViewHolder extends RecyclerView.ViewHolder {

    public TextView title, description;

    public DataViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.tvTitle);
        description = itemView.findViewById(R.id.tvDiscription);
    }
}
