package br.edu.ifma.icmbiodelta.Fragments.Navigation;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.edu.ifma.icmbiodelta.R;

public class CreatorFragment extends Fragment {
    androidx.appcompat.widget.Toolbar toolbar;
    EditText editTextCreateTitle;
    EditText editTextMultiLineCreateNews;
    FloatingActionButton floatingActionButtonCreate;
    ImageView imageViewCreate;
    String editTextCreateTitleString;
    String editTextMultiLineCreateNewsString;

    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container
            , Bundle savedInstanceState) {
        View creatorFragmentView = inflater
                .inflate(R.layout.fragment_creator
                        , container
                        , false);
        // set status bar color
        getActivity()
                .getWindow()
                .setStatusBarColor(ContextCompat
                        .getColor(getContext(), R.color.darker_green));
        // set toolbar
        toolbar = creatorFragmentView.findViewById(R.id.toolbar);
        createToolbarListener();

        editTextCreateTitle = creatorFragmentView
                .findViewById(R.id.editTextCreateTitle);
        imageViewCreate = creatorFragmentView
                .findViewById(R.id.imageViewCreate);
        editTextMultiLineCreateNews = creatorFragmentView
                .findViewById(R.id.editTextMultiLineCreateNews);
        floatingActionButtonCreate = creatorFragmentView
                .findViewById(R.id.floatingActionButtonCreate);
        setFloatingActionButtonCreateListener();

        if (!checkPermission()) {
            requestPermission();
        }
        // Inflate the layout for this fragment
        return creatorFragmentView;
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity()
                        .getApplicationContext()
                , Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {

                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
                        Log.i("icmbio", "Permissão dada");
                    } else {
                        Log.i("icmbio", "Permissão negada");
                    }
                }
            }
    );

    public void createToolbarListener() {
        toolbar.setOnMenuItemClickListener((Toolbar.OnMenuItemClickListener) item -> {
            switch (item.getItemId()) {
                case R.id.toolbar_gallery:
                    imagePicker();
                    return true;
            }
            return false;
        });
    }

    public void setFloatingActionButtonCreateListener() {
        floatingActionButtonCreate
                .setOnClickListener(new View
                        .OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        send();
                    }
                });

    }

    public void send() {
        editTextCreateTitleString =
                editTextCreateTitle
                        .getText()
                        .toString()
                        .trim();

        editTextMultiLineCreateNewsString =
                editTextMultiLineCreateNews
                        .getText()
                        .toString()
                        .trim();
    }

    public void imagePicker() {
        Intent imagePicker = new Intent(Intent.ACTION_PICK);
        imagePicker.setType("image/*");
        activityResultLauncher.launch(imagePicker);
    }

    private ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Intent data = result.getData();
                                Uri imageUri = data.getData();
                                imageViewCreate.setImageURI(imageUri);

                            }
                        }
                    });


}