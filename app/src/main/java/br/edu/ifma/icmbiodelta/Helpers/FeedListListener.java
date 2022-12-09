package br.edu.ifma.icmbiodelta.Helpers;

import java.util.ArrayList;

import br.edu.ifma.icmbiodelta.Models.NewsData;

public interface FeedListListener {

    public void getStartPosts(ArrayList<NewsData> newsData);
    public void getNewPosts(ArrayList<NewsData> newsData);
}
