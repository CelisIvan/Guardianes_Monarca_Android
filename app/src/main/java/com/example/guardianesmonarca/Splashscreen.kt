package com.example.guardianesmonarca

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class Splashscreen : AppCompatActivity() {

    private var SPLAH_DURATION:Long = 1000
    private var context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)

        var handler: Handler = Handler()

        handler.postDelayed({
            var intent: Intent = Intent(context,LoginActivity::class.java)

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            finish()
            context.startActivity(intent)
        },SPLAH_DURATION)

    }
}
