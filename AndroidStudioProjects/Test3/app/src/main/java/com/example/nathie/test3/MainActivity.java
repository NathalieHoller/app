package com.example.nathie.test3;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private FrameLayout mainFrame;
    private Fragment homeFragment;
    private Fragment favoritesFragment;
    private Fragment listFragment;
    private Fragment settingsFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFrame = (FrameLayout) findViewById(R.id.fragment_container);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigation);

        homeFragment = new HomeFragment();
        favoritesFragment = new FavoritesFragment();
        listFragment = new ListFragment();
        settingsFragment = new SettingsFragment();

        setFragment(homeFragment);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {

                    case R.id.nav_home:
                        setFragment(homeFragment);
                        return true;

                    case R.id.nav_favorites:
                        setFragment(favoritesFragment);
                        return true;

                    case R.id.nav_list:
                        setFragment(listFragment);
                        return true;

                    case R.id.nav_settings:
                        setFragment(settingsFragment);
                        return true;

                    default: return false;

                }
            }
        });
    }

    private void setFragment(Fragment fragment) {

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
