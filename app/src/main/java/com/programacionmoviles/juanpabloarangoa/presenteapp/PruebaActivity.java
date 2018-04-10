package com.programacionmoviles.juanpabloarangoa.presenteapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Users;

import java.util.ArrayList;

public class PruebaActivity extends AppCompatActivity {

    private EditText eNombree, eEdadd;

    private ListView listView;
    private ArrayAdapter listAdapter;
    private ArrayList<String> listNombres;
    private ArrayList<Users> listUsuarios;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        eNombree = findViewById(R.id.eNombree);
        eEdadd = findViewById(R.id.eEdadd);
        listView = findViewById(R.id.listView);

        listNombres = new ArrayList<>();
        listUsuarios = new ArrayList<>();

        listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listNombres);
        listView.setAdapter(listAdapter);

        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listNombres.clear();
                listUsuarios.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Log.d("data",snapshot.toString());
                        Users users = snapshot.getValue(Users.class);
                        listNombres.add(users.getNombre());
                        listUsuarios.add(users);
                    }
                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Metodo para eliminar cuando deja presionado por un momento en el listview
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String uid = listUsuarios.get(position).getId();
                databaseReference.child("users").child(uid).removeValue();
                listNombres.remove(position);
                listUsuarios.remove(position);
                return false;
            }
        });

    }

    public void onGuardarPruebaClick(View view) {
        /*
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        Users users = new Users(firebaseUser.getUid(),
                firebaseUser.getDisplayName(),
                0);
        //databaseReference.child("users").setValue(users);
        */

        Users users = new Users(databaseReference.push().getKey(),
                eNombree.getText().toString(),
                Integer.parseInt(eEdadd.getText().toString()));

        databaseReference.child("users").child(users.getId()).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d("Entre1","OK");
                }else {
                    Log.d("Entre2","OK");
                    Log.d("Save", task.getException().toString());
                }
            }
        });

    }
}


//ERROR QUE SALIO
/*
 com.google.firebase.database.DatabaseException: Can't convert object of type java.lang.Long to type com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Users
 Se soluciona borrando users en la DB, porque eso me está creando dentro de la base de datos el users
 */
