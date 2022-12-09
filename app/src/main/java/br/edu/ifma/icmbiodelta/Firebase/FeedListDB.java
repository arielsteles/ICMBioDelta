package br.edu.ifma.icmbiodelta.Firebase;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import br.edu.ifma.icmbiodelta.Helpers.FeedListListener;
import br.edu.ifma.icmbiodelta.Models.NewsData;

public class FeedListDB {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public FeedListDB(FeedListListener feedListListener) {
        ArrayList arrayFeed = new ArrayList<>();
        db.collection("Publicacoes").orderBy("Data").limit(3)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            Log.e("Firestore error",error.getMessage());
                            return;
                        }
                        for(DocumentChange ds: value.getDocumentChanges()){
                            if(ds.getType() == DocumentChange.Type.ADDED){
                                NewsData newsData = new NewsData();
                                newsData.setDate(ds.getDocument().getString("Data"));
                                newsData.setDescription(ds.getDocument().getString("Descricao"));
                                newsData.setTitle(ds.getDocument().getString("Titulo"));
                                arrayFeed.add(newsData);
                                Log.i("icmbio", "Get de post");
                            }
                        }
                        feedListListener.getStartPosts(arrayFeed);
                    }
                });
    }

    //TODO - retornar sempre 3
    public void getPublications(FeedListListener feedListListener, int qtd) {
        ArrayList arrayFeed = new ArrayList<>();
        db.collection("Publicacoes").orderBy("Data").limit(qtd)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            Log.e("Firestore error",error.getMessage());
                            return;
                        }
                        for(DocumentChange ds: value.getDocumentChanges()){
                            if(ds.getType() == DocumentChange.Type.ADDED){
                                NewsData newsData = new NewsData();
                                newsData.setDate(ds.getDocument().getString("Data"));
                                newsData.setDescription(ds.getDocument().getString("Descricao"));
                                newsData.setTitle(ds.getDocument().getString("Titulo"));
                                arrayFeed.add(newsData);
                                Log.i("icmbio", "Get de post");
                            }
                        }
                        feedListListener.getNewPosts(arrayFeed);
                    }
                });
    }

}
