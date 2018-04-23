package com.programacionmoviles.juanpabloarangoa.presenteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.programacionmoviles.juanpabloarangoa.presenteapp.comunicaciones.comunicador_logout;

public class MainActivity extends AppCompatActivity  implements GoogleApiClient.OnConnectionFailedListener,comunicador_logout {

    FragmentManager fm;
    FragmentTransaction ft;
    Button bLogout;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleApiClient mGoogleApiClient;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ft = fm.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    ProfileFragment fProfile = new ProfileFragment();
                    ft.replace(R.id.frame, fProfile).commit();
                    return true;
                case R.id.navigation_home:
                    HomeFragment fHome = new HomeFragment();
                    ft.replace(R.id.frame, fHome).commit();
                    return true;
                case R.id.navigation_cursos:
                    CoursesFragment fCourses = new CoursesFragment();
                    ft.replace(R.id.frame, fCourses).commit();
                    return true;
                case R.id.navigation_clase:
                    StartclassFragment fstartclass = new StartclassFragment();
                    ft.replace(R.id.frame, fstartclass).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializeFirebaseLogin();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        HomeFragment fHome = new HomeFragment();
        ft.add(R.id.frame, fHome).commit();
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

                    //----Daño agregado
                    //String provider = firebaseUser.getProviders().get(0);
                    //Log.d("Tipo de Logueo", provider );


                }else{
                    Log.d("Firebase user","el usuario ha cerrado sesión");
                    goLoginActivity();
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


    }

    private void goLoginActivity() {
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void envioDatosLogOut(int viewId) {
        firebaseAuth.signOut();
        if(Auth.GoogleSignInApi != null){
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if(status.isSuccess()){
                        goLoginActivity();
                    }else{
                        Toast.makeText(MainActivity.this,"Error cerrando sesión con google",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }if(LoginManager.getInstance() != null){
            LoginManager.getInstance().logOut();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}


