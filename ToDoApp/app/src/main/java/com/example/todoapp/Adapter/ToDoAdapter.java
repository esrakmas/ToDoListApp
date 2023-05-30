package com.example.todoapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.AddTask;
import com.example.todoapp.GorevF;
import com.example.todoapp.Model.ToDoModel;
import com.example.todoapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ToDoAdapter extends   RecyclerView.Adapter<ToDoAdapter.Myholder> {
    private List<ToDoModel> todolist;
    private GorevF fragment;
    private FirebaseFirestore firestore;

    public ToDoAdapter(GorevF fragment ,List<ToDoModel> todolist){
        this.todolist=todolist;
        this.fragment = fragment;

    }

    @NonNull
    @Override
    public ToDoAdapter.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view= LayoutInflater.from(fragment.requireContext()).inflate(R.layout.task,parent,false);
        firestore=FirebaseFirestore.getInstance();

        return new Myholder(view);
    }


    public void deleteTask(int position){
        ToDoModel toDoModel = todolist.get(position);
        firestore.collection("gorev").document(toDoModel.GorevId).delete();
        todolist.remove(position);
        notifyItemRemoved(position);
    }
    public Context getContext(){
        return fragment.requireContext() ;
    }


    public void editTask(int position){
        ToDoModel toDoModel = todolist.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("gorev" , toDoModel.getGorev());
        bundle.putString("yapıldı" , toDoModel.getYapıldı());
        bundle.putString("id" , toDoModel.GorevId);

        AddTask addNewTask = new AddTask();
        addNewTask.setArguments(bundle);
        addNewTask.show(fragment.getChildFragmentManager(), addNewTask.getTag());
    }



    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.Myholder holder, int position) {
        ToDoModel toDoModel=todolist.get(position);
        holder.mycheckbox.setText(toDoModel.getGorev());
        holder.txtTarih.setText("tamamlandı " + toDoModel.getYapıldı());


        holder.mycheckbox.setChecked(toBoolean(toDoModel.getDurum()));
        holder.mycheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if(b){
                    firestore.collection("gorev").document(toDoModel.GorevId).update("durum",1);


                }else{
                    firestore.collection("gorev").document(toDoModel.GorevId).update("durum",0);

                }

            }
        });

    }

    private boolean toBoolean(int durum){
        return durum!=0;
    }


    @Override
    public int getItemCount() {
        return todolist.size();
    }


    public class Myholder extends RecyclerView.ViewHolder {

        TextView  txtTarih;//mDueDatetv
        CheckBox mycheckbox;
        public Myholder(@NonNull View itemView) {
            super(itemView);

            txtTarih=itemView.findViewById(R.id.txtTarih);
            mycheckbox=itemView.findViewById(R.id.mycheckbox);


        }
    }
}
