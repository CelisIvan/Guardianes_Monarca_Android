package com.example.guardianesmonarca

import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

import java.util.Arrays
import java.util.HashMap

class RegisterActivity : AppCompatActivity() {

    var signIn: CardView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var mFirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        var emailId: EditText = findViewById(R.id.email)
        var names: EditText = findViewById(R.id.names)
        var lastnames: EditText = findViewById(R.id.lastnames)
        var pass: EditText = findViewById(R.id.pwd_register)
        var passconfirm: EditText = findViewById(R.id.pwdconfirm_register)

        var back: TextView = findViewById(R.id.back_btn)
        var buttonRegister: Button = findViewById(R.id.register_button)
        buttonRegister.setOnClickListener {
            val mail = emailId.text.toString()
            val name = names.text.toString()
            val lnames = lastnames.text.toString()
            val pwd = pass.text.toString()
            val pwdconfirm = passconfirm.text.toString()
            if (mail.isEmpty()) {
                emailId.error = "Por favor introduce el correo electrónico"
                emailId.requestFocus()
            } else if (name.isEmpty()) {
                names.error = "Por favor introduce los nombres"
                names.requestFocus()
            } else if (pwd.isEmpty()) {
                pass.error = "Por favor introduce la contraseña"
                pass.requestFocus()
            } else if (mail.isEmpty() && name.isEmpty() && pwd.isEmpty()) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Por favor llena todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!(mail.isEmpty() && name.isEmpty() && pwd.isEmpty())) {
                mFirebaseAuth.createUserWithEmailAndPassword(mail, pwd)
                    .addOnCompleteListener(this@RegisterActivity) { task ->
                        if (!task.isSuccessful) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Ha ocurrido un error 1. Intente más tarde",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val roles1 = arrayOf(1)
                            val roles = Arrays.asList(*roles1)
                            val user = mFirebaseAuth.currentUser
                            val uid = user!!.uid
                            val new_user = HashMap<String, Any>()
                            new_user["nombre"] = name
                            new_user["apellidos"] = lnames
                            new_user["email"] = mail
                            new_user["puntos"] = 100
                            new_user["roles"] = roles
                            db.collection("usuarios").document(uid)
                                .set(new_user)
                                .addOnSuccessListener {
                                    Log.d(
                                        TAG,
                                        "DocumentSnapshot successfully written!"
                                    )
                                }
                                .addOnFailureListener { e ->
                                    Log.w(
                                        TAG,
                                        "Error writing document",
                                        e
                                    )
                                }
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        }
                    }
            } else {
                Toast.makeText(
                    this@RegisterActivity,
                    "Ha ocurrido un error 2. Intente más tarde",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        back.setOnClickListener {
            val logi = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(logi)
        }
    }

    companion object {
        private val TAG = RegisterActivity::class.java.name
    }

}
