package com.example.guardianesmonarca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SettingsFragment extends Fragment {

    EditText newName, newEmail,newLastnames;
    Button other;
    ImageView registercompany;
    FirebaseFirestore db;
    FirebaseAuth mFirebaseAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment,container,false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newName = (EditText)getView().findViewById(R.id.newnames);
        newLastnames = (EditText)getView().findViewById(R.id.newlastnames);
        newEmail = (EditText)getView().findViewById(R.id.newemail);
        other =(Button)getView().findViewById(R.id.other_settings);
        db = FirebaseFirestore.getInstance();

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        String user_uid = user.getUid();

        registercompany = (ImageView)getView().findViewById(R.id.register_company_code);
        registercompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String up_name = newName.getText().toString().trim();
                String up_lastnames = newLastnames.getText().toString().trim();
                String up_mail = newEmail.getText().toString().trim();

                DocumentReference updateuser= db.collection("usuarios").document(user_uid);
                updateuser
                        .update(
                                "nombres",up_name,
                                "apellidos",up_lastnames,
                                "correo",up_mail

                        ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(),"Se actualizaron los datos",Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }
}
