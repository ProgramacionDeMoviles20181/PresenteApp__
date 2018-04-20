package com.programacionmoviles.juanpabloarangoa.presenteapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Estudiantes;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Profesor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private int registerRequest = 002, google_login_request = 001;
    private EditText eMail,ePassword;

    private String[] sProfileType = {"Estudiantes","Profesores"};
    private String sProfile;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private GoogleApiClient googleApiClient;
    private SignInButton bGoogleSignIn;

    private CallbackManager callbackManager;
    private LoginButton btnSignInFacebook;

    private String sEmail      ="";
    private String sPass       ="";
    private String sName       ="";
    private String sMobile     ="";
    private int edad           =0;
    private String sFoto       ="";
    private String sCarnet     ="";
    private String sUniversity ="";
    private boolean bProfile   = true;
    private String Uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());

        eMail     = findViewById(R.id.eMail);
        ePassword = findViewById(R.id.ePassword);
        bGoogleSignIn = findViewById(R.id.bGoogleLogin);
        btnSignInFacebook = findViewById(R.id.btnSignInFacebook);

        inicializeFirebaseLogin();
        callbackManager = CallbackManager.Factory.create();

        btnSignInFacebook.setReadPermissions("email","public_profile");

        btnSignInFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Login con Facebook", "Login Exitoso");
                signInFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("Login con Facebook", "Login Cancelado");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Login con Facebook", "Login Error");
                error.printStackTrace();
            }
        });

        //Facebook-- Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.programacionmoviles.juanpabloarangoa.presenteapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

    }
    }

    private void signInFacebook(AccessToken accessToken){
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());

        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Firebase Message",task.getResult().toString());
                if(task.isSuccessful()){
                    goMainActivity();
                }else{
                    Toast.makeText(LoginActivity.this, "Autenticacion con Facebook no exitosa", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inicializeFirebaseLogin() {
        firebaseAuth      = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    Log.d("Firebase user","usuario logueado: "+firebaseUser.getDisplayName() );
                    Log.d("Firebase user","usuario logueado: "+firebaseUser.getEmail() );
                }else{
                    Log.d("Firebase user","el usuario ha cerrado sesión");
                }
            }
        };

        //Google Login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        bGoogleSignIn.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(i,google_login_request);
            }
        });

    }

    public void onTextClick(View view) {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivityForResult(intent,registerRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == registerRequest && resultCode == RESULT_OK){
            Toast.makeText(this,"Registro Completado", Toast.LENGTH_LONG).show();
            Bundle extras       = data.getExtras();
            sEmail       = extras.getString("email");
            sPass        = extras.getString("password");
            sName        = extras.getString("nombre");
            sMobile      = extras.getString("telefono");
            edad            = extras.getInt("edad");
            sFoto        = extras.getString("fotoLink");
            sCarnet      = extras.getString("cedula");
            sUniversity  = extras.getString("universidad");
            bProfile     = extras.getBoolean("bProfile");
            Uid          = extras.getString("uid");

            createCuenta();

            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();

        }else if(requestCode == registerRequest && resultCode == RESULT_CANCELED){
            Toast.makeText(this,"Registro Cancelado" , Toast.LENGTH_LONG).show();
        }else if(requestCode == google_login_request){
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            signInWithGoogle(googleSignInResult);
        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void signInWithGoogle(GoogleSignInResult googleSignInResult) {
        if(googleSignInResult.isSuccess()){
            AuthCredential authCredential = GoogleAuthProvider.getCredential(
                    googleSignInResult.getSignInAccount().getIdToken(),null);
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            goMainActivity();
                        }
                    });
        }else{
            Toast.makeText(LoginActivity.this,"error en autenticación con Google" , Toast.LENGTH_LONG).show();
        }
    }

    private void goMainActivity() {
        createCuenta("Estudiantes");
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void createCuenta() {
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(sEmail,sPass);

        FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        if (bProfile) {
            sProfile = sProfileType[1];
        } else {
            sProfile = sProfileType[0];
        }
        String a = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child(sProfile).child(a).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("CreateCuenta():", "usuario creado");
                } else {
                    Log.d("CreateCuenta():", "usuario no creado");
                    if(bProfile){
                        Profesor profe = new Profesor(
                                Uid,
                                sName,
                                sMobile,
                                edad,
                                "gs://presenteapp2.appspot.com/improfile.png",
                                sCarnet,
                                sUniversity
                        );
                        databaseReference.child(sProfileType[1]).child(String.valueOf(Uid)).setValue(profe);
                    }else{
                        Estudiantes est = new Estudiantes(
                                Uid,
                                sName,
                                sMobile,
                                edad,
                                "gs://presenteapp2.appspot.com/improfile.png",
                                sCarnet,
                                sUniversity
                        );
                        databaseReference.child(sProfileType[0]).child(String.valueOf(Uid)).setValue(est);
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void createCuenta(String sProfile) {
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        bProfile = sProfile.equals(sProfileType[1]);
        databaseReference.child(sProfile).child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("CreateCuenta():", "usuario creado");
                } else {
                    Log.d("CreateCuenta():", "usuario no creado");
                    if(bProfile){
                        Profesor profe = new Profesor(
                                firebaseUser.getUid(),
                                sName,
                                sMobile,
                                edad,
                                "gs://presenteapp2.appspot.com/improfile.png",
                                sCarnet,
                                sUniversity
                        );
                        databaseReference.child(sProfileType[1]).child(firebaseUser.getUid()).setValue(profe);
                    }else{
                        Estudiantes est = new Estudiantes(
                                firebaseUser.getUid(),
                                sName,
                                sMobile,
                                edad,
                                "gs://presenteapp2.appspot.com/improfile.png",
                                sCarnet,
                                sUniversity
                        );
                        databaseReference.child(sProfileType[0]).child(firebaseUser.getUid()).setValue(est);
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void onButtonClick(View view) {
        String mail,passw;
        mail  = eMail.getText().toString();
        passw = ePassword.getText().toString();
        if(!mail.equals("") && !passw.equals("")) {
            logInTask(mail,passw);
        }else{
            Toast.makeText(LoginActivity.this,"error en inicio de sesión" , Toast.LENGTH_LONG).show();
        }
    }

    private void logInTask(String mail,String passw) {
        firebaseAuth.signInWithEmailAndPassword(mail,passw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            goMainActivity();
                        }else{
                           Log.d("Error!!!!!!!!!!!!!!!",task.toString());
                           Toast.makeText(LoginActivity.this,"error en inicio de sesión" , Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
