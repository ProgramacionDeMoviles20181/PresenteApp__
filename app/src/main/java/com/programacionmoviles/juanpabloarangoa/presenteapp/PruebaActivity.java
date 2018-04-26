package com.programacionmoviles.juanpabloarangoa.presenteapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Users;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PruebaActivity extends AppCompatActivity {

    private EditText eNombree, eEdadd;

    private ListView listView;
    private ArrayAdapter listAdapter;
    private ArrayList<String> listNombres;
    private ArrayList<Users> listUsuarios;

    private CircleImageView iFoto;
    private DatabaseReference databaseReference;

    private String urlFoto = "No se ha cargado";

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        eNombree = findViewById(R.id.eNombree);
        eEdadd = findViewById(R.id.eEdadd);
        listView = findViewById(R.id.listView);
        iFoto = findViewById(R.id.iFoto);

        listNombres = new ArrayList<>();
        listUsuarios = new ArrayList<>();


        final UsuarioAdapter usuarioAdapter = new UsuarioAdapter(this,listUsuarios);

        /*listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listNombres);
        listView.setAdapter(listAdapter);*/ //Porque es para un listview simple

        listView.setAdapter(usuarioAdapter);

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
                //listAdapter.notifyDataSetChanged(); Es para list view simple
                usuarioAdapter.notifyDataSetChanged();
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

    public void fotoClicked(View view) {

        Intent fotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        fotoIntent.setType("image/");
        startActivityForResult(fotoIntent, 12345);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12345 && resultCode == RESULT_OK){
            if(data == null){
                Toast.makeText(this,"Error cargando foto", Toast.LENGTH_SHORT).show();

            }else{
                Uri imagen = data.getData();

                try {
                    InputStream is = getContentResolver().openInputStream(imagen);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    bitmap = BitmapFactory.decodeStream(bis);

                    iFoto.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class UsuarioAdapter extends ArrayAdapter<Users>{
        public UsuarioAdapter(@NonNull Context context, ArrayList<Users> data) {
            super(context, R.layout.prueba_list_item ,data);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.prueba_list_item, null);

            Users user = getItem(position);

            TextView nombre = item.findViewById(R.id.tNombreee);
            nombre.setText(user.getNombre());

            TextView edad = item.findViewById(R.id.eEdadd);
            edad.setText(String.valueOf(user.getEdad()));

            CircleImageView iFoto2 = item.findViewById(R.id.iFoto);

            Picasso.get().load(user.getFoto()).into(iFoto2);

            return item;
        }
    }



    public void onGuardarPruebaClick(View view) {
        /*

        //databaseReference.child("users").setValue(users);
        */
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getInstance().getReference();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //Comprimir
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        storageReference.child("estudiantesFotos").child(databaseReference.push().getKey())
                .putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                urlFoto = taskSnapshot.getDownloadUrl().toString();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("error", e.getMessage().toString());
            }
        });

        Users users = new Users(databaseReference.push().getKey(),
                eNombree.getText().toString(),
                Integer.parseInt(eEdadd.getText().toString()), urlFoto);

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
