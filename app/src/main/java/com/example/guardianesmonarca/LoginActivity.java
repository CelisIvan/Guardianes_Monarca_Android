package com.example.guardianesmonarca;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends AppCompatActivity {
    EditText emailId,pass,names,lastnames;
    Button buttonLogin,forgot;
    CardView signUp;
    FirebaseAuth mFirebaseAuth;
    SignInButton googlebutt;
    static final int GOOGLE_SIGN=123;
    GoogleSignInClient mGoogleSignInClient;


    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        googlebutt=(SignInButton)findViewById(R.id.google_btn);
        mFirebaseAuth=FirebaseAuth.getInstance();
        emailId=findViewById(R.id.email);
        pass=findViewById((R.id.password));
        signUp =findViewById(R.id.register_buttonl);
        forgot=findViewById(R.id.forgotpassbutton);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        GoogleSignInOptions googleSignInOptions= new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        buttonLogin = findViewById(R.id.login_button);

        googlebutt.setOnClickListener(v -> SignInGoogle());

        mAuthStateListener= new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser= mFirebaseAuth.getCurrentUser();
                if((mFirebaseUser!= null)){
                    Toast.makeText(LoginActivity.this,"Has iniciado sesion",Toast.LENGTH_LONG).show();
                    Intent i= new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"No se ha podido recuperar una sesión",Toast.LENGTH_LONG).show();
                }
            }
        };
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=emailId.getText().toString();
                String pwd=pass.getText().toString();

                if(mail.isEmpty()){
                    emailId.setError("Por favor introduce el correo electrónico");
                    emailId.requestFocus();
                }else if (pwd.isEmpty()){
                    pass.setError("Por favor introduce la contraseña");
                    pass.requestFocus();
                }
                else if(mail.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Por favor llena todos los campos",Toast.LENGTH_LONG).show();
                }
                else if(!((mail.isEmpty() && pwd.isEmpty()))){
                   mFirebaseAuth.signInWithEmailAndPassword(mail,pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(!(task.isSuccessful())){
                               Toast.makeText(LoginActivity.this,"Ha ocurrido un error con el inicio de sesión. Intente más tarde",Toast.LENGTH_LONG).show();
                           }else{
                               Intent iHome= new Intent(LoginActivity.this,HomeActivity.class);
                               startActivity(iHome);
                           }
                       }
                   });
                }
                else{
                    Toast.makeText(LoginActivity.this,"Ha ocurrido un error con el servidor. Intente más tarde",Toast.LENGTH_SHORT).show();
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegister= new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(toRegister);
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toForgot = new Intent(LoginActivity.this,ForgotPass.class);
                startActivity(toForgot);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    void SignInGoogle(){
        Intent signIntent= mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent,GOOGLE_SIGN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GOOGLE_SIGN){
            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);

            try{
                GoogleSignInAccount account=task.getResult(ApiException.class);
                if(account!=null){
                    firebaseAuthWithGoogle(account);
                }
            }catch (ApiException e){
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG","firebaseAuthWithGoogle: "+ account.getId());

        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this,task ->{
        if(task.isSuccessful()){
            Log.d("TAG", "signin succes");
            FirebaseUser user= mFirebaseAuth.getCurrentUser();

        }else{
            Log.w("TAG", "singin failure", task.getException());
            Toast.makeText(this,"Fallo de inicio de sesión",Toast.LENGTH_SHORT).show();
        }

        });
    }
}
