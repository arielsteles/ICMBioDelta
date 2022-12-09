package br.edu.ifma.icmbiodelta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.edu.ifma.icmbiodelta.Fragments.Navigation.AboutFragment;
import br.edu.ifma.icmbiodelta.Fragments.Navigation.CreatorFragment;
import br.edu.ifma.icmbiodelta.Fragments.Navigation.HomeFragment;
import br.edu.ifma.icmbiodelta.Fragments.Navigation.ProfileFragment;
import br.edu.ifma.icmbiodelta.Helpers.CurrentUserListener;
import br.edu.ifma.icmbiodelta.Models.UserData;
import br.edu.ifma.icmbiodelta.R;

public class NavigationActivity extends AppCompatActivity implements CurrentUserListener {
    AboutFragment aboutFragment = new AboutFragment();
    CreatorFragment creatorFragment = new CreatorFragment();
    HomeFragment homeFragment = new HomeFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    private BottomNavigationView bottomNavigationView;
    UserData userData = new UserData();
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerNav, homeFragment)
                .commit();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        setBottomNavigationView();
    }

    @Override
    public void setUser(UserData user) {
        userType = user.getTipo();
    }

    @SuppressLint("NonConstantResourceId")
    public void setBottomNavigationView() {
       /*
       if (userType.equals("public")) {
            if (bottomNavigationView.getMenu().findItem(R.id.bottom_nav_add) != null) {
                bottomNavigationView.getMenu().removeItem(R.id.bottom_nav_add);
            }
        }
        */
        bottomNavigationView
                .setOnItemSelectedListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.bottom_nav_home:
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.containerNav, homeFragment)
                                    .commit();
                            return true;
                        case R.id.bottom_nav_add:
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.containerNav, creatorFragment)
                                    .commit();
                            return true;
                        case R.id.bottom_nav_profile:
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.containerNav, profileFragment)
                                    .commit();
                            return true;
                        case R.id.bottom_nav_info:
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.containerNav, aboutFragment)
                                    .commit();
                            return true;
                    }
                    return false;
                });
    }
}