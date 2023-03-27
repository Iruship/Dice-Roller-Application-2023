package com.example.dicegame
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
//w18674138
//Irushi Perera

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val aboutButton = findViewById<Button>(R.id.about_button)
        aboutButton.setOnClickListener {
            val message =
                "Student ID: w1867413\nName: Irushi Perera\n\nI confirm that I understand what plagiarism is and have read and understood the section on Assessment Offences in the Essential Information for Students. The work that I have submitted is entirely my own. Any work from other authors is duly referenced and acknowledged."
            val builder = AlertDialog.Builder(this)
            builder.setMessage(message)
                .setTitle("About")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            val dialog = builder.create()
            dialog.show()
        }

    }
    //Opens the NewGame activity
    fun openNewGame(view: View){
        val newGameButton = findViewById<Button>(R.id.new_game)
        newGameButton.setOnClickListener() {
            val gameScreen = Intent(this, NewGame::class.java)
            startActivity(gameScreen)
        }
    }
     // Opens the SetTargetScore activity
    fun openSetTarget (view: View){
        val setATargetBtn = findViewById<Button>(R.id.set_score)
        setATargetBtn.setOnClickListener() {
            val gameScreen = Intent(this, SetTargetScore::class.java)
            startActivity(gameScreen)
        }
    }
}