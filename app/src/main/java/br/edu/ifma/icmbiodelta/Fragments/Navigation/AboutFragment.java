package br.edu.ifma.icmbiodelta.Fragments.Navigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.ifma.icmbiodelta.R;

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container
            , Bundle savedInstanceState) {
        View aboutFragmentView = inflater
                .inflate(R.layout.fragment_about
                        , container
                        , false);
        // Inflate the layout for this fragment
        return aboutFragmentView;
    }
}