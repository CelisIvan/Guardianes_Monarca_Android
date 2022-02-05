package com.example.guardianesmonarca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class Settings2Activity : AppCompatActivity() {
    var mfirebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()
    var db : FirebaseFirestore? = FirebaseFirestore.getInstance()
    val user = mfirebaseAuth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings2)
        var other = findViewById<Button>(R.id.other_settings) as Button
        var newnamesf = findViewById<EditText>(R.id.newnames) as EditText
        var  newlastf = findViewById<EditText>(R.id.newlastnames)as EditText
        var newmailf = findViewById<EditText>(R.id.newemail)as EditText

        other.setOnClickListener{
            val nombre : String = newnamesf.text.toString()
            val newlast : String = newlastf.text.toString()
            val newmailf : String = newmailf.text.toString()

            db?.collection("usuarios")?.document(user?.uid.toString())
        }
    }

}
