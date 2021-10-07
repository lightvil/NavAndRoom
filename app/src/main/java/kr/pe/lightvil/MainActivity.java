package kr.pe.lightvil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kr.pe.lightvil.databinding.ActivityMainBinding;
import kr.pe.lightvil.repositories.CallLogRepo;
import kr.pe.lightvil.repositories.CallLogRepository;

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

        repository = new CallLogRepository(getApplicationContext());
    }

    public CallLogRepository getRepository() {
        return repository;
    }
}