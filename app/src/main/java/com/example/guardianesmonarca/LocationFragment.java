package com.example.guardianesmonarca;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LocationFragment extends Fragment {
    ImageView location;
    FirebaseFirestore db;
    static final String TAG = HomeActivity.class.getName();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.location_fragment,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        location = (ImageView)getView().findViewById(R.id.button_location);
        db= FirebaseFirestore.getInstance();
        requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION},123);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPStracker g = new GPStracker(getContext());
                Location l = g.getLocation();
                Calendar calendar= Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
                if(l != null){
                    double lat = l.getLatitude();
                    double lon = l.getLongitude();
                    Map<String,Object> loc = new HashMap<>();
                    loc.put("latitud",lat);
                    loc.put("longitud",lon);
                    loc.put("fecha",calendar.getTime());
                    db.collection("localizacion")
                            .add(loc)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getContext(),"Avistamiento registrado.",Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(),"Problema con el servidor. Intente más tarde",Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "No se pudo men: ", e);
                                }
                            });
                }

            }
        });
    }
}
