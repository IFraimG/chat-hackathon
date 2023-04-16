package com.example.todolisthwhackathon.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todolisthwhackathon.MainActivity;
import com.example.todolisthwhackathon.R;
import com.example.todolisthwhackathon.data.SharedPreferences.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Fragment_Profil extends Fragment {

    private EditText editName;
    private EditText editPassword;

    private ImageButton chek;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("Users").document(MainActivity.UserId);

    public Fragment_Profil() {
        // required empty public constructor.
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Вызов findViewById после того, как фрагмент был добавлен в представление
        editName = (EditText) view.findViewById(R.id.editName);
        editPassword = (EditText) view.findViewById(R.id.editPassword);
        editName.setText(MainActivity.NameForProfil);
        editPassword.setText(MainActivity.PasswordForProfil);

        chek = (ImageButton) view.findViewById(R.id.chek);

        chek.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                docRef.update("userName", editName.getText().toString(),
                                "password", editPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Данные пользователя успешко обновлены", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "при обновлении данных возникла ошибка", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

}
