package br.edu.ifma.icmbiodelta.Firebase;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import br.edu.ifma.icmbiodelta.Models.NewsData;

public class PostDB {
    private String PublicacaoID;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String UserID;
    private View view;
    private String msg;

    public PostDB(View view,String msg){
        this.view = view;
        this.msg = msg;
    }

    public void Post(NewsData newsData){
        UserID = FirebaseAuth
                .getInstance()
                .getCurrentUser()
                .getUid();
        String Publicador = UserID;
        Map<String, Object> postagem = new HashMap<>();
        postagem.put("Titulo", newsData.getTitle());
        postagem.put("Descricao", newsData.getDescription());
        postagem.put("Data", newsData.getDate());
        postagem.put("Imagem", String.valueOf(newsData.getImage()));
        postagem.put("Publicador", Publicador);

        db.collection("Publicacoes").add(postagem)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
                        View view = snackbar.getView();
                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                        params.gravity = Gravity.TOP;
                        view.setLayoutParams(params);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();
                    }
                });
    }
}
