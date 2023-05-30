package com.example.todoapp.Model;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

public class GorevId {
    @Exclude
    public String GorevId;
    public <T extends  GorevId> T withId (@NonNull final String id){
        this.GorevId=id;
        return (T) this;

    }


}
