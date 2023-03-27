package com.example.dicegame

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlin.random.Random

class NewGame : AppCompatActivity() {
    //Dice Id's of human and computer
    private val HumanSetOfDice = arrayOf(R.id.dice1, R.id.dice2, R.id.dice3, R.id.dice4, R.id.dice5)
    private val computerSetOfDice = arrayOf(R.id.computer_dice1, R.id.computer_dice2, R.id.computer_dice3, R.id.computer_dice4, R.id.computer_dice5)
    private var computerPoints = 0
    private var playerScore = 0
    private var diceOfComp = arrayOfNulls<Int>(5)
    private var diceOfHuman = arrayOfNulls<Int>(5)
    private var selectedHumanDice = mutableListOf<Int>()
    private var compDiceSelected = mutableListOf<Int>()
    private var diceThrown = false
    private var select = false
    private var rollTracker = 0
    var computerDesicionRoll=false
    var winsByUser=0
    var winsByComputer =0
    var defaultMarks = 101
//w18674138
//Irushi Perera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        //Initializing the throw button by finding it by it's id
        val throwBtn = findViewById<Button>(R.id.throwBtn)

        //Initialize the 'totalRollsNo' variable by finding the text view with the id 'rerollText'
        val rollsTotal = findViewById<TextView>(R.id.rerollText)
        throwBtn.setOnClickListener {
            if (rollTracker < 3) {
                diceThrown = true  //Setting the 'diceThrown' boolean variable to true
                throwDice()        //Calling the function to throw the dice
                rollTracker++      //Increasing the 'rollTracker' counter
                rollsTotal.setText("No Of Total Rolls: $rollTracker")

            } else if (rollTracker == 3) {
                aggregateScores()
                throwDice()
                select = false
                rollTracker = 0
                rollsTotal.setText("No Of Total Rolls: $rollTracker")
            }
        }

        //Action listener of the scoreBtn
        val scoreButton = findViewById<Button>(R.id.scoreBtn)
        scoreButton.setOnClickListener {
            if (diceThrown) {
                aggregateScores()
                var compCounter=0   //Variable for a counter
                if (!computerDesicionRoll){
                    compCounter=rollTracker
                    Log.e("Decision on rolling",(rollTracker.toString()))
                // rollTracker=rollTracker+1
                }else{
                    compCounter=3-rollTracker
                }
                diceThrown = false
            }
        }

