package com.example.todoapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.Model.CalendarHolder;
import com.example.todoapp.R;

import java.util.ArrayList;

public class CalendarAdapter extends   RecyclerView.Adapter<CalendarHolder>  {

    private final ArrayList<String> celldaytxt;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<String> celldaytxt, OnItemListener onItemListener) {
        this.celldaytxt = celldaytxt;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.calender_cell,parent,false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);


        return new CalendarHolder(view, onItemListener);


    }

    @Override
    public void onBindViewHolder(@NonNull CalendarHolder holder, int position) {
        holder.celldaytxt.setText(celldaytxt.get(position));
    }



    @Override
    public int getItemCount() {
        return celldaytxt.size();
    }


    public interface  OnItemListener
    {
        void onItemClick(int position, String dayText);
    }

}
