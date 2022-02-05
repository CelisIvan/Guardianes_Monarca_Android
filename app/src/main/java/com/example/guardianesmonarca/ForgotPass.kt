package com.example.guardianesmonarca

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class ForgotPass : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)
        var back: Button= findViewById(R.id.back_btn)
        var sendmail: Button= findViewById(R.id.send_mail)
        var mail: EditText = findViewById(R.id.mailtosend)
        var mFirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

        sendmail.setOnClickListener {
            val mailto = mail.text.toString().trim { it <= ' ' }
            mFirebaseAuth.sendPasswordResetEmail(mailto).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@ForgotPass, "Correo enviado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ForgotPass, task.exception!!.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        back.setOnClickListener {
            val intent = Intent(this@ForgotPass, LoginActivity::class.java)
            startActivity(intent)
        }


    }


}
