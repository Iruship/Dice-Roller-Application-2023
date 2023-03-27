package com.example.dicegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
//w18674138
//Irushi Perera

class SetTargetScore : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_target_score)
        val targetScoreeditText = findViewById<EditText>(R.id.target_score)
        val setButton = findViewById<Button>(R.id.set)

        // Set an OnClickListener to the "Set" button
        setButton.setOnClickListener(){
            val values = targetScoreeditText.text.toString().toInt()
            // Create an Intent to pass the target score value to the NewGame activity
            val intent = Intent(this@SetTargetScore, NewGame::class.java)
            intent.putExtra("INTIEGR_VALUES", values)
            startActivity(intent)
        }

    }
}