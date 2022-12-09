package br.edu.ifma.icmbiodelta.Fragments.Main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import br.edu.ifma.icmbiodelta.Activities.NavigationActivity;
import br.edu.ifma.icmbiodelta.R;

public class LoginFragment extends Fragment {
    RegisterFragment registerFragment = new RegisterFragment();
    private CheckBox checkBoxLogin;
    private EditText loginPassword;
    private TextView loginRegister;

    private EditText loginEmail;
    private Button buttonLogin;
    private Button buttonLoginGoogle;

    String[] messages = {"Preencha todos os campos!"};

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container
            , Bundle savedInstanceState) {
        View loginFragmentView = inflater
                .inflate(R.layout.fragment_login
                        , container
                        , false);
        loginPassword = loginFragmentView
                .findViewById(R.id.loginPassword);
        checkBoxLogin = loginFragmentView
                .findViewById(R.id.checkBoxLogin);
        showPassword();
        loginRegister = loginFragmentView
                .findViewById(R.id.loginRegister);
        register();

        loginEmail = loginFragmentView
                .findViewById(R.id.loginEmail);
        buttonLogin = loginFragmentView
                .findViewById(R.id.buttonLogin);
        login();

        buttonLoginGoogle = loginFragmentView
                .findViewById(R.id.buttonLoginGoogle);
        loginGloogle();
        // Inflate the layout for this fragment
        return loginFragmentView;
    }

    private void showPassword() {
        checkBoxLogin.setOnCheckedChangeListener((compoundButton, b) -> {
            if (checkBoxLogin.isChecked()) {
                loginPassword.setTransformationMethod(
                        HideReturnsTransformationMethod.getInstance());
            } else {
                loginPassword.setTransformationMethod(
                        PasswordTransformationMethod.getInstance());
            }
        });
    }

    private void register() {
        loginRegister.setOnClickListener(view -> {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerMain, registerFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    public void login() {
        buttonLogin
                .setOnClickListener(loginFragmentView -> {
                    String email = loginEmail
                            .getText()
                            .toString();
                    String password = loginPassword
                            .getText()
                            .toString();

                    if (email.isEmpty() || password.isEmpty()) {
                        Snackbar snackbar = Snackbar
                                .make(loginFragmentView
                                        , messages[0]
                                        , Snackbar.LENGTH_SHORT);
                        View view = snackbar.getView();
                        FrameLayout.LayoutParams params =
                                (FrameLayout.LayoutParams)
                                        view.getLayoutParams();
                        params.gravity = Gravity.TOP;
                        view.setLayoutParams(params);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();
                    } else {
                        AutenticarUsuario(loginFragmentView);
                    }
                });
    }

    private void AutenticarUsuario(View loginFragmentView) {
        String email = loginEmail
                .getText().toString();
        String senha = loginPassword
                .getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(getContext()
                                , NavigationActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        String erro;
                        try {
                            throw task.getException();
                        } catch (Exception e) {
                            erro = "Erro ao logar usuario";
                        }
                        Snackbar snackbar = Snackbar
                                .make(loginFragmentView
                                        , erro
                                        , Snackbar.LENGTH_SHORT);
                        View view = snackbar.getView();
                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)
                                view.getLayoutParams();
                        params.gravity = Gravity.TOP;
                        view.setLayoutParams(params);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();
                    }
                });
    }

    private void loginGloogle() {
        gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(getContext(), gso);
        buttonLoginGoogle.setOnClickListener(v -> {
            Intent i = gsc.getSignInIntent();
            startActivityForResult(i, 1234);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234) {
            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount gsa = task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider
                        .getCredential(gsa.getIdToken(), null);
                FirebaseAuth.getInstance()
                        .signInWithCredential(credential)
                        .addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Intent intent = new Intent(getContext()
                                        , NavigationActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            } else {
                                Toast.makeText(getContext(), task1.getException()
                                        .getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser usuarioAtual = FirebaseAuth
                .getInstance()
                .getCurrentUser();
        if (usuarioAtual != null) {
            Intent intent = new Intent(getContext()
                    , NavigationActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
}