package com.cydinfo.roomandnav;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.os.Bundle;

import com.cydinfo.roomandnav.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.cydinfo.roomandnav.repositories.CallLogRepository;

public class MainActivity extends AppCompatActivity {
    CallLogRepository repository;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.main_bottom_navigation);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_first_tab, R.id.navigation_second_tab, R.id.navigation_settings
        ).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host);
        NavigationUI.setupWithNavController(binding.mainBottomNavigation, navController);

        repository = CallLogRepository.Companion.getInstance(getApplicationContext());
    }

    public CallLogRepository getRepository() {
        return repository;
    }
}