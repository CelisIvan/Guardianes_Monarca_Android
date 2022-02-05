package com.example.guardianesmonarca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    Button buttonlogout;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mFirebaseAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemReselectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new AccountFragment()).commit();
        /*
        buttonlogout= findViewById(R.id.logout);

        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intToLogin= new Intent (HomeActivity.this,LoginActivity.class);
                startActivity(intToLogin);
            }
        });*/
    }
    private BottomNavigationView.OnNavigationItemReselectedListener navListener =
            new BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch(menuItem.getItemId()){
                        case R.id.nav_events:
                            selectedFragment = new EventFragment();
                            break;
                        case R.id.nav_location:
                            selectedFragment = new LocationFragment();
                            break;
                        case R.id.nav_user_account:
                            selectedFragment = new AccountFragment();
                            break;
                        case R.id.nav_seeds:
                            selectedFragment = new SeedsFragment();
                            break;
                        case R.id.nav_settings:
                            selectedFragment = new SettingsFragment();
                            break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
            }
    };




}
