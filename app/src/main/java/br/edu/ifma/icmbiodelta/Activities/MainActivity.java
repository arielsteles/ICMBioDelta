package br.edu.ifma.icmbiodelta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import br.edu.ifma.icmbiodelta.Fragments.Main.LoginFragment;
import br.edu.ifma.icmbiodelta.R;

public class MainActivity extends AppCompatActivity {
    LoginFragment loginFragment = new LoginFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow()
                .setFlags(WindowManager.LayoutParams
                                .FLAG_LAYOUT_NO_LIMITS
                        , WindowManager.LayoutParams
                                .FLAG_LAYOUT_NO_LIMITS);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerMain, loginFragment)
                .commit();
    }
}