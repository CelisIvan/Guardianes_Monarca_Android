package com.example.guardianesmonarca;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountFragment extends Fragment {
    Button signout;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    static final String TAG = HomeActivity.class.getName();
    TextView user_name,user_name2,current_points;
    EditText event_code,package_code;
    ImageView button_event,button_package;
    int user_Points;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.account_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signout = (Button)view.findViewById(R.id.close_session);
        user_name = (TextView)view.findViewById(R.id.us_name);
        user_name2 = (TextView)view.findViewById(R.id.name1);
        event_code = (EditText)view.findViewById(R.id.event_code);
        package_code = (EditText)view.findViewById(R.id.package_code);
        button_event = (ImageView)view.findViewById(R.id.register_event_code);
        button_package = (ImageView)view.findViewById(R.id.register_package_code);
        current_points = (TextView) view.findViewById(R.id.pointsrn);
        FirebaseUser user = mAuth.getCurrentUser();
        DocumentReference docRef = db.collection("usuarios").document(user.getUid());
        String user_uid= user.getUid();
        int puntos_show= getUserPoints(user_uid);
        String show_points= Integer.toString(puntos_show);
        current_points.setText(show_points);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot docu = task.getResult();
                    if (docu != null) {
                        Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                        String name = docu.getString("nombre");
                        int points = docu.getLong("puntos").intValue();
                        Log.d(TAG, "Nombre del wey: " + name);
                        user_name.setText(name);
                        user_name2.setText(name);

                    } else {
                        Log.d(TAG, "Nostakrnal");
                    }
                } else {
                    Log.d(TAG, "Error con: ", task.getException());
                }
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToLogin= new Intent (getContext(),LoginActivity.class);
                startActivity(intToLogin);
            }
        });
        button_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event=event_code.getText().toString().trim();
                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                DocumentReference docIdRef = rootRef.collection("yourCollection").document(event);
                docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "Document exists!");
                                String user_id= user.getUid();
                                int puntos_a = getUserPoints(user.getUid());
                                updatePoints(user_id,puntos_a,200);
                            } else {
                                Log.d(TAG, "Document does not exist!");
                            }
                        } else {
                            Log.d(TAG, "Failed with: ", task.getException());
                        }
                    }
                });
            }
        });
    }

    public int getUserPoints(String user_uid){
        DocumentReference docRef = db.collection("usuarios").document(user_uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot docu = task.getResult();
                    if (docu != null) {
                        Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                        int punts = docu.getLong("puntos").intValue();
                        user_Points= punts;
                    } else {
                        Log.d(TAG, "Nostakrnal");
                    }
                } else {
                    Log.d(TAG, "Error con: ", task.getException());
                }
            }
        });
        return user_Points;
    }
    public void updatePoints(String user_uid,int current_Points,int event_type){
        int new_points= current_Points+event_type;
        db.collection("usuarios").document(user_uid).update(
                "puntos",new_points
            ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(),"¡Genial! Se te han sumado 500 puntos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
