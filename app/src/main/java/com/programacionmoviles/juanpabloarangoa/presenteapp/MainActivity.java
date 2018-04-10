package com.programacionmoviles.juanpabloarangoa.presenteapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    FragmentManager fm;
    FragmentTransaction ft;


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

        BottomNavigationView navigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        HomeFragment fHome = new HomeFragment();
        ft.add(R.id.frame, fHome).commit();
    }

}

