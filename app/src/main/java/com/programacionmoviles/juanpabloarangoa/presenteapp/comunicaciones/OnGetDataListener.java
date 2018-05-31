package com.programacionmoviles.juanpabloarangoa.presenteapp.comunicaciones;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface OnGetDataListener {
    public void onStartData();
    public void onSuccess(DataSnapshot data);
    public void onFailed(DatabaseError databaseError);
}