        //Set click listeners for each dice in 'HumanSetOfDice'
        for (id in HumanSetOfDice) {
            var selectedDice=findViewById<ImageView>(id)
            selectedDice.setOnClickListener { // Set an onClickListener for the current dice
                select = true
                val diceNumber = getDiceValue(id)
                // Add the dice number to the 'selectedHumanDice' list if it isn't already there
                selectedHumanDice.plus(diceNumber)
                if (!selectedHumanDice.contains(diceNumber)) {
                    selectedHumanDice.add(diceNumber)

                    // Highlighting by adding a colored border
                    val strokeColor = ContextCompat.getColor(this, R.color.purple_200)
                    val strokeWidth = 10
                    val shape = GradientDrawable()
                    shape.shape = GradientDrawable.RECTANGLE
                    shape.setStroke(strokeColor, strokeWidth)
                    selectedDice.background = shape

                }
            }
        }
    }

    /**
     * This function aggregates the score of the computer and the human player
     * This updates the score values and display them in the relevant text views.
     * Determines and displays the winner of the  dice game
     */

    private fun aggregateScores() {
        // Calculating the sum of non-null elements in the diceOfComp list
        var computerDiceSum = 0
        for (die in diceOfComp) {
            if (die != null) {
                computerDiceSum += die
            }
        }

        // Update the computer score with the sum of the non-null elements in the diceOfComp list
        computerPoints += computerDiceSum

        // Calculating the sum of non-null elements in the diceOfHuman list
        var humanDieAddition = 0
        for (die in diceOfHuman) {
            if (die != null) {
                humanDieAddition += die
            }
        }

        // Updating the human player's score with the sum of the non-null elements in the diceOfHuman list
        playerScore += humanDieAddition

        // Update the human player's score TextView to display the updated score
        val playerScoreTextView = findViewById<TextView>(R.id.humanScores)
        playerScoreTextView.text = "H : " + playerScore.toString()

        // Update the computer's score TextView to display the updated score
        val computerPointsTextView = findViewById<TextView>(R.id.computerScores)
        computerPointsTextView.text = "C : " + computerPoints.toString()

        // Calling the displayWinner()  function to display the winner of the game
        displayWinner()
    }

    private fun throwDice() {
        val randomVals = Random
        // Generate a random boolean value for the computer player's decision to roll the dice
        computerDesicionRoll=Random.nextBoolean()

        // Loop through each of the five dice for the human player
        for (i in 0 until 5) {

            // random die values for the human player is generated here
            val diceValueH = randomVals.nextInt(6) + 1
            // Get the resource id for the image corresponding to the human player's dice roll
            val imageIdHuman = getDicefaceImageId(diceValueH)

            // Check if the current dice is selected by the human player to be held
            if (select) {
                for (k in selectedHumanDice) {
                    if (!selectedHumanDice.contains(i + 1)) {
                        if (i !== k - 1) {
                            // Set the image of the current dice to the image corresponding to the dice roll
                            findViewById<ImageView>(HumanSetOfDice[i]).setImageResource(imageIdHuman)
                            // Store the current dice roll in the array that stores the human player's dice
                            diceOfHuman[i] = diceValueH

                        }
                    }
                }

            } else {
                // Set the image of the current dice to the image corresponding to the dice roll
                findViewById<ImageView>(HumanSetOfDice[i]).setImageResource(imageIdHuman)
                // Store the current dice roll in the array that stores the human player's dices
                diceOfHuman[i] = diceValueH
            }

            //code executed if the computer player wants to roll the dices
            if (computerDesicionRoll){
                computerDesicionRoll=true // Setting the computer player's decision to roll the dice to true
                generateComputerDiceValues(i) //Calling the function that handles the computer player's dice rolls
            }
        }

        // Reset the value of 'select' to false, to clear the selected dices on the next iteration
        select = false
        selectedHumanDice.clear() //Clears the list of selected dice for the human

    }
    /**
     * Returns the image resource ID for the specified dice face value.
     * @throws IllegalArgumentException if the specified dice value is not between 1 and 6 ( is inclusive).
     */

    private fun getDicefaceImageId(valueOfDice: Int): Int {
        // List of image resource IDs for each dice face
        val diceImgIds = listOf(R.drawable.dice_one_c,
            R.drawable.dice_two_c,
            R.drawable.dice_three_c,
            R.drawable.dice_four_c,
            R.drawable.dice_five_c,
            R.drawable.dice_six_c
        )
        // Check if the specified dice value is valid (between 1 and 6 inclusive)
        if (valueOfDice < 1 || valueOfDice > 6) {
            // If the specified dice value is invalid, throw an exception
            throw IllegalArgumentException("Die values are invalid: $valueOfDice")
        }
        // Returns the image resource ID for the specified die face value
        return diceImgIds[valueOfDice - 1]
    }

    /**
     * Returns the dice value for the specified image resource ID.
     * @return The dice value (1-5) for the specified image resource ID.
     * @throws IllegalArgumentException if the specified ID is not a valid dice image resource ID.
     */

    fun getDiceValue(id: Int): Int {
        // A map that maps image resource IDs to dice values (1-5)
        val imgIdMap = mapOf(
            R.id.dice1 to 1,
            R.id.dice2 to 2,
            R.id.dice3 to 3,
            R.id.dice4 to 4,
            R.id.dice5 to 5
        )
        // Returns the dice value associated with the specified image resource ID,
        // Or it will throw an exception if the img ID is invalid
        return imgIdMap[id] ?: throw IllegalArgumentException("Dice Id is invalid!")
    }

    //dices generated by computer player
    fun generateComputerDiceValues(dice:Int){
        // Generate a random dice roll for the computer player

        val randomVals = Random //Creating a new Random object
        val compDiceNums = randomVals.nextInt(6) + 1
        val compDiceImgIds = getDicefaceImageId(compDiceNums) // Gets the image resource ID for the corresponding dice value
        findViewById<ImageView>(computerSetOfDice[dice]).setImageResource(compDiceImgIds)

        // Pushing the values to the computer's dice storing array
        diceOfComp[dice] = compDiceNums
    }
    fun displayWinner(){
        // Get the integer value of the score needed to win the game
        intent?.let {
            val values = it.getIntExtra("INTIEGR_VALUES", defaultMarks)
            defaultMarks = values
        }
        //If the computer wins

        if (computerPoints>defaultMarks && computerPoints>playerScore){
            // Display the "you lose" message dialog
            val messageDisplay = layoutInflater.inflate(R.layout.losemsgdisplay,null)
            val ComputerWinMsg = Dialog(this)
            ComputerWinMsg.setContentView(messageDisplay)
            ComputerWinMsg.setCancelable(true)
            ComputerWinMsg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            ComputerWinMsg.show()

            // Set up the "OK" button to dismiss the dialog and update the scores
            val OKBtn = messageDisplay.findViewById<Button>(R.id.irushiOKbtn)
            OKBtn.setOnClickListener {
                ComputerWinMsg.dismiss()
                var compwinsBoard =findViewById<TextView>(R.id.computerScore)
                winsByComputer += 1
                compwinsBoard.text ="Computer wins : "+winsByComputer.toString()
                computerPoints=0
                playerScore=0
                findViewById<TextView>(R.id.computerScores).setText( "C : "+computerPoints.toString())
                findViewById<TextView>(R.id.humanScoreBoard).setText("H : "+playerScore.toString())

            }
            // Set up the "play again" button to return to the main menu
            val backButton = messageDisplay.findViewById<TextView>(R.id.playAgain)
            backButton.setOnClickListener(){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
        }

        // If the human wins

        else if(playerScore>defaultMarks && playerScore>computerPoints){

            // Display the "you win" message dialog

            val messageDisplay = layoutInflater.inflate(R.layout.winmsgdisplay,null)
            val WinMsg = Dialog(this)
            WinMsg.setContentView(messageDisplay)
            WinMsg.setCancelable(true)
            WinMsg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            WinMsg.show()
            // Setting up the "OK" button to dismiss the dialog and update the scores
            val oKButton = messageDisplay.findViewById<Button>(R.id.irushiOKbtn2)
            oKButton.setOnClickListener{
                WinMsg.dismiss()
                var humanWinsBoard =findViewById<TextView>(R.id.humanScoreBoard)
                winsByUser=winsByUser+1
                humanWinsBoard.text ="User wins  : "+winsByUser.toString()
                computerPoints=0
                playerScore=0
                findViewById<TextView>(R.id.computerScores).setText("C : "+computerPoints.toString())
                findViewById<TextView>(R.id.humanScores).setText("H : "+playerScore.toString())
            }
            // Setting up the "play again" button to return to the main menu
            val backButtonA = messageDisplay.findViewById<TextView>(R.id.playAgain2)
            backButtonA.setOnClickListener(){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }

        }
        //When the game is a tie
        else if (playerScore==computerPoints && playerScore>defaultMarks){
            // Display the "tie game" message dialog
            val messageDisplay = layoutInflater.inflate(R.layout.tiemsg,null)
            val WinMsg = Dialog(this)
            WinMsg.setContentView(messageDisplay)
            WinMsg.setCancelable(true)
            WinMsg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            WinMsg.show()
            // Set up the "OK" button to dismiss the dialog and make the hidden button visible
            val oKButtonC = messageDisplay.findViewById<Button>(R.id.okaybtn3)
            oKButtonC.setOnClickListener {
                WinMsg.dismiss()
                var invisiblebtn = findViewById<Button>(R.id.btn3)
                invisiblebtn.visibility =View.VISIBLE
                invisiblebtn.setOnClickListener(){
                    invisiblebtn.visibility =View.INVISIBLE
                }
            }

        }

    }
}
