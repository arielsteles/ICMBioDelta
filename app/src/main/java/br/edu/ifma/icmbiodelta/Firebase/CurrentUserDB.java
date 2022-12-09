package br.edu.ifma.icmbiodelta.Firebase;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.User;

import br.edu.ifma.icmbiodelta.Helpers.CurrentUserListener;
import br.edu.ifma.icmbiodelta.Models.UserData;

public class CurrentUserDB {
    private String UserName, UserEmail, UserTipo;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String UserID;
    //private CurrentUserListener currentUserListener;

    public CurrentUserDB(CurrentUserListener currentUserListener){
        //this.currentUserListener = currentUserListener;
        UserID = FirebaseAuth
                .getInstance()
                .getCurrentUser()
                .getUid();
        DocumentReference documentReference = db
                .collection("Usuarios")
                .document(UserID);
        documentReference
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot
                            , @Nullable FirebaseFirestoreException error) {
                        if (documentSnapshot != null) {
                            UserData user = new UserData();
                            user.setNome(documentSnapshot.getString("Nome"));
                            user.setEmail(documentSnapshot.getString("Email"));
                            user.setTipo(documentSnapshot.getString("Tipo"));
                            currentUserListener.setUser(user);
                        }
                    }
                });
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public String getUserTipo() {
        return UserTipo;
    }
}
