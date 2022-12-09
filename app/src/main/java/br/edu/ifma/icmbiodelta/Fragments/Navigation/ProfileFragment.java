package br.edu.ifma.icmbiodelta.Fragments.Navigation;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.edu.ifma.icmbiodelta.Firebase.CurrentUserDB;
import br.edu.ifma.icmbiodelta.Helpers.CurrentUserListener;
import br.edu.ifma.icmbiodelta.Models.UserData;
import br.edu.ifma.icmbiodelta.R;

public class ProfileFragment extends Fragment implements CurrentUserListener {
    private CurrentUserDB currentUserDB;
    private TextView username;

    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container
            , Bundle savedInstanceState) {
        View profileFragmentView = inflater
                .inflate(R.layout.fragment_profile
                        , container
                        , false);

        getActivity()
                .getWindow()
                .setStatusBarColor(ContextCompat
                        .getColor(getContext(), R.color.light_gray));
        username = profileFragmentView
                .findViewById(R.id.username);

        currentUserDB = new CurrentUserDB(this);
        // Inflate the layout for this fragment
        return profileFragmentView;
    }

    @Override
    public void setUser(UserData user) {
        username.setText(user.getNome());
    }
}