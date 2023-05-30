package com.example.todoapp;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BenF extends Fragment implements CloseListner{

    private TextView txtCompletedTasksCount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_ben, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtCompletedTasksCount = view.findViewById(R.id.txtCompletedTasksCount);
    }


    @Override
    public void closeL(DialogInterface dialogInterface) {

        // Update the completed task count
        int completedTasksCount = getCompletedTasksCount();
        txtCompletedTasksCount.setText("Completed Tasks: " + completedTasksCount);

    }
    private int getCompletedTasksCount() {
        int completedCount = 0;




        return completedCount;// Replace with your actual logic
    }


}