package br.edu.ifma.icmbiodelta.Fragments.Main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import br.edu.ifma.icmbiodelta.Activities.MainActivity;
import br.edu.ifma.icmbiodelta.R;

public class RegisterFragment extends Fragment {
    private CheckBox checkBoxRegister;
    private EditText registerName;
    private EditText registerEmail;
    private EditText registerPassword;
    private EditText registerPasswordConfirm;
    private Button buttonRegister;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Mensagens de retorno
    String[] mensagens = {"Preencha todos os campos","Cadastro realizado com sucesso","As senhas não são iguais!"};

    String usuarioID;

    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container
            , Bundle savedInstanceState) {
        View registerFragmentView = inflater
                .inflate(R.layout.fragment_register
                        , container
                        , false);

        registerPassword = registerFragmentView
                .findViewById(R.id.registerPassword);
        registerPasswordConfirm = registerFragmentView
                .findViewById(R.id.registerPasswordConfirm);
        checkBoxRegister = registerFragmentView
                .findViewById(R.id.checkBoxRegister);
        showPassword();
        //Login Email and Password
        registerName = registerFragmentView.findViewById(R.id.registerName);
        registerEmail = registerFragmentView.findViewById(R.id.registerEmail);
        buttonRegister = registerFragmentView.findViewById(R.id.buttonRegister);
        //Metodo de registrar EmailAndPassword
        RegistrarEmailAndPassword();
        // Inflate the layout for this fragment
        return registerFragmentView;
    }

    private void showPassword() {
        checkBoxRegister.setOnCheckedChangeListener((compoundButton, b) -> {
            if (checkBoxRegister.isChecked()) {
                registerPassword.setTransformationMethod(
                        HideReturnsTransformationMethod.getInstance());
                registerPasswordConfirm.setTransformationMethod(
                        HideReturnsTransformationMethod.getInstance());
            } else {
                registerPassword.setTransformationMethod(
                        PasswordTransformationMethod.getInstance());
                registerPasswordConfirm.setTransformationMethod(
                        PasswordTransformationMethod.getInstance());
            }
        });
    }

    private void  RegistrarEmailAndPassword(){
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View registerFragmentView) {
                String nome = registerName.getText().toString();
                String email = registerEmail.getText().toString();
                String senha = registerPassword.getText().toString();
                String senhaConfirmar = registerPasswordConfirm.getText().toString();


                if (nome.isEmpty()||email.isEmpty()||senha.isEmpty()||senhaConfirmar.isEmpty()){
                    Snackbar snackbar = Snackbar.make(registerFragmentView,mensagens[0],Snackbar.LENGTH_LONG);
                    View view = snackbar.getView();
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    view.setLayoutParams(params);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    if(senha.equals(senhaConfirmar)){
                        CadastrarUsuario(registerFragmentView);
                        ReiniciarLogin();
                    }else {
                        Snackbar snackbar = Snackbar.make(registerFragmentView,mensagens[2],Snackbar.LENGTH_LONG);
                        View view = snackbar.getView();
                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                        params.gravity = Gravity.TOP;
                        view.setLayoutParams(params);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();
                    }
                }
            }
        });
    }

    private void CadastrarUsuario(View registerFragmentView) {
        String email = registerEmail.getText().toString();
        String senha = registerPassword.getText().toString();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    SalvarDadosUsuario();

                    Snackbar snackbar = Snackbar.make(registerName, mensagens[1], Snackbar.LENGTH_LONG);
                    View view = snackbar.getView();
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    view.setLayoutParams(params);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();


                } else {
                    String erro;
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Digite uma seha com no mínimo 6 caracteres!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "Esta conta já foi cadastrada";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "Email inválido";
                    } catch (Exception e) {
                        erro = "Erro ao cadastrar usuário";
                    }
                    Snackbar snackbar = Snackbar.make(registerFragmentView, erro, Snackbar.LENGTH_SHORT);
                    View view = snackbar.getView();
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    view.setLayoutParams(params);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }


    private void SalvarDadosUsuario() {

        String user = registerName.getText().toString();
        String email = registerEmail.getText().toString();

        Map<String, Object> usuarios = new HashMap<>();
        usuarios.put("Nome", user);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReferencia = db.collection("Usuarios").document(usuarioID);
        documentReferencia.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("db", "Sucessso ao salvar os dados");
                        SalvarEmailUsuario(email);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db_error", "Erro ao salvar os dados" + e.toString());
                    }
                });

    }

    private void SalvarEmailUsuario(String email){

        Map<String, Object> usuarioEmail = new HashMap<>();
        usuarioEmail.put("Email", email);
        usuarioEmail.put("Tipo", "");

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("Usuarios")
                .document(usuarioID)
                .update(usuarioEmail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("db", "Sucessso ao salvar os dados");
                    }
                });
    }

    private void ReiniciarLogin(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

}