package com.example.todoapp.Model;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.Adapter.CalendarAdapter;
import com.example.todoapp.R;


public class CalendarHolder extends   RecyclerView.ViewHolder  implements View.OnClickListener{

    public TextView  celldaytxt;

    private final CalendarAdapter.OnItemListener onItemListener;


    public CalendarHolder(@NonNull View itemView,CalendarAdapter.OnItemListener onItemListener) {
        super(itemView);

        celldaytxt=itemView.findViewById(R.id.celldaytxt);

        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition(), (String) celldaytxt.getText());

    }
}
