package br.edu.ifma.icmbiodelta.Fragments.Navigation;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifma.icmbiodelta.Adapters.NewsAdapter;
import br.edu.ifma.icmbiodelta.Firebase.FeedListDB;
import br.edu.ifma.icmbiodelta.Helpers.FeedListListener;
import br.edu.ifma.icmbiodelta.Models.NewsData;
import br.edu.ifma.icmbiodelta.R;

public class HomeFragment extends Fragment implements FeedListListener, ViewTreeObserver.OnScrollChangedListener {
    private RecyclerView recyclerViewHome;
    private List<NewsData> newsList = new ArrayList<>();
    private FeedListDB feedListDB;
    private NestedScrollView scrollView;


    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container
            , Bundle savedInstanceState) {
        View homeFragmentView = inflater
                .inflate(R.layout.fragment_home
                        , container
                        , false);
        getActivity()
                .getWindow()
                .setStatusBarColor(ContextCompat
                        .getColor(getContext(), R.color.light_gray));

        recyclerViewHome = homeFragmentView
                .findViewById(R.id.recyclerViewHome);
        RecyclerView.LayoutManager homeLayoutManager = new
                LinearLayoutManager(getContext());
        recyclerViewHome.setLayoutManager(homeLayoutManager);

        scrollView = homeFragmentView
                .findViewById(R.id.nestedscrollviewhome);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(this);
        feedListDB = new FeedListDB(this);
        return homeFragmentView;
    }

    @Override
    public void getStartPosts(ArrayList<NewsData> newsData) {
        newsList.clear();
        newsList.addAll(newsData);
        NewsAdapter newsAdapter = new NewsAdapter(newsList);
        recyclerViewHome.setAdapter(newsAdapter);
    }

    @Override
    public void getNewPosts(ArrayList<NewsData> newsData) {
        newsList.addAll(newsData);
        NewsAdapter newsAdapter = new NewsAdapter(newsList);
        recyclerViewHome.setAdapter(newsAdapter);
    }

    @Override
    public void onScrollChanged(){
        View view = scrollView.getChildAt(scrollView.getChildCount() - 1);
        int bottomDetector = view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY());
        if(bottomDetector == 0 ){
            feedListDB.getPublications(this, 1);
            Log.i("icmbio-teste", "mandou a primeira de novo");
        }
    }

}